package com.mateo.freetpv.controller;

import com.mateo.freetpv.dao.UsuarioDAO;
import com.mateo.freetpv.model.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    @FXML private ChoiceBox rolChoiceBox;
    @FXML private Button guardarButton;
    @FXML private Button cancelarButton;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML public void initialize() {

        // Nos aseguramos que el panel de nuevo empleado esta invisible
        nuevoempleadoPane.setVisible(false);

        // Indicamos a cada columna que atributo de usuario mostrar
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        rolColumn.setCellValueFactory(new PropertyValueFactory<>("rol"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));

        // Creamos una lista de usuarios llamando al metodo de UsuarioDAO
        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();

        // Convertimos la lista a una observableList y lo ponemos como los items en la tabla
        empleadosTable.setItems(FXCollections.observableList(usuarios));

        // Añadimos los roles al choicebox para elegir
        rolChoiceBox.setItems(FXCollections.observableArrayList("admin", "camarero"));
    }

    // Mostramos el formulario vacio si le damos al boton de Nuevo Usuario
    @FXML public void mostrarFormularioNuevo() {
        empleadosPane.setDisable(true);
        nuevoempleadoPane.setVisible(true);
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
}
