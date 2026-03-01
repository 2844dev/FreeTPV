package com.mateo.freetpv.util;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Cojemos el directorio del cual se esta ejecutando
    private String path = System.getProperty("user.dir");

    // Añadimos al directorio el archivo .db
    private String url = "jdbc:sqlite:" + path + "/freetpv.db";

    // Conexion a base de datos
    public void connect() {
        try ( var connection = DriverManager.getConnection(url)) {
            System.out.println("Database connection established");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Creamos las tablas si no existen
    public void initDatabase() {
        var sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre text NOT NULL,"
                + "hash text NOT NULL,"
                + "salt text NOT NULL,"
                + "rol text NOT NULL"
                + ")";
        try (var connection = DriverManager.getConnection(url)) {
            var stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
