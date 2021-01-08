package oop.inheritance;

/**
 * InheritanceDemo shows how to work with class inheritance in order to define, create and use the instances accordingly.
 */
public class InheritanceDemo {

    public static void main(String[] args) {
        // Wir erzeugen eine Mensch-Instanz und rufen ein eigene Methode auf
        Mann b = new Mann("Bernhard", 37, 67, "braun", "Deutsch");
        Frau m = new Frau("Michaela", 34, 65, "braun", "Deutsch");
        String s = m.reden();
        System.out.println(s);
        System.out.println(b.reden());

        // Wir erzeugen eine Hund-Instanz und rufen eine abgeleitete Methode auf
        Hund j = new Hund("Jessi", 11, 27, "hellbraun", "Goldenretriever");
        j.bewegen();

        // Wir greifen auf die Mensch-Instanz über eine Variable vom Typ der Superklasse zu
        Lebewesen l = b;
        l.essen("Brot");
        l = m;
        m.essen("Vegi-Burger");

        // Wir "casten" auf die abgeleitete Klasse um eine Methode von Mensch aufzurufen
        ((Mensch) l).tanzen("Harlem-Shake");
    }
}
