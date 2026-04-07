package com.mateo.freetpv.dao;

import com.mateo.freetpv.model.Producto;
import com.mateo.freetpv.util.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mateo.freetpv.util.ConversionUtil.booleanInt;
import static com.mateo.freetpv.util.ConversionUtil.intBoolean;

public class ProductoDAO {

    private static final Logger log = LoggerFactory.getLogger(ProductoDAO.class);
    private final DatabaseConnection db = DatabaseConnection.getInstancia();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

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
    public List<Producto> obtenerProductos(String buscar, int categoria, boolean activo, boolean noactivo) {
        if (!activo && !noactivo) return new ArrayList<>();

        // Consulta por defecto
        StringBuilder sql = new StringBuilder("SELECT * FROM productos WHERE 1=1 AND categoria_id = ?");

        // Comprobamos si buscamos
        if (buscar != null && !buscar.isEmpty()) {
            sql.append(" AND nombre LIKE ? COLLATE NOCASE");
        }
        // Comprobamos si filtramos por estado (OR)
        if (activo && !noactivo) {
            sql.append(" AND estado = 1");
        }
        if (!activo && noactivo) {
            sql.append(" AND estado = 0");
        }

        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(sql.toString())) {
            stmt.setInt(1, categoria);
            if (buscar != null && !buscar.isEmpty()) {
                stmt.setString(2, "%" + buscar + "%");
            }
            ResultSet rs = stmt.executeQuery();

            List<Producto> productos = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String nombre_ticket = rs.getString("nombre_ticket");
                String imagen = rs.getString("imagen");
                int precio = rs.getInt("precio");
                int iva = rs.getInt("iva");
                boolean estado = intBoolean(rs.getInt("estado"));
                boolean favorito = intBoolean(rs.getInt("favorito"));
                int categoria_id = rs.getInt("categoria_id");

                Producto producto = new Producto(id,nombre,nombre_ticket, imagen, precio, iva, estado, favorito, categoria_id);
                productos.add(producto);
            }
            return productos;
        } catch (SQLException e) {
            log.error("Error al obtener todos los datos de productos", e);
            return new ArrayList<>();
        }
    }
//
//    public boolean existeProducto(String nombre) {
//
//    }
}
