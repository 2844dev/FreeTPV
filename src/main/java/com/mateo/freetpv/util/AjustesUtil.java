package com.mateo.freetpv.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class AjustesUtil {
    private static final Logger log = LoggerFactory.getLogger(AjustesUtil.class);
    // Cojemos el directorio del cual se esta ejecutando
    private String path = System.getProperty("user.home") + "/.freetpv/freetpv.properties";

    public Properties cargarAjustes() {
        Properties ajustes = new Properties();
        File archivo = new File(path);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                ajustes.setProperty( "Tema", "Primer Light");
                guardarAjustes(ajustes);
                return ajustes;
            } catch (IOException e) {
                log.error("Error al crear los ajustes por defecto", e);
            }
        }
        try (FileInputStream in = new FileInputStream(archivo)) {
            ajustes.load(in);
        } catch (IOException e) {
            log.error("Error al cargar los ajustes", e);
        }
        return ajustes;
    }

    public void guardarAjustes(Properties ajustes) {
        try (FileOutputStream out = new FileOutputStream(path)) {
            ajustes.store(out, "Configuración FreeTPV");
        } catch (IOException e) {
            log.error("Error al guardar los ajustes", e);
        }
    }
}
