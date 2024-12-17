package com.mycompany.platazoPlato.controlador;

import com.mycompany.platazoPlato.modelo.GolesPorTiempo;
import com.mycompany.platazoPlato.modelo.Usuario;
import com.mycompany.platazoPlato.modelo.Partido;
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

public class CopaServlet extends HttpServlet {

    private PartidoDAO partidoDAO;
    private UsuarioDAO usuarioDAO;
    private GolesPorTiempoDAO golesPorTiempoDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        partidoDAO = new PartidoDAO();
        usuarioDAO = new UsuarioDAO();
        golesPorTiempoDAO = new GolesPorTiempoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Partido> partidos = partidoDAO.getPartidos();

        // Filtrar los partidos del evento "mundial"
        List<Partido> partidosMundial = partidos.stream()
                .filter(p -> "copa".equals(p.getTipoEvento()))
                .collect(Collectors.toList());

        // Obtener los usuarios y goles por tiempo
        ArrayList<Usuario> usuarios = usuarioDAO.getUsuarios();
        ArrayList<GolesPorTiempo> golesPorTiempo = golesPorTiempoDAO.getGolesPorTiempo();

        // Crear un mapa que relacione usuarioId con el nombre del equipo
        Map<Integer, String> equipoNombres = new HashMap<>();
        for (Usuario usuario : usuarios) {
            equipoNombres.put(usuario.getUsuarioId(), usuario.getNombre());
        }

        // Crear un mapa que relacione partidoId con los goles por tiempo
        Map<Integer, List<GolesPorTiempo>> golesPorPartido = new HashMap<>();
        for (GolesPorTiempo gol : golesPorTiempo) {
            golesPorPartido.computeIfAbsent(gol.getPartidoId(), k -> new ArrayList<>()).add(gol);
        }

        // Agrupar los partidos del mundial por fase del torneo
        Map<String, List<Partido>> partidosPorFase = new HashMap<>();
        for (Partido partido : partidosMundial) {
            partidosPorFase
                    .computeIfAbsent(partido.getFaseCopa(), k -> new ArrayList<>())
                    .add(partido);
        }

        // Ordenar las fases del torneo en el orden deseado
        List<String> fasesOrdenadas = Arrays.asList("final", "semifinal", "cuartos", "octavos", "grupos");
        Map<String, List<Partido>> partidosPorFaseOrdenados = new LinkedHashMap<>();
        for (String fase : fasesOrdenadas) {
            if (partidosPorFase.containsKey(fase)) {
                partidosPorFaseOrdenados.put(fase, partidosPorFase.get(fase));
            }
        }

        // Pasar los datos a la página JSP
        request.setAttribute("partidosPorFase", partidosPorFaseOrdenados);
        request.setAttribute("equipoNombres", equipoNombres);
        request.setAttribute("golesPorPartido", golesPorPartido);

        // Redirigir a la página paginaDeCopa.jsp
        request.getRequestDispatcher("/WEB-INF/jsp/paginaDeCopa.jsp").forward(request, response);
    }
}