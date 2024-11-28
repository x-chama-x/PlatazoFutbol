package com.mycompany.promediosPlato.modelo.db;

import com.mycompany.promediosPlato.modelo.Clasificacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClasificacionDAO {
    private static final String SELECT_ALL_CLASIFICACIONES = "SELECT clasificacionId, usuarioId, partidosJugados, victorias, empates, derrotas, golesFavor, golesContra, difGoles, puntos FROM Clasificacion";
    private static final String INSERT_CLASIFICACION = "INSERT INTO Clasificacion (usuarioId, partidosJugados, victorias, empates, derrotas, golesFavor, golesContra, difGoles, puntos) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public ArrayList<Clasificacion> getClasificaciones() {
        ArrayList<Clasificacion> clasificaciones = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_CLASIFICACIONES);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Clasificacion clasificacion = mapResultSetToClasificacion(rs);
                clasificaciones.add(clasificacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clasificaciones;
    }

    public void addClasificacion(Clasificacion clasificacion) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_CLASIFICACION)) {

            ps.setInt(1, clasificacion.getUsuarioId());
            ps.setInt(2, clasificacion.getPartidosJugados());
            ps.setInt(3, clasificacion.getVictorias());
            ps.setInt(4, clasificacion.getEmpates());
            ps.setInt(5, clasificacion.getDerrotas());
            ps.setInt(6, clasificacion.getGolesFavor());
            ps.setInt(7, clasificacion.getGolesContra());
            ps.setInt(8, clasificacion.getDiferenciaGoles());
            ps.setInt(9, clasificacion.getPuntos());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return ConnectionPool.getInstance().getConnection();
    }

    private Clasificacion mapResultSetToClasificacion(ResultSet rs) throws SQLException {
        int clasificacionId = rs.getInt("clasificacionId");
        int usuarioId = rs.getInt("usuarioId");
        int partidosJugados = rs.getInt("partidosJugados");
        int victorias = rs.getInt("victorias");
        int empates = rs.getInt("empates");
        int derrotas = rs.getInt("derrotas");
        int golesFavor = rs.getInt("golesFavor");
        int golesContra = rs.getInt("golesContra");
        int diferenciaGoles = rs.getInt("difGoles");
        int puntos = rs.getInt("puntos");

        return new Clasificacion(clasificacionId, usuarioId, partidosJugados, victorias, empates, derrotas, golesFavor, golesContra, diferenciaGoles, puntos);
    }
}