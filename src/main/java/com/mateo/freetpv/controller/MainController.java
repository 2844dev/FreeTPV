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
    @FXML private StackPane contenedor;


    private void cargarVista(String fxml) {
        try {

            // Creamos un fxmlloader con un archivo fxml indicado
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/" + fxml));

            // Creamos un nuevo nodo donde cargamos el fxml
            Node vista = fxmlLoader.load();

            // Y cargamos el nodo en el contenedor
            contenedor.getChildren().setAll(vista);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML public void mostrarVentas() {
        cargarVista("ventas-view.fxml");
    }
    @FXML public void mostrarEmpleados() {
        cargarVista("empleados-view.fxml");
    }

}
