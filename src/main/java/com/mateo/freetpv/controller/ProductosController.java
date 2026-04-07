package com.mateo.freetpv.controller;

import atlantafx.base.util.Animations;
import com.mateo.freetpv.dao.CategoriaDAO;
import com.mateo.freetpv.dao.ProductoDAO;
import com.mateo.freetpv.model.Categoria;
import com.mateo.freetpv.model.Producto;
import com.mateo.freetpv.util.BingImageScraper;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.util.Duration;

import java.util.List;

public class ProductosController {

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

    @FXML private ComboBox<Categoria> categoriaComboBox;
    @FXML private TextField buscarField;
    @FXML private Button nuevoProductoButton;

    // Panel para crear y editar productos
    @FXML private BorderPane nuevoproductoPane;
    @FXML private TextField nombreField;
    @FXML private TextField nombreTicketField;
    @FXML private TextField precioField;
    @FXML private ChoiceBox<Integer> ivaChoiceBox;
    @FXML private ChoiceBox<Categoria> categoriaChoiceBox;
    @FXML private Button imagenButton;
    @FXML private ToggleButton favoritoToggleButton;
    @FXML private ToggleButton estadoToggleButton;
    @FXML private Label errorLabel;
    @FXML private Button guardarButton;
    @FXML private Button cancelarButton;

    // Panel para escoger imagen de producto
    @FXML private BorderPane imagenPane;
    @FXML private ImageView imagenImageView;
    @FXML private Button subirImagenButton;
    @FXML private TextField buscarImagenField;
    @FXML private Button buscarImagenButton;
    @FXML private FlowPane bingimagesFlowPane;

    private final ProductoDAO productoDAO = new ProductoDAO();

    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    private Producto productoEditando = null;

    private String imagenSeleccionada = null;

    @FXML public void initialize() {
        categoriaComboBox.setItems(FXCollections.observableArrayList(categoriaDAO.obtenertodasCategorias()));
    }

    @FXML public void mostrarformularioNuevo() {
        errorLabel.setText("");
        productosPane.setDisable(true);
        nuevoproductoPane.setVisible(true);

        var animation = Animations.fadeIn(nuevoproductoPane, Duration.seconds(0.5));
        animation.playFromStart();
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
    }

    @FXML public void mostrareditarImagen() {
        nuevoproductoPane.setDisable(true);
        imagenPane.setVisible(true);

        var animation = Animations.fadeInUp(imagenPane, Duration.seconds(0.5));
        animation.playFromStart();
    }

    @FXML public void guardarImagen() {
        nuevoproductoPane.setDisable(false);
        imagenPane.setVisible(false);
    }

    @FXML public void cerrarImagen() {
        nuevoproductoPane.setDisable(false);
        imagenPane.setVisible(false);
        imagenSeleccionada = null;
    }

    @FXML public void buscarImagenes() {
        // Limpiamos el panel
        bingimagesFlowPane.getChildren().clear();


        Task<List<BingImageScraper.ImagenResultado>> tarea = new Task<>() {
            // En un nuevo hilo diferente del de JavaFX ejecutamos la búsqueda de imágenes
            @Override protected List<BingImageScraper.ImagenResultado> call() {
                return BingImageScraper.buscar(buscarImagenField.getText(), 50);
            }
        };
        // En el hilo de JavaFX ya creamos los botones
        tarea.setOnSucceeded(e -> {
            List<BingImageScraper.ImagenResultado> urls = tarea.getValue();
            // Pasamos por todas las urls conseguidas
            for (BingImageScraper.ImagenResultado i : urls) {
                // Creamos una imagen con una url (turl de miniatura) y que pueda cargar en el fondo y limitamos a 100x100px dentro de un ImageView
                ImageView iv = new ImageView(new Image(i.turl(), 100, 100, false, false, true));
                // Creamos el botón con el grafico de la ImageView
                Button btn = new Button(null, iv);
                // Establecemos que el tamaño maximo del boton sean 110px
                btn.setMinSize(110, 110);
                btn.setPrefSize(110, 110);
                btn.setMaxSize(110, 110);
                btn.setOnAction(x -> {
                    imagenSeleccionada = i.murl();
                    imagenImageView.setImage(new Image(imagenSeleccionada, 300, 300, false, true, true));
                });

                bingimagesFlowPane.getChildren().add(btn);
            }

        });

        // Lanzamos el hilo
        new Thread(tarea).start();
    }

    @FXML public void actualizarProductos() {
        int categoria_id = categoriaComboBox.getSelectionModel().getSelectedItem().getId();
        List<Producto> productos = productoDAO.obtenerProductos(buscarField.getText(), categoria_id, activosMenuItem.isSelected(), noactivosMenuItem.isSelected());
        productosTable.getItems().setAll(FXCollections.observableList(productos));
    }
}
