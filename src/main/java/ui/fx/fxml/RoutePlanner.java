package ui.fx.fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.rest.MapQuestDirections;

import java.io.IOException;
import java.util.Scanner;

/**
 * RoutePlanner is s very simple route-planing GUI application using UI-layouting and data-binding.
 */
public class RoutePlanner extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/fx/fxml/RoutePlannerMainWindow.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Routenplaner");
        primaryStage.show();
    }
}
