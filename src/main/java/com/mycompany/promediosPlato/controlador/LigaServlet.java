package com.mycompany.promediosPlato.controlador;

import com.mycompany.promediosPlato.modelo.*;
import com.mycompany.promediosPlato.modelo.db.ClasificacionDAO;
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

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        clasificacionDAO = new ClasificacionDAO();
        partidoDAO = new PartidoDAO();
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Clasificacion> clasificaciones = clasificacionDAO.getClasificaciones();
        ArrayList<Partido> partidos = partidoDAO.getPartidos();
        ArrayList<Usuario> usuarios = usuarioDAO.getUsuarios();

        // Crear un mapa que relacione usuarioId con el nombre del usuario
        Map<Integer, String> usuarioNombres = new HashMap<>();
        for (Usuario usuario : usuarios) {
            usuarioNombres.put(usuario.getUsuarioId(), usuario.getNombre());
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

        request.setAttribute("clasificaciones", clasificaciones);
        request.setAttribute("partidos", partidosFiltrados);
        request.setAttribute("usuarioNombres", usuarioNombres);
        request.setAttribute("maxJornada", maxJornada);
        request.setAttribute("jornadaSeleccionada", jornadaSeleccionada);

        request.getRequestDispatcher("/WEB-INF/jsp/paginaDeLiga.jsp").forward(request, response);
    }
}