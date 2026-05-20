package com.mateo.freetpv.controller;

import atlantafx.base.theme.Styles;
import com.mateo.freetpv.FreeTPVApplication;
import com.mateo.freetpv.dao.CategoriaDAO;
import com.mateo.freetpv.dao.ProductoDAO;
import com.mateo.freetpv.model.DatosTicket;
import com.mateo.freetpv.model.LineaTicket;
import com.mateo.freetpv.model.Producto;
import com.mateo.freetpv.service.AjustesService;
import com.mateo.freetpv.service.ImprimirService;
import com.mateo.freetpv.util.SesionActual;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.mateo.freetpv.util.ConversionUtil.centimosEuros;
import static com.mateo.freetpv.util.ConversionUtil.eurosCentimos;

public class VentasController {
    private static final Logger log = LoggerFactory.getLogger(VentasController.class);

    @FXML private BorderPane ventasBorderPane;

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

    @FXML private BorderPane cobrarBorderPane;
    @FXML private Button efectivoButton;
    @FXML private Button tarjetaButton;
    @FXML private GridPane calculadoraGrid;
    @FXML private GridPane rapidoGrid;
    @FXML private Button cancelarButton;
    @FXML private Button finalCobrarButton;
    @FXML private TextField totalField;
    @FXML private TextField entregadoField;
    @FXML private TextField vueltaField;

    private final ProductoDAO productoDAO = new ProductoDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    private final ObservableList<LineaTicket> ticket = FXCollections.observableArrayList();

    private final AjustesService ajustesService = new AjustesService();

    private String metodoPago = null;

    private int iva;
    private int subtotal;
    private int total;
    private String ivaFormat;
    private String subtotalFormat;
    private String totalFormat;

    private StringBuilder entregado = new StringBuilder("");


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

        // Crear boton favoritos
        Button botonFavoritos = crearBotonCategoria("Favoritos", Optional.empty());
        botonFavoritos.getStyleClass().add(Styles.ACCENT);
        categoriasTilePane.getChildren().add(botonFavoritos);

        categoriaDAO.obtenerCategorias("").forEach(c ->
                categoriasTilePane.getChildren().add(crearBotonCategoria(c.getNombre(), Optional.of(c.getId())))
        );

        for (Node b : calculadoraGrid.getChildren()) {
            if (!(b instanceof Button btn)) continue;
            String num = btn.getText();

            btn.setOnAction(e -> {
               if (num == null || num.isEmpty()) return;
               if (num.equals(",")) {
                   if (entregado.toString().contains(",")) return;
               }

               if (num.equals("Borrar")) {
                   if (entregado.length() > 0) {
                       entregado.deleteCharAt(entregado.length() - 1);
                   }
                   entregadoField.setText(entregado.toString());
                   actualizarVuelta();
                   return;
               }
                entregado.append(num);

                entregadoField.setText(entregado.toString());
                actualizarVuelta();
            });

        }

        for (Node b : rapidoGrid.getChildren()) {
            if (!(b instanceof Button btn)) continue;
            String num = btn.getText().substring(1);
            int numFormat = eurosCentimos(num).orElse(0);

            btn.setOnAction(e -> {
                int entregadoFormat = eurosCentimos(entregado.toString()).orElse(0);
                int entregadoSuma = entregadoFormat + numFormat;
                String entregadoFinal = centimosEuros(entregadoSuma).orElse("");
                entregado.setLength(0);
                entregado.append(entregadoFinal);
                entregadoField.setText(entregado.toString());
                actualizarVuelta();
            });
        }

        cargarProductos(Optional.empty()); // Cargar categoria Favoritos
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

    private void actualizarVuelta() {
        Optional<Integer> entregadoCentimos = eurosCentimos(entregado.toString());
        if (entregadoCentimos.isEmpty()) {
            vueltaField.clear();
            return;
        }
        Optional<String> vueltaEuros = centimosEuros(entregadoCentimos.get() - total);
        if (vueltaEuros.isPresent()) {
            vueltaField.setText(vueltaEuros.get());
        }
    }

