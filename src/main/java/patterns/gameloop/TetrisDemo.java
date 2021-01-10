/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patterns.gameloop;

import patterns.gameloop.controller.GameController;
import patterns.gameloop.controller.GameWithScoreController;
import patterns.gameloop.ui.Board;
import patterns.gameloop.ui.BoardWithBackground;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * TetrisDemo implements the tetris game using a simple form of the gameloop behavioral pattern
 * with a Swing/AWT user interface.
 * Remarks: Swing and AWT are old and legacy frameworks, use JavaFX in new projects.
 *
 * see https://gameprogrammingpatterns.com/game-loop.html
 */
public class TetrisDemo extends javax.swing.JFrame {

    public static final int WINDOW_LEFT = 8;
    public static final int WINDOW_TOP = 28;
    public static final int WINDOW_WIDTH = 320;
    public static final int WINDOW_HEIGHT = 480;
    
    private Board spielfeld;
    private GameController spiel;
    private Timer timer;

    /**
     * Creates new form GameFrame
     */
    public TetrisDemo() {
        initComponents();

        this.setSize(WINDOW_LEFT + WINDOW_WIDTH + WINDOW_LEFT, WINDOW_TOP + WINDOW_HEIGHT + WINDOW_LEFT);		// Displaymaße festlegen:
        //	Breite = SPALTEN * QUADRAT_BREITE
        //	Höhe = ZEILEN*QUADRAT_HOEHE

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if (spiel.isEnde()) { // das Spiel ist (noch immer) zu Ende
                    if (ke.getKeyChar() == ' ') // Wenn der Spieler die Leertaste drückt, dann starte ein neues Spiel
                    {
                        spiel.neuesSpiel();
                    }
                } else {	// das Spiel läuft gerade
                    if (ke.isActionKey()) {
                        if (ke.getKeyCode() == KeyEvent.VK_UP) {
                            spiel.steinDrehen();
                        } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                            spiel.steinFallen();
                        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                            spiel.steinNachLinks();
                        } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                            spiel.steinNachRechts();
                        }
                    } else if (ke.getKeyChar() == '+') {
                        spiel.levelUp();
                    } else if (ke.getKeyChar() == '-') {
                        spiel.levelDown();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
            }
        });
        
        spielfeld = new BoardWithBackground();

        spiel = new GameWithScoreController(spielfeld);
        spiel.neuesSpiel();     

        timer = new Timer();
        timer.schedule(new GameLoop(), 0, 10);
    }

    private class GameLoop extends TimerTask {

        @Override
        public void run() {
            spiel.spielsteuerung();
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {      // WALL: Ergänzung, damit die Anzeige nicht flimmert - nennt man "Double Buffering"
        Image offscreen;
        Dimension d = size();

        // create the offscreen buffer and associated Graphics
        offscreen = createImage(d.width, d.height);
        Graphics offgc = offscreen.getGraphics();

        super.paint(offgc);
        zeichneSpielfeld(offgc);
        if (!spiel.isEnde()) { // das Spiel läuft gerade
            spiel.zeichneStein(offgc);
        } else { // das Spiel ist zu Ende
            spiel.zeichneSpielende(offgc);	// zeige den Punktestand an; 
            // warte auf Tastendruck, damit ein neues Spiel beginnt
        }

        // transfer offscreen to window
        g.drawImage(offscreen, WINDOW_LEFT, WINDOW_TOP, this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tetris");
        setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );

        pack();
    }

    /**
     * das ganze Spielfeld wird mit allen vorhandenen Hindernissen gezeichnet
     * ACHTUNG: der gerade vom Spieler bewegte Stein ist NICHT Teil des Spielfelds
     */
    private void zeichneSpielfeld(Graphics offgc) {
        // Hintergrund 
        offgc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        spielfeld.zeichneHintergrund(offgc, spiel.getLevel());

        // Spielstandsanzeige (links-oben)
        spiel.zeichneSpielstand(offgc);

        // Vorschau auf den nächsten Stein (rechts oben)
        spielfeld.zeichneSteinXY(offgc, spiel.getNaechsterStein(), WINDOW_WIDTH - 4, 4);
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TetrisDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TetrisDemo().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
