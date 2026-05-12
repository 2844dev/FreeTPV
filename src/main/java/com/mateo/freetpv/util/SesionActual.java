package com.mateo.freetpv.util;

import com.mateo.freetpv.model.Usuario;

public class SesionActual {
    private static final SesionActual instancia = new SesionActual();

    private Usuario usuario;

    private SesionActual() {}

    public static SesionActual getInstancia() { return instancia; }

    public Usuario getUsuario() { return usuario; }
    public boolean esAdmin() { return usuario != null && "Admin".equals(usuario.getRol()); }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
