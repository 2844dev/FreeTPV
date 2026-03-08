package com.mateo.freetpv.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class EmpleadosController {
    @FXML private BorderPane empleadosPane;
    @FXML private Button editarButton;
    @FXML private Button nuevoButton;
    @FXML private TextField buscarField;
    @FXML private TableView empleadosTable;
    @FXML private TableColumn nombreColumn;
    @FXML private TableColumn rolColumn;
    @FXML private TableColumn fechaColumn;

    @FXML private BorderPane nuevoempleadoPane;
    @FXML private TextField usuarioField;
    @FXML private PasswordField pinField;
    @FXML private ChoiceBox rolChoiceBox;
    @FXML private Button guardarButton;
    @FXML private Button cancelarButton;

    @FXML public void initialize() {

    }
}
