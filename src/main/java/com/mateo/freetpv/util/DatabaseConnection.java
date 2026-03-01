package com.mateo.freetpv.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Cojemos el directorio del cual se esta ejecutando
    private String path = System.getProperty("user.dir");

    // Añadimos al directorio el archivo .db
    private String url = "jdbc:sqlite:" + path + "/freetpv.db";

    // Conectarse a la bd en otras clases
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Conexion a base de datos
    public void connect() {
        try ( var connection = DriverManager.getConnection(url)) {
            System.out.println("Base de datos conectada");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Creamos las tablas si no existen
    public void initDatabase() {

        // Crear tabla usuarios
        var tabla_usuarios = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre text NOT NULL,"
                + "hash text NOT NULL,"
                + "salt text NOT NULL,"
                + "rol text NOT NULL"
                + ")";

        // Crear tabla categorias
        var tabla_categorias = "CREATE TABLE IF NOT EXISTS categorias ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre text NOT NULL"
                + ")";

        // Crear tabla productos
        var tabla_productos = "CREATE TABLE IF NOT EXISTS productos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre text NOT NULL,"
                + "nombre_ticket text NOT NULL,"
                + "imagen text,"
                + "precio real NOT NULL,"
                + "iva integer NOT NULL,"
                + "categoria_id integer NOT NULL,"
                + "FOREIGN KEY (categoria_id) REFERENCES categorias(id)"
                + ")";

        // Crear tabla mesas
        var tabla_mesas = "CREATE TABLE IF NOT EXISTS mesas ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "numero integer NOT NULL,"
                + "estado text NOT NULL"
                + ")";

        // Crear tabla pedidos
        var tabla_pedidos = "CREATE TABLE IF NOT EXISTS pedidos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "fecha text NOT NULL,"
                + "total real NOT NULL,"
                + "estado text NOT NULL,"
                + "usuario_id integer NOT NULL,"
                + "mesa_id integer NOT NULL,"
                + "FOREIGN KEY (usuario_id) REFERENCES usuarios(id),"
                + "FOREIGN KEY (mesa_id) REFERENCES mesas(id)"
                + ")";

        // Crear tabla liena pedidos
        var tabla_lineas_pedidos = "CREATE TABLE IF NOT EXISTS lineas_pedidos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "cantidad integer NOT NULL,"
                + "precio_unitario real NOT NULL,"
                + "pedido_id integer NOT NULL,"
                + "producto_id integer NOT NULL,"
                + "FOREIGN KEY (pedido_id) REFERENCES pedidos(id),"
                + "FOREIGN KEY (producto_id) REFERENCES productos(id)"
                + ")";

        // Crear tabla clientes
        var tabla_clientes = "CREATE TABLE IF NOT EXISTS clientes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre text NOT NULL,"
                + "nif text NOT NULL,"
                + "direccion text NOT NULL,"
                + "telefono text NOT NULL,"
                + "email text NOT NULL"
                + ")";

        // Intentar conexion y ejecutar todas las consultas sql
        try (var connection = DriverManager.getConnection(url)) {
            var stmt = connection.createStatement();
            stmt.execute(tabla_usuarios);
            stmt.execute(tabla_categorias);
            stmt.execute(tabla_productos);
            stmt.execute(tabla_mesas);
            stmt.execute(tabla_pedidos);
            stmt.execute(tabla_lineas_pedidos);
            stmt.execute(tabla_clientes);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
