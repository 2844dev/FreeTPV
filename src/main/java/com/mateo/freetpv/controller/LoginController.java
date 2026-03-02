package com.mateo.freetpv.controller;

import com.mateo.freetpv.HelloApplication;
import com.mateo.freetpv.dao.UsuarioDAO;
import com.mateo.freetpv.model.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LoginController {
    @FXML private ComboBox<String> usuarioComboBox;
    @FXML private PasswordField pinField;
    @FXML private Button loginButton;
    @FXML private Label errorLabel;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML public void initialize() {

        // Cargamos todos los usuarios de la base de datos
        List<String> usuarios = usuarioDAO.obtenerNombres();
        usuarioComboBox.setItems(FXCollections.observableList(usuarios));
    }

    @FXML public void handleLogin() {
        String usuario = usuarioComboBox.getSelectionModel().getSelectedItem();
        String pin = pinField.getText();

        // Comprobar que introduzca usuario y pin
        if (usuario == null || pin.isEmpty()) {
            errorLabel.setVisible(true);
            errorLabel.setText("Ingrese un usuario o pin.");
        } else {

            // Comprobamos si devuelve usuario o null para saber si es valido o no
            Usuario user = usuarioDAO.validarLogin(usuario, pin);
            if (user != null) {
                try {
                    // Cargar ventana principal
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/main-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

                    // Conseguir stage desde el un objeto
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.setScene(scene);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                errorLabel.setVisible(true);
                errorLabel.setText("Pin incorrecto.");
            }
        }
    }
}
