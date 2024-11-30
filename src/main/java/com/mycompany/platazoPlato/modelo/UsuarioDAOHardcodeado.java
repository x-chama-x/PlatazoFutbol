package com.mycompany.platazoPlato.modelo;

import java.util.ArrayList;

public class UsuarioDAOHardcodeado {
    private ArrayList<Usuario> usuarios;

    public UsuarioDAOHardcodeado() {
        usuarios = new ArrayList<>();
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        usuarios.add(new Usuario(1, "x_chama_x", 1554, 827, 49));
        usuarios.add(new Usuario(2, "facubostero", 476, 253, 25));
        usuarios.add(new Usuario(3, "HOm3ro_s1ns0", 100, 70, 35));
        usuarios.add(new Usuario(4, "elmasmejor", 70, 40, 12));
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
}
