package com.mateo.freetpv.model;

import java.util.List;

public record DatosTicket(
        String nombreEmpresa,
        String cif,
        String direccion,
        String codigoPostal,
        String ciudad,
        String telefono,
        String web,
        String qr,
        String tituloTicket,
        String mensajeFinal,
        boolean mostrarCif,
        boolean mostrarTelefono,
        boolean mostrarWeb,
        boolean mostrarIva,
        boolean mostrarQr,
        String nombreCamarero,
        List<LineaTicket> lineas,
        String metodoPago,
        int entregado) {

}