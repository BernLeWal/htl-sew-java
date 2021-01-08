/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop.boxingmatch;

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
        Training.main(args);
        Fight.main(args);
    }
    
}
