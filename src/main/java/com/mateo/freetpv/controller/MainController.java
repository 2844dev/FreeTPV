package com.mateo.freetpv.controller;

import atlantafx.base.theme.Styles;
import com.mateo.freetpv.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    @FXML private Button ventasButton;
    @FXML private Button empleadosButton;
    @FXML private Button productosButton;
    @FXML private Button mesasButton;
    @FXML private Button clientesButton;
    @FXML private Button ajustesButton;
    @FXML private StackPane contenedor;

    private String panelActual = null;
    private List<Button> botones;

    @FXML public void initialize() {
        // Establecemos el panel actual
        panelActual = "blank-view";
        botones = List.of(ventasButton, empleadosButton, productosButton, mesasButton, clientesButton, ajustesButton);
    }

    private void cargarVista(String fxml, Button button) {
        try {
            // Comprobamos que el panel actual no es el mismo ni null
            if (panelActual == null || panelActual.equals(fxml)) {
                return;
            }
            // Creamos un fxmlloader con un archivo fxml indicado
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/" + fxml));

            // Creamos un nuevo nodo donde cargamos el fxml
            Node vista = fxmlLoader.load();

            // Y cargamos el nodo en el contenedor
            contenedor.getChildren().setAll(vista);

            // Reestablecemos todos los botones a su estado por defecto
            for (Button b : botones) {
                b.getStyleClass().remove(Styles.ACCENT);
            }
            button.getStyleClass().add(Styles.ACCENT);
            panelActual = fxml;
        } catch (IOException e) {
            log.error("Error al cargar vista", e);
        }
    }

    @FXML public void mostrarVentas() {
        cargarVista("ventas-view.fxml", ventasButton);
    }
    @FXML public void mostrarEmpleados() {
        cargarVista("empleados-view.fxml", empleadosButton);
    }
    @FXML public void mostrarProductos() { cargarVista(("productos-view.fxml"), productosButton); }
    @FXML public void mostrarAjustes() {
        cargarVista("ajustes-view.fxml", ajustesButton);
    }
}
