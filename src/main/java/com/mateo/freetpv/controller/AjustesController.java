package com.mateo.freetpv.controller;

import atlantafx.base.controls.Tile;
import com.mateo.freetpv.service.AjustesService;
import com.mateo.freetpv.util.SesionActual;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AjustesController {

    private static final Logger log = LoggerFactory.getLogger(AjustesController.class);

    @FXML private BorderPane ajustesPane;
    @FXML private Button guardarButton;



    // EMPRESA
    @FXML private Tile nombreEmpresaTile;
    @FXML private Tile cifEmpresaTile;
    @FXML private Tile direccionEmpresaTile;
    @FXML private Tile codigoPostalEmpresaTile;
    @FXML private Tile ciudadEmpresaTile;
    @FXML private Tile telefonoEmpresaTile;
    @FXML private Tile webEmpresaTile;
    @FXML private Tile qrEmpresaTile;

    // TICKET
    @FXML private Tile tituloTicketTile;
    @FXML private Tile mensajeFinalTicketTile;
    @FXML private Tile mostrarCifTicketTile;
    @FXML private Tile mostrarTelefonoTicketTile;
    @FXML private Tile mostrarWebTicketTile;
    @FXML private Tile mostrarIvaTicketTile;
    @FXML private Tile mostrarQrTicketTile;

    // IMPRESORA
    @FXML private Tile impresoraNombreTile;
    @FXML private Tile impresoraAnchoTile;
    @FXML private Tile impresoraCodepageTile;
    @FXML private Tile impresoraCortarPapelTile;
    @FXML private Tile impresoraAbrirCajonTile;

    // APARIENCIA
    @FXML private Tile temaTile;

    // BACKUPS
    @FXML private Tile backupRutaTile;
    @FXML private Tile backupUltimoTile;

    private final AjustesService ajustesService = new AjustesService();
    private final List<Runnable> accionesGuardar = new ArrayList<>();

    @FXML
    public void initialize() {

        configurarEmpresa();
        configurarTicket();
        configurarImpresora();
        configurarApariencia();
        configurarBackups();

        guardarButton.setDisable(true);
    }


    private void configurarEmpresa() {
        configurarTextFieldTile(
                nombreEmpresaTile,
                ajustesService.getEmpresaNombre(),
                "Kitauji Bar",
                ajustesService::setEmpresaNombre
        );

        configurarTextFieldTile(
                cifEmpresaTile,
                ajustesService.getEmpresaCif(),
                "B12345678",
                ajustesService::setEmpresaCif
        );

        configurarTextFieldTile(
                direccionEmpresaTile,
                ajustesService.getEmpresaDireccion(),
                "Calle Ejemplo 123",
                ajustesService::setEmpresaDireccion
        );

        configurarTextFieldTile(
                codigoPostalEmpresaTile,
                ajustesService.getEmpresaCodigoPostal(),
                "28000",
                ajustesService::setEmpresaCodigoPostal
        );

        configurarTextFieldTile(
                ciudadEmpresaTile,
                ajustesService.getEmpresaCiudad(),
                "Madrid",
                ajustesService::setEmpresaCiudad
        );

        configurarTextFieldTile(
                telefonoEmpresaTile,
                ajustesService.getEmpresaTelefono(),
                "600 000 000",
                ajustesService::setEmpresaTelefono
        );

        configurarTextFieldTile(
                webEmpresaTile,
                ajustesService.getEmpresaWeb(),
                "www.kitaujibar.com",
                ajustesService::setEmpresaWeb
        );
    }

    private void configurarTicket() {
        configurarTextFieldTile(
                tituloTicketTile,
                ajustesService.getTicketTitulo(),
                "TICKET DE VENTA",
                ajustesService::setTicketTitulo
        );

        configurarTextFieldTile(
                mensajeFinalTicketTile,
                ajustesService.getTicketMensajeFinal(),
                "Gracias por su compra",
                ajustesService::setTicketMensajeFinal
        );

        configurarCheckTile(
                mostrarCifTicketTile,
                ajustesService.getTicketMostrarCif(),
                ajustesService::setTicketMostrarCif
        );

        configurarCheckTile(
                mostrarTelefonoTicketTile,
                ajustesService.getTicketMostrarTelefono(),
                ajustesService::setTicketMostrarTelefono
        );

        configurarCheckTile(
                mostrarWebTicketTile,
                ajustesService.getTicketMostrarWeb(),
                ajustesService::setTicketMostrarWeb
        );

        configurarCheckTile(
                mostrarIvaTicketTile,
                ajustesService.getTicketMostrarIva(),
                ajustesService::setTicketMostrarIva
        );
    }

    private void configurarImpresora() {
        List<String> impresoras = obtenerNombresImpresoras();

        configurarChoiceTile(
                impresoraNombreTile,
                impresoras,
                ajustesService.getImpresoraNombre(),
                impresora -> {
                    if (impresora != null && !"No hay impresoras disponibles".equals(impresora)) {
                        ajustesService.setImpresoraNombre(impresora);
                    }
                }
        );

        configurarChoiceTile(
                impresoraAnchoTile,
                List.of("58", "80"),
                String.valueOf(ajustesService.getImpresoraAncho()),
                ancho -> ajustesService.setImpresoraAncho(Integer.parseInt(ancho))
        );

        configurarChoiceTile(
                impresoraCodepageTile,
                List.of("CP858_Euro", "CP850"),
                ajustesService.getImpresoraCodepage(),
                ajustesService::setImpresoraCodepage
        );

        configurarCheckTile(
                impresoraCortarPapelTile,
                ajustesService.getImpresoraCortarPapel(),
                ajustesService::setImpresoraCortarPapel
        );

        configurarCheckTile(
                impresoraAbrirCajonTile,
                ajustesService.getImpresoraAbrirCajon(),
                ajustesService::setImpresoraAbrirCajon
        );
    }

    private void configurarApariencia() {
        configurarChoiceTile(
                temaTile,
                List.of(
                        "Primer Light",
                        "Primer Dark",
                        "Nord Light",
                        "Nord Dark",
                        "Cupertino Light",
                        "Cupertino Dark",
                        "Dracula"
                ),
                ajustesService.getTema(),
                tema -> {
                    ajustesService.setTema(tema);
                    ajustesService.loadTema();
                }
        );
    }

    private void configurarBackups() {
        configurarTextFieldTile(
                backupRutaTile,
                ajustesService.getBackupRuta(),
                "Carpeta de backups",
                ajustesService::setBackupRuta
        );

        TextField ultimoBackupField = configurarTextFieldTile(
                backupUltimoTile,
                ajustesService.getBackupUltimo(),
                "Sin backups todavía",
                ajustesService::setBackupUltimo
        );

        ultimoBackupField.setEditable(false);
        ultimoBackupField.setDisable(true);
        ultimoBackupField.setFocusTraversable(false);
    }

    @FXML
    public void guardarAjustes() {
        if (!SesionActual.getInstancia().esAdmin()) {
            return;
        }

        accionesGuardar.forEach(Runnable::run);
        guardarButton.setDisable(true);

        log.info("Ajustes guardados correctamente");
    }

    private TextField configurarTextFieldTile(
            Tile tile,
            String valor,
            String prompt,
            Consumer<String> guardar
    ) {
        TextField textField = new TextField(valor);
        textField.setPromptText(prompt);
        textField.setPrefWidth(175);

        configurarAccionTile(tile, textField, textField::requestFocus);

        textField.textProperty().addListener((obs, oldValue, newValue) ->
                guardarButton.setDisable(false)
        );

        accionesGuardar.add(() -> guardar.accept(textField.getText()));

        return textField;
    }

    private CheckBox configurarCheckTile(
            Tile tile,
            boolean valor,
            Consumer<Boolean> guardar
    ) {
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(valor);

        configurarAccionTile(tile, checkBox, checkBox::fire);

        checkBox.selectedProperty().addListener((obs, oldValue, newValue) ->
                guardarButton.setDisable(false)
        );

        accionesGuardar.add(() -> guardar.accept(checkBox.isSelected()));

        return checkBox;
    }

    private ChoiceBox<String> configurarChoiceTile(
            Tile tile,
            List<String> opciones,
            String valor,
            Consumer<String> guardar
    ) {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().setAll(opciones);
        choiceBox.setPrefWidth(175);

        if (valor != null && opciones.contains(valor)) {
            choiceBox.getSelectionModel().select(valor);
        } else if (!opciones.isEmpty()) {
            choiceBox.getSelectionModel().selectFirst();
        }

        configurarAccionTile(tile, choiceBox, choiceBox::show);

        choiceBox.valueProperty().addListener((obs, oldValue, newValue) ->
                guardarButton.setDisable(false)
        );

        accionesGuardar.add(() -> guardar.accept(choiceBox.getValue()));

        return choiceBox;
    }

    private List<String> obtenerNombresImpresoras() {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);

        List<String> impresoras = java.util.Arrays.stream(printServices)
                .map(PrintService::getName)
                .toList();

        if (impresoras.isEmpty()) {
            return List.of("No hay impresoras disponibles");
        }

        return impresoras;
    }

    private void configurarAccionTile(Tile tile, javafx.scene.Node actionNode, Runnable actionHandler) {
        Platform.runLater(() -> {
            tile.setAction(actionNode);
            tile.setActionHandler(actionHandler);
        });
    }
}