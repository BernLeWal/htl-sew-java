package patterns.gameloop;

/**
 * Diese Klasse repräsentiert einen Stein (aus vier Quadraten), der am Spielfeld bewegt wird.
 * @author Bernhard Wallisch
 */
public class Token {

    public static final int ANZAHL = 7;	// es gibt 7 verschiedene Spielsteine
    
    private int[][] stein;              // die Definition des Spielsteins [X][Y]
    private int hoehe;                  // damit die Steine über die Mitte gedreht werden können,
    private int breite;                 // .. steht hier die tatsächliche Breite/Höhe pro Stein
    
    private int steinNr;

    
    public Token(int steinNr) {
        this.steinNr = steinNr;
        stein = new int[4][];
        switch (steinNr) {
            // (leer), d.h. kein Stein
            case 0:
                stein[0] = new int[]{0, 0, 0, 0};
                stein[1] = new int[]{0, 0, 0, 0};
                stein[2] = new int[]{0, 0, 0, 0};
                stein[3] = new int[]{0, 0, 0, 0};
                breite = 4;
                hoehe = 4;
                break;

            // XXXX
            case 1:
                stein[0] = new int[]{1, 1, 1, 1};
                stein[1] = new int[]{0, 0, 0, 0};
                stein[2] = new int[]{0, 0, 0, 0};
                stein[3] = new int[]{0, 0, 0, 0};
                breite = 4;
                hoehe = 1;
                break;

            // X
            // XXX
            case 2:
                stein[0] = new int[]{2, 0, 0, 0};
                stein[1] = new int[]{2, 2, 2, 0};
                stein[2] = new int[]{0, 0, 0, 0};
                stein[3] = new int[]{0, 0, 0, 0};
                breite = 3;
                hoehe = 2;
                break;

            // XX
            // XX
            case 3:
                stein[0] = new int[]{3, 3, 0, 0};
                stein[1] = new int[]{3, 3, 0, 0};
                stein[2] = new int[]{0, 0, 0, 0};
                stein[3] = new int[]{0, 0, 0, 0};
                breite = 2;
                hoehe = 2;
                break;

            //  XX
            // XX
            case 4:
                stein[0] = new int[]{0, 4, 4, 0};
                stein[1] = new int[]{4, 4, 0, 0};
                stein[2] = new int[]{0, 0, 0, 0};
                stein[3] = new int[]{0, 0, 0, 0};
                breite = 3;
                hoehe = 2;
                break;

            //  X
            // XXX
            case 5:
                stein[0] = new int[]{0, 5, 0, 0};
                stein[1] = new int[]{5, 5, 5, 0};
                stein[2] = new int[]{0, 0, 0, 0};
                stein[3] = new int[]{0, 0, 0, 0};
                breite = 3;
                hoehe = 2;
                break;

            // XX
            //  XX
            case 6:
                stein[0] = new int[]{6, 6, 0, 0};
                stein[1] = new int[]{0, 6, 6, 0};
                stein[2] = new int[]{0, 0, 0, 0};
                stein[3] = new int[]{0, 0, 0, 0};
                breite = 3;
                hoehe = 2;
                break;

            //   X
            // XXX
            case 7:
                stein[0] = new int[]{0, 0, 7, 0};
                stein[1] = new int[]{7, 7, 7, 0};
                stein[2] = new int[]{0, 0, 0, 0};
                stein[3] = new int[]{0, 0, 0, 0};
                breite = 3;
                hoehe = 2;
                break;
        }
    }

    public int getBreite() {
        return breite;
    }

    public int getHoehe() {
        return hoehe;
    }

    public int getQuadrat(int lage, int col, int lin) {
        int bcol, blin;
        if (lage == 1) { // Stein ist 90° gedreht
            blin = hoehe - 1 - col;
            bcol = lin;
        } else if (lage == 2) { // Stein ist 180° gedreht
            blin = hoehe - 1 - lin;
            bcol = breite - 1 - col;
        } else if (lage == 3) { // Stein ist 270° gedreht
            blin = col;
            bcol = breite - 1 - lin;
        } else { // Stein in der Normallage, d.h. nicht gedreht
            blin = lin;
            bcol = col;
        }

        if (blin >= 0 && bcol >= 0) {
            return stein[blin][bcol];
        } else {
            return 0;
        }
    }
}
