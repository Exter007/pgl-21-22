package com.pgl.application;

import com.pgl.utils.GlobalStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Client-Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        GlobalStage.init(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
