package com.mateo.freetpv.controller;

import com.mateo.freetpv.model.Categoria;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class CategoriasController {
    // Panel principal categorias
    @FXML private BorderPane categoriasPane;
    @FXML private TableView<Categoria> categoriasTable;
    @FXML private TableColumn<Categoria, Integer> codigoColumn;
    @FXML private TableColumn<Categoria, String> nombreColumn;
    @FXML private TableColumn<Categoria, String> editarColumn;
    @FXML private TextField buscarField;
    @FXML private Button nuevacategoriaButton;

    // Panel para editar y crear categorias
    @FXML private BorderPane nuevacategoriaPane;
    @FXML private TextField usuarioField;
    @FXML private Label errorLabel;
    @FXML private Button guardarButton;
    @FXML private Button cancelarButton;
    @FXML private Button borrarButton;

    @FXML public void initialize() {
        // Nos aseguramos que al cargarlo se ve solo el panel principal
        categoriasPane.setVisible(true);
        nuevacategoriaPane.setVisible(false);

        // Indicamos a cada columna que atributo de las categorias mostrar
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }

    @FXML public void mostrarformularioNuevo() {
        categoriasPane.setDisable(true);
        nuevacategoriaPane.setVisible(true);
    }
    @FXML public void cerrarFormulario() {
        usuarioField.clear();
        nuevacategoriaPane.setVisible(false);
        categoriasPane.setDisable(false);
    }
}
