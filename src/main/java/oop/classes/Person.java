package oop.classes;

public class Person {

    // Attribute:
    private String name;
    private int alter;
    private int gewicht;

    // Konstruktoren:
    public Person() // Default-Konstruktor
    {
        name = "unbekannt";
        alter = -1;
        gewicht = -1;
    }

    public Person(String name, int alter, int gewicht) // Paremter-Konstruktor
    {
        this.name = name;
        this.alter = alter;
        this.gewicht = gewicht;
    }

    public Person(Person other) // Copy-Konstruktor
    {
        this.name = other.name;
        this.alter = other.alter;
        this.gewicht = other.gewicht;
    }

    // Getter & Setter:
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAlter() {
        return alter;
    }

    public void setAlter(int alter) {
        this.alter = alter;
    }

    public int getGewicht() {
        return gewicht;
    }

    public void setGewicht(int gewicht) {
        this.gewicht = gewicht;
    }

    
    // Methoden:
    public String reden() {
        return "Hallo ich heiße " + name + " und bin " + alter + " Jahre alt!";
    }

    public void essen(String mahl) {
        if (mahl.compareToIgnoreCase("Spaghetti") != 0) // ist mahl "Spaghetti"?
        {   // Nein
            System.out.println(mahl + " ist pfui!");
        } else if (mahl.compareToIgnoreCase("Pizza") != 0) // ist mahl "Pizza"?
        {   // Nein
            System.out.println(mahl + " ist gut!");
        } else {
            System.out.println(mahl + " kenn' ich nicht!");
        }
    }
}
