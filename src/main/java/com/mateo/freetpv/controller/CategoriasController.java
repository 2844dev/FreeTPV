package com.mateo.freetpv.controller;

import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Tweaks;
import atlantafx.base.util.Animations;
import com.mateo.freetpv.dao.CategoriaDAO;
import com.mateo.freetpv.model.Categoria;
import com.mateo.freetpv.util.SesionActual;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.Optional;

public class CategoriasController {
    // Panel principal categorias
    @FXML private BorderPane categoriasPane;
    @FXML private TableView<Categoria> categoriasTable;
    @FXML private TableColumn<Categoria, Integer> codigoColumn;
    @FXML private TableColumn<Categoria, String> nombreColumn;
    @FXML private TableColumn<Categoria, Void> editarColumn;
    @FXML private TextField buscarField;
    @FXML private Button nuevacategoriaButton;

    // Panel para editar y crear categorias
    @FXML private BorderPane nuevacategoriaPane;
    @FXML private Text nuevoText;
    @FXML private Text categoriaText;
    @FXML private TextField nombreField;
    @FXML private Label errorLabel;
    @FXML private Button guardarButton;
    @FXML private Button cancelarButton;
    @FXML private Button borrarButton;

    final private CategoriaDAO categoriaDAO = new CategoriaDAO();

    private Categoria categoriaEditando = null;

    final private Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);

    final private Alert warnAlert = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML public void initialize() {
        // Nos aseguramos que al cargarlo se ve solo el panel principal
        categoriasPane.setVisible(true);
        nuevacategoriaPane.setVisible(false);

        // Añadimos un listener al field de buscar para que actualize los resultados
        buscarField.textProperty().addListener((observable, oldValue, newValue) -> actualizarCategorias());

        // Indicamos a cada columna que atributo de las categorias mostrar
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        editarColumn.getStyleClass().add(Tweaks.ALIGN_CENTER);

        // Configuramos fabrica para poner los botones en cada celda
        editarColumn.setCellFactory(param -> new TableCell<>() {

            // Creamos un atributo boton
            private Button editarButton = new Button(null, new FontIcon("fas-edit"));

            // Bloque inicializador
            {
                // Configuramos como se vera el boton
                editarButton.getStyleClass().addAll(Styles.BUTTON_ICON, Styles.BUTTON_OUTLINED);
                editarButton.setOnAction(e -> {
                    mostrarFormularioEditar(getTableView().getItems().get(getIndex()));
                });
            }

            // Llamamos a updateItem para decir si se vera o no el botón
            @Override protected void updateItem(Void item, boolean empty) {
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editarButton);
                }
            }
        });

        actualizarCategorias();
    }

    @FXML public void guardarCategoria() {

        String nombre = nombreField.getText().trim();

        if (!SesionActual.getInstancia().esAdmin()) {
            errorLabel.setText("Debes ser administrador");
            return;
        }

        if (nombre.isEmpty()) {
            errorLabel.setText("Debe ingresar un nombre");
            return;
        }
        if (nombre.length() > 35) {
            errorLabel.setText("El nombre debe tener menos de 35 caracteres");
            return;
        }
        if (nombre.equalsIgnoreCase("Favoritos") || nombre.equalsIgnoreCase("Favorito")) {
            errorLabel.setText("No puedes crear una categoría con ese nombre");
            return;
        }
        if (categoriaDAO.existeCategoria(nombre) && (categoriaEditando == null || !nombre.equalsIgnoreCase(categoriaEditando.getNombre()))) {
            errorLabel.setText("Ya existe una categoria con ese nombre");
            return;
        }
        if (categoriaEditando == null) {
            if (categoriaDAO.crearCategoria(nombre)) {
                infoAlert.setTitle("Crear Categoria");
                infoAlert.setHeaderText("Categoria creada correctamente");
                infoAlert.setContentText("Categoria " + nombre + " creada.");
                infoAlert.showAndWait();
            } else {
                errorLabel.setText("No se puedo crear la categoria");
                return;
            }
        } else {
            if (categoriaDAO.editarCategoria(categoriaEditando, nombre)) {
                infoAlert.setTitle("Editar Categoria");
                infoAlert.setHeaderText("Categoria editada correctamente");
                infoAlert.setContentText("Categoria " + nombre + " editada.");
                infoAlert.showAndWait();
            } else {
                errorLabel.setText("No se pudo editar la categoria");
                return;
            }
        }

        actualizarCategorias();
        cerrarFormulario();
    }

    @FXML
    public void mostrarFormularioNuevo() {
        errorLabel.setText("");
        nuevoText.setText("Creando nueva categoría ");
        categoriaText.setText("");
        categoriaText.setVisible(false);

        categoriasPane.setDisable(true);
        nuevacategoriaPane.setVisible(true);

        var animation = Animations.fadeIn(nuevacategoriaPane, Duration.seconds(0.5));
        animation.playFromStart();
    }

    private void mostrarFormularioEditar(Categoria categoria) {

        categoriaEditando = categoria;

        if  (categoriaEditando != null) {

            nuevoText.setText("Editando categoría: ");
            categoriaText.setText(categoria.getNombre());
            categoriaText.setVisible(true);

            nombreField.setText(categoriaEditando.getNombre());

            // Deseleccionamos el campo despues de renderizarlo
            Platform.runLater(() -> guardarButton.requestFocus());

        }

        errorLabel.setText("");
        categoriasPane.setDisable(true);
        nuevacategoriaPane.setVisible(true);

        var animation = Animations.fadeIn(nuevacategoriaPane, Duration.seconds(0.5));
        animation.playFromStart();
    }

    @FXML public void borrarCategoria() {

        if (!SesionActual.getInstancia().esAdmin()) {
            errorLabel.setText("Debes ser administrador");
            return;
        }

        if (categoriaEditando != null) {
            if (categoriaDAO.tieneProductos(categoriaEditando)) {
                errorLabel.setText("Esta categoria contiene productos.");
                return;
            }
            warnAlert.setTitle("Eliminar Categoria");
            warnAlert.setHeaderText("Se va a eliminar una categoria!");
            warnAlert.setContentText("Vas a eliminar la categoria " + categoriaEditando.getNombre() +
                    " esto no se puede revertir.");
            Optional<ButtonType> resultado = warnAlert.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                if (categoriaDAO.borrarCategoria(categoriaEditando)) {
                    cerrarFormulario();
                    actualizarCategorias();
                } else {
                    errorLabel.setText("No se pudo eliminar la categoría");
                }
            }
        } else {
            errorLabel.setText("No se pudo eliminar la categoria");
        }
    }

    @FXML public void cerrarFormulario() {
        nombreField.clear();
        nuevacategoriaPane.setVisible(false);
        categoriasPane.setDisable(false);
        categoriaEditando = null;
    }

    @FXML public void actualizarCategorias() {
        // Creamos una lista de usuarios llamando al metodo de UsuarioDAO
        List<Categoria> categorias = categoriaDAO.obtenerCategorias(buscarField.getText());

        // Convertimos la lista a una observableList y lo ponemos como los items en la tabla
        categoriasTable.getItems().setAll(FXCollections.observableList(categorias));
    }
}
