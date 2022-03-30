package com.pgl.utils;

import com.pgl.controllers.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalStage {

    private static Stage stage;

    public static void init(Stage stage){
        GlobalStage.stage = stage;
        GlobalStage.stage.show();
    }
    public static void setStage(Stage stage){
        GlobalStage.stage.close();
        GlobalStage.stage = stage;
        GlobalStage.stage.show();
    }

}
