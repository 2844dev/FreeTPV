package com.mateo.freetpv.controller;

import atlantafx.base.theme.*;
import com.mateo.freetpv.dao.AjustesDAO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;

public class AjustesController {
    @FXML private BorderPane ajustesPane;
    @FXML private ChoiceBox<String> temasChoiceBox;
    @FXML private Button guardarButton;

    private AjustesDAO ajustesDAO = new AjustesDAO();

    @FXML public void initialize() {
        temasChoiceBox.setItems(FXCollections.observableArrayList("Primer Light", "Primer Dark", "Nord Light", "Nord Dark", "Cupertino Light", "Cupertino Dark", "Dracula"));
        temasChoiceBox.getSelectionModel().select(ajustesDAO.getTema());
    }
    @FXML public void guardarAjustes() {
        ajustesDAO.setTema(temasChoiceBox.getSelectionModel().getSelectedItem());
        ajustesDAO.loadTema();
    }
}
