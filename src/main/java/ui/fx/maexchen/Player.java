/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.fx.maexchen;

import java.util.Random;

/**
 *
 * @author martina
 */
public class Player {
    private int points=0;

    public int getPoints() {
        return points;
    }
    
    public void dice() {
        //Dice d = new Dice();
        //int a1 = d.augenzahl();
        //int a2 = d.augenzahl();
        Random r = new Random();
        int a1 = r.nextInt(6) + 1;
        int a2 = r.nextInt(6) + 1;
        System.out.println("Augenzahl 1: " + a1);
        System.out.println("Augenzahl 2: " + a2);
        this.points = this.points + calcPoints(a1,a2);
    }
    
    public int calcPoints(int a1, int a2) {
    
        if (a1==1 && a2==2) { //Maexchen
            return 1000;
        }
        else if (a1 ==a2) {  // Pasch
            return (a1+a2)*100;
        }
        else {
            if (a1>a2) {
                return a1*10+a2;
            }
            else return a2*10+a1;
        }
    }
}
