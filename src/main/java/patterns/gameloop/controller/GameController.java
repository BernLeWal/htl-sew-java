package patterns.gameloop.controller;

import patterns.gameloop.ui.Board;
import patterns.gameloop.Token;
import patterns.gameloop.TetrisDemo;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 */
// Die Game-Loop wird wiederholt solange das Spiel nicht zuende ist
public class GameController {
    public final int WARTEN_MILLIS = 50;

    protected Board spielfeld;

    private boolean spielEnde = true; // zeigt an, ob das Spie gerade läuft (spielEnde==false) 
    private Token aktuellerStein = null;
    private Token naechsterStein = null;
    private int aktuelleLage;		// 0..normal; 1..90°, 2..180°, 3..270° gedreht
    private int aktuellePosSpalte;		// Spalten-Position der linken-oberen Ecke des Steins im Spielfeld
    private int aktuellePosZeile;		// Zeilen-Position der linken-oberen Ecke des Steins im Spielfeld
    
    protected int aktuellRelativZeile;	// für die flüssige Bewegung nach unten; 
    // wieviele Pixel der Stein im Spielfeld weiter nach unten geschoben ist

    public GameController(Board spielfeld) {
        this.spielfeld = spielfeld;
    }

    public boolean isEnde() {
        return spielEnde;
    }

    public void spielsteuerung() {
        if (!spielEnde) { // das Spiel läuft gerade

            // Hinweis: hier würden lt. Game-Loop die Benutzereingaben verarbeitet werden.
            // Das passiert aber Ereignisgesteuert in keyPressed() --> also "gleichzeitig" zur Game-Loop
            if (!bewegeStein()) {
                // Stein kann nicht bewegt werden, da der neue Platz am Spielfeld durch ein Hindernis
                // verstellt ist --> platziere den Stein an der alten Position im Spielfeld.
                spielfeld.platziereStein(aktuellerStein, aktuelleLage, aktuellePosSpalte, aktuellePosZeile);
                sucheUndEntferneVolleZeilen();
                neuerStein();	// mache weiter mit dem nächsten Stein (von ganz oben)
            }
            warten();
        }
    }

    public void steinDrehen() {
        // drehe den Stein um eine Drehung weiter
        int neueLage = aktuelleLage + 1;
        if (neueLage > 3) {
            neueLage = 0;
        }
        if (spielfeld.istFrei(aktuellerStein, neueLage, aktuellePosSpalte, aktuellePosZeile)) {
            if (aktuellRelativZeile == 0 || spielfeld.istFrei(aktuellerStein, neueLage, aktuellePosSpalte, aktuellePosZeile + 1)) {
                aktuelleLage = neueLage;
            }
        }
    }

    public void steinFallen() {
        // den Stein "fallenlassen"
        while (spielfeld.istFrei(aktuellerStein, aktuelleLage, aktuellePosSpalte, aktuellePosZeile + 1)) {
            aktuellePosZeile++;
        }
        spielfeld.platziereStein(aktuellerStein, aktuelleLage, aktuellePosSpalte, aktuellePosZeile);

        sucheUndEntferneVolleZeilen();
        neuerStein();
    }

    public void steinNachLinks() {
        // Stein nach links bewegen
        if (spielfeld.istFrei(aktuellerStein, aktuelleLage, aktuellePosSpalte - 1, aktuellePosZeile)) {
            if (aktuellRelativZeile == 0 || spielfeld.istFrei(aktuellerStein, aktuelleLage, aktuellePosSpalte - 1, aktuellePosZeile + 1)) {
                aktuellePosSpalte--;
            }
        }
    }

    public void steinNachRechts() {
        // Stein nach rechts bewegen
        if (spielfeld.istFrei(aktuellerStein, aktuelleLage, aktuellePosSpalte + 1, aktuellePosZeile)) {
            if (aktuellRelativZeile == 0 || spielfeld.istFrei(aktuellerStein, aktuelleLage, aktuellePosSpalte + 1, aktuellePosZeile)) {
                aktuellePosSpalte++;
            }
        }
    }

