package ui.fx.demo.samples;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DataBindingDemo extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            // Remarks: to use user-defined controls in the FXML-files it is necessary to create an instance of the FXMLLoader in
            // order to be able to load the class of user-defined controls implemented as plain java-classes.
            FXMLLoader fl = new FXMLLoader();
            fl.setLocation(getClass().getResource("/ui/fx/demo/DataBinding.fxml"));
            fl.load();
            Parent root = fl.getRoot();

            Scene scene = new Scene(root, 1024, 768);
            scene.getStylesheets().add(getClass().getResource("/ui/fx/demo/application.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("JavaFX DataBinding Demo");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}