package com.mycompany.promediosPlato.modelo.db;

import com.mycompany.promediosPlato.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAO {
    private static final String SELECT_ALL_USUARIOS = "SELECT usuarioId, nombre, partidosJugados, victorias, nivel FROM Usuario";

    public ArrayList<Usuario> getUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = pool.getConnection();
            ps = connection.prepareStatement(SELECT_ALL_USUARIOS);
            rs = ps.executeQuery();

            while (rs.next()) {
                int usuarioId = rs.getInt("usuarioId");
                String nombre = rs.getString("nombre");
                int partidosJugados = rs.getInt("partidosJugados");
                int victorias = rs.getInt("victorias");
                int nivel = rs.getInt("nivel");

                Usuario usuario = new Usuario(usuarioId, nombre, partidosJugados, victorias, nivel);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                pool.freeConnection(connection);
            }
        }

        return usuarios;
    }
}