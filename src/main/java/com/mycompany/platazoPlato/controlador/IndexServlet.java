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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;
    private PartidoDAO partidoDAO;
    private GolesPorTiempoDAO golesPorTiempoDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("Inicializando IndexServlet");
        usuarioDAO = new UsuarioDAO();
        partidoDAO = new PartidoDAO();
        golesPorTiempoDAO = new GolesPorTiempoDAO();
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

        // Filtrar y agrupar los partidos jugados por tipo de evento
        Map<String, List<Partido>> partidosJugadosPorEvento = new HashMap<>();
        for (Partido partido : partidos) {
            if ("finalizado".equals(partido.getEstado())) {
                partidosJugadosPorEvento
                        .computeIfAbsent(partido.getTipoEvento(), k -> new ArrayList<>())
                        .add(partido);
            }
        }

        // Pasar los datos a la página JSP
        request.setAttribute("partidosJugadosPorEvento", partidosJugadosPorEvento);
        request.setAttribute("equipoNombres", equipoNombres);
        request.setAttribute("golesPorPartido", golesPorPartido);

        // Redirigir a la página index.jsp
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}