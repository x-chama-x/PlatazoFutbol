package com.mycompany.platazoPlato.controlador;

import com.mycompany.platazoPlato.modelo.Usuario;
import com.mycompany.platazoPlato.modelo.Partido;
import com.mycompany.platazoPlato.modelo.db.PartidoDAO;
import com.mycompany.platazoPlato.modelo.db.UsuarioDAO;
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

public class ProximosPartidosServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;
    private PartidoDAO partidoDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        usuarioDAO = new UsuarioDAO();
        partidoDAO = new PartidoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los datos de los usuarios y partidos
        ArrayList<Usuario> usuarios = usuarioDAO.getUsuarios();
        ArrayList<Partido> partidos = partidoDAO.getPartidos();

        // Crear un mapa que relacione usuarioId con el nombre del equipo
        Map<Integer, String> equipoNombres = crearMapaEquipoNombres(usuarios);

        // Filtrar y agrupar los partidos no jugados por tipo de evento
        Map<String, List<Partido>> proximosPartidosPorEvento = filtrarProximosPartidosPorEvento(partidos);

        // Calcular la probabilidad de victoria
        Map<Integer, Map<String, Integer>> probabilidades = calcularProbabilidades(usuarios, proximosPartidosPorEvento);

        // Pasar los datos a la página JSP
        request.setAttribute("proximosPartidosPorEvento", proximosPartidosPorEvento);
        request.setAttribute("equipoNombres", equipoNombres);
        request.setAttribute("probabilidades", probabilidades);

        // Redirigir a la página proximosPartidos.jsp
        request.getRequestDispatcher("/WEB-INF/jsp/proximosPartidos.jsp").forward(request, response);
    }

    // Crear un mapa que relacione usuarioId con el nombre del equipo
    private Map<Integer, String> crearMapaEquipoNombres(ArrayList<Usuario> usuarios) {
        Map<Integer, String> equipoNombres = new HashMap<>();
        for (Usuario usuario : usuarios) {
            equipoNombres.put(usuario.getUsuarioId(), usuario.getNombre());
        }
        return equipoNombres;
    }

    // Filtrar y agrupar los partidos no jugados por tipo de evento
    private Map<String, List<Partido>> filtrarProximosPartidosPorEvento(ArrayList<Partido> partidos) {
        Map<String, List<Partido>> proximosPartidosPorEvento = new HashMap<>();
        for (Partido partido : partidos) {
            if (!"finalizado".equals(partido.getEstado())) {
                proximosPartidosPorEvento
                        .computeIfAbsent(partido.getTipoEvento(), k -> new ArrayList<>())
                        .add(partido);
            }
        }
        return proximosPartidosPorEvento;
    }

    // Calcular la probabilidad de victoria para cada partido
    private Map<Integer, Map<String, Integer>> calcularProbabilidades(ArrayList<Usuario> usuarios, Map<String, List<Partido>> proximosPartidosPorEvento) {
        Map<Integer, Map<String, Integer>> probabilidades = new HashMap<>();
        for (List<Partido> proximosPartidos : proximosPartidosPorEvento.values()) {
            for (Partido partido : proximosPartidos) {
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