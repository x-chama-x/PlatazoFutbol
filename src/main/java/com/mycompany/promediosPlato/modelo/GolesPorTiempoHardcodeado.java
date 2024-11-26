package com.mycompany.promediosPlato.modelo;

import java.util.ArrayList;

public class GolesPorTiempoHardcodeado {
    private ArrayList<GolesPorTiempo> golesPorPartido;

    public GolesPorTiempoHardcodeado(){
        golesPorPartido = new ArrayList<>();
        cargarGolesPorPartido();
    }

    private void cargarGolesPorPartido(){
        golesPorPartido.add(new GolesPorTiempo(1, 1, "primer_tiempo", 2, 1));
        golesPorPartido.add(new GolesPorTiempo(2, 1, "segundo_tiempo", 1, 1));
        golesPorPartido.add(new GolesPorTiempo(3, 2, "primer_tiempo", 1, 0));
        golesPorPartido.add(new GolesPorTiempo(4, 2, "segundo_tiempo", 0, 1));

    }

    public ArrayList<GolesPorTiempo> getGolesPorTiempo(){
        return golesPorPartido;
    }
}
