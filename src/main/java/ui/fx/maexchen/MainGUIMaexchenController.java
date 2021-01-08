/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.fx.maexchen;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author martina
 */
public class MainGUIMaexchenController implements Initializable {

    @FXML
    private Button buttonD1;
    @FXML
    private Button buttonD2;
    @FXML
    private Label labelPoints;
    @FXML
    private Label labelPlayer1;
    @FXML
    private Label labelPlayer2;
    @FXML
    private Label LabelPointsP1;
    @FXML
    private Label LabelPointsP2;
    @FXML
    private Button buttonNewGame;
    private static Game g;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        newGame();
    }

    @FXML
    private void buttonD1Clicked(ActionEvent event) {
        g.getP1().dice();
        this.LabelPointsP1.setText(Integer.toString(g.getP1().getPoints()));
        this.buttonD1.setDisable(true);
        this.buttonD2.setDisable(false);
    }

    @FXML
    private void buttonD2Clicked(ActionEvent event) {
        g.getP2().dice();
        this.LabelPointsP2.setText(Integer.toString(g.getP2().getPoints()));
        this.buttonD2.setDisable(true);
        this.buttonD1.setDisable(false);
    }

    @FXML
    private void buttonNewGameClicked(ActionEvent event) {
        newGame();
    }

    private void newGame() {
        g = new Game();
        this.LabelPointsP1.setText(Integer.toString(g.getP1().getPoints()));
        this.LabelPointsP2.setText(Integer.toString(g.getP2().getPoints()));
        this.buttonD2.setDisable(true);
        this.buttonD1.setDisable(false);
    }
}
