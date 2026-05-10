package com.mateo.freetpv.controller;

import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Tweaks;
import atlantafx.base.util.Animations;
import com.mateo.freetpv.dao.CategoriaDAO;
import com.mateo.freetpv.dao.ProductoDAO;
import com.mateo.freetpv.model.Producto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static com.mateo.freetpv.util.ConversionUtil.centimosEuros;
import static com.mateo.freetpv.util.ConversionUtil.eurosCentimos;

public class ProductosController {

    private static final Logger log = LoggerFactory.getLogger(ProductosController.class);
    // Panel principal productos
    @FXML private BorderPane productosPane;

    @FXML private TableView<Producto> productosTable;
    @FXML private TableColumn<Producto, Integer> codigoColumn;
    @FXML private TableColumn<Producto, String> nombreColumn;
    @FXML private TableColumn<Producto, Integer> precioColumn;
    @FXML private TableColumn<Producto, Integer> ivaColumn;
    @FXML private TableColumn<Producto, Void> estadoColumn;
    @FXML private TableColumn<Producto, Void> favoritoColumn;
    @FXML private TableColumn<Producto, Void> editarColumn;

    @FXML private MenuButton filtrarMenu;
    @FXML private CheckMenuItem todosMenuItem;
    @FXML private CheckMenuItem activosMenuItem;
    @FXML private CheckMenuItem noactivosMenuItem;

    @FXML private ComboBox<String> categoriaComboBox;
    @FXML private TextField buscarField;
    @FXML private Button nuevoProductoButton;

    // Panel para crear y editar productos
    @FXML private BorderPane nuevoproductoPane;
    @FXML
    private Text nuevoText;
    @FXML
    private Text productoText;
    @FXML private TextField nombreField;
    @FXML private TextField nombreTicketField;
    @FXML private TextField precioField;
    @FXML private ChoiceBox<Integer> ivaChoiceBox;
    @FXML private ChoiceBox<String> categoriaChoiceBox;
    @FXML
    private Button subirImagenButton;
    @FXML
    private Button pegarImagenButton;
    @FXML
    private Button borrarImagenButton;
    @FXML private ImageView imagenActualImageView;
    @FXML private ToggleButton favoritoToggleButton;
    @FXML private ToggleButton estadoToggleButton;
    @FXML private Label errorLabel;
    @FXML private Button guardarButton;
    @FXML private Button cancelarButton;

    private final ProductoDAO productoDAO = new ProductoDAO();

    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    private Producto productoEditando = null;

    private String imagenSeleccionada = "";

