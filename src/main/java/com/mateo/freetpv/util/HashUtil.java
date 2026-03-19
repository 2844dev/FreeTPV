package com.mateo.freetpv.util;

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

    /**
     * Genera un salt aleatorio de 32 bytes
     *
     * @return Devuelve un {@code String} de un salt aleatorio
     */
    public String generarSalt(){

        // Generamos un salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);

        // Convertimos el salt a un string y lo devolvemos
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     *
     * Calcula un hash dependiendo del pin y el salt
     *
     * @param pin Contraseña introducida
     * @param salt Salt del usuario
     * @return Devolvemos un hash en {@code String} calculado
     *          con un pin y un salt.
     */
    public String hashPin(String pin, String salt) {

        // Inicializamos variable
        String hashedPassword_string = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            // Convertimos el salt a bytes
            byte[] salt_bytes = Base64.getDecoder().decode(salt);

            // Indicamos el salt que utilizara md
            md.update(salt_bytes);

            // Generamos el hash
            byte[] hashedPassword = md.digest(pin.getBytes(StandardCharsets.UTF_8));

            // Pasamos el hash a string
            hashedPassword_string = Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return hashedPassword_string;
    }
}
