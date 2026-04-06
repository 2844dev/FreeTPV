package com.mateo.freetpv.dao;

import com.mateo.freetpv.model.Producto;
import com.mateo.freetpv.util.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

import static com.mateo.freetpv.util.ConversionUtil.booleanInt;

public class ProductoDAO {

    private static final Logger log = LoggerFactory.getLogger(ProductoDAO.class);
    private final DatabaseConnection db = DatabaseConnection.getInstancia();

    public boolean crearProducto(String nombre, String nombre_ticket, String imagen, int precio, int iva, boolean favorito, int categoria_id) {

        String sql = "INSERT INTO productos (nombre, nombre_ticket, imagen, precio, iva, estado, favorito, categoria_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Forzamos que un producto este activo siempre al crearse
        int estado = 1;
        int favorito_int = booleanInt(favorito);
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, nombre_ticket);
            stmt.setString(3, imagen);
            stmt.setInt(4, precio);
            stmt.setInt(5, iva);
            stmt.setInt(6, estado);
            stmt.setInt(7, favorito_int);
            stmt.setInt(8, categoria_id);
            int filas = stmt.executeUpdate();
            log.info("Producto creado: {}", nombre);
            return filas > 0;
        } catch (SQLException e) {
            log.error("Error al crear un producto", e);
            return false;
        }
    }

//    public boolean editarProducto(Producto producto, String nombre, String nombre_ticket, String imagen, int precio, int iva, boolean estado, boolean favorito, int categoria_id) {
//
//    }
//
//    public List<Producto> obtenerProductos(String buscar, String categoria, boolean activo, boolean noactivo) {
//
//    }
//
//    public boolean existeProducto(String nombre) {
//
//    }
}
