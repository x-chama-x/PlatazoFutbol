package com.mycompany.platazoPlato.controlador;

import com.mycompany.platazoPlato.modelo.Estado;
import com.mycompany.platazoPlato.modelo.Usuario;
import com.mycompany.platazoPlato.modelo.db.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UsuariosLigaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.getUsuarios();

        // Separar usuarios activos y no activos
        List<Usuario> usuariosActivos = new ArrayList<>();
        List<Usuario> usuariosNoActivos = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getEstado() == Estado.ACTIVO) {
                usuariosActivos.add(usuario);
            } else {
                usuariosNoActivos.add(usuario);
            }
        }

        // Calcular la diferencia y ordenar las listas
        usuariosActivos.forEach(usuario -> usuario.setDiferencia(usuario.getVictorias() - (usuario.getPartidosJugados() - usuario.getVictorias())));
        usuariosNoActivos.forEach(usuario -> usuario.setDiferencia(usuario.getVictorias() - (usuario.getPartidosJugados() - usuario.getVictorias())));
        usuariosActivos.sort(Comparator.comparingInt(Usuario::getDiferencia).reversed());
        usuariosNoActivos.sort(Comparator.comparingInt(Usuario::getDiferencia).reversed());

        request.setAttribute("usuariosActivos", usuariosActivos);
        request.setAttribute("usuariosNoActivos", usuariosNoActivos);
        request.getRequestDispatcher("/WEB-INF/jsp/usuariosDeLiga.jsp").forward(request, response);
    }
}
