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

/**
 * Clase DAO, permite acceder a la base de datos para hacer operaciones CRU y consultas
 * relacionadas con los objetos {@link Usuario}.
 *
 * @author Mateo
 * @since 01/03/2026
 */
public class UsuarioDAO {
    private HashUtil hashUtil = new HashUtil();
    private DatabaseConnection db = DatabaseConnection.getInstancia();

    /**
     *
     * Comprueba si ya existe un usuario con ese nombre en la BD
     *
     * @param nombre Nombre del usuario
     * @return Devuelve {@code true} si ya existe un usuario con ese nombre
     *         o si da error. Si no, devuelve {@code false}
     */
    public boolean existeUsuario(String nombre) {
       String sql = "SELECT nombre FROM usuarios WHERE nombre = ?";
       try (var connection = db.getConnection();
            var stmt = connection.prepareStatement(sql)) {
           stmt.setString(1, nombre);
           ResultSet rs = stmt.executeQuery();
           return rs.next();
       } catch (SQLException e) {
           System.out.println(e.getMessage());
           return true;
       }
    }

    /**
     * Recupera una lista con los nombres de todos los usuarios que
     * tienen un estado activo en la base de datos.
     *
     * @return Una {@code List<String>} con los nombres de los usuarios activos.
     *         Devuelve una lista vacía si no hay usuarios activos o {@code null}
     *         si ocurre un error durante la conexión o la consulta.
     */
    // Descripción hecha por modo IA de Google.
    public List<String> obtenerNombres() {

        // Seleccionamos todos los valores de la columna nombres de la tabla usuarios que tengan estado 1 (Activos)
        String sql = "SELECT nombre FROM usuarios WHERE estado = 1";
        try (var connection = db.getConnection()) {
            var stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Creamos una lista de arrays de string y añadimos todos a ella
            List<String> nombres = new ArrayList<>();

            // Añadimos todos los valores a la lista
            while (rs.next()) {
                nombres.add(rs.getString("nombre"));
            }
            return nombres;
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Recupera una lista de tipo {@link Usuario} con todos los datos de todos los usuarios.
     *
     * @param filtro 0 -> devuelve todos los usuarios 1 -> Devuelve solo los activos
     *
     * @return Devuelve una {@code List<Usuario>} con todos los Usuarios
     * guardados en la base de datos. Devuelve una lista vacia en caso de
     * que no hubiera resultados o {@code null} en caso de error.
     */
    public List<Usuario> obtenerUsuarios(Boolean filtro) {

        // Consulta por defecto
        String sql = "SELECT * FROM usuarios";

        // Comprobamos si filtramos o no
        if (filtro) {
            sql = "SELECT * FROM usuarios WHERE estado = 1";
        }

        // Nos conectamos a la base de datos
        try (var connection = db.getConnection()) {
            var stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Creamos una lista de usuarios y todos los atributos que tiene un usuario
            List<Usuario> usuarios = new ArrayList<>();
            int id;
            String nombre;
            String hash;
            String salt;
            String rol;
            String fecha_creacion;
            boolean estado;
            Usuario usuario;

            // Probamos a crear usuarios hasta que no haya mas usuarios
            while (rs.next()) {
                id = rs.getInt("id");
                nombre = rs.getString("nombre");
                hash = rs.getString("hash");
                salt = rs.getString("salt");
                rol = rs.getString("rol");
                fecha_creacion = rs.getString("fecha_creacion");

                // Pasamos el estado de un int a un boolean
                estado = intBoolean(rs.getInt("estado"));

                usuario = new Usuario(id, nombre, hash, salt, rol, estado, fecha_creacion);
                usuarios.add(usuario);
            }
            return usuarios;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *
     * Edita los valores de un usuario acorde a los parametros
     * y los guarda en la BD
     * <p>
     * Genera un nuevo salt y hash si se cambia el pin.
     *
     * @param usuario Usuario que editaremos
     * @param nombre Nuevo nombre de usuario
     * @param pin Opcional
     * @param rol Admin, Camarero
     * @param estado Activo o no activo (true, false)
     */
    public void editarUsuario(Usuario usuario, String nombre, String pin, String rol, boolean estado) {
        int id = usuario.getId();
        int estado_int = booleanInt(estado);

        // Escribimos el caso default si no tiene que cambiar el pin
        String sql = "UPDATE usuarios SET nombre = ?, rol = ?, estado = ? WHERE id = ?";
        String salt = null;
        String hash = null;

        // Si el pin se cambia generamos nuevo hash y salt y cambiamos la consulta
        if (pin != null && !pin.isEmpty()) {
            salt = hashUtil.generarSalt();
            hash = hashUtil.hashPin(pin, salt);
            sql = "UPDATE usuarios SET nombre = ?, hash = ?, salt = ?, rol = ?, estado = ? WHERE id = ?";
        }
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(sql)) {

            // En el caso de que se cambie el pin ponemos los datos en su orden
            if (hash != null && salt != null) {
                stmt.setString(1, nombre);
                stmt.setString(2, hash);
                stmt.setString(3, salt);
                stmt.setString(4, rol);
                stmt.setInt(5, estado_int);
                stmt.setInt(6, id);

                // Si no se cambia el pin ponemos los datos en el orden del caso default
            } else {
                stmt.setString(1, nombre);
                stmt.setString(2, rol);
                stmt.setInt(3, estado_int);
                stmt.setInt(4, id);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     * Crea un nuevo usuario con hash y salt y lo guarda
     * en la base de datos
     *
     * @param nombre Nombre de usuario
     * @param pin Pin del usuario
     * @param rol Rol del usuario
     */
    public void crearUsuario(String nombre, String pin, String rol) {
        String salt = hashUtil.generarSalt();
        String hash = hashUtil.hashPin(pin, salt);

        // Cogemos la fecha actual y la ponemos automaticamente en la base de datos
        LocalDate fecha = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fecha_creacion = fecha.format(formato);

        // Hacemos que el estado al crear un usuario sea activo por defecto
        int estado_int = 1;

        // Utilizamos prepareStatement para evitar SQL injection
        String sql = "INSERT INTO usuarios (nombre, hash, salt, rol, estado, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?)";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, hash);
            stmt.setString(3, salt);
            stmt.setString(4, rol);
            stmt.setInt(5, estado_int);
            stmt.setString(6, fecha_creacion);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     * Valida un login comparando hashes
     *
     * @param nombre
     * @param pin
     * @return Si el hash calculado es igual al guardado devolvemos un {@code Usuario}
     * del tipo {@link Usuario}, si no es igual o da error devolvemos {@code null}
     */
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

                    // Pasamos el estado a un boolean
                    int estado_int = rs.getInt("estado");
                    boolean estado = intBoolean(estado_int);

                    // Creamos el usuario validado
                    String fecha_creacion = rs.getString("fecha_creacion");
                    return new Usuario(id, nombre, hashAlmacenado, saltAlmacenado, rol, estado, fecha_creacion);
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

    /**
     *
     * Convierte un int a un boolean
     *
     * @param i Int que convertir a boolean
     * @return Devuelve {@code true} si llega {@code 1},
     *         si no, devuelve {@code false}
     */
    private boolean intBoolean(int i) {
        return i == 1;
    }

    /**
     *
     * Convierte un boolean a un int
     *
     * @param i Boolean que convertir a int
     * @return Devuelve {@code 1} si llega {@code True},
     *         si no, devuelve {@code 0}
     */
    private int booleanInt(boolean i) {
        if (i) return 1;
        return 0;
    }
}
