package com.mateo.freetpv.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

//
// Utilizamos SHA-512 con salt para generar un Hash del pin
//

public class HashUtil {
    public String generarSalt(){

        // Generamos un salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);

        // Convertimos el salt a un string
        String salt_string = Base64.getEncoder().encodeToString(salt);
        return salt_string;
    }
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
