package com.mycompany.promediosPlato.controlador;

import com.mycompany.promediosPlato.modelo.*;
import com.mycompany.promediosPlato.modelo.db.ClasificacionDAO;
import com.mycompany.promediosPlato.modelo.db.GolesPorTiempoDAO;
import com.mycompany.promediosPlato.modelo.db.PartidoDAO;
import com.mycompany.promediosPlato.modelo.db.UsuarioDAO;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LigaServlet extends HttpServlet {

    private ClasificacionDAO clasificacionDAO;
    private PartidoDAO partidoDAO;
    private UsuarioDAO usuarioDAO;
    private GolesPorTiempoDAO golesPorTiempoDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        clasificacionDAO = new ClasificacionDAO();
        partidoDAO = new PartidoDAO();
        usuarioDAO = new UsuarioDAO();
        golesPorTiempoDAO = new GolesPorTiempoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Clasificacion> clasificaciones = clasificacionDAO.getClasificaciones();
        ArrayList<Partido> partidos = partidoDAO.getPartidos();
        ArrayList<Usuario> usuarios = usuarioDAO.getUsuarios();
        ArrayList<GolesPorTiempo> golesPorTiempo = golesPorTiempoDAO.getGolesPorTiempo();

        // Crear un mapa que relacione usuarioId con el nombre del usuario
        Map<Integer, String> usuarioNombres = new HashMap<>();
        for (Usuario usuario : usuarios) {
            usuarioNombres.put(usuario.getUsuarioId(), usuario.getNombre());
        }

        // Crear un mapa que relacione partidoId con los goles por tiempo
        Map<Integer, List<GolesPorTiempo>> golesPorPartido = new HashMap<>();
        for (GolesPorTiempo gol : golesPorTiempo) {
            golesPorPartido.computeIfAbsent(gol.getPartidoId(), k -> new ArrayList<>()).add(gol);
        }

        // Calcular la cantidad de jornadas
        int maxJornada = partidos.stream()
                .filter(p -> p.getJornada() != null)
                .mapToInt(Partido::getJornada)
                .max()
                .orElse(0);

        // Obtener la jornada seleccionada
        String jornadaParam = request.getParameter("jornada");
        int jornadaSeleccionada = jornadaParam != null ? Integer.parseInt(jornadaParam) : 1;

        // Filtrar los partidos por la jornada seleccionada
        List<Partido> partidosFiltrados = partidos.stream()
                .filter(p -> p.getJornada() != null && p.getJornada() == jornadaSeleccionada)
                .collect(Collectors.toList());

        // Calcular la probabilidad de victoria
        Map<Integer, Map<String, Integer>> probabilidades = calcularProbabilidades(usuarios, partidosFiltrados);

        request.setAttribute("clasificaciones", clasificaciones);
        request.setAttribute("partidos", partidosFiltrados);
        request.setAttribute("usuarioNombres", usuarioNombres);
        request.setAttribute("golesPorPartido", golesPorPartido);
        request.setAttribute("maxJornada", maxJornada);
        request.setAttribute("jornadaSeleccionada", jornadaSeleccionada);
        request.setAttribute("probabilidades", probabilidades);

        request.getRequestDispatcher("/WEB-INF/jsp/paginaDeLiga.jsp").forward(request, response);
    }

    // Calcular la probabilidad de victoria para cada partido
    private Map<Integer, Map<String, Integer>> calcularProbabilidades(ArrayList<Usuario> usuarios, List<Partido> partidos) {
        Map<Integer, Map<String, Integer>> probabilidades = new HashMap<>();
        for (Partido partido : partidos) {
            if (!"finalizado".equals(partido.getEstado())) {
                Usuario local = getUsuarioById(usuarios, partido.getEquipoLocalId());
                Usuario visitante = getUsuarioById(usuarios, partido.getEquipoVisitanteId());

                Map<String, Integer> probabilidad = calcularProbabilidad(local, visitante);
                probabilidades.put(partido.getPartidoId(), probabilidad);
            }
        }
        return probabilidades;
    }

    // Calcular la probabilidad de victoria, empate y derrota entre dos equipos
    private Map<String, Integer> calcularProbabilidad(Usuario local, Usuario visitante) {
        double probLocal = (local.getNivel() * (double) local.getVictorias() / local.getPartidosJugados()) /
                ((local.getNivel() * (double) local.getVictorias() / local.getPartidosJugados()) +
                        (visitante.getNivel() * (double) visitante.getVictorias() / visitante.getPartidosJugados()));

        double probVisitante = (visitante.getNivel() * (double) visitante.getVictorias() / visitante.getPartidosJugados()) /
                ((local.getNivel() * (double) local.getVictorias() / local.getPartidosJugados()) +
                        (visitante.getNivel() * (double) visitante.getVictorias() / visitante.getPartidosJugados()));

        double probEmpate = 2 * probLocal * probVisitante / (probLocal + probVisitante);

        // Limitar la probabilidad de empate a un máximo de 40%
        if (probEmpate > 0.4) {
            probEmpate = 0.4;
        }

        // Redistribuir el resto de la probabilidad entre los jugadores
        double totalProb = probLocal + probVisitante;
        probLocal = probLocal / totalProb * (1 - probEmpate);
        probVisitante = probVisitante / totalProb * (1 - probEmpate);

        // Normalizar las probabilidades para asegurar que sumen 100%
        int probLocalInt = (int) (probLocal * 100);
        int probEmpateInt = (int) (probEmpate * 100);
        int probVisitanteInt = 100 - probLocalInt - probEmpateInt;

        Map<String, Integer> probabilidad = new HashMap<>();
        probabilidad.put("local", probLocalInt);
        probabilidad.put("empate", probEmpateInt);
        probabilidad.put("visitante", probVisitanteInt);
        return probabilidad;
    }

    // Obtener un usuario por su ID
    private Usuario getUsuarioById(ArrayList<Usuario> usuarios, int usuarioId) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsuarioId() == usuarioId) {
                return usuario;
            }
        }
        return null;
    }
}