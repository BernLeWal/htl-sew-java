package ui.fx.drawing;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * AnimatedBallDemo shows an animated ball inside the content-area of the GUI window.
 */
public class AnimatedBallDemo extends Application {
    public static final int INIT_WIDTH = 300;
    public static final int INIT_HEIGHT = 250;
    public static final int BALL_RADIUS = 10;

    private int x = INIT_WIDTH / 2;
    private int deltaX = +1;
    private int y = INIT_HEIGHT / 2;
    private int deltaY = +1;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Circle ball = new Circle(x, y, BALL_RADIUS);
        root.getChildren().add(ball);

        Scene scene = new Scene(root, INIT_WIDTH, INIT_HEIGHT);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), ev -> {
            ball.setCenterX(x);
            x += deltaX;
            if (x < BALL_RADIUS)
                deltaX = +1;
            else if (x > (scene.getWidth() - BALL_RADIUS))
                deltaX = -1;

            ball.setCenterY(y);
            y += deltaY;
            if (y < BALL_RADIUS)
                deltaY = +1;
            else if (y > (scene.getHeight() - BALL_RADIUS))
                deltaY = -1;
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        primaryStage.setTitle("Animated Ball");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
