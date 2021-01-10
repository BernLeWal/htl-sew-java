package patterns.mvvm.viewmodel;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import patterns.mvvm.biz.BoardState;
import patterns.mvvm.model.Board;
import patterns.mvvm.model.Player;

public class GameViewModel {
    private Player[] players = new Player[2];
    private BoardState board = new BoardState();

    private int current = 0;    // index of current Player

    public GameViewModel() {
        // Spieler erzeugen
        players[0] = new Player("Mr. X", "X");
        players[1] = new Player("Computer", "O");
    }

    public Player getPlayer(int index) {
        return players[index];
    }

    public Player getCurrentPlayer() {
        return players[current];
    }


    public void reset(Parent parent, boolean resetScore) {
        board.reset();
        if (resetScore) {
            players[0].setScore(0);
            players[1].setScore(0);
        }
        for (Node child : parent.getChildrenUnmodifiable()) {
            if (child instanceof Button) {
                updateButton((Button) child, " ", false);
            }
        }
    }

    public void onFieldClicked(Button button) {
        String sYX = button.getId().substring(6);
        int y = sYX.charAt(0) - '0';
        int x = sYX.charAt(1) - '0';

        board.setField(x, y, current);
        getCurrentPlayer().incrementScore();
        updateButton(button, getCurrentPlayer().getSymbol(), true);
        checkGameOver(button.getParent());

        current = 1 - current;    // switch Player
        if (current == 1)      // der Computer
        {
            var nextMove = board.calculateNextMove(0, 0);
            if (nextMove.isPresent()) {
                int index = nextMove.get().getKey();
                board.setField(index, current);
                players[current].incrementScore();
                updateButton(button.getParent(), index, players[current].getSymbol(), true);
                checkGameOver(button.getParent());
            }

            current = 0;    // der Mensch ist an der Reihe
        }
    }

    private void checkGameOver(Parent parent) {
        if (board.getWinner() >= 0) {
            players[board.getWinner()].incrementScore(board.getNumberOfEmptyFields() + 1);
            var result = new Alert(Alert.AlertType.NONE, "Das Spiel ist zu Ende, \n" + players[board.getWinner()] + " hat gewonnen!", ButtonType.OK).showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                reset(parent, false);
                current = 1 - current;    // der Verlierer darf beginnen
            }
        }
        if (board.isGameOver()) {
            var result = new Alert(Alert.AlertType.NONE, "Das Spiel ist zu Ende, \nes gibt keinen Gewinner.", ButtonType.OK).showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                reset(parent, false);
            }
        }
    }

    private void updateButton(Button button, String text, boolean disabled) {
        button.setText(text);
        button.setDisable(disabled);
    }

    private void updateButton(Parent parent, int index, String text, boolean disabled) {
        String id = "button" + Board.indexToY(index) + "" + Board.indexToX(index);
        for (Node child : parent.getChildrenUnmodifiable()) {
            if (child instanceof Button) {
                Button b = (Button) child;
                if (b.getId().equals(id)) {
                    updateButton(b, text, disabled);
                }
            }
        }
    }
}
