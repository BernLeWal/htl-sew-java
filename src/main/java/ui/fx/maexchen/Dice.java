/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.fx.maexchen;

import java.util.Random;

/**
 * @author martina
 */
public class Dice {
    public Random r = new Random();

    public int augenzahl() {
        int z = r.nextInt(6) + 1;
        System.out.println("Augenzahl:" + z);
        return z;
    }

}
