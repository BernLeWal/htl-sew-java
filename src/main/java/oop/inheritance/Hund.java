package oop.inheritance;

public class Hund extends Lebewesen {
    // Attribute
    private String fell;
    private String rasse;

    // Standard-Konstruktor
    public Hund() {
        fell = "";
        rasse = "";
    }

    // Parameter-Konstruktor
    public Hund(String name, int alter, int gewicht, String fell, String rasse) {
        super(name, alter, gewicht);
        this.fell = fell;
        this.rasse = rasse;
    }

    // Methoden
    public String bellen() {
        return "wau wau";
    }

    public void knurren(int lautProzent) {
    }

    @Override
    public void bewegen() {
        super.bewegen();
        System.out.println("Ich laufe doppelt so schnell!");
        super.bewegen();
    }
}
