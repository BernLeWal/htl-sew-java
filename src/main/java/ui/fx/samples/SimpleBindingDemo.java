package ui.fx.samples;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * SimpleBindingDemo is a JavaFX application showing how very simple (low level)
 * bindings directly defined on multiple controls during the UI initialization works.
 * Demonstrates direct selected, visible and text-value bindings.
 */
public class SimpleBindingDemo extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ui/fx/samples/SimpleBinding.fxml"));
            Scene scene = new Scene(root, 1024, 768);
            scene.getStylesheets().add(getClass().getResource("/ui/fx/samples/application.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("JavaFX Simple-Binding Demo");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}