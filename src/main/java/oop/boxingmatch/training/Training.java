/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oop.boxingmatch.training;

import oop.boxingmatch.boxer.BoxerInterface;
import oop.boxingmatch.boxer.MyBoxer;

/**
 * Training uses a specific boxer instance and trains it,
 * that means it asks for the movements and prints them on the console.
 */
public class Training {
    public static void main(String[] args) {
        BoxerInterface boxer = new MyBoxer(); //new ShyBoxer();
        System.out.println("Trainiere Boxer: " + boxer.name());

        for (int round = 1; round <= 10; round++) {
            System.out.print(round + ".Runde: ");
            int movement = boxer.getMovement(round);
            System.out.print((movement == 0) ? "verteidigt " : "schlägt ");
            int position = boxer.getPosition(round);
            System.out.print((position == 0) ? "Kopf" : ((position == 1) ? "Körper" : "Beine"));
            System.out.println(".");
        }
    }
}
