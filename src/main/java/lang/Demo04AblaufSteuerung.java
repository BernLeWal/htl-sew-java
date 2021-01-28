package lang;

/**
 * Demo04AblaufSteuerung zeigt die Möglichkeiten der Ablaufsteuerung in einem Java-Programm.
 */
public class Demo04AblaufSteuerung {
    public static void main(String[] args) {
        int a = 3;
        int b = 8;

        // ========== 1. Einfache Verzweigung: if ============
        //Verzweigungen sind immer an Bedingungen geknüpft.
        if ( a < 5 )
        {
            // weiteren Code hier einfügen, dieser wird nur
            // ausgeführt wenn die Bedingung „wahr“ ist !
            System.out.println( "Variable a ist kleiner als 5");
        }


        // ========== 2. Bedingungen ==========
        // Eine Bedingung hat nur zwei Ergebnisse, entweder „wahr“ oder „falsch“.
        // Beim Ausführen steht statt der Bedingung nur mehr true oder false an dieser Stelle:

        if ( a > b ) {
            System.out.println("Die Bedingung ist wahr wenn a größer b ist.");
        }

        if ( a >= b ) {
            System.out.println("... wahr wenn a größer oder gleich b ist.");
        }

        if( a < b ) {
            System.out.println("... wahr wenn a kleiner b ist.");
        }

        if ( a <= b ) {
            System.out.println("... wahr wenn a kleiner oder gleich b ist.");
        }

        if ( a == b ) {
            System.out.println("... wahr wenn a gleich b ist.");
        }

        if ( a != b ) {
            System.out.println("... wahr wenn a ungleich b ist.");
        }

        //!(Bedingung)    // Dreht die Bedingung um, wahr wird zu falsch und umgekehrt.
        if ( !(a != b) ) {
            System.out.println("... unwahr wenn (a ungleich b) ist");
        }


        // ========== 3.Verzweigung if/else ==========
        if ( a < 5 )
        {
            // weiteren Code hier einfügen,
            System.out.println(" dieser wird nur ausgeführt wenn die Bedingung „wahr“ ist !");
        }
        else
        {
            // weiteren Code hier einfügen,
            System.out.println(" dieser wird nur ausgeführt wenn die Bedingung „falsch“ ist !");
        }


        // ========== 4a. Wiederholungen bzw. Schleifen: for ==========
        // Syntax: for (<Startwert>; <Laufbedingung>; <Schrittweite>) { }
        for (int i=0; i<5; i++)
        {   // 5 Wiederholungen
            System.out.println( i + "te Wiederholung.");
        }
        // Gleiche Anzahl:
        for(int i=1; i<=5; i++) {
            System.out.println( i + "te Wiederholung.");
        }

        // Vorsicht FALLE: kein Strichpunkt nach der runden Klammer bei for!
        for (int i=0; i<5; i++);
        {
            // Das ist kein Schleifenblock, die Variable i kann hier nicht mehr verwendet werden !!
            System.out.println("Dieser Block gehört nicht zur for-Schleife und wird daher nicht wiederholt!");
        }

        // Andere Schrittweite:
        for (int i=1; i<5; i=i+2) {
            System.out.println( i );
        }

        // ========== 4b. (kopfgesteuerte) Schleife: while ==========
        int i = 0;
        while (i < b)
        {
            System.out.println( i );

            i++; // oder i=i+1;
        }

        // ========== 4c. (Fußgesteuerte) Schleife: do/while ==========
        //Diese Schleife wird immer zumindest einmal ausgeführt, sonst gleich wie vorhin
        i = 0;
        do
        {
            System.out.println( i );

            i++;
        }
        while (i < b);

        // ========== 5. Mehrere Bedingungen Verknüpfungen ==========
        i = 6;

        //Logische UND Verknüpfung: beide Bedingungen müssen wahr sein.
        if ( (i<a) && (i<b) ) {
            System.out.println("Alle Bedingungen sind wahr.");
        }
        // && ... Wenn Bedingung1 bereits falsch ist, dann wird Bedingung2 nicht mehr geprüft.
        // & ... Es werden immer beide Bedingungen geprüft.

        //Logische ODER Verknüpfung: zumindest eine der beiden Bedingungen muss wahr sein
        if ( (i<a) || (i<b) ) {
            System.out.println("Eine der Bedingungen ist wahr.");
        }
        // || ... Wenn Bedingung1 bereits wahr ist, dann wird Bedingung2 nicht mehr geprüft.
        // | ... Es werden immer beide Bedingungen geprüft.
    }
}
