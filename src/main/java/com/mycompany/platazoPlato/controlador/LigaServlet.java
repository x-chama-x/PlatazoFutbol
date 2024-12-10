package com.mycompany.platazoPlato.controlador;

import com.mycompany.platazoPlato.modelo.GolesPorTiempo;
import com.mycompany.platazoPlato.modelo.Usuario;
import com.mycompany.platazoPlato.modelo.Partido;
import com.mycompany.platazoPlato.modelo.Clasificacion;
import com.mycompany.platazoPlato.modelo.db.ClasificacionDAO;
import com.mycompany.platazoPlato.modelo.db.GolesPorTiempoDAO;
import com.mycompany.platazoPlato.modelo.db.PartidoDAO;
import com.mycompany.platazoPlato.modelo.db.UsuarioDAO;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
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

        // Verificar si la jornada seleccionada es válida
        if (jornadaSeleccionada < 1 || jornadaSeleccionada > maxJornada) {
            response.sendRedirect(request.getContextPath() + "/liga");
            return;
        }

        // Filtrar los partidos por la jornada seleccionada
        List<Partido> partidosFiltrados = partidos.stream()
                .filter(p -> p.getJornada() != null && p.getJornada() == jornadaSeleccionada)
                .collect(Collectors.toList());

        // Calcular la probabilidad de victoria
        Map<Integer, Map<String, Integer>> probabilidades = calcularProbabilidades(usuarios, partidosFiltrados);

        // Ordenar las clasificaciones por puntos y diferencia de goles
        clasificaciones.sort(Comparator.comparingInt(Clasificacion::getPuntos)
                .thenComparingInt(Clasificacion::getDiferenciaGoles)
                .reversed());

        // Asignar el nuevo índice de clasificación basado en el orden
        for (int i = 0; i < clasificaciones.size(); i++) {
            clasificaciones.get(i).setClasificacionId(i + 1);
        }

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

                // Verificar si los datos del usuario no son todos ceros
                if ((local.getNivel() != 0 || local.getVictorias() != 0 || local.getPartidosJugados() != 0) &&
                        (visitante.getNivel() != 0 || visitante.getVictorias() != 0 || visitante.getPartidosJugados() != 0)) {

                    Map<String, Integer> probabilidad = calcularProbabilidad(local, visitante);
                    probabilidades.put(partido.getPartidoId(), probabilidad);
                }
            }
        }
        return probabilidades;
    }

    // Calcular la probabilidad de victoria, empate y derrota entre dos equipos
    private Map<String, Integer> calcularProbabilidad(Usuario local, Usuario visitante) {
        final double MATCH_WEIGHT = 0.55;  // 55% importancia a los partidos
        final double LEVEL_WEIGHT = 0.45;  // 45% importancia al nivel

        // Calcular el rendimiento basado en los partidos
        double matchPerformanceLocal = (double) local.getVictorias() / local.getPartidosJugados();
        double matchPerformanceVisitante = (double) visitante.getVictorias() / visitante.getPartidosJugados();

        // Calcular la contribución del nivel (normalizado)
        double normalizedLevelLocal = local.getNivel() / 10.0;  // Suponiendo que el nivel máximo es 10
        double normalizedLevelVisitante = visitante.getNivel() / 10.0;

        // Combinar el rendimiento de los partidos y el nivel con los pesos ajustados
        double factorLocal = (matchPerformanceLocal * MATCH_WEIGHT) + (normalizedLevelLocal * LEVEL_WEIGHT);
        double factorVisitante = (matchPerformanceVisitante * MATCH_WEIGHT) + (normalizedLevelVisitante * LEVEL_WEIGHT);

        // Cálculo inicial de probabilidades
        double probLocal = factorLocal / (factorLocal + factorVisitante);
        double probEmpate = 2 * (factorLocal * factorVisitante) / Math.pow(factorLocal + factorVisitante, 2);

        // Limitar la probabilidad de empate a un máximo de 40%
        if (probEmpate > 0.4) {
            probEmpate = 0.4;
        }

        // Redistribuir la probabilidad restante
        double remainingProb = 1 - probEmpate;
        probLocal = remainingProb * (factorLocal / (factorLocal + factorVisitante));
        double probVisitante = remainingProb * (factorVisitante / (factorLocal + factorVisitante));

        // Normalizar las probabilidades para asegurar que sumen 100%
        double totalProb = probLocal + probEmpate + probVisitante;
        probLocal = probLocal / totalProb * 100;
        probEmpate = probEmpate / totalProb * 100;
        probVisitante = probVisitante / totalProb * 100;

        // Redondear las probabilidades y ajustar para que sumen 100%
        int probLocalInt = (int) Math.round(probLocal);
        int probEmpateInt = (int) Math.round(probEmpate);
        int probVisitanteInt = (int) Math.round(probVisitante);

        // Ajustar la suma de las probabilidades a 100%
        int totalInt = probLocalInt + probEmpateInt + probVisitanteInt;
        if (totalInt != 100) {
            int diff = 100 - totalInt;
            if (diff > 0) {
                probEmpateInt += diff;
            } else {
                probEmpateInt += diff;
            }
        }

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