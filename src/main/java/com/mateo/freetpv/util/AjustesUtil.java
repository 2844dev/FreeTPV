package com.mateo.freetpv.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class AjustesUtil {

    private static final Logger log = LoggerFactory.getLogger(AjustesUtil.class);

    private static final Path AJUSTES_PATH = Path.of(
            System.getProperty("user.home"),
            ".freetpv",
            "freetpv.properties"
    );

    public Properties cargarAjustes() {
        Properties ajustes = crearAjustesDefecto();

        try {
            Files.createDirectories(AJUSTES_PATH.getParent());

            if (!Files.exists(AJUSTES_PATH)) {
                guardarAjustes(ajustes);
                return ajustes;
            }

            try (FileInputStream in = new FileInputStream(AJUSTES_PATH.toFile())) {
                ajustes.load(in);
            }

            guardarAjustes(ajustes);
            return ajustes;

        } catch (IOException e) {
            log.error("Error al cargar los ajustes", e);
            return ajustes;
        }
    }

    public void guardarAjustes(Properties ajustes) {
        try {
            Files.createDirectories(AJUSTES_PATH.getParent());

            try (FileOutputStream out = new FileOutputStream(AJUSTES_PATH.toFile())) {
                ajustes.store(out, "Configuración FreeTPV");
            }

        } catch (IOException e) {
            log.error("Error al guardar los ajustes", e);
        }
    }

    private Properties crearAjustesDefecto() {
        Properties ajustes = new Properties();

        ajustes.setProperty("tema", "Primer Light");

        ajustes.setProperty("empresa.nombre", "");
        ajustes.setProperty("empresa.cif", "");
        ajustes.setProperty("empresa.direccion", "");
        ajustes.setProperty("empresa.codigoPostal", "");
        ajustes.setProperty("empresa.ciudad", "");
        ajustes.setProperty("empresa.telefono", "");
        ajustes.setProperty("empresa.web", "");
        ajustes.setProperty("empresa.qr", "");

        ajustes.setProperty("ticket.titulo", "TICKET DE VENTA");
        ajustes.setProperty("ticket.mensajeFinal", "Gracias por su compra");
        ajustes.setProperty("ticket.mostrarWeb", "true");
        ajustes.setProperty("ticket.mostrarTelefono", "true");
        ajustes.setProperty("ticket.mostrarCif", "true");
        ajustes.setProperty("ticket.mostrarIva", "true");
        ajustes.setProperty("ticket.mostrarQr", "false");

        ajustes.setProperty("impresora.nombre", "");
        ajustes.setProperty("impresora.ancho", "58");
        ajustes.setProperty("impresora.codepage", "CP858_Euro");
        ajustes.setProperty("impresora.cortarPapel", "true");
        ajustes.setProperty("impresora.abrirCajon", "false");

        ajustes.setProperty("backup.ruta", "");
        ajustes.setProperty("backup.ultimo", "");
        ajustes.setProperty("backup.frecuencia", "7");

        return ajustes;
    }
}