    protected void warten() {
        try {
            Thread.sleep( WARTEN_MILLIS );
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }

// bewegt den Stein nach unten mit einer "fließenden" Bewegung,
// die Fallgeschwindigkeit steigert sich mit dem schwierigkeitsGrad
// Rückgabewert: 
// - wenn die Bewegung erfolgreich war --> true
// - oder nicht (z.B. Hindernis am Spielfeld) --> false
    protected boolean bewegeStein() {
        aktuellRelativZeile++;
        if (aktuellRelativZeile >= Board.QUADRAT_HOEHE) {
            if (spielfeld.istFrei(aktuellerStein, aktuelleLage, aktuellePosSpalte, aktuellePosZeile + 1)) {
                // den Stein eine ganze Zeile nach unten schieben
                aktuellRelativZeile = 0;
                aktuellePosZeile++;
                return true;  // Bewegung erfolgreich durchgeführt
            } else {
                return false; // die Bewegung konnte aufgrund eines Hindernisses nicht durchgeführt werden
            }
        } else {
            return true;    // Bewegung erfolgreich durchgeführt
        }
    }

// ein neues Spiel wird gestartet --> Initialisierung der Variablen
    public void neuesSpiel() {
        if (spielEnde) {
            // Spielfeld löschen:
            spielfeld.neu();
            spielEnde = false;	// Programmmodus auf "Spiel läuft gerade" setzen.
        }
        neuerStein();  	// den ersten Spielstein erzeugen
    }

    private void neuerStein() {
        aktuellerStein = (naechsterStein!= null) ? naechsterStein : spielfeld.neuerStein();
        naechsterStein = spielfeld.neuerStein();    // zufälligen Stein auswählen
        aktuelleLage = 0; // int(random(0,4));	   // in normaler, d.h. nicht gedrehter, Lage
        aktuellePosSpalte = Board.SPALTEN / 2 - 1;	   // mittig Positionieren
        aktuellePosZeile = 0;				   // oben beginnen
        aktuellRelativZeile = 0;			   // Pixelverschiebung nach unten ebenfalls bei 0 beginnen

        // Überprüfe ob das Spiel zu Ende ist, 
        // wenn aufgrund von Hindernissen kein neuer Stein mehr eingefügt werden kann
        if (!spielfeld.istFrei(aktuellerStein, aktuelleLage, aktuellePosSpalte, aktuellePosZeile)) {
            spielEnde = true;
            //timer.cancel();
        } else {
            spielEnde = false;
        }
    }

        /**
     * @return the naechsterStein
     */
    public Token getNaechsterStein() {
        return naechsterStein;
    }
    
    protected int sucheUndEntferneVolleZeilen() {
        return spielfeld.sucheUndEntferneVolleZeilen();
    }

    public void zeichneStein(Graphics g) {
        spielfeld.zeichneStein(g, aktuellerStein, aktuelleLage, aktuellePosSpalte, aktuellePosZeile, aktuellRelativZeile);
    }

// Zeiche den "GAME OVER"-Screen mit Punktestand
    public void zeichneSpielende(Graphics offgc) {
        offgc.setColor(Color.BLACK);
        //stroke(0);
        String s = "GAME OVER";
        offgc.fillRect(0, TetrisDemo.WINDOW_HEIGHT / 3 - Board.QUADRAT_HOEHE, TetrisDemo.WINDOW_WIDTH, 2 * Board.QUADRAT_HOEHE);
        offgc.setColor(Color.WHITE);
        //textAlign(CENTER);
        offgc.drawString(s, TetrisDemo.WINDOW_WIDTH / 4, TetrisDemo.WINDOW_HEIGHT / 2 - 8);
        s = "Drücke Leertaste zum Start";
        offgc.drawString(s, TetrisDemo.WINDOW_WIDTH / 4, TetrisDemo.WINDOW_HEIGHT / 2 + 8);
    }    
    
    
    public void zeichneSpielstand(Graphics g) {
        g.setColor(Color.WHITE);
        int textHeight = 16;//(int) (textAscent() + textDescent());
        int y = textHeight;
        int x = 4;
        g.drawString("Tetris", x, y);        
    }

    public int getLevel() {
        return 0;
    }
    
    public void levelUp() {
    }

    public void levelDown() {
    }
}
