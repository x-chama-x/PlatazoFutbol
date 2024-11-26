package com.mycompany.promediosPlato.modelo.db;

import com.mycompany.promediosPlato.modelo.GolesPorTiempo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GolesPorTiempoDAO {
    private static final String SELECT_ALL_GOLES_POR_TIEMPO = "SELECT golesPorTiempoId, tiempo, golesLocal, golesVisitantes, partidoId FROM  GolesPorTiempo";

    public ArrayList<GolesPorTiempo> getGolesPorTiempo() {
        ArrayList<GolesPorTiempo> golesPorTiempo = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = pool.getConnection();
            ps = connection.prepareStatement(SELECT_ALL_GOLES_POR_TIEMPO);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("golesPorTiempoId");
                int partidoId = rs.getInt("partidoId");
                String tiempo = rs.getString("tiempo");
                int golesLocal = rs.getInt("golesLocal");
                int golesVisitante = rs.getInt("golesVisitantes");

                GolesPorTiempo gol = new GolesPorTiempo(id, partidoId, tiempo, golesLocal, golesVisitante);
                golesPorTiempo.add(gol);
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

        return golesPorTiempo;
    }
}