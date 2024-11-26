package com.mycompany.promediosPlato.modelo.db;

import com.mycompany.promediosPlato.modelo.Partido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PartidoDAO {
    private static final String SELECT_ALL_PARTIDOS = "SELECT partidoId, fecha, equipoLocaId, equipoVisitanteId, resultado, tipoEvento, faseCopa, jornada, estado FROM Partido";

    public ArrayList<Partido> getPartidos() {
        ArrayList<Partido> partidos = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = pool.getConnection();
            ps = connection.prepareStatement(SELECT_ALL_PARTIDOS);
            rs = ps.executeQuery();

            while (rs.next()) {
                int partidoId = rs.getInt("partidoId");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                int equipoLocalId = rs.getInt("equipoLocaId");
                int equipoVisitanteId = rs.getInt("equipoVisitanteId");
                String resultado = rs.getString("resultado");
                String tipoEvento = rs.getString("tipoEvento");
                String faseCopa = rs.getString("faseCopa");
                Integer jornada = rs.getObject("jornada", Integer.class);
                String estado = rs.getString("estado");

                Partido partido = new Partido(partidoId, fecha, equipoLocalId, equipoVisitanteId, resultado, tipoEvento, faseCopa, jornada, estado);
                partidos.add(partido);
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

        return partidos;
    }
}