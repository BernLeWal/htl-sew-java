package oop.inheritance;

public class Lebewesen {
    // Attribute
    protected String name;
    protected int alter;
    protected int gewicht;

    // Standardkonstruktor
    public Lebewesen() {
        name = "unbekannt";
        alter = 0;
        gewicht = 0;
    }

    // Paremter-Konstruktor
    public Lebewesen(String name, int alter, int gewicht) {
        this.name = name;
        this.alter = alter;
        this.gewicht = gewicht;
    }

    // Methoden
    public void bewegen() {
    }

    public void essen(String mahl) {
    }
}
