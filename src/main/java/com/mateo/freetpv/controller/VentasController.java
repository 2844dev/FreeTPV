package com.mateo.freetpv.controller;

import atlantafx.base.theme.Styles;
import com.mateo.freetpv.dao.CategoriaDAO;
import com.mateo.freetpv.dao.ProductoDAO;
import com.mateo.freetpv.model.Producto;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static com.mateo.freetpv.util.ConversionUtil.centimosEuros;

public class VentasController {
    @FXML
    private TilePane productosTilePane;
    @FXML
    private TilePane categoriasTilePane;

    private final ProductoDAO productoDAO = new ProductoDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @FXML
    public void initialize() {
        categoriasTilePane.getChildren().add(crearBotonCategoria("Favoritos", Optional.empty()));
        categoriaDAO.obtenerCategorias("").forEach(c ->
                categoriasTilePane.getChildren().add(crearBotonCategoria(c.getNombre(), Optional.of(c.getId())))
        );
        cargarProductos(Optional.empty());
    }

    private Button crearBotonCategoria(String nombre, Optional<Integer> categoriaId) {
        Button botonCategoria = new Button(nombre);
        botonCategoria.setPadding(new Insets(10));
        botonCategoria.setFont(Font.font(16));
        botonCategoria.setMaxWidth(Double.MAX_VALUE);
        botonCategoria.setMaxHeight(Double.MAX_VALUE);
        botonCategoria.setOnAction(e -> cargarProductos(categoriaId));
        return botonCategoria;
    }

    private void cargarProductos(Optional<Integer> categoriaId) {
        productosTilePane.getChildren().clear();
        List<Producto> productos;
        if (categoriaId.isEmpty()) {
            productos = productoDAO.obtenerProductos("", categoriaId, true, false, true);
        } else {
            productos = productoDAO.obtenerProductos("", categoriaId, true, false, false);
        }
        for (Producto producto : productos) {
            Button botonProducto = new Button();
            botonProducto.setPadding(new Insets(10));
            botonProducto.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            botonProducto.setMaxWidth(Double.MAX_VALUE);
            botonProducto.setMaxHeight(Double.MAX_VALUE);
            VBox boxProducto = new VBox(10);
            boxProducto.setAlignment(Pos.TOP_CENTER);

            ImageView imageProducto = new ImageView();
            imageProducto.setFitHeight(100);
            imageProducto.setFitWidth(100);
            if (producto.getImagen() != null && !producto.getImagen().isEmpty()) {
                File img = new File(producto.getImagen());
                imageProducto.setImage(new Image(img.toURI().toString(), 100, 100, false, false, true));
            }

            Label nombreProducto = new Label(producto.getNombre());
            nombreProducto.setWrapText(true);

            Label precioProducto = new Label();
            String precio = centimosEuros(producto.getPrecio()).orElse("0,00");
            precioProducto.setText(precio + " €");
            precioProducto.getStyleClass().addAll(Styles.ACCENT, Styles.TEXT_BOLD);
            precioProducto.setFont(Font.font(14));

            boxProducto.getChildren().addAll(imageProducto, nombreProducto, precioProducto);
            botonProducto.setGraphic(boxProducto);
            productosTilePane.getChildren().add(botonProducto);
        }
    }
}
