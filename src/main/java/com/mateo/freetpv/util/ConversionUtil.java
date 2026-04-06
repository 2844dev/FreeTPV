package com.mateo.freetpv.util;

public class ConversionUtil {

    /**
     *
     * Convierte un int a un boolean
     *
     * @param i Int que convertir a boolean
     * @return Devuelve {@code true} si llega {@code 1},
     *         si no, devuelve {@code false}
     */
    public static boolean intBoolean(int i) { return i == 1; }

    /**
     *
     * Convierte un boolean a un int
     *
     * @param i Boolean que convertir a int
     * @return Devuelve {@code 1} si llega {@code True},
     *         si no, devuelve {@code 0}
     */
    public static int booleanInt(boolean i) { return i ? 1 : 0; }
}
