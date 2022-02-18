package com.pgl.utils;

import javafx.stage.Stage;

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
