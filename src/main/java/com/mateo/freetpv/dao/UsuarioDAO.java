package com.mateo.freetpv.dao;

import com.mateo.freetpv.model.Usuario;
import com.mateo.freetpv.util.DatabaseConnection;
import com.mateo.freetpv.util.HashUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private HashUtil hashUtil = new HashUtil();
    private DatabaseConnection db = new DatabaseConnection();

    // Nombres para el combobox de ventana login
    public List<String> obtenerNombres() {

        // Seleccionamos todos los valores de la columna nombres de la tabla usuarios
        String sql = "SELECT nombre FROM usuarios";
        try (var connection = db.getConnection()) {
            var stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Creamos una lista de arrays de string y añadimos todos a ella
            List<String> nombres = new ArrayList<>();

            // Vamos por todos los valores, si no hay ninguno devolvemos lista vacia
            while (rs.next()) {
                nombres.add(rs.getString("nombre"));
            }
            return nombres;
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void crearUsuario(String nombre, String pin, String rol) {
        String salt = hashUtil.generarSalt();
        String hash = hashUtil.hashPin(pin, salt);

        // Cogemos la fecha actual y la ponemos automaticamente en la base de datos
        LocalDate fecha = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fecha_creacion = fecha.format(formato);

        // Utilizamos prepareStatement para evitar SQL injection
        String sql = "INSERT INTO usuarios (nombre, hash, salt, rol, fecha_creacion) VALUES (?, ?, ?, ?, ?)";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, hash);
            stmt.setString(3, salt);
            stmt.setString(4, rol);
            stmt.setString(5, fecha_creacion);
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
