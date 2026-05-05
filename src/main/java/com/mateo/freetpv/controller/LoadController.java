package com.mateo.freetpv.controller;

import com.mateo.freetpv.FreeTPVApplication;
import com.mateo.freetpv.dao.UsuarioDAO;
import com.mateo.freetpv.service.AjustesService;
import com.mateo.freetpv.util.DatabaseConnection;
import com.mateo.freetpv.util.SesionActual;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LoadController {

    private static final Logger log = LoggerFactory.getLogger(LoadController.class);
    @FXML
    private TableColumn<?, ?> cantidadTableColumn;

    @FXML
    private Button crearButton;

    @FXML
    private Button importarButton;

    @FXML
    private TableView<?> importarTableView;

    @FXML
    private PasswordField newPinField;

    @FXML
    private TextField newUsuarioField;

    @FXML
    private Label errorLabel;

    @FXML
    private TableColumn<?, ?> resultadoTableColumn;

    @FXML
    private Button seleccionarButton;

    @FXML
    private TableColumn<?, ?> verTableColumn;

    private final String path = System.getProperty("user.home") + "/.freetpv";

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void initialize() {
        new File(path).mkdirs();
        File db = new File(path + "/freetpv.db");
        if (!db.exists()) {
            DatabaseConnection.getInstancia().initDatabase();
            new AjustesService().loadTema();
            return;
        }

        DatabaseConnection.getInstancia().initDatabase();
        new AjustesService().loadTema();

        if (!usuarioDAO.existenUsuarios()) {
            return;
        }
        Platform.runLater(() -> cargarLogin());
    }

    private void cargarLogin() {
        try {
            // Cargar ventana principal
            FXMLLoader fxmlLoader = new FXMLLoader(FreeTPVApplication.class.getResource("view/login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Conseguir stage desde el un objeto
            Stage stage = (Stage) crearButton.getScene().getWindow();

            stage.setScene(scene);
        } catch (IOException e) {
            log.error("Error al cargar el view login", e);
        }
    }

    @FXML
    public void crearAdministrador() {
        String usuario = newUsuarioField.getText();
        String pin = newPinField.getText();
        errorLabel.setText("");
        // Comprobamos que en la base de datos no existen usuarios
        if (usuarioDAO.existenUsuarios()) {
            errorLabel.setText("La base de datos ya tiene usuarios.");
            return;
        }
        // Comprobamos que haya pin y nombre escrito
        if (usuario.isEmpty() || pin.isEmpty()) {
            errorLabel.setText("Inserte un usuario y pin.");
            return;
        }

        // Comprobamos que no tenga mas de 35 caracteres
        if (usuario.length() > 35) {
            errorLabel.setText("El nombre no debe tener mas de 35 caracteres");
            return;
        }

        // Comprobamos que no existe un usuario con el mismo nombre
        if (usuarioDAO.existeUsuario(usuario)) {
            errorLabel.setText("Ya existe un usuario con ese nombre.");
            return;
        }

        // Creamos un usuario nuevo Admin
        usuarioDAO.crearUsuario(usuario, pin, "Admin");

        cargarLogin();
    }
}
