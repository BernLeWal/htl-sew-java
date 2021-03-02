package ui.mvvm.biz;

import javafx.util.Pair;
import ui.mvvm.model.Board;

import java.util.Optional;

public class BoardState {
    private Board board;

    private boolean gameOver = false;
    private int winner = -1;

    public BoardState() {
        this.board = new Board();
    }

    public BoardState(Board board) {
        this.board = new Board(board);
        this.gameOver = checkGameOver();
        this.winner = checkWinner();
    }


    public int getField(int index) {
        return board.getField(index);
    }

    public int getField(int x, int y) {
        return board.getField(x, y);
    }

    public void setField(int index, int value) {
        board.setField(index, value);
        if ((winner = checkWinner()) >= 0)
            gameOver = true;
        else
            gameOver = checkGameOver();
    }

    public void setField(int x, int y, int value) {
        board.setField(x, y, value);
        if ((winner = checkWinner()) >= 0)
            gameOver = true;
        else
            gameOver = checkGameOver();
    }

    public int getNumberOfEmptyFields() {
        return board.getNumberOfEmptyFields();
    }


    public void reset() {
        board.clear();
        gameOver = false;
        winner = -1;
    }


    public boolean isGameOver() {
        return gameOver;
    }

    public int getWinner() {
        return winner;
    }


    public Optional<Pair<Integer, Float>> calculateNextMove(int player, int nextPlayer) {
        // alle möglichen Spielzüge berechnen; depth-first-search
        Pair<Integer, Float> bestMove = null;
        float count = 0;
        float score = 0.f;
        for (int i = 0; i < Board.NUMBER_OF_FIELDS; i++) {
            if (board.getField(i) == -1) {
                BoardState nextMove = new BoardState(board);
                nextMove.setField(i, nextPlayer);
                if (nextMove.isGameOver()) {
                    float val = (nextMove.getWinner() == player) ? 1.f : -1.f;
                    if (bestMove == null || bestMove.getValue() < val)
                        bestMove = new Pair<>(i, val);
                    count++;
                    score += val;
                } else {
                    var result = nextMove.calculateNextMove(player, 1 - nextPlayer);
                    if (result.isPresent()) {
                        if (bestMove == null || bestMove.getValue() < result.get().getValue()) {
                            bestMove = result.get();
                        }
                        count++;
                        score += result.get().getValue();
                    }
                }
            }
        }
        if (bestMove == null)
            return Optional.empty();    // no next move found!

        return Optional.of(new Pair<>(bestMove.getKey(), /*(float)board.getNumberOfEmptyFields()*/score / count));
    }

    private int checkWinner() {
        for (int i = 0; i < 3; i++) {
            // Check row
            int p = board.getField(0, i);
            if (p >= 0 && board.getField(1, i) == p && board.getField(2, i) == p)
                return p;

            // Check col
            p = board.getField(i, 0);
            if (p >= 0 && board.getField(i, 1) == p && board.getField(i, 2) == p)
                return p;
        }

        // Check diagonales
        int p = board.getField(0, 0);
        if (p >= 0 && board.getField(1, 1) == p && board.getField(2, 2) == p)
            return p;

        p = board.getField(2, 0);
        if (p >= 0 && board.getField(1, 1) == p && board.getField(0, 2) == p)
            return p;

        return -1;
    }

    private boolean checkGameOver() {
        return board.getNumberOfEmptyFields() == 0;
    }
}
