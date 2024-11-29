package com.mycompany.promediosPlato.modelo;
import java.io.Serializable;

public class Usuario implements Serializable {
    private int usuarioId;
    private String nombre;
    private int partidosJugados;
    private int victorias;
    private int nivel;
    private int diferencia;

    public Usuario(){
    }

    public Usuario(int usuarioId, String nombre, int partidosJugados, int victorias, int nivel) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.partidosJugados = partidosJugados;
        this.victorias = victorias;
        this.nivel = nivel;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(int partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    public int getVictorias() {
        return victorias;
    }

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(int diferencia) {
        this.diferencia = diferencia;
    }

    @Override
    public String toString() {
        return "Usuario{" + "usuarioId=" + usuarioId + ", nombre=" + nombre + ", partidosJugados=" + partidosJugados + ", victorias=" + victorias + ", nivel=" + nivel + '}';
    }
}
