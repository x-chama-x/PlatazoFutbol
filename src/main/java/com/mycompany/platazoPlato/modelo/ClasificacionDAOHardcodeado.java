package com.mycompany.platazoPlato.modelo;

import com.mycompany.platazoPlato.modelo.Clasificacion;
import java.util.ArrayList;

public class ClasificacionDAOHardcodeado {
    private ArrayList<Clasificacion> clasificaciones;

    public ClasificacionDAOHardcodeado() {
        clasificaciones = new ArrayList<>();
        cargarClasificaciones();
    }

    private void cargarClasificaciones() {
        clasificaciones.add(new Clasificacion(1, 1, 10, 7, 2, 1, 25, 15, 10, 23));
        clasificaciones.add(new Clasificacion(2, 2, 10, 6, 3, 1, 22, 14, 8, 21));
        clasificaciones.add(new Clasificacion(3, 3, 10, 5, 3, 2, 20, 16, 4, 18));
    }

    public ArrayList<Clasificacion> getClasificaciones() {
        return new ArrayList<>(clasificaciones);
    }
}
