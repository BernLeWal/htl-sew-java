package ui.fx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * SimpleUiFXDemo generates a UI consisting of a label and a button by programming the gui and layout directly in the sourcecode.
 */
public class SimpleUiFXDemo extends Application {       // die Anwendung
    @Override
    public void start(Stage stage) {                    // das Fenster
        stage.setTitle("Programmed FX!");

        Scene scene;                                    // der Fenster-Inhalt
        // xxxPane bestimmt die Anordnung der Elemente im Fenster,
        // hier FlowPane, d.h. "Eines nach dem Anderen"
        FlowPane scenePane = new FlowPane();
        scene = new Scene(scenePane, 300, 250);

        Label lbl = new Label("Hello World!");      // ein Anzeige-Element
        //lbl.setFont(Font.font("Serif", FontWeight.NORMAL, 20));
        lbl.setPadding(new Insets(5, 10, 0, 5));
        scenePane.getChildren().add(lbl);

        Button btn = new Button();                      // ein Button-Element
        btn.setText("Quit");
        btn.setOnAction((ActionEvent event) -> Platform.exit());
        scenePane.getChildren().add(btn);

        stage.setScene(scene);                          // Zuordnung des Inhalts
                                                        //      zum Fenster
        stage.show();                                   // Fenster anzeigen
    }


    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
