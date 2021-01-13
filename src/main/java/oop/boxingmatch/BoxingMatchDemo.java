/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop.boxingmatch;

import oop.boxingmatch.boxer.BoxerInterface;
import oop.boxingmatch.boxer.MyBoxer;
import oop.boxingmatch.boxer.ShyBoxer;
import oop.boxingmatch.boxer.WeirdBoxer;
import oop.boxingmatch.fight.Fight;
import oop.boxingmatch.training.Training;

/**
 * BoxingMatchDemo
 *
 * Ziel dieses Java-Projektes ist es eine Implementierung eines Boxers zu schreiben
 * und diesen in Boxkämpfen gegen die Implementierungen der Kollegen bzw.
 * in einem Turnier innerhalb der ganzen Klasse antreten zu lassen.
 *
 * Bei einem Turnier machen die Instanzen der Boxer aller Schüler mit und treten im k.o.-System immer 2 und 2 gegeneinander an.
 */
public class BoxingMatchDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Training.main(args);
        //Fight.main(args);

        System.out.println("********** Das Turnier beginnt **********");

        BoxingMatchDemo competition = new BoxingMatchDemo();
        BoxerInterface[] boxers = new BoxerInterface[] {
                new ShyBoxer(),
                new WeirdBoxer(),
                new MyBoxer(),
                new ShyBoxer()
        };
        BoxerInterface winner = competition.run( boxers );

        System.out.println("*****************************************");
        System.out.println("Der Gewinner ist " + winner.name());

    }

    private int runde = 0;

    public BoxerInterface run(BoxerInterface[] boxers) {
        while( boxers.length>2 )
            boxers = round(boxers);
        return boxers[0];   // der übriggebliebene ist der Gewinner
    }

    public BoxerInterface[] round(BoxerInterface[] boxers) {
        runde++;
        System.out.println();
        System.out.println("=========================================");
        System.out.println("== " + runde + ". Turnier-Runde: ");
        System.out.println("=========================================");

        // Die Kämpfer auf die einzelnen Zweikämpfe aufteilen:
        BoxerInterface[] fighter1 = new BoxerInterface[boxers.length/2+1];
        BoxerInterface[] fighter2 = new BoxerInterface[boxers.length/2+1];
        for(int i = 0; i < boxers.length; i++) {
            fighter1[i/2] = boxers[i];
            i++;
            if( i < boxers.length )
                fighter2[i/2] = boxers[i];
            else
                fighter2[i/2] = null;   // kein Gegner für diesen Boxer
        }

        // Alle Kämpfe durchführen
        BoxerInterface[] winners = new BoxerInterface[fighter1.length];
        for(int k = 0; k < fighter1.length; k++) {
            if( fighter1[k]!=null || fighter2[k]!=null ) {
                Fight match = new Fight(fighter1[k], fighter2[k]);
                int winner = match.fight();
                if (winner == 1)
                    winners[k] = fighter1[k];
                else if (winner == 2)
                    winners[k] = fighter2[k];
                else // unentschieden
                    winners[k] = fighter1[k];
            }
        }

        // Die Runde ist zu Ende
        return winners;
    }
}
