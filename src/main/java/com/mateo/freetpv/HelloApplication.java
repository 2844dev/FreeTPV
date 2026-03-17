package com.mateo.freetpv;

import com.mateo.freetpv.dao.UsuarioDAO;
import com.mateo.freetpv.util.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DatabaseConnection db = new DatabaseConnection();
        db.connect();
        db.initDatabase();
        crearUsuarioPrueba();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("FreeTPV");
        stage.setScene(scene);
        stage.show();
    }

    public void crearUsuarioPrueba() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.crearUsuario("Admin", "1234", "Admin");
    }
}
