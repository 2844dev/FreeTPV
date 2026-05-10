package com.mateo.freetpv.controller;

import com.mateo.freetpv.FreeTPVApplication;
import com.mateo.freetpv.dao.UsuarioDAO;
import com.mateo.freetpv.model.Usuario;
import com.mateo.freetpv.util.SesionActual;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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


    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @FXML
    public void initialize() {

        // Nos aseguramos que el panel de primer uso no se ve
        // Y el panel de login esta activo y el texto de error invisible
        loginPane.setDisable(false);
        errorLabel.setVisible(false);

        // Cargamos todos los nombres de usuarios de la base de datos
        List<String> usuarios = usuarioDAO.obtenerNombres();

        // Comprobamos que la lista no esta vacia
        if (!usuarios.isEmpty()) {
            usuarioComboBox.setItems(FXCollections.observableList(usuarios));
            return;
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

                    stage.setResizable(true);
                    stage.setMinWidth(1280);
                    stage.setMinHeight(720);
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
}