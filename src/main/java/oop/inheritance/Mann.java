package oop.inheritance;

public class Mann extends Mensch {
    // Konstruktor
    public Mann(String name, int alter, int gewicht, String augen, String sprache) {
        this.name = name;
        this.alter = alter;
        this.gewicht = gewicht;
        this.augen = augen;
        this.sprache = sprache;
    }

    // Methoden
    @Override
    public String reden() {
        return "Was gibt's heute im TV?";
    }

    @Override
    public void essen(String mahl) {
        if (mahl.compareToIgnoreCase("Pizza") != 0) // ist mahl "Pizza"?
        {   // Nein
            System.out.println("Pfui!");
        }
    }

    public void fussballspielen() {

    }
}