    @FXML public void salir() {
        try {

            // Cargar ventana principal
            FXMLLoader fxmlLoader = new FXMLLoader(FreeTPVApplication.class.getResource("view/main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Conseguir stage desde el un objeto
            Stage stage = (Stage) cobrarButton.getScene().getWindow();

            stage.setResizable(true);
            stage.setMinWidth(1280);
            stage.setMinHeight(720);
            stage.setScene(scene);
        } catch (IOException e) {
            log.error("Error al cargar el view principal", e);
        }
    }

    @FXML
    public void seleccionarEfectivo() {

        calculadoraGrid.setDisable(false);
        rapidoGrid.setDisable(false);

        metodoPago = "Efectivo";
        efectivoButton.getStyleClass().remove(Styles.BUTTON_OUTLINED);
        tarjetaButton.getStyleClass().add(Styles.BUTTON_OUTLINED);
    }

    @FXML
    public void seleccionarTarjeta() {

        calculadoraGrid.setDisable(true);
        rapidoGrid.setDisable(true);

        metodoPago = "Tarjeta";
        efectivoButton.getStyleClass().add(Styles.BUTTON_OUTLINED);
        tarjetaButton.getStyleClass().remove(Styles.BUTTON_OUTLINED);
    }

    @FXML public void finalizarCobro() {

        if (metodoPago == null) {
            //errorLabel.setText("Debes seleccionar un metodo de pago.")
            return;
        }
        if (metodoPago.equals("Efectivo")) {
            int entregadoCentimos = eurosCentimos(entregado.toString()).orElse(0);
            if (entregadoCentimos < total) {
                return;
            }
        }

        int entregadoFinal = metodoPago.equals("Efectivo") ? eurosCentimos(entregado.toString()).orElse(0) : total;

        DatosTicket datosTicket = new DatosTicket(
                ajustesService.getEmpresaNombre(),
                ajustesService.getEmpresaCif(),
                ajustesService.getEmpresaDireccion(),
                ajustesService.getEmpresaCodigoPostal(),
                ajustesService.getEmpresaCiudad(),
                ajustesService.getEmpresaTelefono(),
                ajustesService.getEmpresaWeb(),
                ajustesService.getEmpresaQr(),
                ajustesService.getTicketTitulo(),
                ajustesService.getTicketMensajeFinal(),
                ajustesService.getTicketMostrarCif(),
                ajustesService.getTicketMostrarTelefono(),
                ajustesService.getTicketMostrarWeb(),
                ajustesService.getTicketMostrarIva(),
                ajustesService.getTicketMostrarQr(),
                SesionActual.getInstancia().getUsuario().getNombre(),
                new ArrayList<>(ticket),
                metodoPago,
                entregadoFinal
        );

        Optional<PrintService> impresora = buscarImpresora();
        if (impresora.isPresent()) {
            try {
                ImprimirService.imprimirTicket(impresora.get(), datosTicket);
                cancelarVenta();
            } catch (IOException e) {
                log.error("Error al imprimir ticket", e);
            }
        } else {
            Alert warn = new Alert(Alert.AlertType.WARNING);
            warn.setTitle("Sin impresora");
            warn.setHeaderText("No se ha podido imprimir el ticket");
            warn.setContentText("No hay ninguna impresora configurada.");
            warn.showAndWait();
        }
        cancelarVenta();
        cancelarCobrar();
    }

    @FXML
    public void cobrar() {
        if (ticket == null || ticket.isEmpty()) return;

        entregado.setLength(0);

        seleccionarEfectivo();

        totalField.setText(totalFormat + "€");

        ventasBorderPane.setDisable(true);
        cobrarBorderPane.setVisible(true);
//        // TODO: diálogo tarjeta/efectivo
//        String metodoPago = "Tarjeta";
//        int entregado = 0;
//
//        DatosTicket datosTicket = new DatosTicket(
//                ajustesService.getEmpresaNombre(),
//                ajustesService.getEmpresaCif(),
//                ajustesService.getEmpresaDireccion(),
//                ajustesService.getEmpresaCodigoPostal(),
//                ajustesService.getEmpresaCiudad(),
//                ajustesService.getEmpresaTelefono(),
//                ajustesService.getEmpresaWeb(),
//                ajustesService.getEmpresaQr(),
//                ajustesService.getTicketTitulo(),
//                ajustesService.getTicketMensajeFinal(),
//                ajustesService.getTicketMostrarCif(),
//                ajustesService.getTicketMostrarTelefono(),
//                ajustesService.getTicketMostrarWeb(),
//                ajustesService.getTicketMostrarIva(),
//                ajustesService.getTicketMostrarQr(),
//                SesionActual.getInstancia().getUsuario().getNombre(),
//                new ArrayList<>(ticket),
//                metodoPago,
//                entregado
//        );
//
//        buscarImpresora().ifPresent(impresora -> {
//            try {
//                ImprimirService.imprimirTicket(impresora, datosTicket);
//                cancelarVenta();
//            } catch (IOException e) {
//                log.error("Error al imprimir ticket", e);
//            }
//        });
    }

    private Optional<PrintService> buscarImpresora() {
        String nombre = ajustesService.getImpresoraNombre();
        return Arrays.stream(PrintServiceLookup.lookupPrintServices(null, null))
                .filter(p -> p.getName().equals(nombre))
                .findFirst();
    }

    private Button crearBotonCategoria(String nombre, Optional<Integer> categoriaId) {
        Button botonCategoria = new Button(nombre);
        botonCategoria.setPadding(new Insets(10));
        botonCategoria.setFont(Font.font(16));
        botonCategoria.setMaxWidth(Double.MAX_VALUE);
        botonCategoria.setMaxHeight(Double.MAX_VALUE);
        botonCategoria.setOnAction(e -> {
            cargarProductos(categoriaId);
            categoriasTilePane.getChildren().forEach(node ->
                    node.getStyleClass().remove(Styles.ACCENT)
            );
            botonCategoria.getStyleClass().add(Styles.ACCENT);
        });
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
            ticket.add(new LineaTicket(producto.getId(), producto.getNombre(), producto.getNombre_ticket(), producto.getPrecio(), producto.getIva()));
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

        iva = ticket.stream().mapToInt(l -> {
            double factor = l.getIva() / 100.0;
            return (int) Math.round(l.getSubtotal() * factor / (1 + factor));
        }).sum();

        total = ticket.stream().mapToInt(LineaTicket::getSubtotal).sum();

        subtotal = total - iva;

        subtotalFormat = centimosEuros(subtotal).orElse("0,00");
        ivaFormat = centimosEuros(iva).orElse("0,00");
        totalFormat = centimosEuros(total).orElse("0,00");


        subtotalLabel.setText(subtotalFormat + "€");
        ivaLabel.setText(ivaFormat + "€");
        totalLabel.setText(totalFormat + "€");
        cobrarButton.setText("Cobrar " + totalFormat + "€");
    }

    @FXML public void cancelarCobrar() {
        entregado.setLength(0);
        entregadoField.clear();

        ventasBorderPane.setDisable(false);
        cobrarBorderPane.setVisible(false);
    }
}
