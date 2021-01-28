package lang;

/**
 * Demo05Strings zeigt die Arbeit mit Zeichenketten (=Strings) in Java.
 */
public class Demo05Strings {
    public static void main(String[] args) {
        // ---------- String erzeugen und initialisieren: ----------
        String zeichenkette = new String();
        zeichenkette = "Das ist ein Text!";

        // Weitere Möglichkeiten einen String zu initialisieren:
        String zeichenkette2 = "Das ist ein Text!";
        String zeichenkette3 = new String("Das ist ein Text!");

        System.out.println(zeichenkette);
        System.out.println(zeichenkette2);
        System.out.println(zeichenkette3);

        // ---------- Zeichen und Zeichenketten auslesen: ----------
        zeichenkette = "Das ist ein Text!";
        //  Position:   0   4  7    12

        // charAt(…) liefert das Zeichen an der angegebenen Stelle
        char zeichenT = zeichenkette.charAt(12);       // 'T'
        System.out.println(zeichenT);

        // substring(… , …) mit zwei Parameter liefert den Teil vom Start- bis zum Endindex
        String kopie1 = zeichenkette.substring(4, 7);     // "ist"
        System.out.println(kopie1);

        // substring(…) mit einem Parameter liefert die Teilzeichenkette ab dem Index bis zum Ende
        String kopie2 = zeichenkette.substring(12);      // "Text!"
        System.out.println(kopie2);

        // ---------- Anzahl der Zeichen in einer Zeichenkette: ----------
        int laenge = zeichenkette.length();
        // liefert die Länge der Zeichenkette, hier 17
        System.out.println("Länge: " + laenge);

        // ---------- Suchen in Zeichenketten: ----------
        int pos1 = zeichenkette.indexOf("ein");     // liefert die Anfangsposition einer Teilzeichenkette (hier: pos=8),
        //bei Nichtfinden ist pos=-1
        System.out.println("Pos1: " + pos1);

        int pos2 = zeichenkette.indexOf("ein", 9);    // wie vorher, beginnt aber erst bei Parameter2 (Startwert=9)
        //zu suchen, (Ergebnis: pos=-1)
        System.out.println("Pos2: " + pos2);

        // ---------- Vergleichen von Zeichenketten: ----------
        // vergleicht ob beide Texte inkl. Groß-/kleinschreibung ident sind:
        boolean gleicherText = zeichenkette.equals("Das ist ein Text!");           //true
        System.out.println("gleicherText: " + gleicherText);
        boolean gleicherInhalt = zeichenkette.equalsIgnoreCase("das ist ein text!"); //true
        System.out.println("gleicherInhalt: " + gleicherInhalt);

        // ---------- weitere Demonstrationen: ----------
        ipAddressDemo1();
        ipAddressDemo2();
    }

    private static void ipAddressDemo1() {
        System.out.println("HOWTO use indexOf() and substring() to work with IPv4-Adresses");
        String ipAddress = "192.168.0.1"; //"84.193.87.12";
        int pt1 = ipAddress.indexOf(".");
        String nr1 = ipAddress.substring(0, pt1);
        System.out.println(nr1);
        int pt2 = ipAddress.indexOf(".", pt1 + 1);
        String nr2 = ipAddress.substring(pt1 + 1, pt2);
        System.out.println(nr2);
        int pt3 = ipAddress.indexOf(".", pt2 + 1);
        String nr3 = ipAddress.substring(pt2 + 1, pt3);
        System.out.println(nr3);
        String nr4 = ipAddress.substring(pt3 + 1);
        System.out.println(nr4);
        System.out.println(nr1 + "." + nr2 + "." + nr3 + "." + nr4);
        System.out.println();
    }

    public static void ipAddressDemo2() {
        System.out.println("HOWTO use split() to work with IPv4-Adresses");
        String ipAddress = "192.168.0.1"; //"84.193.87.12";
        String[] parts = ipAddress.split("\\.");    // split works with regular-expressions, so you need to escape the '.' character
        System.out.println(parts[0] + "." + parts[1] + "." + parts[2] + "." + parts[3]);
        System.out.println();
    }
}
