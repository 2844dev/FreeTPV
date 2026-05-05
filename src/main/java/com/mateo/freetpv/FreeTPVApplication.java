package com.mateo.freetpv;

import com.mateo.freetpv.service.AjustesService;
import com.mateo.freetpv.util.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FreeTPVApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DatabaseConnection db = DatabaseConnection.getInstancia();
        db.initDatabase();
        FXMLLoader fxmlLoader = new FXMLLoader(FreeTPVApplication.class.getResource("view/load-view.fxml"));
        AjustesService ajustesService = new AjustesService();
        ajustesService.loadTema();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("FreeTPV");
        stage.setScene(scene);
        stage.setMinWidth(850);
        stage.setMinHeight(500);
        stage.setResizable(false);
        stage.show();
    }
}
