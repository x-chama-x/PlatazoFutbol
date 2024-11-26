package com.mycompany.promediosPlato.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class PartidoDAOHardcodeado {
    private ArrayList<Partido> partidos;

    public PartidoDAOHardcodeado(){
        partidos = new ArrayList<>();
        cargarPartidos();
    }

    private void cargarPartidos(){
        partidos.add(new Partido(1, LocalDate.now(), 1, 2, "DRAW", "liga", null, 1, "finalizado"));
        partidos.add(new Partido(2, LocalDate.now(), 3, 4, "6-2", "liga", null, 1, "finalizado"));
    }

    public ArrayList<Partido> getPartidos(){
        return new ArrayList<>(partidos);
    }

}
