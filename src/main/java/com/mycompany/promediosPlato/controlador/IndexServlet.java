package com.mycompany.promediosPlato.controlador;

import com.mycompany.promediosPlato.modelo.*;

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

public class IndexServlet extends HttpServlet {

    private UsuarioDAOHardcodeado usuarioDAO;
    private PartidoDAOHardcodeado partidoDAO;
    private GolesPorTiempoDAOHardcodeado golesPorTiempoDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("Inicializando IndexServlet");
        usuarioDAO = new UsuarioDAOHardcodeado();
        partidoDAO = new PartidoDAOHardcodeado();
        golesPorTiempoDAO = new GolesPorTiempoDAOHardcodeado();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Obtener los datos de los usuarios, partidos y goles por tiempo
        ArrayList<Usuario> usuarios = usuarioDAO.getUsuarios();
        ArrayList<Partido> partidos = partidoDAO.getPartidos();
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

        // Imprimir los datos de los partidos, goles por tiempo y nombres de los equipos
        for (Partido partido : partidos) {
            System.out.println("Partido: " + partido.getPartidoId() + " - " + partido.getFecha() + " - " + equipoNombres.get(partido.getEquipoLocalId()) + " vs " + equipoNombres.get(partido.getEquipoVisitanteId()));
            if (golesPorPartido.containsKey(partido.getPartidoId())) {
                for (GolesPorTiempo gol : golesPorPartido.get(partido.getPartidoId())) {
                    System.out.println("Goles por tiempo: " + gol.getTiempo() + " - " + gol.getGolesLocal() + " - " + gol.getGolesVisitante());
                }
            }
        }

        // Filtrar los partidos jugados
        List<Partido> partidosJugados = new ArrayList<>();
        for (Partido partido : partidos) {
            if ("finalizado".equals(partido.getEstado())) {
                partidosJugados.add(partido);
            }
        }

        // Pasar los datos a la página JSP
        request.setAttribute("partidos", partidosJugados);
        request.setAttribute("equipoNombres", equipoNombres);
        request.setAttribute("golesPorPartido", golesPorPartido);

        // Redirigir a la página index.jsp
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}