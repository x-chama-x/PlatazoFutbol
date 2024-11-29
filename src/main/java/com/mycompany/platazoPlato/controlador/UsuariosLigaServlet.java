package com.mycompany.platazoPlato.controlador;

import com.mycompany.platazoPlato.modelo.Usuario;
import com.mycompany.platazoPlato.modelo.db.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class UsuariosLigaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.getUsuarios();

        // Calcular la diferencia y ordenar la lista
        usuarios.forEach(usuario -> usuario.setDiferencia(usuario.getVictorias() - (usuario.getPartidosJugados() - usuario.getVictorias())));
        usuarios.sort(Comparator.comparingInt(Usuario::getDiferencia).reversed());

        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/WEB-INF/jsp/usuariosDeLiga.jsp").forward(request, response);
    }
}
