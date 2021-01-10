package patterns.gameloop.ui;

import patterns.gameloop.Token;
import patterns.gameloop.TetrisDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Das Spielfeld bestehtehend aus 10x20 Quadraten.
 *
 * @author Bernhard Wallisch
 */
public class Board {

    public static final int SPALTEN = 10;               // das Spielfeld ist 10 Quadrate breit
    public static final int ZEILEN = 20;                // das Spielfeld ist 20 Quadrate hoch
    public static final int QUADRAT_BREITE = 24;	// ein Quadrat ist 24 Pixel breit
    public static final int QUADRAT_HOEHE = 24;         // ein Quadrat ist 24 Pixel hoch

    private int[][] spielfeld = new int[SPALTEN][ZEILEN];   // Speichert die im Spielfeld vorhandenen Hindernisse
    private Image[] quadrat = new Image[Token.ANZAHL + 1];
    protected int spielfeldX, spielfeldY;

    
    public Board() {
        for (int i = 1; i <= Token.ANZAHL; i++) {
            quadrat[i] = loadImage("images/block" + i + ".png");
        }
        spielfeldX = (TetrisDemo.WINDOW_WIDTH - SPALTEN * QUADRAT_BREITE) / 2;
        spielfeldY = (TetrisDemo.WINDOW_HEIGHT - ZEILEN * QUADRAT_HOEHE) / 2;
    }

    
    public void neu() {
        for (int x = 0; x < SPALTEN; x++) {
            for (int y = 0; y < ZEILEN; y++) {
                spielfeld[x][y] = 0;
            }
        }
    }

    // Überprüft ob der Platz am Spielfeld für den angegebenen Stein frei ist (d.h. keine Hindernisse)
    public boolean istFrei(Token stein, int lage, int x, int y) {
        if (x < 0 || y < 0 || x >= SPALTEN || y >= (ZEILEN - 1)) {
            return false;  // außerhalb des Spielfelds
        }
        for (int lin = 0; lin < 4; lin++) {
            for (int col = 0; col < 4; col++) {
                int b = stein.getQuadrat(lage, col, lin);
                if (b > 0) {
                    int fx = x + col;
                    int fy = y + lin;
                    if (fx < 0 || fy < 0 || fx >= SPALTEN || fy >= (ZEILEN - 1)) {
                        return false;  // außerhalb des Spielfelds
                    }
                    if (spielfeld[fx][fy] > 0) {
                        return false;  // Kollision mit einem Hindernis
                    }
                }
            }
        }
        return true;  // frei
    }

    // platziere den Stein auf das Spielfeld
    // Hinweis: solange der Spieler den Stein bewegt ist der Stein NICHT Teil des Spielfeldes
    public void platziereStein(Token stein, int r, int x, int y) {
        for (int lin = 0; lin < 4; lin++) {
            for (int col = 0; col < 4; col++) {
                int b = stein.getQuadrat(r, col, lin);
                if (b > 0) {
                    int fx = x + col;
                    int fy = y + lin;
                    if (fx >= 0 && fy >= 0 && fx < SPALTEN && fy < (ZEILEN - 1)) {
                        spielfeld[fx][fy] = b;
                    }
                }
            }
        }
    }

    public int sucheUndEntferneVolleZeilen() {
        int x, vz = 0;
        for (int y = ZEILEN - 1; y > 0; y--) {
            for (x = 0; x < SPALTEN; x++) {
                if (spielfeld[x][y] <= 0) {
                    break;
                }
            }
            if (x < SPALTEN) {
                continue;
            }

            // die Zeile ist voll --> schiebe die darüberliegenden Zeilen nach unten
            for (int dy = y; dy >= 0; dy--) {
                for (x = 0; x < SPALTEN; x++) {
                    if (dy > 0) {
                        spielfeld[x][dy] = spielfeld[x][dy - 1];
                    } else {
                        spielfeld[x][dy] = 0;
                    }
                }
            }
            vz++;

            y++;  // Überprüfe die aktuelle Zeile nochmals (wegen Verschiebung)
        }
        return vz;
    }

    public void zeichneHintergrund(Graphics g, int level) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, spielfeldX, TetrisDemo.WINDOW_HEIGHT);
        g.fillRect(TetrisDemo.WINDOW_WIDTH - spielfeldX - 1, 0, spielfeldX, TetrisDemo.WINDOW_HEIGHT);

        for (int x = 0; x < SPALTEN; x++) {
            for (int y = 0; y < ZEILEN; y++) {
                int q = spielfeld[x][y];
                if (q > 0) {    // Belegte quadrate
                    g.drawImage(quadrat[q], spielfeldX + x * QUADRAT_BREITE, spielfeldY + (y + 1) * QUADRAT_HOEHE, null);
                }
            }
        }
    }

    public void zeichneSteinXY(Graphics g, Token stein, int x, int y) {
        for (int zeile = 0; zeile < 4; zeile++) {
            for (int spalte = 0; spalte < 4; spalte++) {
                int q = stein.getQuadrat(0, spalte, zeile);
                if (q > 0) {
                    g.drawImage(quadrat[q], x - stein.getBreite() * QUADRAT_BREITE / 2 + spalte * QUADRAT_BREITE / 2, y + zeile * QUADRAT_HOEHE / 2, QUADRAT_BREITE / 2, QUADRAT_HOEHE / 2, null);
                }
            }
        }
    }

    // Diese Funktion zeichnet eine Stein auf das Display, d.h. über das Spielfeld
    public void zeichneStein(Graphics g, Token stein, int lage, int posSpalte, int posZeile, int pixelVerschiebungZeile) {
        for (int zeile = 0; zeile < 4; zeile++) {
            for (int spalte = 0; spalte < 4; spalte++) {
                int q = stein.getQuadrat(lage, spalte, zeile);
                if (q > 0) {
                    g.drawImage(quadrat[q], spielfeldX + (posSpalte + spalte) * QUADRAT_BREITE, spielfeldY + (posZeile + zeile) * QUADRAT_HOEHE + pixelVerschiebungZeile, null);
                }
            }
        }
    }

    public void levelUp() {

    }

    public void levelDown() {

    }

    public Token neuerStein() {
        return new Token((int) (1 + Math.random() * Token.ANZAHL));
    }

    public Image loadImage(String path) {
        try {
            URL resource = TetrisDemo.class.getResource("/ui/tetris/" + path);
            return ImageIO.read(resource);
        } catch (IOException ex) {
            System.err.println(ex);
            return null;
        }
    }
}
