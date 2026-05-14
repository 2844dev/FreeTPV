package com.mateo.freetpv.service;

import atlantafx.base.theme.*;
import com.mateo.freetpv.util.AjustesUtil;
import javafx.application.Application;

import java.util.Properties;

public class AjustesService {

    private final AjustesUtil ajustesUtil = new AjustesUtil();
    private final Properties ajustes = ajustesUtil.cargarAjustes();

    public String getTema() {
        return getString("tema");
    }

    public void setTema(String tema) {
        setString("tema", tema);
    }

    public String getEmpresaNombre() {
        return getString("empresa.nombre");
    }

    public void setEmpresaNombre(String nombre) {
        setString("empresa.nombre", nombre);
    }

    public String getEmpresaCif() {
        return getString("empresa.cif");
    }

    public void setEmpresaCif(String cif) {
        setString("empresa.cif", cif);
    }

    public String getEmpresaDireccion() {
        return getString("empresa.direccion");
    }

    public void setEmpresaDireccion(String direccion) {
        setString("empresa.direccion", direccion);
    }

    public String getEmpresaCodigoPostal() {
        return getString("empresa.codigoPostal");
    }

    public void setEmpresaCodigoPostal(String codigoPostal) {
        setString("empresa.codigoPostal", codigoPostal);
    }

    public String getEmpresaCiudad() {
        return getString("empresa.ciudad");
    }

    public void setEmpresaCiudad(String ciudad) {
        setString("empresa.ciudad", ciudad);
    }

    public String getEmpresaTelefono() {
        return getString("empresa.telefono");
    }

    public void setEmpresaTelefono(String telefono) {
        setString("empresa.telefono", telefono);
    }

    public String getEmpresaWeb() {
        return getString("empresa.web");
    }

    public void setEmpresaWeb(String web) {
        setString("empresa.web", web);
    }

    public String getTicketTitulo() {
        return getString("ticket.titulo");
    }

    public void setTicketTitulo(String titulo) {
        setString("ticket.titulo", titulo);
    }

    public String getTicketMensajeFinal() {
        return getString("ticket.mensajeFinal");
    }

    public void setTicketMensajeFinal(String mensajeFinal) {
        setString("ticket.mensajeFinal", mensajeFinal);
    }

    public boolean getTicketMostrarWeb() {
        return getBoolean("ticket.mostrarWeb");
    }

    public void setTicketMostrarWeb(boolean mostrarWeb) {
        setBoolean("ticket.mostrarWeb", mostrarWeb);
    }

    public boolean getTicketMostrarTelefono() {
        return getBoolean("ticket.mostrarTelefono");
    }

    public void setTicketMostrarTelefono(boolean mostrarTelefono) {
        setBoolean("ticket.mostrarTelefono", mostrarTelefono);
    }

    public boolean getTicketMostrarCif() {
        return getBoolean("ticket.mostrarCif");
    }

    public void setTicketMostrarCif(boolean mostrarCif) {
        setBoolean("ticket.mostrarCif", mostrarCif);
    }

    public boolean getTicketMostrarIva() {
        return getBoolean("ticket.mostrarIva");
    }

    public void setTicketMostrarIva(boolean mostrarIva) {
        setBoolean("ticket.mostrarIva", mostrarIva);
    }

    public String getImpresoraNombre() {
        return getString("impresora.nombre");
    }

    public void setImpresoraNombre(String nombre) {
        setString("impresora.nombre", nombre);
    }

    public int getImpresoraAncho() {
        return getInt("impresora.ancho", 58);
    }

    public void setImpresoraAncho(int ancho) {
        if (ancho != 58 && ancho != 80) {
            ancho = 58;
        }

        setInt("impresora.ancho", ancho);
    }

    public String getImpresoraCodepage() {
        String codepage = getString("impresora.codepage");

        if (codepage.isBlank()) {
            return "CP858_Euro";
        }

        return codepage;
    }

    public void setImpresoraCodepage(String codepage) {
        if (codepage == null || codepage.isBlank()) {
            codepage = "CP858_Euro";
        }

        setString("impresora.codepage", codepage);
    }

    public boolean getImpresoraCortarPapel() {
        return getBoolean("impresora.cortarPapel");
    }

    public void setImpresoraCortarPapel(boolean cortarPapel) {
        setBoolean("impresora.cortarPapel", cortarPapel);
    }

    public boolean getImpresoraAbrirCajon() {
        return getBoolean("impresora.abrirCajon");
    }

    public void setImpresoraAbrirCajon(boolean abrirCajon) {
        setBoolean("impresora.abrirCajon", abrirCajon);
    }

    public String getBackupRuta() {
        return getString("backup.ruta");
    }

    public void setBackupRuta(String ruta) {
        setString("backup.ruta", ruta);
    }

    public String getBackupUltimo() {
        return getString("backup.ultimo");
    }

    public void setBackupUltimo(String ultimo) {
        setString("backup.ultimo", ultimo);
    }

    public String getEmpresaQr() {
        return getString("empresa.qr");
    }

    public void setEmpresaQr(String qr) {
        setString("empresa.qr", qr);
    }

    public boolean getTicketMostrarQr() {
        return getBoolean("ticket.mostrarQr");
    }

    public void setTicketMostrarQr(boolean mostrarQr) {
        setBoolean("ticket.mostrarQr", mostrarQr);
    }

    public void loadTema() {
        String tema = getTema();

        switch (tema) {
            case "Primer Dark" -> Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
            case "Nord Light" -> Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());
            case "Nord Dark" -> Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
            case "Cupertino Light" -> Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
            case "Cupertino Dark" -> Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
            case "Dracula" -> Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
            default -> Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        }
    }

    private String getString(String key) {
        return ajustes.getProperty(key, "");
    }

    private void setString(String key, String value) {
        ajustes.setProperty(key, value == null ? "" : value.trim());
        ajustesUtil.guardarAjustes(ajustes);
    }

    private boolean getBoolean(String key) {
        return Boolean.parseBoolean(ajustes.getProperty(key, "false"));
    }

    private void setBoolean(String key, boolean value) {
        ajustes.setProperty(key, String.valueOf(value));
        ajustesUtil.guardarAjustes(ajustes);
    }

    private int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(ajustes.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void setInt(String key, int value) {
        ajustes.setProperty(key, String.valueOf(value));
        ajustesUtil.guardarAjustes(ajustes);
    }
}