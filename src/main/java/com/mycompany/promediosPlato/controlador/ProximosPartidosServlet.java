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

public class ProximosPartidosServlet extends HttpServlet {

    private UsuarioDAOHardcodeado usuarioDAO;
    private PartidoDAOHardcodeado partidoDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        usuarioDAO = new UsuarioDAOHardcodeado();
        partidoDAO = new PartidoDAOHardcodeado();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Obtener los datos de los usuarios y partidos
        ArrayList<Usuario> usuarios = usuarioDAO.getUsuarios();
        ArrayList<Partido> partidos = partidoDAO.getPartidos();

        // Crear un mapa que relacione usuarioId con el nombre del equipo
        Map<Integer, String> equipoNombres = new HashMap<>();
        for (Usuario usuario : usuarios) {
            equipoNombres.put(usuario.getUsuarioId(), usuario.getNombre());
        }

        // Filtrar los partidos no jugados
        List<Partido> proximosPartidos = new ArrayList<>();
        for (Partido partido : partidos) {
            if (!"finalizado".equals(partido.getEstado())) {
                proximosPartidos.add(partido);
            }
        }

        // Calcular la probabilidad de victoria (hardcodeado por ahora)
        Map<Integer, Map<String, Integer>> probabilidades = new HashMap<>();
        for (Partido partido : proximosPartidos) {
            Map<String, Integer> probabilidad = new HashMap<>();
            probabilidad.put("local", 40); // 40% de victoria local
            probabilidad.put("empate", 30); // 30% de empate
            probabilidad.put("visitante", 30); // 30% de victoria visitante
            probabilidades.put(partido.getPartidoId(), probabilidad);
        }

        // Pasar los datos a la página JSP
        request.setAttribute("proximosPartidos", proximosPartidos);
        request.setAttribute("equipoNombres", equipoNombres);
        request.setAttribute("probabilidades", probabilidades);

        // Redirigir a la página proximosPartidos.jsp
        request.getRequestDispatcher("/WEB-INF/jsp/proximosPartidos.jsp").forward(request, response);
    }
}
