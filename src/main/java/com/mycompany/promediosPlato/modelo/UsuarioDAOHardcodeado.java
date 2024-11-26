package com.mycompany.promediosPlato.modelo;

import java.util.ArrayList;

public class UsuarioDAOHardcodeado {
    private ArrayList<Usuario> usuarios;

    public UsuarioDAOHardcodeado() {
        usuarios = new ArrayList<>();
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        usuarios.add(new Usuario(1, "x_chama_x", 10, 7, 3));
        usuarios.add(new Usuario(2, "facubostero", 10, 6, 2));
        usuarios.add(new Usuario(3, "HOm3ro_s1ns0", 10, 5, 1));
        usuarios.add(new Usuario(4, "elmasmejor", 10, 4, 0));
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
}
