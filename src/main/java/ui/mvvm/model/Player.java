package ui.mvvm.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Player {
    private StringProperty name;
    private String symbol;
    private IntegerProperty score;

    public Player(String name, String symbol) {
        this.name = new SimpleStringProperty();
        this.name.set(name);
        this.symbol = symbol;
        this.score = new SimpleIntegerProperty();
        this.score.set(0);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getScore() {
        return score.get();
    }

    public void setScore(int score) {
        this.score.set(score);
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public void incrementScore() {
        score.set(score.get()+1);
    }

    public void incrementScore(int n) {
        score.set(score.get()+n);
    }

    @Override
    public String toString() {
        return name.get();
    }
}
