package com.mateo.freetpv.model;

// CLASE MODELO USUARIOS

public class Usuario {
    private int id;
    private String nombre;
    private String hash;
    private String salt;
    private String rol;
    private String fecha_creacion;

    // Constructor
    public Usuario(int id, String nombre, String hash, String salt, String rol, String fecha_creacion) {
        this.id = id;
        this.nombre = nombre;
        this.hash = hash;
        this.salt = salt;
        this.rol = rol;
        this.fecha_creacion = fecha_creacion;
    }

    // Setters
    public void setSalt(String salt) {
        this.salt = salt;
    }
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
    public String getSalt() {
        return salt;
    }
    public String getRol() {
        return rol;
    }
    public String getFecha_creacion() {
        return fecha_creacion;
    }
}
