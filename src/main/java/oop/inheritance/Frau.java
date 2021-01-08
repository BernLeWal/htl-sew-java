package oop.inheritance;

public class Frau extends Mensch {
    // Konstruktor
    public Frau(String name, int alter, int gewicht, String augen, String sprache) {
        this.name = name;
        this.alter = alter;
        this.gewicht = gewicht;
        this.augen = augen;
        this.sprache = sprache;
    }

    // Methoden
    @Override
    public String reden() {
        super.reden();
        return "Ich hab' nichts anzuziehen!";
    }

    @Override
    public void essen(String mahl) {
        if (mahl.compareToIgnoreCase("Vegi-Burger") == 0) // ist mahl "Veggi-Burger"?
        {   // Nein
            System.out.println("Lecker!");
        }
        super.essen(mahl);
    }

    public void einkaufen(int geld) {

    }
}
