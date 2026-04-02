package com.mateo.freetpv.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 *
 * Utilidad para generar Salt y Hash para las contraseñas
 *
 * @author Mateo
 * @since 01/03/2026
 */
public class HashUtil {

    /**
     *
     * Calcula un hash dependiendo del pin
     *
     * @param pin Contraseña introducida
     * @return Devolvemos un hash en {@code String} calculado
     *          desde un pin
     */
    public String hashPin(String pin) {

        // Convertimos el pin en un hash utilizando bcrypt con factor de coste 12
        return BCrypt.withDefaults().hashToString(12, pin.toCharArray());
    }

    /**
     *
     * Verifica si el pin insertado y el guardado son iguales
     *
     * @param pin
     * @param hashAlmacenado
     * @return Devuelve un {@code Boolean} dependiendo de si es correcto o no
     */
    public boolean verificarPin(String pin, String hashAlmacenado) {
        return BCrypt.verifyer().verify(pin.toCharArray(), hashAlmacenado).verified;
    }
}
