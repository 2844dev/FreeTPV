package com.mateo.freetpv.dao;

import com.mateo.freetpv.model.Usuario;
import com.mateo.freetpv.util.DatabaseConnection;
import com.mateo.freetpv.util.HashUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private HashUtil hashUtil = new HashUtil();
    private DatabaseConnection db = new DatabaseConnection();

    public void crearUsuario(String nombre, String pin, String rol) {
        String salt = hashUtil.generarSalt();
        String hash = hashUtil.hashPin(pin, salt);

        // Utilizamos prepareStatement para evitar SQL injection
        String sql = "INSERT INTO usuarios (nombre, hash, salt, rol) VALUES (?, ?, ?, ?)";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, hash);
            stmt.setString(3, salt);
            stmt.setString(4, rol);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Usuario validarLogin(String nombre, String pin) {

        // Buscamos de la tabla usuarios donde el nombre coincide
        String sql = "SELECT * FROM usuarios WHERE nombre = ?";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            // Comprobamos si hay resultados
            if (rs.next()) {
                String hashAlmacenado = rs.getString("hash");
                String saltAlmacenado = rs.getString("salt");

                // Comparamos el hash de la base de datos con el hash insertado
                String hashInsertado = hashUtil.hashPin(pin, saltAlmacenado);

                // Si coincide hacemos consultas y creamos un objeto usuario
                if (hashAlmacenado.equals(hashInsertado)) {
                    int id  = rs.getInt("id");
                    String rol = rs.getString("rol");
                    return new Usuario(id, nombre, hashAlmacenado, saltAlmacenado, rol);
                } else  {
                    return null;
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
