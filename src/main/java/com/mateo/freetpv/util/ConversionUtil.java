package com.mateo.freetpv.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

        String dineroFormat = dinero.trim().replace(",", ".");

        try {
            BigDecimal dineroDecimal = new BigDecimal(dineroFormat);
            // Convertimos el BigDecimal a un int utilizando intValueExact para que no permita .5
            int dineroFinal = dineroDecimal.multiply(new BigDecimal(100)).intValueExact();
            return Optional.of(dineroFinal);
        } catch (Exception e) {
            log.error("Error al convertir a centimos" ,e);
            return Optional.empty();
        }
    }

    public static Optional<String> centimosEuros(int centimos) {
        try {
            BigDecimal centimosDecimal = new BigDecimal(centimos);
            BigDecimal euros = centimosDecimal.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
            return Optional.of(euros.toString().replace(".", ","));
        } catch (Exception e) {
            log.error("Error al convertir a euros", e);
            return Optional.empty();
        }
    }
}
