package com.mycompany.platazoPlato.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class PartidoDAOHardcodeado {
    private ArrayList<Partido> partidos;

    public PartidoDAOHardcodeado(){
        partidos = new ArrayList<>();
        cargarPartidos();
        cargarPartidosAunNoJugados();
    }

    private void cargarPartidos(){
        partidos.add(new Partido(1, LocalDate.now(), 1, 2, "DRAW", "liga", null, 1, "finalizado"));
        partidos.add(new Partido(2, LocalDate.now(), 3, 4, "6-2", "liga", null, 1, "finalizado"));
        partidos.add(new Partido(3, LocalDate.now(), 1, 2, "1-6", "mundial", null, 1, "finalizado"));
    }

    private void cargarPartidosAunNoJugados(){
        partidos.add(new Partido(4, LocalDate.now(), 1, 2, null, "liga", null, 1, null));
        partidos.add(new Partido(5, LocalDate.now(), 3, 4, null, "liga", null, 1, null));
    }

    public ArrayList<Partido> getPartidos(){
        return new ArrayList<>(partidos);
    }

}
