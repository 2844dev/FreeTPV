package com.mateo.freetpv;

import atlantafx.base.theme.PrimerLight;
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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/login-view.fxml"));
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("FreeTPV");
        stage.setScene(scene);
        stage.show();
    }
}
