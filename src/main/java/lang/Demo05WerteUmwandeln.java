package lang;

/**
 * Demo05WerteUmwandeln demonstriert wie Werte zwischen unterschiedlichsten einfachen Datentypen umgewandelt werden.
 */
public class Demo05WerteUmwandeln {
    public static void main(String[] args) {
        int i = 17;
        float f = 1.2f;
        double d = Math.PI;

        // ========== 1. Nummern in Strings ==========
        String si = String.valueOf(i);  // aus int wird String
        String sf = String.valueOf(f);  // aus float wird String
        String sd = String.valueOf(d);  // aus double wird String

        // ========== 2. Gleitkommazahlen formatieren ==========
        String sfd = String.format("%5d", i);       // liefert "   17" (insgesamt 5 Zeichen)
        System.out.println(sfd);

        String sff = String.format("%5.2f", f);     // liefert " 1,20" (insgesamt 5 Zeichen; 2 Nachkommast.)
        System.out.println(sff);

        // ========== 3. (einfache) Umwandlung von Strings in Nummern ==========
        i = Integer.valueOf("17");
        f = Float.valueOf("1.2");
        d = Double.valueOf("1.22");
        //Achtung: Kann der String nicht umgewandelt werden, so liefert der obengenannte Code den Wert 0.

        // ========== 4. Casting: Umwandlung von Werten zwischen primitiven Datentypen ==========
        float f_i = (float)17;      // ergibt float mit Wert 17.0f
        int i_d = (int)1.2;         // ergibt int mit Wert 1. ACHTUNG: Datenverlust!
        double d_f = (double)1.2f;  // ergibt double Wert 1.2

        // ========== 5. Zufallszahlen erzeugen ==========
        // Math.random() gibt eine Zufallszahl zwischen 0 und 1 vom Typ double aus.
        int zufallszahl = (int)(Math.random()*50+1);    // Zufallszahl zwischen 1 und 50

        // ========== 6. Restwertdivision ==========
        if (i % 2 == 0) {
            // alle geraden Zahlen von "i" ausgeben
            System.out.println(i);
        }
    }
}
