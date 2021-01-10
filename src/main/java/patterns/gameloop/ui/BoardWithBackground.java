package patterns.gameloop.ui;

import java.awt.Graphics;
import java.awt.Image;

/**
 * Erweiterung von Spielfeld: Hintergrundbild das sich je nach Level ändert.
 * @author Bernhard Wallisch
 */
public class BoardWithBackground extends Board {

    private Image hintergrundBild;
    private int hintergrundDX;
    private int hintergrundX;

    public BoardWithBackground() {
        hintergrundBild = loadImage("images/thema.png");
        hintergrundDX = (hintergrundBild.getWidth(null) - SPALTEN * QUADRAT_BREITE) / 9;
        hintergrundX = 0;  // keine Animation
    }

    @Override
    public void levelUp() {
        hintergrundX = 1;
    }

    @Override
    public void levelDown() {
        hintergrundX = -1;
    }

    @Override
    public void zeichneHintergrund(Graphics g, int level) {
        int x, y;

        if (hintergrundX > 0) {
            // Themaanimation zum nächsten Level 
            x = (level - 2) * hintergrundDX + hintergrundX;
            hintergrundX += 2;      // Themaanimation fortsetzen
            if (hintergrundX >= hintergrundDX) {
                hintergrundX = 0;  // Themaanimation beenden
            }
        } else if (hintergrundX < 0) {
            // Themaanimation zum vorigen Level 
            x = (level) * hintergrundDX + hintergrundX;
            hintergrundX -= 2;      // Themaanimation fortsetzen
            if (hintergrundX <= -hintergrundDX) {
                hintergrundX = 0;  // Themaanimation beenden
            }
        } else /* hintergrundX == 0 */  {
            x = (level - 1) * hintergrundDX;
        }
        g.drawImage(hintergrundBild, spielfeldX - x, spielfeldY, null);

        super.zeichneHintergrund(g, level);
    }

}
