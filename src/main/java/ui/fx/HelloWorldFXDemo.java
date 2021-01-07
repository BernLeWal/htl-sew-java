package ui.fx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * HelloWorldFXDemo presents a simple "Hello World!" in a GUI window.
 *
 * To enable JavaFX you first need to add the following Maven dependencies in the pom.xml-file:
 *     <dependencies>
 *         <dependency>
 *             <groupId>org.openjfx</groupId>
 *             <artifactId>javafx-graphics</artifactId>
 *             <version>13</version>
 *         </dependency>
 *         <dependency>
 *             <groupId>org.openjfx</groupId>
 *             <artifactId>javafx-controls</artifactId>
 *             <version>13</version>
 *         </dependency>
 *     </dependencies>
 * and also add the javafx-plugin there
 *             <plugin>
 *                 <groupId>org.openjfx</groupId>
 *                 <artifactId>javafx-maven-plugin</artifactId>
 *                 <version>0.0.4</version>
 *                 <configuration>
 *                     <mainClass>ui.fx.HelloWorldFXDemo</mainClass>
 *                     <options>
 *                         <option>--enable-preview</option>
 *                     </options>
 *                 </configuration>
 *             </plugin>
 * now you can clean, compile the complete Maven build system.
 * Run the application with Maven by executing the "javafx:run" plugin, f.e. "mvn clean javafx:run"
 *
 * To enable JavaFX in the IntelliJ-IDE directly, you also must add the runtime-components to the Run configuration:
 * - Run -> Edit Configurations:
 * - Modify Options -> add VM options:
 *      --module-path "C:\Program Files\Java\javafx-sdk-13.0.2\lib" --add-modules javafx.controls,javafx.fxml
 */
public class HelloWorldFXDemo extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(event -> System.out.println("Hello World!"));

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
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
