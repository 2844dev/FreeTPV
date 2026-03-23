package com.mateo.freetpv.controller;

import com.mateo.freetpv.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {
    @FXML private Button ventasButton;
    @FXML private Button empleadosButton;
    @FXML private Button productosButton;
    @FXML private Button mesasButton;
    @FXML private Button clientesButton;
    @FXML private Button ajustesButton;
    @FXML private StackPane contenedor;

    private void cargarVista(String fxml, Button button) {
        try {

            // Creamos un fxmlloader con un archivo fxml indicado
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/" + fxml));

            // Creamos un nuevo nodo donde cargamos el fxml
            Node vista = fxmlLoader.load();

            // Y cargamos el nodo en el contenedor
            contenedor.getChildren().setAll(vista);

            ventasButton.setStyle("-fx-background-radius: 0");
            empleadosButton.setStyle("-fx-background-radius: 0");
            ajustesButton.setStyle("-fx-background-radius: 0");
            button.setStyle("-fx-background-color: -color-accent-4; -fx-background-radius: 0");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML public void mostrarVentas() {
        cargarVista("ventas-view.fxml", ventasButton);
    }
    @FXML public void mostrarEmpleados() {
        cargarVista("empleados-view.fxml", empleadosButton);
    }
    @FXML public void mostrarAjustes() {
        cargarVista("ajustes-view.fxml", ajustesButton);
    }
}
