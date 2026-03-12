package com.mateo.freetpv.controller;

import com.mateo.freetpv.dao.UsuarioDAO;
import com.mateo.freetpv.model.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class EmpleadosController {
    // Panel principal de empleados
    @FXML private BorderPane empleadosPane;
    @FXML private Button editarButton;
    @FXML private Button nuevoButton;
    @FXML private TextField buscarField;
    @FXML private TableView<Usuario> empleadosTable;
    @FXML private TableColumn<Usuario, Integer> codigoColumn;
    @FXML private TableColumn<Usuario, String> nombreColumn;
    @FXML private TableColumn<Usuario, String> rolColumn;
    @FXML private TableColumn<Usuario, String> fechaColumn;

    // Panel extra de edicion y creacion de empleados
    @FXML private BorderPane nuevoempleadoPane;
    @FXML private TextField usuarioField;
    @FXML private PasswordField pinField;
    @FXML private ChoiceBox<String> rolChoiceBox;
    @FXML private Button guardarButton;
    @FXML private Button cancelarButton;
    @FXML private Label errorLabel;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    private Usuario usuarioEditando = null;

    @FXML public void initialize() {

        // Nos aseguramos que el panel de nuevo empleado esta invisible
        nuevoempleadoPane.setVisible(false);

        // Indicamos a cada columna que atributo de usuario mostrar
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        rolColumn.setCellValueFactory(new PropertyValueFactory<>("rol"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));

        // Actualizamos la tabla con los usuarios
        cargarUsuarios();

        // Añadimos los roles al choicebox para elegir
        rolChoiceBox.setItems(FXCollections.observableArrayList("admin", "camarero"));
    }

    // Guardamos el usuario

    @FXML public void guardarFormulario() {
        if (usuarioEditando != null) {
            if (usuarioField.getText().equals("") || rolChoiceBox.getSelectionModel().getSelectedItem() == null) {
                errorLabel.setText("Debe rellenar usuario y rol");
            } else {
                usuarioDAO.editarUsuario(usuarioEditando, usuarioField.getText(), pinField.getText(), rolChoiceBox.getSelectionModel().getSelectedItem());
                // Actualizamos la tabla
                cargarUsuarios();

                // Cerramos el formulario
                cerrarFormulario();
            }
        } else {
            if (usuarioField.getText().equals("") || pinField.getText().equals("") || rolChoiceBox.getSelectionModel().getSelectedItem() == null) {
                errorLabel.setText("Debe rellenar todos los campos.");
            } else {
                usuarioDAO.crearUsuario(usuarioField.getText(), pinField.getText(), rolChoiceBox.getSelectionModel().getSelectedItem());
                // Actualizamos la tabla
                cargarUsuarios();

                // Cerramos el formulario
                cerrarFormulario();
            }
        }
    }

    // Mostramos el formulario vacio si le damos al boton de Nuevo Usuario
    @FXML public void mostrarFormularioNuevo() {
        empleadosPane.setDisable(true);
        nuevoempleadoPane.setVisible(true);
    }

    @FXML public void mostrarFormularioEditar() {

        // Escogemos el usuario editado como el seleccionado en la tabla
        usuarioEditando = empleadosTable.getSelectionModel().getSelectedItem();

        // Comprobamos que haya un usuario seleccionado
        if (usuarioEditando != null) {
        // Ponemos los datos del usuario en el formulario
        usuarioField.setText(usuarioEditando.getNombre());

        // Dejamos el pin en blanco si no lo queremos editar
        pinField.clear();

        // Seleccionamos admin si tiene admin, si no siempre sera camarero
        if (usuarioEditando.getRol().equals("admin")) {
            rolChoiceBox.getSelectionModel().select(0);
        } else {
            rolChoiceBox.getSelectionModel().select(1);
        }

        // Hacemos visible el panel
        empleadosPane.setDisable(true);
        nuevoempleadoPane.setVisible(true);

        // Si no hay usuario seleccionado cancelamos
        }
    }

    // Eliminamos todos los datos que queden en los campos del formulario una vez se cancela
    @FXML public void cerrarFormulario() {
        nuevoempleadoPane.setVisible(false);

        // Limpiamos el formulario
        usuarioField.clear();
        pinField.clear();
        rolChoiceBox.getSelectionModel().clearSelection();

        // Habilitamos el panel principal
        empleadosPane.setDisable(false);
    }

    private void cargarUsuarios() {
        // Creamos una lista de usuarios llamando al metodo de UsuarioDAO
        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();

        // Convertimos la lista a una observableList y lo ponemos como los items en la tabla
        empleadosTable.setItems(FXCollections.observableList(usuarios));
    }
}
