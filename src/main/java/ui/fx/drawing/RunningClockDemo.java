package ui.fx.drawing;

import java.time.LocalTime;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * RunningClockDemo draws the current time as analog clock into a FX-window.
 * The clock hands are animated.
 */
public class RunningClockDemo extends Application {
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setTitle("Analoge Uhr");

        // Ziffernblatt zeichnen
        drawClockFace(root);

        // Zeiger erstellen
        Line hours = new Line(150, 150, 150, 150);
        hours.setStrokeWidth(8);
        hours.setStroke(Color.RED);
        Line minutes = new Line(150, 150, 150, 150);
        minutes.setStrokeWidth(8);
        minutes.setStroke(Color.BLACK);
        Line seconds = new Line(150, 150, 150, 150);
        seconds.setStrokeWidth(2);
        seconds.setStroke(Color.BLACK);
        root.getChildren().addAll(hours, minutes, seconds);

        // Uhr regelmäßig aktualisieren
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            LocalTime now = LocalTime.now();
            updatePointer(hours, 60, hoursToRadiant(now.getHour()));
            updatePointer(minutes, 80, minutesToRadiant(now.getMinute()));
            updatePointer(seconds, 100, minutesToRadiant(now.getSecond()));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // Tastensteuerung, d.h. bei ESC --> Programmende
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }
        });

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

    private double minutesToRadiant(int minute) {
        // Umrechung von Minuten/Sekunden in Radianten, d.h.
        // eine Umdrehung 60 Minuten --> 2*PI
        //      60 ... 2*PI
        //       1 ... 2*PI/60
        return minute * 2.0 * Math.PI / 60 - Math.PI / 2;
    }

    private void updatePointer(Line line, float radius, double angle) {
        line.setEndX(150 + Math.cos(angle) * radius);
        line.setEndY(150 + Math.sin(angle) * radius);
    }

    private void drawClockFace(Group root) {
        for (int hour = 0; hour < 12; hour++) {
            double angle = hoursToRadiant(hour);
            double x1 = 150 + Math.cos(angle) * 100;
            double y1 = 150 + Math.sin(angle) * 100;
            double x2 = 150 + Math.cos(angle) * 110;
            double y2 = 150 + Math.sin(angle) * 110;
            Line line = new Line(x1, y1, x2, y2);
            line.setStrokeWidth(4);
            line.setStroke(Color.BLACK);
            root.getChildren().addAll(line);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
