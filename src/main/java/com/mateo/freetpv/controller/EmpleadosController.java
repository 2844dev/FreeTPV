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
    @FXML private CheckBox estadoCheckBox;
    @FXML private Button guardarButton;
    @FXML private Button cancelarButton;
    @FXML private Label errorLabel;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    private Usuario usuarioEditando = null;

    private Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);

    @FXML public void initialize() {

        // Nos aseguramos que el panel de nuevo empleado esta invisible
        nuevoempleadoPane.setVisible(false);

        // Nos aseguramos que no tiene texto
        errorLabel.setText("");

        // Indicamos a cada columna que atributo de usuario mostrar
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        rolColumn.setCellValueFactory(new PropertyValueFactory<>("rol"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));

        // Actualizamos la tabla con los usuarios
        cargarUsuarios();

        // Añadimos los roles al choicebox para elegir
        rolChoiceBox.setItems(FXCollections.observableArrayList("Admin", "Camarero"));
    }

    // Guardamos el usuario

    @FXML public void guardarFormulario() {
        if (usuarioEditando != null) {
            if (usuarioField.getText().isEmpty() || rolChoiceBox.getSelectionModel().getSelectedItem() == null) {
                errorLabel.setText("Debe rellenar usuario y rol");
            } else {

                // Crear usuario
                usuarioDAO.editarUsuario(usuarioEditando, usuarioField.getText(), pinField.getText(), rolChoiceBox.getSelectionModel().getSelectedItem(), estadoCheckBox.isSelected());

                // Mostramos un mensaje de confirmación
                infoAlert.setTitle("Editar Usuario");
                infoAlert.setHeaderText("Usuario editado correctamente");
                infoAlert.setContentText("Usuario " + usuarioField.getText() + " editado.");
                infoAlert.showAndWait();

                // Actualizamos la tabla
                cargarUsuarios();

                // Cerramos el formulario
                cerrarFormulario();
            }
        } else {
            if (usuarioField.getText().isEmpty() || pinField.getText().isEmpty() || rolChoiceBox.getSelectionModel().getSelectedItem() == null) {
                errorLabel.setText("Debe rellenar todos los campos.");
            } else {
                usuarioDAO.crearUsuario(usuarioField.getText(), pinField.getText(), rolChoiceBox.getSelectionModel().getSelectedItem());

                // Mostramos un mensaje de confirmación
                infoAlert.setTitle("Crear Usuario");
                infoAlert.setHeaderText("Usuario creado correctamente");
                infoAlert.setContentText("Usuario " + usuarioField.getText() + " creado.");
                infoAlert.showAndWait();

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

        // Forzamos que un usuario este habilitado por defecto
        estadoCheckBox.setSelected(true);
        estadoCheckBox.setDisable(true);
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
        if (usuarioEditando.getRol().equals("Admin")) {
            rolChoiceBox.getSelectionModel().select(0); // Admin es la opcion 0
        } else {
            rolChoiceBox.getSelectionModel().select(1); // Camarero es la opcion 1
        }

        // Habilitamos la edición del estado y lo seleccionamos acorde a su estado
        estadoCheckBox.setDisable(false);
        estadoCheckBox.setSelected(usuarioEditando.getEstado());

        // Hacemos visible el panel
        empleadosPane.setDisable(true);
        nuevoempleadoPane.setVisible(true);

        }
    }

    // Eliminamos todos los datos que queden en los campos del formulario una vez se cancela
    @FXML public void cerrarFormulario() {
        nuevoempleadoPane.setVisible(false);

        // Limpiamos el formulario
        usuarioField.clear();
        pinField.clear();
        rolChoiceBox.getSelectionModel().clearSelection();
        errorLabel.setText("");

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
