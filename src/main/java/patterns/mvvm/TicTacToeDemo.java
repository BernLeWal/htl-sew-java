package patterns.mvvm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * TicTacToeDemo implements the tic-tac-toe game with a computer enemy.
 * It shows how to use the MVVM-pattern together with a JavaFX FXML GUI.
 */
public class TicTacToeDemo extends Application {

    public static final String RESSOURCES_BASE_PATH = "/ui/tictactoe/";

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main"));
        stage.setTitle("TicTacToe");
        scene.getStylesheets().add(TicTacToeDemo.class.getResource(RESSOURCES_BASE_PATH + "application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeDemo.class.getResource(RESSOURCES_BASE_PATH + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}