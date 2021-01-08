/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.fx.maexchen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * MaexchenDemo implements a simple dice game with a JavaFX GUI
 *
 * @author martina
 */
public class MaexchenDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Maexchen");
        Parent root = FXMLLoader.load(getClass().getResource("/ui/fx/maexchen/MainGUIMaexchen.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
