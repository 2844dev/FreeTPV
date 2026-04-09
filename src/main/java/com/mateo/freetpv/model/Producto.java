package com.mateo.freetpv.model;

/**
 *
 * Clase modelo para los productos
 *
 */
public class Producto {
    private int id;
    private String nombre;
    private String nombre_ticket;
    private String imagen;
    private int precio;
    private int iva;
    private boolean estado;
    private boolean favorito;
    private int categoria_id;

    // CONSTRUCTOR
    public Producto(int id, String nombre, String nombre_ticket, String imagen, int precio, int iva, boolean estado, boolean favorito, int categoria_id) {
        this.categoria_id = categoria_id;
        this.favorito = favorito;
        this.estado = estado;
        this.iva = iva;
        this.precio = precio;
        this.imagen = imagen;
        this.nombre_ticket = nombre_ticket;
        this.nombre = nombre;
        this.id = id;
    }

    // GETTERS
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getNombre_ticket() { return nombre_ticket; }
    public String getImagen() { return imagen; }
    public int getPrecio() { return precio; }
    public int getIva() { return iva; }
    public boolean getEstado() { return estado; }
    public boolean getFavorito() { return favorito; }
    public int getCategoria_id() { return categoria_id; }

    // SETTERS
    public void setCategoria_id(int categoria_id) { this.categoria_id = categoria_id; }
    public void setFavorito(boolean favorito) { this.favorito = favorito; }
    public void setEstado(boolean estado) { this.estado = estado; }
    public void setIva(int iva) { this.iva = iva; }
    public void setPrecio(int precio) { this.precio = precio; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    public void setNombre_ticket(String nombre_ticket) { this.nombre_ticket = nombre_ticket; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setId(int id) { this.id = id; }
}
