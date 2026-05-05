package com.mateo.freetpv.controller;

import com.mateo.freetpv.FreeTPVApplication;
import com.mateo.freetpv.dao.UsuarioDAO;
import com.mateo.freetpv.model.Usuario;
import com.mateo.freetpv.util.NombreUtil;
import com.mateo.freetpv.util.SesionActual;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class LoginController {


    // Panel login
    @FXML
    private BorderPane loginPane;
    @FXML
    private ComboBox<String> usuarioComboBox;
    @FXML
    private PasswordField pinField;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;

    // Panel primer uso
    @FXML
    private BorderPane firstPane;
    @FXML
    private TextField newusuarioField;
    @FXML
    private PasswordField newpinField;
    @FXML
    private Label newerrorLabel;
    @FXML
    private Button newguardarButton;
    @FXML
    private Button buscarButton; // Boton para buscar BD

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @FXML
    public void initialize() {

        // Nos aseguramos que el panel de primer uso no se ve
        // Y el panel de login esta activo y el texto de error invisible
        firstPane.setVisible(false);
        loginPane.setDisable(false);
        errorLabel.setVisible(false);

        // Cargamos todos los nombres de usuarios de la base de datos
        List<String> usuarios = usuarioDAO.obtenerNombres();

        // Comprobamos que la lista no esta vacia
        if (!usuarios.isEmpty()) {
            usuarioComboBox.setItems(FXCollections.observableList(usuarios));
            return;
        }
        if (!usuarioDAO.existenUsuarios()) {
            // Si la lista esta vacia mostramos para crear el primer usuario o importar

            // Cojemos un nombre aleatorio de NombreUtil y lo usamos de prompt
            NombreUtil prompt = new NombreUtil();
            newusuarioField.setPromptText(prompt.getNombre());

            // Mostramos la pantalla y deshabilitamos la otra por si acaso
            loginPane.setDisable(true);
            firstPane.setVisible(true);
        }
    }

    @FXML
    public void handleLogin() {
        String usuario = usuarioComboBox.getSelectionModel().getSelectedItem();
        String pin = pinField.getText();

        // Comprobar que introduzca usuario y pin
        if (usuario == null || pin.isEmpty()) {
            errorLabel.setVisible(true);
            errorLabel.setText("Ingrese un usuario y pin.");
        } else {

            // Comprobamos si devuelve usuario o null para saber si es valido o no
            Usuario user = usuarioDAO.validarLogin(usuario, pin);
            if (user != null) {
                try {
                    // Cargar ventana principal
                    FXMLLoader fxmlLoader = new FXMLLoader(FreeTPVApplication.class.getResource("view/main-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());

                    // Conseguir stage desde el un objeto
                    Stage stage = (Stage) loginButton.getScene().getWindow();

                    // Ajustamos la sesion al usuario logeado
                    SesionActual.getInstancia().setUsuario(user);

                    stage.setScene(scene);
                } catch (IOException e) {
                    log.error("Error al cargar el view principal", e);
                }
            } else {
                errorLabel.setVisible(true);
                errorLabel.setText("Pin incorrecto.");
            }
        }
    }

    /**
     *
     * Crea el primer usuario administrador al no tener ningun usuario
     * Valida formulario correctamente y da error visual en {@code newerrorLabel}
     *
     */
    @FXML
    public void crearPrimerUsuario() {
        String usuario = newusuarioField.getText();
        String pin = newpinField.getText();
        newerrorLabel.setText("");
        // Comprobamos que en la base de datos no existen usuarios
        if (usuarioDAO.existenUsuarios()) {
            newerrorLabel.setText("La base de datos ya tiene usuarios.");
            newerrorLabel.setVisible(true);
            return;
        }
        // Comprobamos que haya pin y nombre escrito
        if (usuario.isEmpty() || pin.isEmpty()) {
            newerrorLabel.setText("Inserte un usuario y pin.");
            newerrorLabel.setVisible(true);
            return;
        }

        // Comprobamos que no tenga mas de 35 caracteres
        if (usuario.length() > 35) {
            newerrorLabel.setText("El nombre no debe tener mas de 35 caracteres");
            newerrorLabel.setVisible(true);
            return;
        }

        // Comprobamos que no existe un usuario con el mismo nombre
        if (usuarioDAO.existeUsuario(usuario)) {
            newerrorLabel.setText("Ya existe un usuario con ese nombre.");
            newerrorLabel.setVisible(true);
            return;
        }

        // Creamos un usuario nuevo Admin
        usuarioDAO.crearUsuario(usuario, pin, "Admin");

        // Actualizamos la lista de usuarios
        List<String> usuarios = usuarioDAO.obtenerNombres();
        usuarioComboBox.setItems(FXCollections.observableList(usuarios));

        // Cerramos la pantalla
        firstPane.setVisible(false);
        loginPane.setDisable(false);
    }
    @FXML
    public void importarBd() {
        // Creamos un selector de archivos
        FileChooser selector = new FileChooser();
        // Creamos un filtro de extension de archivo con opcion a .bd
        FileChooser.ExtensionFilter filtro = new FileChooser.ExtensionFilter("Base de datos (.db)", "*.db");
        // Añadimos el filtro al selector de archivos
        selector.getExtensionFilters().setAll(filtro);
        // Conseguir stage desde el un objeto
        Stage stage = (Stage) loginButton.getScene().getWindow();
        File bd = selector.showOpenDialog(stage);
        // Si se ha escogido un archivo
        if (bd != null) {
            // Establecemos el destino hasta donde tiene que estar el archivo
            Path destino = Path.of(System.getProperty("user.home") + "/.freetpv/freetpv.db");
            try {
                // Copiamos el archivo en el destino reemplazando el existente
                Files.copy(bd.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
                // Recargamos la pantalla
                initialize();
            } catch (IOException e) {
                log.error("Error al copiar base de datos importada", e);
            }
        }
    }
}