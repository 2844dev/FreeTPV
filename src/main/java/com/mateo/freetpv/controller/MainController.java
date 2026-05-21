package com.mateo.freetpv.controller;

import atlantafx.base.theme.Styles;
import com.mateo.freetpv.FreeTPVApplication;
import com.mateo.freetpv.service.AjustesService;
import com.mateo.freetpv.service.BackupService;
import com.mateo.freetpv.util.SesionActual;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    @FXML private Button ventasButton;
    @FXML private Button empleadosButton;
    @FXML private Button productosButton;
    @FXML private Button mesasButton;
    @FXML private Button clientesButton;
    @FXML private Button ajustesButton;
    @FXML private StackPane contenedor;
    @FXML private VBox sidebar;

    private String panelActual = null;
    private List<Button> botones;

    private final AjustesService ajustesService = new AjustesService();

    private final BackupService backupService = new BackupService();

    @FXML public void initialize() {
        // Establecemos el panel actual
        panelActual = "blank-view";
        botones = List.of(ventasButton, empleadosButton, productosButton, mesasButton, clientesButton, ajustesButton);
        if (!SesionActual.getInstancia().esAdmin()) {
            empleadosButton.setDisable(true);
            productosButton.setDisable(true);
            ajustesButton.setDisable(true);
        }

        String ruta = ajustesService.getBackupRuta();
        if (ruta.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Copia de seguridad");
            alert.setHeaderText("Copia de seguridad automática");
            alert.setContentText("No hay ruta de copia de seguridad configurada");
            alert.showAndWait();
            return;
        }
        int backupFrecuencia = ajustesService.getBackupFrecuencia();
        String ultimoBackup = ajustesService.getBackupUltimo();
        Platform.runLater(() -> {
            String mensaje = ultimoBackup.isEmpty()
                    ? "Se va a realizar tu primera copia en:\n" + ruta + "\n¿Continuar?"
                    : "Se va a realizar una copia en:\n" + ruta + "\n¿Continuar?";

            if (!ultimoBackup.isEmpty()) {
                LocalDate ultimo = LocalDate.parse(ultimoBackup, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if (ChronoUnit.DAYS.between(ultimo, LocalDate.now()) < backupFrecuencia) return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Copia de seguridad");
            alert.setHeaderText("Copia de seguridad automática");
            alert.setContentText(mensaje);
            Optional<ButtonType> resultado = alert.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                backupService.hacerBackup(ruta);
                ajustesService.setBackupUltimo(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        });
    }

    private void cargarVista(String fxml, Button button, boolean mostrarSidebar) {
        try {
            // Comprobamos que el panel actual no es el mismo ni null
            if (panelActual == null || panelActual.equals(fxml)) {
                return;
            }
            // Creamos un fxmlloader con un archivo fxml indicado
            FXMLLoader fxmlLoader = new FXMLLoader(FreeTPVApplication.class.getResource("view/" + fxml));

            // Creamos un nuevo nodo donde cargamos el fxml
            Node vista = fxmlLoader.load();

            // Y cargamos el nodo en el contenedor
            contenedor.getChildren().setAll(vista);

            // Reestablecemos todos los botones a su estado por defecto
            for (Button b : botones) {
                b.getStyleClass().remove(Styles.ACCENT);
            }
            button.getStyleClass().add(Styles.ACCENT);
            sidebar.setVisible(mostrarSidebar);
            sidebar.setManaged(mostrarSidebar);
            panelActual = fxml;
        } catch (IOException e) {
            log.error("Error al cargar vista", e);
        }
    }

    @FXML public void mostrarVentas() { cargarVista("ventas-view.fxml", ventasButton, false); }
    @FXML public void mostrarEmpleados() {
        if (SesionActual.getInstancia().esAdmin()) {
            cargarVista("empleados-view.fxml", empleadosButton, true);
        }
    }
    @FXML public void mostrarProductos() {
        if (SesionActual.getInstancia().esAdmin()) {
            cargarVista(("productos-view.fxml"), productosButton, true);
        }
    }
    @FXML public void mostrarAjustes() {
        if (SesionActual.getInstancia().esAdmin()) {
            cargarVista("ajustes-view.fxml", ajustesButton, true);
        }
    }
}
