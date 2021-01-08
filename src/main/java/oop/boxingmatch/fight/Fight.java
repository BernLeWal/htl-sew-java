package oop.boxingmatch.fight;


import oop.boxingmatch.boxer.BoxerInterface;
import oop.boxingmatch.boxer.MyBoxer;
import oop.boxingmatch.boxer.WeirdBoxer;

/**
 * Fight implements the boxing match between two instances of boxers.
 * <p>
 * Bei einem Kampf treten zwei (unterschiedliche) Instanzen von Boxern gegeneinander an.
 * Der Kampf geht über zehn Runden in denen sich beide Boxer auf je eine Bewegung (hit/block)
 * an einer Position (head/body/foot) in der Vorbereitung
 * (d.h. das steht in der Implementierung der Klasse) festlegen.
 * <p>
 * Diese zehn Runden werden hintereinander ausgeführt und die Boxer bekommen je nach Rundenausgang Gut-/ oder Schlechtpunkte:
 * -	Wenn beide Boxer verteidigen, dann bekommt niemand einen Punkt.
 * -	Wenn beide Boxer schlagen, dann
 * -	…bekommt niemand einen Punkt wenn sie sich auf unterschiedlicher Position treffen (=gleichstand)
 * - 	…bekommen beide einen Punkt Abzug, wenn sie sich auf gleicher Position treffen
 * -	Wenn ein Boxer schlägt und der andere verteidigt, dann…
 * -	…bekommt der Schläger einen Punkt, wenn der Verteidiger auf einer anderen Position verteidigt
 * -	…bekommt der Verteidiger einen Punkt, wenn er den Schlag an der richtigen Position abfängt.
 * Der Boxer mit den meisten Punkten am Ende des Kampfes hat gewonnen.
 */
public class Fight {
    private BoxerInterface boxer1;
    private int score1;

    private BoxerInterface boxer2;
    private int score2;

    public Fight(BoxerInterface boxer1, BoxerInterface boxer2) {
        this.boxer1 = boxer1;
        score1 = 0;

        this.boxer2 = boxer2;
        score2 = 0;
    }

    public int fight() {
        System.out.println(boxer1.name() + " gegen " + boxer2.name());

        for (int round = 1; round <= 10; round++) {
            System.out.print(round + ".Runde: ");

            System.out.print(boxer1.name() + " ");
            final int m1 = boxer1.getMovement(round);
            System.out.print((m1 == 0) ? "verteidigt " : "schlägt    ");
            final int p1 = boxer1.getPosition(round);
            System.out.print((p1 == 0) ? "Kopf  " : ((p1 == 1) ? "Körper" : "Beine "));

            System.out.print(", ");

            System.out.print(boxer2.name() + " ");
            final int m2 = boxer2.getMovement(round);
            System.out.print((m2 == 0) ? "verteidigt " : "schlägt    ");
            final int p2 = boxer2.getPosition(round);
            System.out.print((p2 == 0) ? "Kopf  " : ((p2 == 1) ? "Körper" : "Beine "));

            System.out.print(" ==> ");
            if (m1 == 0 && m2 == 0)
                System.out.println("keine Punkte");
            else if (m1 == 1 && m2 == 1) {
                if (p1 == p2) {
                    System.out.println("OUCH! beide 1 Punkt Abzug!");
                    score1--;
                    score2--;
                } else
                    System.out.println("Gleichstand, keine Punkte.");
            } else if (p1 == p2) {
                if (m1 == 1 /* && m2==0*/) {
                    System.out.println("Punkt für " + boxer2.name());
                    score2++;
                } else {
                    System.out.println("Punkt für " + boxer1.name());
                    score1++;
                }
            } else /*p1!=p2*/ {
                if (m1 == 1 /* && m2==0*/) {
                    System.out.println("Punkt an " + boxer1.name());
                    score1++;
                } else {
                    System.out.println("Punkt an " + boxer2.name());
                    score2++;
                }
            }
        }

        System.out.println("Es steht " + score1 + " zu " + score2);
        if (score1 > score2) {
            System.out.println(boxer1.name() + " ist der Gewinner!");
            return 1;
        } else if (score2 > score1) {
            System.out.println(boxer2.name() + " ist der Gewinner!");
            return 2;
        } else {
            System.out.println("Unentschieden.");
            return 0;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Fight match = new Fight(new MyBoxer(), new WeirdBoxer());
        match.fight();
    }
}
