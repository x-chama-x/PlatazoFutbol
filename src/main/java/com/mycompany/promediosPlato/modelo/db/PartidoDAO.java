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
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_PARTIDOS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Partido partido = mapResultSetToPartido(rs);
                partidos.add(partido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partidos;
    }

    private Connection getConnection() throws SQLException {
        return ConnectionPool.getInstance().getConnection();
    }

    private Partido mapResultSetToPartido(ResultSet rs) throws SQLException {
        int partidoId = rs.getInt("partidoId");
        LocalDate fecha = rs.getDate("fecha").toLocalDate();
        int equipoLocalId = rs.getInt("equipoLocaId");
        int equipoVisitanteId = rs.getInt("equipoVisitanteId");
        String resultado = rs.getString("resultado");
        String tipoEvento = rs.getString("tipoEvento");
        String faseCopa = rs.getString("faseCopa");
        Integer jornada = rs.getObject("jornada", Integer.class);
        String estado = rs.getString("estado");
        return new Partido(partidoId, fecha, equipoLocalId, equipoVisitanteId, resultado, tipoEvento, faseCopa, jornada, estado);
    }
}