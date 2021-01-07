package ui.fx.drawing;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * DrawingFXDemo shows how to draw graphical primitives like line, rect, ellipse but also text and images.
 */
public class DrawingFXDemo extends Application {
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 300, 300);//, Color.BEIGE);

        // TODO: hier wird dein Sourcecode eingefügt!
        drawLine(root);
        drawTriangle(root);
        drawRect(root);
        drawEllipse(root);
        drawText(root);
        drawImage(root);

        primaryStage.setTitle("Zeichnen mit JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void drawLine(Group root) {
        Line line1 = new Line(20, 20, 280, 280);
        line1.setStrokeWidth(4);
        Line line2 = new Line(280, 20, 20, 280);
        line2.setStrokeWidth(8);
        root.getChildren().addAll(line1, line2);
    }

    private void drawTriangle(Group root) {
        Polygon poly1 = new Polygon(30, 30, 270, 30, 210, 210);
        poly1.setFill(Color.RED);
        Polygon poly2 = new Polygon(30, 120, 270, 120, 210, 300);
        poly2.setFill(new Color(0.5, 0.5, 0.5, 0.8));
        root.getChildren().addAll(poly1, poly2);
    }

    private void drawRect(Group root) {
        Rectangle rect1 = new Rectangle(90, 60, 165, 165);
        rect1.setFill(Color.YELLOW);
        //rect1.setFill(Color.TRANSPARENT);
        //rect1.setStroke(Color.BLACK);
        rect1.setStroke(Color.TRANSPARENT);
        root.getChildren().addAll(rect1);
    }

    private void drawEllipse(Group root) {
        Ellipse eli1 = new Ellipse(168, 138, 82, 62);
        eli1.setFill(Color.YELLOW);
        eli1.setStroke(Color.BLACK);
        root.getChildren().addAll(eli1);
    }

    private void drawText(Group root) {
        for (int i = 1; i < 10; i++) {
            Text txt = new Text("a");
            txt.setX(i * i * 3);
            txt.setY(150);
            txt.setFont(Font.font("Verdana", FontWeight.BOLD, i * 10));
            // Schriftart, Darstellung, Textgröße
            txt.setFill(Color.RED);
            root.getChildren().addAll(txt);
        }
    }

    private void drawImage(Group root) {
        ImageView imageView = new ImageView(new Image("/ui/fx/Roboter.jpg"));
        imageView.setX(25);
        imageView.setY(25);

        //Bild skalieren, d.h. Breite und Höhe angeben
        imageView.setFitHeight(250);
        imageView.setFitWidth(250);
        imageView.setPreserveRatio(true);

        root.getChildren().addAll(imageView);
    }

}
