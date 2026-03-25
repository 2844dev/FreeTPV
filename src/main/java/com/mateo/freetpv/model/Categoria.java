package com.mateo.freetpv.model;

/**
 *
 * Clase modelo para las categorías
 *
 */
public class Categoria {
    private int id;
    private String nombre;

    // CONSTRUCTOR
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // GETTERS
    public int getId() { return id; }
    public String getNombre() { return nombre; }

    // SETTERS
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
