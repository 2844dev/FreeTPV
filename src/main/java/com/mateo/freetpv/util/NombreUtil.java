package com.mateo.freetpv.util;

import java.util.List;
import java.util.Random;

public class NombreUtil {
    // Lista con todos los nombres
    private static final List<String> lista = List.of(
            "Yui Hirasawa", "Azusa Nakano", "Ritsu Tainaka", "Mio Akiyama", "Mugi Kotobuki", //K-ON!
            "Reina Kousaka", "Kumiko Oumae", "Asuka Tanaka" //Hibike! Euphonium
            );

    private final Random r = new Random();

    // Metodo para sacar un nombre aleatorio de la lista
    public String getNombre() {
        int i = r.nextInt(lista.size());
        return lista.get(i);
    }
}
