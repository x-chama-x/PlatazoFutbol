package com.mycompany.promediosPlato.controlador;

import com.mycompany.promediosPlato.modelo.*;

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

        // Calcular la probabilidad de victoria
        Map<Integer, Map<String, Integer>> probabilidades = new HashMap<>();
        for (Partido partido : proximosPartidos) {
            Usuario local = getUsuarioById(usuarios, partido.getEquipoLocalId());
            Usuario visitante = getUsuarioById(usuarios, partido.getEquipoVisitanteId());

            Map<String, Integer> probabilidad = getStringIntegerMap(local, visitante);
            probabilidades.put(partido.getPartidoId(), probabilidad);
        }

        // Pasar los datos a la página JSP
        request.setAttribute("proximosPartidos", proximosPartidos);
        request.setAttribute("equipoNombres", equipoNombres);
        request.setAttribute("probabilidades", probabilidades);

        // Redirigir a la página proximosPartidos.jsp
        request.getRequestDispatcher("/WEB-INF/jsp/proximosPartidos.jsp").forward(request, response);
    }

    private static Map<String, Integer> getStringIntegerMap(Usuario local, Usuario visitante) {
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

    private Usuario getUsuarioById(ArrayList<Usuario> usuarios, int usuarioId) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsuarioId() == usuarioId) {
                return usuario;
            }
        }
        return null;
    }
}