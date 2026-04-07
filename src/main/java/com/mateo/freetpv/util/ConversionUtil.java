package com.mateo.freetpv.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ConversionUtil {

    private static final Logger log = LoggerFactory.getLogger(ConversionUtil.class);

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

    /**
     *
     * Convierte los euros de un formulario a centimos
     *
     * @return
     */
    public static Optional<Integer> eurosCentimos(String dinero) {
        if (!dinero.contains("," || !dinero.contains(",")))
        String regex = "[,\\.]";
        String[] total = dinero.split(regex);
        try {
            int euros = Integer.parseInt(total[0]);
            int centimos = Integer.parseInt(total[1]);
        } catch (NumberFormatException e) {
            log.error("Error al pasar de euros a centimos", e);
            return Optional.empty();
        }
    }
}
