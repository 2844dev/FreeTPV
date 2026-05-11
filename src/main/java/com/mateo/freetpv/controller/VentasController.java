package com.mateo.freetpv.controller;

import atlantafx.base.theme.Styles;
import com.mateo.freetpv.dao.CategoriaDAO;
import com.mateo.freetpv.dao.ProductoDAO;
import com.mateo.freetpv.model.LineaTicket;
import com.mateo.freetpv.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static com.mateo.freetpv.util.ConversionUtil.centimosEuros;

public class VentasController {
    @FXML private TilePane productosTilePane;
    @FXML private TilePane categoriasTilePane;

    @FXML private TableView<LineaTicket> ticketTableView;
    @FXML private TableColumn<LineaTicket, String> productoTableColumn;
    @FXML private TableColumn<LineaTicket, Integer> cantidadTableColumn;
    @FXML private TableColumn<LineaTicket, Integer> precioTableColumn;
    @FXML private TableColumn<LineaTicket, Integer> subtotalTableColumn;
    @FXML private TableColumn<LineaTicket, Void> eliminarTableColumn;

    @FXML private Label subtotalLabel;
    @FXML private Label ivaLabel;
    @FXML private Label totalLabel;

    @FXML private Button borrarVentaButton;
    @FXML private Button borrarLineaButton;
    @FXML private Button cobrarButton;

    private final ProductoDAO productoDAO = new ProductoDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    private final ObservableList<LineaTicket> ticket = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        ticketTableView.setItems(ticket);

        productoTableColumn.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        cantidadTableColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        precioTableColumn.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        subtotalTableColumn.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        precioTableColumn.setCellFactory(column -> celdaPrecio());
        subtotalTableColumn.setCellFactory(column -> celdaPrecio());

        eliminarTableColumn.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button(null, new FontIcon("fas-minus"));
            {
                btn.getStyleClass().addAll(Styles.BUTTON_ICON, Styles.BUTTON_OUTLINED, Styles.DANGER);
                btn.setOnAction(e -> restarTicket(getTableView().getItems().get(getIndex())));
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                setGraphic(empty ? null : btn);
            }
        });

        categoriasTilePane.getChildren().add(crearBotonCategoria("Favoritos", Optional.empty()));
        categoriaDAO.obtenerCategorias("").forEach(c ->
                categoriasTilePane.getChildren().add(crearBotonCategoria(c.getNombre(), Optional.of(c.getId())))
        );
        cargarProductos(Optional.empty());
        actualizarTotales();
    }

    private TableCell<LineaTicket, Integer> celdaPrecio() {
        return new TableCell<>() {
            @Override
            protected void updateItem(Integer precioCentimos, boolean empty) {
                super.updateItem(precioCentimos, empty);
                if (empty || precioCentimos == null) {
                    setText(null);
                } else {
                    setText(centimosEuros(precioCentimos).orElse("0,00") + " €");
                }
            }
        };
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

            botonProducto.setOnAction(e -> añadirTicket(producto));
            productosTilePane.getChildren().add(botonProducto);
        }
    }

    private void añadirTicket(Producto producto) {
        Optional<LineaTicket> lineaExistente = ticket.stream()
                .filter(l -> l.getProductoId() == producto.getId())
                .findFirst();

        if (lineaExistente.isPresent()) {
            lineaExistente.get().setCantidad(lineaExistente.get().getCantidad() + 1);
            ticketTableView.refresh();
        } else {
            ticket.add(new LineaTicket(producto.getId(), producto.getNombre(), producto.getPrecio(), producto.getIva()));
        }
        actualizarTotales();
    }

    private void restarTicket(LineaTicket linea) {
        if (linea.getCantidad() > 1) {
            linea.setCantidad(linea.getCantidad() - 1);
            ticketTableView.refresh();
        } else {
            ticket.remove(linea);
        }
        actualizarTotales();
    }

    @FXML public void borrarLinea() {
        LineaTicket seleccionado = ticketTableView.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            ticket.remove(seleccionado);
            actualizarTotales();
        }
    }

    @FXML public void cancelarVenta() {
        ticket.clear();
        actualizarTotales();
    }

    private void actualizarTotales() {
        int subtotal = ticket.stream().mapToInt(LineaTicket::getSubtotal).sum();

        String subtotalFormat = centimosEuros(subtotal).orElse("0,00€");

        int iva = ticket.stream().mapToInt(l -> {
            double factor = l.getIva() / 100.0;
            return (int) Math.round(l.getSubtotal() * factor / (1 + factor));
        }).sum();

        String ivaFormat = centimosEuros(iva).orElse("0,00€");

        subtotalLabel.setText(subtotalFormat + "€");
        ivaLabel.setText(ivaFormat + "€");
        totalLabel.setText(subtotalLabel.getText());
        cobrarButton.setText("Cobrar " + totalLabel.getText());
    }
}
