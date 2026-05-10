package com.mateo.freetpv.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConnection {

    private static final DatabaseConnection instancia = new DatabaseConnection();

    private static final Logger log = LoggerFactory.getLogger(DatabaseConnection.class);

    // Cogemos el directorio del cual se está ejecutando
    private final String path = System.getProperty("user.home") + "/.freetpv";

    // Añadimos al directorio el archivo .db
    private final String url = "jdbc:sqlite:" + path + "/freetpv.db";

    // Ponemos el constructor privado para Singleton
    private DatabaseConnection() { }

    // Creamos método para coger instancia desde otras clases
    public static DatabaseConnection getInstancia() {
        return instancia;
    }

    // Conectarse a la bd en otras clases
    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url);

        // Activamos las FK en cada conexión
        try (var stmt = connection.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        }
        return connection;
    }

    // Creamos las tablas si no existen
    public void initDatabase() {

        var tabla_usuarios = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre TEXT NOT NULL UNIQUE,"
                + "hash TEXT NOT NULL,"
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
                + "precio INTEGER NOT NULL," // Guardado en centimos
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
                + "fondo_inicial INTEGER NOT NULL,"
                + "total_efectivo_esperado INTEGER NOT NULL,"
                + "total_efectivo_real INTEGER NOT NULL,"
                + "total_tarjeta INTEGER NOT NULL,"
                + "descuadre INTEGER NOT NULL"
                + ")";

        var tabla_mesas = "CREATE TABLE IF NOT EXISTS mesas ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "numero INTEGER NOT NULL,"
                + "zona_id INTEGER NOT NULL,"
                + "pedido_id INTEGER UNIQUE," // NULL si la mesa está libre
                + "FOREIGN KEY (zona_id) REFERENCES zonas(id)"
                + ")";

        var tabla_pedidos = "CREATE TABLE IF NOT EXISTS pedidos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "fecha TEXT NOT NULL,"
                + "total INTEGER NOT NULL,"
                + "estado TEXT NOT NULL," // Abierto, Pagado
                + "metodo_pago TEXT," // Efectivo, Tarjeta o todavía no se ha cobrado
                + "usuario_id INTEGER NOT NULL,"
                + "mesa_id INTEGER,"
                + "caja_id INTEGER NOT NULL,"
                + "cliente_id INTEGER,"
                + "FOREIGN KEY (usuario_id) REFERENCES usuarios(id),"
                + "FOREIGN KEY (mesa_id) REFERENCES mesas(id),"
                + "FOREIGN KEY (caja_id) REFERENCES caja(id),"
                + "FOREIGN KEY (cliente_id) REFERENCES clientes(id)"
                + ")";

        var tabla_lineas_pedidos = "CREATE TABLE IF NOT EXISTS lineas_pedidos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre_producto TEXT NOT NULL,"
                + "cantidad INTEGER NOT NULL,"
                + "precio_unitario INTEGER NOT NULL,"
                + "iva INTEGER NOT NULL,"
                + "subtotal INTEGER NOT NULL,"
                + "pedido_id INTEGER NOT NULL,"
                + "producto_id INTEGER NOT NULL,"
                + "FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE,"
                + "FOREIGN KEY (producto_id) REFERENCES productos(id)"
                + ")";

        // Intentar conexión y ejecutar todas las consultas sql
        try (var connection = getConnection();
            var stmt = connection.createStatement()) {
            stmt.execute(tabla_usuarios);
            stmt.execute(tabla_categorias);
            stmt.execute(tabla_zonas);
            stmt.execute(tabla_clientes);

            stmt.execute(tabla_productos);
            stmt.execute(tabla_mesas);
            stmt.execute(tabla_pedidos);
            stmt.execute(tabla_lineas_pedidos);
            stmt.execute(tabla_caja);
            log.info("Base de datos creada/cargada correctamente");
        } catch (SQLException e) {
            log.error("Error al crear tablas de la base de datos.", e);
        }
    }
}
