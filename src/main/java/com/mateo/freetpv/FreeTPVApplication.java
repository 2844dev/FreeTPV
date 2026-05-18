package com.mateo.freetpv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FreeTPVApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FreeTPVApplication.class.getResource("view/load-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("FreeTPV");
        stage.setScene(scene);
        stage.setMinWidth(850);
        stage.setMinHeight(500);
        stage.setResizable(false);
        stage.show();
    }
}
