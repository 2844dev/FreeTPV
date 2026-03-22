package com.mateo.freetpv.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;

public class AjustesController {
    @FXML private BorderPane ajustesPane;
    @FXML private ChoiceBox temasChoiceBox;

    @FXML public void initialize() {
        temasChoiceBox.setItems(FXCollections.observableArrayList("Primer Light", "Primer Dark", "Nord Light", "Nord Dark", "Cupertino Light", "Cupertino Dark", "Dracula"));
    }
}
