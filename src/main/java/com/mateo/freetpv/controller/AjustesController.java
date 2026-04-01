package com.mateo.freetpv.controller;

import com.mateo.freetpv.service.AjustesService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AjustesController {
    private static final Logger log = LoggerFactory.getLogger(AjustesController.class);
    @FXML private BorderPane ajustesPane;
    @FXML private ChoiceBox<String> temasChoiceBox;
    @FXML private Button guardarButton;

    private AjustesService ajustesService = new AjustesService();

    @FXML public void initialize() {
        temasChoiceBox.setItems(FXCollections.observableArrayList("Primer Light", "Primer Dark", "Nord Light", "Nord Dark", "Cupertino Light", "Cupertino Dark", "Dracula"));
        temasChoiceBox.getSelectionModel().select(ajustesService.getTema());
    }
    @FXML public void guardarAjustes() {
        ajustesService.setTema(temasChoiceBox.getSelectionModel().getSelectedItem());
        ajustesService.loadTema();
        log.info("Tema cargado: {}", ajustesService.getTema());
    }
}
