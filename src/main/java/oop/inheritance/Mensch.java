package oop.inheritance;

public class Mensch extends Lebewesen {
    // Attribute
    protected String augen;
    protected String sprache;

    // Standard-Konstruktor
    public Mensch() {
        augen = "";
        sprache = "";
    }

    // Parameter-Konstruktor
    public Mensch(String name, int alter, int gewicht, String augen, String sprache) {
        this.name = name;
        this.alter = alter;
        this.gewicht = gewicht;
        this.augen = augen;
        this.sprache = sprache;
    }

    // Methoden
    public String reden() {
        return "Hallo.";
    }

    public void tanzen(String tanz) {
    }

    @Override
    public void essen(String mahl) {
        if (mahl.compareToIgnoreCase("Spaghetti") != 0) // ist mahl "Spaghetti"?
        {   // Nein
            System.out.println("Pfui!");
        }
    }
}
