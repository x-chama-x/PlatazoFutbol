package com.mycompany.platazoPlato.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Partido implements Serializable {
    private int partidoId;
    private LocalDate fecha;
    private int equipoLocalId;
    private int equipoVisitanteId;
    private String resultado;
    private String tipoEvento; // 'liga', 'mundial', 'otro'
    private String faseCopa; // 'octavos', 'cuartos', 'semifinal', 'final', 'otro', null si no aplica
    private Integer jornada; // Puede ser null si no aplica
    private String estado; // 'finalizado', 'suspendido', 'en_progreso'
    private String nota; // Nueva nota para partidos suspendidos

    public Partido(){
    }

    public Partido(int partidoId, LocalDate fecha, int equipoLocalId, int equipoVisitanteId, String resultado, String tipoEvento, String faseCopa, Integer jornada, String estado, String nota) {
        this.partidoId = partidoId;
        this.fecha = fecha;
        this.equipoLocalId = equipoLocalId;
        this.equipoVisitanteId = equipoVisitanteId;
        this.resultado = resultado;
        this.tipoEvento = tipoEvento;
        this.faseCopa = faseCopa;
        this.jornada = jornada;
        this.estado = estado;
        this.nota = nota;
    }

    public int getPartidoId() {
        return partidoId;
    }

    public void setPartidoId(int partidoId) {
        this.partidoId = partidoId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getEquipoLocalId() {
        return equipoLocalId;
    }

    public void setEquipoLocalId(int equipoLocalId) {
        this.equipoLocalId = equipoLocalId;
    }

    public int getEquipoVisitanteId() {
        return equipoVisitanteId;
    }

    public void setEquipoVisitanteId(int equipoVisitanteId) {
        this.equipoVisitanteId = equipoVisitanteId;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getFaseCopa() {
        return faseCopa;
    }

    public void setFaseCopa(String faseCopa) {
        this.faseCopa = faseCopa;
    }

    public Integer getJornada() {
        return jornada;
    }

    public void setJornada(Integer jornada) {
        this.jornada = jornada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Partido{" + "partidoId=" + partidoId + ", fecha=" + fecha + ", equipoLocalId=" + equipoLocalId + ", equipoVisitanteId=" + equipoVisitanteId + ", resultado=" + resultado + ", tipoEvento=" + tipoEvento + ", faseCopa=" + faseCopa + ", jornada=" + jornada + ", estado=" + estado + '}';
    }
}
