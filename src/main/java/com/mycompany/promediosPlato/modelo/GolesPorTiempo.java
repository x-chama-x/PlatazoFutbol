package com.mycompany.promediosPlato.modelo;

import java.io.Serializable;

public class GolesPorTiempo implements Serializable {
    private int id;
    private int partidoId;
    private String tiempo;  // 'primer_tiempo', 'segundo_tiempo', 'tercer_tiempo', 'unico_tiempo'
    private int golesLocal;
    private int golesVisitante;

    public GolesPorTiempo(){
    }

    public GolesPorTiempo(int id, int partidoId, String tiempo, int golesLocal, int golesVisitante) {
        this.id = id;
        this.partidoId = partidoId;
        this.tiempo = tiempo;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPartidoId() {
        return partidoId;
    }

    public void setPartidoId(int partidoId) {
        this.partidoId = partidoId;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(int golesLocal) {
        this.golesLocal = golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(int golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    private String traducirTiempo(String tiempo) {
        switch (tiempo) {
            case "primer_tiempo":
                return "1'";
            case "segundo_tiempo":
                return "2'";
            case "tercer_tiempo":
                return "3'";
            case "unico_tiempo":
                return "U'";
            default:
                return tiempo;
        }
    }

    public String getGolesLocalFormatted() {
        return golesLocal + " G " + traducirTiempo(tiempo);
    }

    public String getGolesVisitanteFormatted() {
        return golesVisitante + " G " + traducirTiempo(tiempo);
    }

    @Override
    public String toString() {
        return "GolesPorPartido{" + "id=" + id + ", partidoId=" + partidoId + ", tiempo=" + tiempo + ", golesLocal=" + golesLocal + ", golesVisitante=" + golesVisitante + '}';
    }
}