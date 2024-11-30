package com.mycompany.platazoPlato.modelo.db;

import com.mycompany.platazoPlato.modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAO {
    private static final String SELECT_ALL_USUARIOS = "SELECT usuarioId, nombre, partidosJugados, victorias, nivel FROM Usuario";

    public ArrayList<Usuario> getUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USUARIOS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = mapResultSetToUsuario(rs);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    private Connection getConnection() throws SQLException {
        return ConnectionPool.getInstance().getConnection();
    }

    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        int usuarioId = rs.getInt("usuarioId");
        String nombre = rs.getString("nombre");
        int partidosJugados = rs.getInt("partidosJugados");
        int victorias = rs.getInt("victorias");
        int nivel = rs.getInt("nivel");
        return new Usuario(usuarioId, nombre, partidosJugados, victorias, nivel);
    }
}