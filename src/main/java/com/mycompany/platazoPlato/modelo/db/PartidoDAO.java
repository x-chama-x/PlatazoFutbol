package com.mycompany.platazoPlato.modelo.db;

import com.mycompany.platazoPlato.modelo.Partido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class PartidoDAO {
    private static final String SELECT_ALL_PARTIDOS = "SELECT partidoId, fecha, equipoLocaId, equipoVisitanteId, resultado, tipoEvento, faseCopa, jornada, estado, nota FROM Partido";

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

    public ArrayList<Partido> getPartidosProximasDosSemanas() {
        ArrayList<Partido> partidos = new ArrayList<>();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusWeeks(2); // Start from two weeks before today
        LocalDate endDate = now.plusWeeks(2); // End two weeks from now

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_PARTIDOS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Partido partido = mapResultSetToPartido(rs);
                if (partido.getFecha().isAfter(startDate.minusDays(1)) && partido.getFecha().isBefore(endDate.plusDays(1))) {
                    partidos.add(partido);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partidos;
    }


    public ArrayList<Partido> getPartidosUltimosDosMeses() {
        ArrayList<Partido> partidos = new ArrayList<>();
        LocalDate now = LocalDate.now();
        LocalDate startDate = YearMonth.from(now.minusMonths(1)).atDay(1);
        LocalDate endDate = YearMonth.from(now).atEndOfMonth();

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_PARTIDOS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Partido partido = mapResultSetToPartido(rs);
                if (partido.getFecha().isAfter(startDate.minusDays(1)) && partido.getFecha().isBefore(endDate.plusDays(1))) {
                    partidos.add(partido);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partidos;
    }

    public ArrayList<Partido> getPartidosDelMesActual() {
        ArrayList<Partido> partidos = new ArrayList<>();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_PARTIDOS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Partido partido = mapResultSetToPartido(rs);
                if (partido.getFecha().isAfter(startDate.minusDays(1)) && partido.getFecha().isBefore(endDate.plusDays(1))) {
                    partidos.add(partido);
                }
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
        String nota = rs.getString("nota");
        return new Partido(partidoId, fecha, equipoLocalId, equipoVisitanteId, resultado, tipoEvento, faseCopa, jornada, estado, nota);
    }
}