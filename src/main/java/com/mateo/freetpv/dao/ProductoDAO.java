package com.mateo.freetpv.dao;

import com.mateo.freetpv.model.Producto;
import com.mateo.freetpv.util.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mateo.freetpv.util.ConversionUtil.booleanInt;
import static com.mateo.freetpv.util.ConversionUtil.intBoolean;

public class ProductoDAO {

    private static final Logger log = LoggerFactory.getLogger(ProductoDAO.class);
    private final DatabaseConnection db = DatabaseConnection.getInstancia();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    public Optional<Integer> crearProducto(String nombre, String nombre_ticket, String imagen, int precio, int iva, boolean favorito, int categoria_id) {

        String sql = "INSERT INTO productos (nombre, nombre_ticket, imagen, precio, iva, estado, favorito, categoria_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Forzamos que un producto este activo siempre al crearse
        int estado = 1;
        int favorito_int = booleanInt(favorito);
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nombre);
            stmt.setString(2, nombre_ticket);
            stmt.setString(3, imagen);
            stmt.setInt(4, precio);
            stmt.setInt(5, iva);
            stmt.setInt(6, estado);
            stmt.setInt(7, favorito_int);
            stmt.setInt(8, categoria_id);
            stmt.executeUpdate();
            log.info("Producto creado: {}", nombre);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return Optional.of(rs.getInt(1));
            }
            return Optional.empty();
        } catch (SQLException e) {
            log.error("Error al crear un producto", e);
            return Optional.empty();
        }
    }

    public boolean editarProducto(Producto producto, String nombre, String nombre_ticket, String imagen, int precio, int iva, boolean estado, boolean favorito, int categoria_id) {
        int id = producto.getId();
        int estadoInt = booleanInt(estado);
        int favoritoInt = booleanInt(favorito);

        String sql = "UPDATE productos SET nombre = ?, nombre_ticket = ?, imagen = ?, precio = ?, iva = ?, estado = ?, favorito = ?, categoria_id = ? WHERE id = ?";

        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, nombre_ticket);
            stmt.setString(3, imagen);
            stmt.setInt(4, precio);
            stmt.setInt(5, iva);
            stmt.setInt(6, estadoInt);
            stmt.setInt(7, favoritoInt);
            stmt.setInt(8, categoria_id);
            stmt.setInt(9, id);

            int filas = stmt.executeUpdate();
            log.info("Producto editado: {}", nombre);
            return filas > 0;
        } catch (SQLException e) {
            log.error("Error al editar un producto", e);
            return false;
        }
    }

    public void actualizarImagen(int id, String imagen) {
        String sql = "UPDATE productos SET imagen = ? WHERE id = ?";

        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, imagen);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            log.error("Error al actualizar una imagen", e);
        }
    }

    public List<Producto> obtenerProductos(String buscar, Optional<Integer> categoria, boolean activo, boolean noactivo, boolean solofavorito) {
        if (!activo && !noactivo) return new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM productos WHERE 1=1");

        if (categoria.isPresent()) {
            sql.append(" AND categoria_id = ?");
        }
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
        if (solofavorito) {
            sql.append(" AND favorito = 1");
        }

        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(sql.toString())) {
            int i = 1;
            if (categoria.isPresent()) {
                stmt.setInt(i++, categoria.get());
            }
            if (buscar != null && !buscar.isEmpty()) {
                stmt.setString(i++, "%" + buscar + "%");
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

    public boolean existeProducto(String nombre) {
        String sql = "SELECT nombre FROM productos WHERE nombre = ?";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            log.error("Error al comprobar si existe un producto", e);
            return true;
        }
    }
}
