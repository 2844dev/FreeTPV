package com.mateo.freetpv.util;

import java.io.*;
import java.util.Properties;

public class AjustesUtil {
    // Cojemos el directorio del cual se esta ejecutando
    private String path = System.getProperty("user.dir") + "/freetpv.properties";

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
                System.out.println(e.getMessage());
            }
        }
        try (FileInputStream in = new FileInputStream(archivo)) {
            ajustes.load(in);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ajustes;
    }

    public void guardarAjustes(Properties ajustes) {
        try (FileOutputStream out = new FileOutputStream(path)) {
            ajustes.store(out, "Configuración FreeTPV");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
