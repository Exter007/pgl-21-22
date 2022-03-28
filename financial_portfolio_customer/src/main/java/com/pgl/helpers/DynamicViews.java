/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pgl.helpers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class DynamicViews {
    @FXML
    public static BorderPane border_pane;
    
    public DynamicViews(){
    }

    public void setBorderPane(BorderPane borderPane){
        border_pane = borderPane;
    }

    public static void loadBorderCenter(BorderPane borderPane, String ressource){
        try {
            Parent dashboard= FXMLLoader.load(new DynamicViews().getClass().getResource("/views/"+ressource+".fxml"));
            
            borderPane.setCenter(dashboard);
        } catch (IOException ex) {
            Logger.getLogger(DynamicViews.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void loadBorderCenter(String ressource){
        try {
            Parent dashboard= FXMLLoader.load(new DynamicViews().getClass().getResource("/views/"+ressource+".fxml"));

            border_pane.setCenter(dashboard);
        } catch (IOException ex) {
            Logger.getLogger(DynamicViews.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
