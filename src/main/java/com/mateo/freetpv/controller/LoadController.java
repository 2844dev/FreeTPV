package com.mateo.freetpv.controller;

import com.mateo.freetpv.FreeTPVApplication;
import com.mateo.freetpv.dao.UsuarioDAO;
import com.mateo.freetpv.service.AjustesService;
import com.mateo.freetpv.service.BackupService;
import com.mateo.freetpv.util.DatabaseConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class LoadController {

    private static final Logger log = LoggerFactory.getLogger(LoadController.class);

    @FXML
    private Button crearButton;

    @FXML
    private Button importarButton;

    @FXML
    private PasswordField newPinField;

    @FXML
    private TextField newUsuarioField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button seleccionarButton;

    @FXML
    private Label seleccionadoLabel;

    private final String path = System.getProperty("user.home") + "/.freetpv";

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    private final BackupService backupService = new BackupService();

    private File archivoSeleccionado;

    public void initialize() {
        new File(path).mkdirs();
        new File(path + "/img/productos/").mkdirs();
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

    @FXML
    public void seleccionarBackup() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Copia de seguridad", "*.tpv"));
        File archivo = fc.showOpenDialog(crearButton.getScene().getWindow());
        if (archivo != null && archivo.exists()) {
            archivoSeleccionado = archivo;
            seleccionadoLabel.setText("Has seleccionado " + archivo.getName());
            importarButton.setDisable(false);
        }
    }

    @FXML
    public void importarBackup() {
        if (archivoSeleccionado == null) return;

        if (backupService.restaurarBackup(archivoSeleccionado)) {
            initialize();
        } else {
            seleccionadoLabel.setText("No se ha podido importar la copia de seguridad");
        }
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
        String usuario = newUsuarioField.getText().trim();
        String clave = newPinField.getText().trim();
        errorLabel.setText("");
        // Comprobamos que en la base de datos no existen usuarios
        if (usuarioDAO.existenUsuarios()) {
            errorLabel.setText("La base de datos ya tiene usuarios.");
            return;
        }
        // Comprobamos que haya pin y nombre escrito
        if (usuario.isEmpty() || clave.isEmpty()) {
            errorLabel.setText("Inserte un usuario y clave.");
            return;
        }

        if (!claveValida(clave)) {
            errorLabel.setText("La clave debe tener entre 4 y 16 caracteres y no contener espacios");
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
        usuarioDAO.crearUsuario(usuario, clave, "Admin");

        cargarLogin();
    }

    private boolean claveValida(String clave) {
        return clave != null && clave.matches("\\S{4,16}");
    }
}
