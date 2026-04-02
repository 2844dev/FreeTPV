package com.mateo.freetpv.model;

/**
 *
 * Clase modelo para los usuarios
 *
 */
public class Usuario {
    private int id;
    private String nombre;
    private String hash;
    private String rol;
    private boolean estado; // 0 (false) no activo, 1 (true) activo
    private String fecha_creacion;

    // Constructor
    public Usuario(int id, String nombre, String hash, String rol, boolean estado, String fecha_creacion) {
        this.id = id;
        this.nombre = nombre;
        this.hash = hash;
        this.rol = rol;
        this.estado = estado;
        this.fecha_creacion = fecha_creacion;
    }

    // Setters
    public void setHash(String hash) {
        this.hash = hash;
    }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setId(int id) {
        this.id = id;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }


    // Getters
    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getHash() {
        return hash;
    }
    public String getRol() {
        return rol;
    }
    public boolean getEstado() {
        return estado;
    }
    public String getFecha_creacion() {
        return fecha_creacion;
    }
}
