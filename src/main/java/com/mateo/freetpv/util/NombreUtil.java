package com.mateo.freetpv.util;

import java.util.List;
import java.util.Random;

public class NombreUtil {
    // Lista con todos los nombres
    List<String> lista = List.of("Yui Hirasawa", "Azusa Nakano", "Ritsu Tainaka",
                                "Mio Akiyama", "Mugi Kotobuki");

    // Metodo para sacar un nombre aleatorio de la lista
    public String getNombre() {
        Random r = new Random();
        int i = r.nextInt(lista.size());
        return lista.get(i);
    }
}
