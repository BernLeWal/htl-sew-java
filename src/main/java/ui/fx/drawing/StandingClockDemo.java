package ui.fx.drawing;

import java.time.LocalTime;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * StandingClockDemo draws the current time as analog clock into a FX-window.
 * Remarks: The clock hands do not move! See also RunningClockDemo.
 */
public class StandingClockDemo extends Application {
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setTitle("Analoge Uhr");

        // Ziffernblatt zeichnen
        drawClockFace(root);

        // Zeiger erstellen
        Line pointer = new Line();
        pointer.setStartX(150);
        pointer.setStartY(150);
        double angle = hoursToRadiant(LocalTime.now().getHour());   // das
        pointer.setEndX(150 + Math.cos(angle) * 100);               // wird
        pointer.setEndY(150 + Math.sin(angle) * 100);               // aktualisiert
        pointer.setStrokeWidth(8);
        pointer.setStroke(Color.BLACK);
        root.getChildren().addAll(pointer);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private double hoursToRadiant(int hour) {
        // Umrechnung von Stunden in Radianten, d.h.
        // eine Umdrehung 12 Stunden --> 2*PI
        //      12h .... 2*PI
        //       1h .... 2*PI/12
        return hour * 2.0 * Math.PI / 12.0 - Math.PI / 2;
    }

    private void drawClockFace(Group root) {
        for (int hour = 0; hour < 12; hour++) {
            double angle = hoursToRadiant(hour);
            Line line = new Line();
            line.setStartX(150 + Math.cos(angle) * 100);
            line.setStartY(150 + Math.sin(angle) * 100);
            line.setEndX(150 + Math.cos(angle) * 110);
            line.setEndY(150 + Math.sin(angle) * 110);
            line.setStrokeWidth(4);
            line.setStroke(Color.BLACK);
            root.getChildren().addAll(line);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
