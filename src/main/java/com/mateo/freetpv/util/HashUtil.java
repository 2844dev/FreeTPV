package com.mateo.freetpv.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 *
 * Utilidad para generar Salt y Hash para las contraseñas
 *
 * @author Mateo
 * @since 01/03/2026
 */
public class HashUtil {

    private static final Logger log = LoggerFactory.getLogger(HashUtil.class);

//    /**
//     * Genera un salt aleatorio de 32 bytes
//     *
//     * @return Devuelve un {@code String} de un salt aleatorio
//     */
//    public String generarSalt(){
//
//        // Generamos un salt
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[32];
//        random.nextBytes(salt);
//
//        // Convertimos el salt a un string y lo devolvemos
//        return Base64.getEncoder().encodeToString(salt);
//    }

    /**
     *
     * Calcula un hash dependiendo del pin y el salt
     *
     * @param pin Contraseña introducida
     * @return Devolvemos un hash en {@code String} calculado
     *          con un pin y un salt.
     */
    public String hashPin(String pin) {

        // Convertimos el pin en un hash utilizando bcrypt con factor de coste 12
        return BCrypt.withDefaults().hashToString(12, pin.toCharArray());
    }

    public boolean verificarPin(String pin, String hashAlmacenado) {
        return BCrypt.verifyer().verify(pin.toCharArray(), hashAlmacenado).verified;
    }
}
