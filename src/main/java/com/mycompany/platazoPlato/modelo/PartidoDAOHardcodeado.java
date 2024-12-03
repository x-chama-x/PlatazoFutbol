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

    }

    private void cargarPartidosAunNoJugados(){

    }

    public ArrayList<Partido> getPartidos(){
        return new ArrayList<>(partidos);
    }

}
