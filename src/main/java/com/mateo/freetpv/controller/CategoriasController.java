package com.mateo.freetpv.controller;

import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Tweaks;
import com.mateo.freetpv.dao.CategoriaDAO;
import com.mateo.freetpv.model.Categoria;
import com.mateo.freetpv.model.Usuario;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

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
    @FXML private TextField usuarioField;
    @FXML private Label errorLabel;
    @FXML private Button guardarButton;
    @FXML private Button cancelarButton;
    @FXML private Button borrarButton;

    final private CategoriaDAO categoriaDAO = new CategoriaDAO();

    private Categoria categoriaEditando = null;

    final private Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);

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

        cargarCategorias();
    }

    @FXML public void guardarCategoria() {

        String nombre = usuarioField.getText();

        if (nombre.isEmpty()) {
            errorLabel.setText("Debe ingresar un nombre");
            return;
        }
        if (nombre.length() > 35) {
            errorLabel.setText("El nombre debe tener menos de 35 caracteres");
            return;
        }
        if (categoriaDAO.existeCategoria(nombre) && (categoriaEditando == null || !nombre.equals(categoriaEditando.getNombre()))) {
            errorLabel.setText("Ya existe una categoria con ese nombre");
            return;
        }
        if (categoriaEditando == null) {
            categoriaDAO.crearCategoria(nombre);
            infoAlert.setTitle("Crear Categoria");
            infoAlert.setHeaderText("Categoria creada correctamente");
            infoAlert.setContentText("Categoria " + nombre + " creada.");
            infoAlert.showAndWait();
        } else {
            categoriaDAO.editarCategoria(categoriaEditando, nombre);
            infoAlert.setTitle("Editar Categoria");
            infoAlert.setHeaderText("Categoria editada correctamente");
            infoAlert.setContentText("Categoria " + nombre + " editada.");
            infoAlert.showAndWait();
        }

        actualizarCategorias();
        cerrarFormulario();
    }

    @FXML public void mostrarformularioNuevo() {
        errorLabel.setText("");
        categoriasPane.setDisable(true);
        nuevacategoriaPane.setVisible(true);
    }

    private void mostrarFormularioEditar(Categoria categoria) {

        categoriaEditando = categoria;

        if  (categoriaEditando != null) {

            usuarioField.setText(categoriaEditando.getNombre());

            // Deseleccionamos el campo despues de renderizarlo
            Platform.runLater(() -> guardarButton.requestFocus());

        }

        errorLabel.setText("");
        categoriasPane.setDisable(true);
        nuevacategoriaPane.setVisible(true);
    }

    @FXML public void cerrarFormulario() {
        usuarioField.clear();
        nuevacategoriaPane.setVisible(false);
        categoriasPane.setDisable(false);
        categoriaEditando = null;
    }

    private void cargarCategorias() {
        // Creamos una lista de usuarios llamando al metodo de UsuarioDAO
        List<Categoria> categorias = categoriaDAO.obtenerCategorias(buscarField.getText());

        // Convertimos la lista a una observableList y lo ponemos como los items en la tabla
        categoriasTable.setItems(FXCollections.observableList(categorias));
    }

    @FXML public void actualizarCategorias() {
        // Creamos una lista de usuarios llamando al metodo de UsuarioDAO
        List<Categoria> categorias = categoriaDAO.obtenerCategorias(buscarField.getText());

        // Convertimos la lista a una observableList y lo ponemos como los items en la tabla
        categoriasTable.getItems().setAll(FXCollections.observableList(categorias));
    }
}
