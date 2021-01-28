package lang;

import java.util.Scanner;

/**
 * Demo03EinUndAusgabe zeigt wie man in Java etwas ausgibt bzw. vom Benutzer Eingaben einliest.
 */
public class Demo03EinUndAusgabe {
    public static void main(String[] args) {
        String wort = "Hallo Welt!";

        // ========== 1. Ausgabe von Text und Variablenwerten ==========
        System.out.println("Ausgabe von Text: " + wort); // mit Zeilenumbruch
        System.out.print("Ausgabe von Text (ohne Zeilenumbruch):  " + wort);   // ohne Zeilenumbruch
        System.out.println(" Hier gehts weiter.");

        // ========== 2. Tastatureingaben ==========
        //Die Eingabe über die Tastatur kann man z.B. mit der Klasse Scanner durchführen
        Scanner sc = new Scanner(System.in);

        System.out.print("Gib ein Wort ein: ");
        wort = sc.next();   // ein Wort einlesen
        System.out.print("Gibt eine Zahl ein: ");
        int i = sc.nextInt();   // eine Zahl einlesen
        System.out.print("Gib eine Zeile mit mehreren Wörtern ein: ");
        String zeile = sc.nextLine();  // eine ganze Textzeile einlesen (bis der User die ENTER-Taste drückt)


        System.out.println("Das eingegebene Wort lautet: " + wort);
        System.out.println("Die eingegebene Zahl lautet: " + i);
        System.out.println("Die eingegebene Zeile war:");
        System.out.println(zeile);
    }
}
