package com.mateo.freetpv.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConnection {

    private static DatabaseConnection instancia;

    private static final Logger log = LoggerFactory.getLogger(DatabaseConnection.class);

    // Cojemos el directorio del cual se esta ejecutando
    private String path = System.getProperty("user.home");

    // Añadimos al directorio el archivo .db
    private String url = "jdbc:sqlite:" + path + "/.freetpv/freetpv.db";

    // Ponemos el constructor privado para Singleton
    private DatabaseConnection() {}

    // Creamos metodo para coger instancia desde otras clases
    public static DatabaseConnection getInstancia() {
        if (instancia == null) {
            instancia = new DatabaseConnection();
        }
        return instancia;
    }

    // Conectarse a la bd en otras clases
    public Connection getConnection() {
        try {
            log.info("Base de datos conectada");
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            log.error("Error al conectar a la base de datos", e);
            return null;
        }
    }

    // Conexion a base de datos
    public void connect() {
        try (var connection = DriverManager.getConnection(url)) {
            log.info("Base de datos inicializada");
        } catch (SQLException e) {
            log.error("Error al conectar a la base de datos.", e);
        }
    }

    // Creamos las tablas si no existen
    public void initDatabase() {

        var tabla_usuarios = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre TEXT NOT NULL UNIQUE,"
                + "hash TEXT NOT NULL,"
                + "salt TEXT NOT NULL,"
                + "rol TEXT NOT NULL,"
                + "estado INTEGER NOT NULL," // 0 no activo 1 activo
                + "fecha_creacion TEXT NOT NULL"
                + ")";

        var tabla_categorias = "CREATE TABLE IF NOT EXISTS categorias ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre TEXT NOT NULL UNIQUE"
                + ")";

        var tabla_zonas = "CREATE TABLE IF NOT EXISTS zonas ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre TEXT NOT NULL"
                + ")";

        var tabla_productos = "CREATE TABLE IF NOT EXISTS productos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre TEXT NOT NULL,"
                + "nombre_ticket TEXT NOT NULL,"
                + "imagen TEXT,"
                + "precio REAL NOT NULL,"
                + "iva INTEGER NOT NULL,"
                + "estado INTEGER NOT NULL,"
                + "favorito INTEGER NOT NULL,"
                + "categoria_id INTEGER NOT NULL,"
                + "FOREIGN KEY (categoria_id) REFERENCES categorias(id)"
                + ")";

        var tabla_clientes = "CREATE TABLE IF NOT EXISTS clientes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre TEXT NOT NULL,"
                + "nif TEXT NOT NULL UNIQUE,"
                + "direccion TEXT NOT NULL,"
                + "telefono TEXT,"
                + "email TEXT"
                + ")";

        var tabla_caja = "CREATE TABLE IF NOT EXISTS caja ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "fecha_apertura TEXT NOT NULL,"
                + "fecha_cierre TEXT," // NULL hasta que se cierre
                + "fondo_inicial REAL NOT NULL,"
                + "total_efectivo_esperado REAL NOT NULL,"
                + "total_efectivo_real REAL NOT NULL,"
                + "total_tarjeta REAL NOT NULL,"
                + "descuadre REAL NOT NULL"
                + ")";

        var tabla_mesas = "CREATE TABLE IF NOT EXISTS mesas ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "numero INTEGER NOT NULL,"
                + "estado INTEGER NOT NULL," // 0 LIBRE, 1 OCUPADA
                + "zona_id INTEGER NOT NULL,"
                + "pedido_id INTEGER UNIQUE," // NULL si la mesa está libre
                + "FOREIGN KEY (zona_id) REFERENCES zonas(id)"
                + ")";

        var tabla_pedidos = "CREATE TABLE IF NOT EXISTS pedidos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "fecha TEXT NOT NULL,"
                + "total REAL NOT NULL,"
                + "estado TEXT NOT NULL," // Abierto, Pagado
                + "metodo_pago TEXT NULL," // Efectivo, Tarjeta o todavia no se ha cobrado
                + "usuario_id INTEGER NOT NULL,"
                + "mesa_id INTEGER NOT NULL,"
                + "caja_id INTEGER NOT NULL,"
                + "cliente_id INTEGER,"
                + "FOREIGN KEY (usuario_id) REFERENCES usuarios(id),"
                + "FOREIGN KEY (mesa_id) REFERENCES mesas(id),"
                + "FOREIGN KEY (caja_id) REFERENCES caja(id),"
                + "FOREIGN KEY (cliente_id) REFERENCES clientes(id)"
                + ")";

        var tabla_lineas_pedidos = "CREATE TABLE IF NOT EXISTS lineas_pedidos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "cantidad INTEGER NOT NULL,"
                + "precio_unitario REAL NOT NULL,"
                + "iva INTEGER NOT NULL,"
                + "subtotal REAL NOT NULL,"
                + "pedido_id INTEGER NOT NULL,"
                + "producto_id INTEGER NOT NULL,"
                + "FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE,"
                + "FOREIGN KEY (producto_id) REFERENCES productos(id)"
                + ")";

        // Intentar conexion y ejecutar todas las consultas sql
        try (var connection = DriverManager.getConnection(url)) {
            var stmt = connection.createStatement();
            stmt.execute(tabla_usuarios);
            stmt.execute(tabla_categorias);
            stmt.execute(tabla_zonas);
            stmt.execute(tabla_productos);
            stmt.execute(tabla_mesas);
            stmt.execute(tabla_pedidos);
            stmt.execute(tabla_lineas_pedidos);
            stmt.execute(tabla_clientes);
            stmt.execute(tabla_caja);
        } catch (SQLException e) {
            log.error("Error al crear tablas de la base de datos.", e);
        }
    }
}