    final private Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);

    private List<CheckMenuItem> filtros;

    private final String path = System.getProperty("user.home") + "/.freetpv";

    @FXML public void initialize() {

        // Añadimos las categorías creadas a los filtros
        categoriaComboBox.setItems(FXCollections.observableArrayList(categoriaDAO.obtenerNombreCategorias()));
        categoriaComboBox.getItems().addFirst("Todas");
        // Seleccionamos por defecto la primera opción
        categoriaComboBox.getSelectionModel().selectFirst();

        categoriaComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> actualizarProductos());


        // Añadimos las categorías a la creación del producto
        categoriaChoiceBox.setItems(FXCollections.observableArrayList(categoriaDAO.obtenerNombreCategorias()));

        // Añadimos los IVA a la creación del producto
        ivaChoiceBox.setItems(FXCollections.observableArrayList(21, 10, 4, 0));

        filtros = List.of(activosMenuItem, noactivosMenuItem);

        buscarField.textProperty().addListener((observable, oldValue, newValue) -> actualizarProductos());

        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));

        precioColumn.setCellFactory(column -> new TableCell<Producto, Integer>() {
            @Override
            protected void updateItem(Integer precioCentimos, boolean empty) {
                super.updateItem(precioCentimos, empty);

                if (empty || precioCentimos == null) {
                    setText(null);
                } else {
                    String texto = centimosEuros(precioCentimos).orElse("0,00");
                    setText(texto + " €");

                }
            }
        });
        ivaColumn.setCellValueFactory(new PropertyValueFactory<>("iva"));

        estadoColumn.getStyleClass().add(Tweaks.ALIGN_CENTER);
        estadoColumn.setCellFactory(param -> new TableCell<>() {
            private FontIcon icon = new FontIcon();

            @Override
            protected void updateItem(Void item, boolean empty) {
                if (empty) {
                    setGraphic(null);
                } else {
                    icon.getStyleClass().removeAll(Styles.SUCCESS, Styles.DANGER);
                    if (getTableView().getItems().get(getIndex()).getEstado()) {
                        icon.setIconLiteral("fas-check-circle");
                        icon.getStyleClass().add(Styles.SUCCESS);
                    } else {
                        icon.setIconLiteral("fas-times-circle");
                        icon.getStyleClass().add(Styles.DANGER);
                    }
                    setGraphic(icon);
                }
            }
        });

        favoritoColumn.getStyleClass().add(Tweaks.ALIGN_CENTER);
        favoritoColumn.setCellFactory(param -> new TableCell<>() {
            private FontIcon icon = new FontIcon();

            @Override
            protected void updateItem(Void item, boolean empty) {
                if (empty) {
                    setGraphic(null);
                } else {
                    icon.getStyleClass().add(Styles.DANGER);
                    if (getTableView().getItems().get(getIndex()).getFavorito()) {
                        icon.setIconLiteral("fas-heart");
                    } else {
                        icon.setIconLiteral("far-heart");
                    }
                    setGraphic(icon);
                }
            }
        });

        editarColumn.getStyleClass().add(Tweaks.ALIGN_CENTER);

        editarColumn.setCellFactory(param -> new TableCell<>() {
            private Button editarButton = new Button(null, new FontIcon("fas-edit"));
            {
                editarButton.getStyleClass().addAll(Styles.BUTTON_ICON, Styles.BUTTON_OUTLINED);
                editarButton.setOnAction(e -> {
                    mostrarFormularioEditar(getTableView().getItems().get(getIndex()));
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editarButton);
                }
            }
        });

        Platform.runLater(() -> {
            TabPane tabPane = (TabPane) productosPane.getParent().getParent().getParent();
            Tab miTab = tabPane.getTabs().get(0); // Productos es el tab 0
            miTab.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                if (isSelected) actualizarCategoriasCombo();
            });
        });

        actualizarProductos();
    }

    @FXML
    public void mostrarFormularioNuevo() {
        errorLabel.setText("");
        productosPane.setDisable(true);
        nuevoproductoPane.setVisible(true);
        favoritoToggleButton.setSelected(false);
        estadoToggleButton.setSelected(true); // activo por defecto al crear
        estadoToggleButton.setDisable(true);
        categoriaChoiceBox.setItems(FXCollections.observableArrayList(categoriaDAO.obtenerNombreCategorias()));

        var animation = Animations.fadeIn(nuevoproductoPane, Duration.seconds(0.5));
        animation.playFromStart();
    }

    private void mostrarFormularioEditar(Producto producto) {
        productoEditando = producto;

        if (productoEditando != null) {
            nombreField.setText(producto.getNombre());
            nombreTicketField.setText(producto.getNombre_ticket());
            Optional<String> precioOpt = centimosEuros(producto.getPrecio());
            if (precioOpt.isPresent()) {
                precioField.setText(precioOpt.get());
            }
            categoriaChoiceBox.setItems(FXCollections.observableArrayList(categoriaDAO.obtenerNombreCategorias()));
            ivaChoiceBox.getSelectionModel().select(Integer.valueOf(producto.getIva()));
            Optional<String> categoriaOpt = categoriaDAO.categoriaIdNombre(producto.getCategoria_id());
            if (categoriaOpt.isPresent()) {
                categoriaChoiceBox.getSelectionModel().select(categoriaOpt.get());
            }
            if (producto.getImagen() != null && !producto.getImagen().isEmpty()) {
                File img = new File(producto.getImagen());
                Image imagen = new Image(img.toURI().toString());
                imagenActualImageView.setImage(imagen);
                imagenSeleccionada = producto.getImagen();
            }
            favoritoToggleButton.setSelected(producto.getFavorito());
            estadoToggleButton.setSelected(producto.getEstado());

            errorLabel.setText("");
            productosPane.setDisable(true);
            nuevoproductoPane.setVisible(true);

            var animation = Animations.fadeIn(nuevoproductoPane, Duration.seconds(0.5));
            animation.playFromStart();
        }
    }

    @FXML public void guardarProducto() {
        String nombre = nombreField.getText().trim();
        String nombreTicket = nombreTicketField.getText().trim();
        String precioRaw = precioField.getText().trim();

        if (nombre.isEmpty() || nombreTicket.isEmpty() || precioRaw.isEmpty() || ivaChoiceBox.getSelectionModel().isEmpty() || categoriaChoiceBox.getSelectionModel().isEmpty()) {
            errorLabel.setText("Debe rellenar todos los campos");
            return;
        }

        Optional<Integer> precioOpt = eurosCentimos(precioRaw);
        if (precioOpt.isEmpty()) {
            errorLabel.setText("El precio no tiene un formato válido");
            return;
        }

        Optional<Integer> categoriaIdOpt = categoriaDAO.categoriaNombreId(categoriaChoiceBox.getSelectionModel().getSelectedItem());
        if (categoriaIdOpt.isEmpty()) {
            errorLabel.setText("La categoría seleccionada no es válida");
            return;
        }

        if (nombre.length() > 35) {
            errorLabel.setText("El nombre debe tener menos de 35 caracteres");
            return;
        }
        if (productoDAO.existeProducto(nombre) && (productoEditando == null || !nombre.equals(productoEditando.getNombre()))) {
            errorLabel.setText("Ya existe un producto con ese nombre");
            return;
        }

        int precioFinal = precioOpt.get();
        int categoriaIdFinal = categoriaIdOpt.get();
        int ivaFinal = ivaChoiceBox.getSelectionModel().getSelectedItem();

        if (productoEditando == null) {
            Optional<Integer> idOpt = productoDAO.crearProducto(nombre, nombreTicket, imagenSeleccionada, precioFinal, ivaFinal, favoritoToggleButton.isSelected(), categoriaIdFinal);
            if (idOpt.isEmpty()) {
                errorLabel.setText("No se pudo crear el producto");
                return;
            }
            int id = idOpt.get();

            if (!imagenSeleccionada.isEmpty()) {
                guardarImagen(id, imagenSeleccionada.startsWith("http"));
                productoDAO.actualizarImagen(id, imagenSeleccionada);
            }

            infoAlert.setTitle("Crear producto");
            infoAlert.setHeaderText("Producto creado correctamente");
            infoAlert.setContentText("Producto " + nombre + " creado.");
            infoAlert.showAndWait();

        } else {

            if (!imagenSeleccionada.equals(productoEditando.getImagen()) && !imagenSeleccionada.isEmpty()) {
                guardarImagen(productoEditando.getId(), imagenSeleccionada.startsWith("http"));
            }

            if (productoDAO.editarProducto(productoEditando, nombre, nombreTicket, imagenSeleccionada, precioFinal, ivaFinal, estadoToggleButton.isSelected(), favoritoToggleButton.isSelected(), categoriaIdFinal)) {
                infoAlert.setTitle("Editar producto");
                infoAlert.setHeaderText("Producto editado correctamente");
                infoAlert.setContentText("Producto " + nombre + " editado.");
                infoAlert.showAndWait();
            } else {
                errorLabel.setText("No se pudo editar el producto");
                return;
            }
        }
        cerrarFormulario();
        actualizarProductos();
    }

    private void guardarImagen(int id, boolean url) {
        try {

            Path destino = Path.of(path + "/img/productos/");
            Files.createDirectories(destino);
            Path imagenFinal = destino.resolve("producto_" + id + ".png");
            BufferedImage original;
            if (url) {
                original = ImageIO.read(new java.net.URL(imagenSeleccionada));
            } else {
                Path origen = Path.of(imagenSeleccionada);
                original = ImageIO.read(origen.toFile());
            }
            BufferedImage resized = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = resized.createGraphics();
            g.drawImage(original.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
            g.dispose();
            ImageIO.write(resized, "png", imagenFinal.toFile());
            imagenSeleccionada = imagenFinal.toString();
        } catch (IOException e) {
            log.error("Error al guardar imagen", e);
            errorLabel.setText("No se pudo guardar la imagen");
        }
    }

    @FXML public void cerrarFormulario() {
        nombreField.clear();
        nombreTicketField.clear();
        precioField.clear();
        ivaChoiceBox.getSelectionModel().clearSelection();
        categoriaChoiceBox.getSelectionModel().clearSelection();
        nuevoproductoPane.setVisible(false);
        productosPane.setDisable(false);
        productoEditando = null;
        imagenSeleccionada = "";
        imagenActualImageView.setImage(null);
        favoritoToggleButton.setSelected(false);
        estadoToggleButton.setSelected(false);
        estadoToggleButton.setDisable(false);
    }

    @FXML public void cargarImagen() {
        FileChooser selector = new FileChooser();

        selector.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.jpg", "*.png", "*.gif", "*.jpeg"));

        Stage stage = (Stage) nuevoproductoPane.getScene().getWindow();
        File img = selector.showOpenDialog(stage);

        if (img != null) {
            imagenSeleccionada = img.getAbsolutePath();

            Image imagen = new Image(img.toURI().toString(), 300, 300, false, true, false);

            imagenActualImageView.setImage(imagen);
        }
    }

    @FXML
    public void pegarImagen() {
        Clipboard cb = Clipboard.getSystemClipboard();

        errorLabel.setText("");

        if (cb.hasImage()) {
            try {
                java.awt.Image awtImage = (java.awt.Image) Toolkit.getDefaultToolkit()
                        .getSystemClipboard()
                        .getData(DataFlavor.imageFlavor);

                BufferedImage buffered = new BufferedImage(
                        awtImage.getWidth(null),
                        awtImage.getHeight(null),
                        BufferedImage.TYPE_INT_RGB
                );
                buffered.getGraphics().drawImage(awtImage, 0, 0, null);

                File img = new File(path + "/img", "clipboard_temp.png");
                ImageIO.write(buffered, "png", img);
                imagenSeleccionada = img.getAbsolutePath();

                imagenActualImageView.setImage(new Image(img.toURI().toString(), 200, 200, false, true));

            } catch (Exception e) {
                log.error("Error al pegar imagen del portapapeles", e);
                errorLabel.setText("No se pudo pegar la imagen");
            }
        } else if (cb.hasString()) {
            String texto = cb.getString().trim();
            if (texto.startsWith("http")) {
                Image imagen = new Image(texto, 200, 200, false, true, true);
                imagen.errorProperty().addListener((obs, old, error) -> {
                    if (error) errorLabel.setText("No se pudo cargar la imagen desde la URL");
                });
                imagenActualImageView.setImage(imagen);
                imagenSeleccionada = texto;
            } else {
                errorLabel.setText("El texto copiado no es una URL válida");
            }
        } else {
            errorLabel.setText("No hay ninguna imagen ni URL en el portapapeles");
        }
    }

    @FXML
    private void borrarImagen() {
        imagenActualImageView.setImage(null);
        imagenSeleccionada = "";
    }

    @FXML
    public void filtrarProductos() {
        int n = 0;
        for (CheckMenuItem i : filtros) {
            if (i.isSelected()) n++;
        }
        if (n == filtros.size()) {
            todosMenuItem.setSelected(true);
            filtrarTodos();
        } else {
            todosMenuItem.setSelected(false);
        }
        actualizarProductos();
    }

    @FXML
    public void filtrarTodos() {
        for (CheckMenuItem i : filtros) {
            i.setSelected(todosMenuItem.isSelected());
        }
        actualizarProductos();
    }


    @FXML public void actualizarProductos() {
        Optional<Integer> categoria_id = categoriaDAO.categoriaNombreId(categoriaComboBox.getSelectionModel().getSelectedItem());
        List<Producto> productos = productoDAO.obtenerProductos(buscarField.getText(), categoria_id, activosMenuItem.isSelected(), noactivosMenuItem.isSelected());
        productosTable.getItems().setAll(FXCollections.observableList(productos));
    }

    private void actualizarCategoriasCombo() {
        String seleccionActual = categoriaComboBox.getSelectionModel().getSelectedItem();
        categoriaComboBox.setItems(FXCollections.observableArrayList(categoriaDAO.obtenerNombreCategorias()));
        categoriaComboBox.getItems().addFirst("Todas");
        if (seleccionActual != null && categoriaComboBox.getItems().contains(seleccionActual)) {
            categoriaComboBox.getSelectionModel().select(seleccionActual);
        } else {
            categoriaComboBox.getSelectionModel().selectFirst();
        }
    }
}
