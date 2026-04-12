package com.mateo.freetpv.controller;

import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Tweaks;
import atlantafx.base.util.Animations;
import com.mateo.freetpv.dao.CategoriaDAO;
import com.mateo.freetpv.dao.ProductoDAO;
import com.mateo.freetpv.model.Producto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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

        actualizarProductos();
    }

    @FXML
    public void mostrarFormularioNuevo() {
        errorLabel.setText("");
        productosPane.setDisable(true);
        nuevoproductoPane.setVisible(true);

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
            ivaChoiceBox.getSelectionModel().select(producto.getIva());
            Optional<String> categoriaOpt = categoriaDAO.categoriaIdNombre(producto.getCategoria_id());
            if (categoriaOpt.isPresent()) {
                categoriaChoiceBox.getSelectionModel().select(categoriaOpt.get());
            }
            if (!producto.getImagen().isEmpty()) {
                File img = new File(producto.getImagen());
                Image imagen = new Image(img.toURI().toString());
                imagenActualImageView.setImage(imagen);
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
            if (productoDAO.crearProducto(nombre, nombreTicket, imagenSeleccionada, precioFinal, ivaFinal, favoritoToggleButton.isSelected(), categoriaIdFinal)) {
                infoAlert.setTitle("Crear producto");
                infoAlert.setHeaderText("Producto creado correctamente");
                infoAlert.setContentText("Producto " + nombre + " creado.");
                infoAlert.showAndWait();
            } else {
                errorLabel.setText("No se pudo crear el producto");
                return;
            }

        } else {
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


    @FXML public void actualizarProductos() {
        Optional<Integer> categoria_id = categoriaDAO.categoriaNombreId(categoriaComboBox.getSelectionModel().getSelectedItem());
        List<Producto> productos = productoDAO.obtenerProductos(buscarField.getText(), categoria_id, activosMenuItem.isSelected(), noactivosMenuItem.isSelected());
        productosTable.getItems().setAll(FXCollections.observableList(productos));
    }
}
