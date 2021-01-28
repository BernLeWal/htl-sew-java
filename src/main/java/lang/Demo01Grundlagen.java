package lang;

/**
 * Demo01Grundlagen demonstriert die grundsätzlichsten Elemente der Java Programmiersprache
 */
public class Demo01Grundlagen {
    public static void main(String[] args) {
        // Zeilen-Kommentar: Alles nach diesen beiden Zeichen ist Erklärungstext (gehört nicht zum Code).

        /*
            Block-Kommentar: Kann überall zwischen oder hinter dem Code eingefügt werden.
        */

        // ========== 1. Deklaration von Variablen: ==========
        // Es wird ein Platz im Speicher bestimmter Größe (je nach Typ) reserviert.
        int i;	        // Für ganze Zahlen
        long h;	        // Für noch längere ganze Zahlen
        double x;	    // Für Kommazahlen
        boolean y;	    // Für zwei Zustände (wahr/falsch)
        int n,k,l;	    // Es können auch mehrere Datentypen (vom gleichen Typ) in einer Zeile deklariert werden.

        // ========== 2. Initialisierung von Variablen: ==========
        // Zuweisungen eines Wertes, diese passieren immer von rechts nach links
        // (der Wert, rechts des = wird in den Speicherplatz des Datentyps links vom = kopiert)
        i = 5;
        y = true;               // Deklaration mit anschließender Initialisierung
        double pi = 3.14159;    // Wert kann auch von anderer Variable stammen
        x = pi;

        // ========== 3. Arbeiten (Rechnen) mit Variablen: ==========
        n = i + 1;      // addition
        k = n - i;      // subtraktion
        l = n * k;      // multiplikation
        x = x / 2.5;    // division

        // ========== 4. Text und Zeichenketten (String)
        //Hinweise: Objekte sind keine primitiven Datentypen
        // Klassenname objektname = new Klassenname();

        String text = new String(); // Das Objekt ist hier „text“ von der Klasse String
                                    // hier wird deklariert und initialisiert in einer Zeile
        String wort;		        // Das ist nur eine Deklaration eines Objekts,
        wort = new String();	    // und hier wird initialisiert.

        wort = "Hallo Welt!";       // hier wird der Variablen der Text "Hallo Welt!" zugewiesen.
    }
}
