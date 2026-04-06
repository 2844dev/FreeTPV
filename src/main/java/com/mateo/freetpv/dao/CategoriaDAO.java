package com.mateo.freetpv.dao;

import com.mateo.freetpv.model.Categoria;
import com.mateo.freetpv.util.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaDAO {
    private static final Logger log = LoggerFactory.getLogger(CategoriaDAO.class);
    private final DatabaseConnection db = DatabaseConnection.getInstancia();

    public Optional<Integer> categoriaNombreId(String nombre) {
        String sql = "SELECT id FROM categorias WHERE nombre = ?";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(rs.getInt("id"));
            }
            return Optional.empty();
        } catch (SQLException e) {
            log.error("Error al obtener el id de categoria", e);
            return Optional.empty();
        }
    }

    public boolean existeCategoria(String nombre) {
        String sql = "SELECT nombre FROM categorias WHERE nombre = ?";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            log.error("Error al comprobar la categoria existe", e);
            return true;
        }
    }

    public boolean crearCategoria(String nombre) {
        String sql = "INSERT INTO categorias (nombre) VALUES (?)";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            int filas = stmt.executeUpdate();
            log.info("Categoria {} creada correctamente", nombre);
            return filas > 0;
        } catch (SQLException e) {
            log.error("Error al crear categoria", e);
            return false;
        }
    }

    public boolean editarCategoria(Categoria categoria, String nombre) {
        String sql = "UPDATE categorias SET nombre = ? WHERE id = ?";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setInt(2, categoria.getId());
            int filas = stmt.executeUpdate();
            log.info("Categoria editada: {}", nombre);
            return filas > 0;
        } catch (SQLException e) {
            log.error("Error al editar categoria", e);
            return false;
        }
    }

    public List<String> obtenerNombresCategorias() {
        String sql = "SELECT nombre FROM categorias";
        try (var connection = db.getConnection();
            var stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            List<String> nombres = new ArrayList<>();
            while (rs.next()) {
                nombres.add(rs.getString("nombre"));
            }
            return nombres;
        } catch (SQLException e) {
            log.error("Error al obtener nombres de categorias", e);
            return new ArrayList<>();
        }
    }

    public List<Categoria> obtenerCategorias(String buscar) {

        StringBuilder sql = new StringBuilder("SELECT * FROM categorias WHERE 1=1");

        if (buscar != null && !buscar.isEmpty()) {
            sql.append(" AND nombre LIKE ? COLLATE NOCASE");
        }

        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(sql.toString())) {

            if (buscar != null && !buscar.isEmpty()) {
                stmt.setString(1, "%" + buscar + "%");
            }

            ResultSet rs = stmt.executeQuery();

            List<Categoria> categorias = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                Categoria categoria = new Categoria(id, nombre);
                categorias.add(categoria);
            }

            return categorias;

        } catch (SQLException e) {
            log.error("Error al obtener todas las categorias", e);
            return new ArrayList<>();
        }
    }
}
