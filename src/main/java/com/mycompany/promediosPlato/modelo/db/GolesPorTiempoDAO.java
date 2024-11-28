package com.mycompany.promediosPlato.modelo.db;

import com.mycompany.promediosPlato.modelo.GolesPorTiempo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GolesPorTiempoDAO {
    private static final String SELECT_ALL_GOLES_POR_TIEMPO = "SELECT golesPorTiempoId, tiempo, golesLocal, golesVisitantes, partidoId FROM GolesPorTiempo";

    public ArrayList<GolesPorTiempo> getGolesPorTiempo() {
        ArrayList<GolesPorTiempo> golesPorTiempo = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_GOLES_POR_TIEMPO);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                GolesPorTiempo gol = mapResultSetToGolesPorTiempo(rs);
                golesPorTiempo.add(gol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return golesPorTiempo;
    }

    private Connection getConnection() throws SQLException {
        return ConnectionPool.getInstance().getConnection();
    }

    private GolesPorTiempo mapResultSetToGolesPorTiempo(ResultSet rs) throws SQLException {
        int id = rs.getInt("golesPorTiempoId");
        int partidoId = rs.getInt("partidoId");
        String tiempo = rs.getString("tiempo");
        int golesLocal = rs.getInt("golesLocal");
        int golesVisitante = rs.getInt("golesVisitantes");
        return new GolesPorTiempo(id, partidoId, tiempo, golesLocal, golesVisitante);
    }
}