package patterns.mvvm.view;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.converter.NumberStringConverter;
import patterns.mvvm.viewmodel.GameViewModel;

public class MainController {

    public Button buttonStartStop;
    public ComboBox<String> comboPlayer0;
    public Label labelScore0;
    public Label labelScore1;
    public ComboBox<String> comboPlayer1;
    public TextArea textMessage;
    public Button button00;

    private GameViewModel game = new GameViewModel();
    private StringProperty message = new SimpleStringProperty();

    @FXML
    public void initialize() {
        textMessage.textProperty().bind(message);
        labelScore0.textProperty().bindBidirectional(game.getPlayer(0).scoreProperty(), new NumberStringConverter());
        labelScore1.textProperty().bindBidirectional(game.getPlayer(1).scoreProperty(), new NumberStringConverter());
        comboPlayer0.getEditor().textProperty().bindBidirectional(game.getPlayer(0).nameProperty());
        comboPlayer1.getEditor().textProperty().bindBidirectional(game.getPlayer(1).nameProperty());

        message.set("Spieler " + game.getCurrentPlayer() + " beginnt.");
    }

    @FXML
    public void onButtonClicked(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        game.onFieldClicked(button);
        message.set("Nächster Spieler " + game.getCurrentPlayer());
    }

    public void onQuitClicked(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onResetClicked(ActionEvent actionEvent) {
        game.reset(button00.getParent(), true);
        message.set("Spieler " + game.getCurrentPlayer() + " beginnt.");
    }
}
