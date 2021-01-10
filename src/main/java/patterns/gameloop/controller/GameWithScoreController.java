package patterns.gameloop.controller;

import patterns.gameloop.TetrisDemo;
import patterns.gameloop.ui.Board;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Bernhard
 */
public class GameWithScoreController extends GameController {

    private int level;		// Schwierigkeitsgrad: 1(leicht) .. 10(schwer)
    private int punktestand;		// erreichte Punkte des Spielers
    private int volleZeilen;		// vollständige Zeilen (im aktuellen schwierigkeitsGrad) die entfernt wurden

    
    public GameWithScoreController(Board spielfeld) {
        super(spielfeld);
    }

    
    @Override
    public void steinFallen() {
        super.steinFallen();
        punktestand += level;
    }
    
    @Override
    public void neuesSpiel() {
        punktestand = 0;	  // Punktestand beginnt von 0
        level = 1; // Start im leichtesten Schwierigkeitsgrad
        volleZeilen = 0;	  // es wurden noch keine vollen Zeilen erreicht        
        
        super.neuesSpiel();
    }
    
    @Override
    protected int sucheUndEntferneVolleZeilen() {
        int vz = super.sucheUndEntferneVolleZeilen();

        // Punkte erhöhen
        punktestand += switch (vz) {
            case 4 -> 10 * level;
            case 3 ->  5 * level;
            case 2 ->  3 * level;
            case 1 ->  2 * level;
            default -> 0;
        };

        volleZeilen += vz;
        if (volleZeilen > Board.ZEILEN) {	// wenn genug volle Zeilen erreicht wurden, dann kommt man in den nächsten schwierigkeitsGrad
            levelUp();
        }

        return vz;
    }
    
    @Override
    public int getLevel() {
        return level;
    }
    
    @Override
    public void levelUp() {
        if (level< 10) {
            level++;
            volleZeilen = 0;
            spielfeld.levelUp();  // Themaanimation starten
        }
    }

    @Override
    public void levelDown() {
        if (level> 1) {
            level--;
            volleZeilen = 0;
            spielfeld.levelDown();  // Themaanimation starten
        }
    }    
    
    @Override
    protected void warten() {
        // Stein pixelweise nach unten schieben, 
        // je höher der Schwierigkeitsgrad, desto weiter)
        if (level > 5) {
            aktuellRelativZeile += (level - 5) * 2 - 1;
        } else {
            try {
                Thread.sleep( WARTEN_MILLIS - (level*10) );
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        } 
    }
    
    @Override
    protected boolean bewegeStein() {
        if( !super.bewegeStein() ) {
            punktestand += level;
            return false;
        }
        return true;
    }
    
    @Override
    public void zeichneSpielstand(Graphics g) {
        super.zeichneSpielstand(g);
        
        // Punktestand, Level und erreichte Reihen
        int textHeight = 16;//(int) (textAscent() + textDescent());
        int y = 3*textHeight;
        int x = 4;
        g.drawString("score", x, y);
        y += textHeight;
        g.drawString(String.valueOf(punktestand), x, y);
        y += textHeight;
        g.drawString("level", x, y);
        y += textHeight;
        g.drawString(String.valueOf(level), x, y);
        y += textHeight;
        g.drawString("rows", x, y);
        y += textHeight;
        g.drawString(String.valueOf(volleZeilen), x, y);
    }    
    
    @Override
    // Zeiche den "GAME OVER"-Screen mit Punktestand
    public void zeichneSpielende(Graphics offgc) {
        offgc.setColor(Color.BLACK);
        //stroke(0);
        String s = "GAME OVER - score:" + punktestand;
        offgc.fillRect(0, TetrisDemo.WINDOW_HEIGHT / 2 - Board.QUADRAT_HOEHE, TetrisDemo.WINDOW_WIDTH, 2 * Board.QUADRAT_HOEHE);
        offgc.setColor(Color.WHITE);
        //textAlign(CENTER);
        offgc.drawString(s, TetrisDemo.WINDOW_WIDTH / 4, TetrisDemo.WINDOW_HEIGHT / 2 - 8);
        s = "Drücke Leertaste zum Start";
        offgc.drawString(s, TetrisDemo.WINDOW_WIDTH / 4, TetrisDemo.WINDOW_HEIGHT / 2 + 8);
    }    
}
