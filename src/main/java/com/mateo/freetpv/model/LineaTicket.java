package com.mateo.freetpv.model;

public class LineaTicket {
    private int id;
    private int productoId;
    private String nombreProducto;
    private int cantidad;
    private int precioUnitario;
    private int iva;

    public LineaTicket(int productoId, String nombreProducto, int precioUnitario, int iva) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.precioUnitario = precioUnitario;
        this.iva = iva;
        this.cantidad = 1;
    }

    public int getSubtotal() {
        return cantidad * precioUnitario;
    }

    public int getId() {
        return id;
    }

    public int getProductoId() {
        return productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getPrecioUnitario() {
        return precioUnitario;
    }

    public int getIva() {
        return iva;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
