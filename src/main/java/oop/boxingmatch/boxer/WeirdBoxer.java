/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oop.boxingmatch.boxer;

/**
 * WeirdBoxer will always behave unexpected - it chooses the movements by random
 */
public class WeirdBoxer implements BoxerInterface {
    @Override
    public String name() {
        return "RudiRatlos";
    }

    @Override
    public String imageFileName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getMovement(int round) {
        return (int) Math.round(Math.random() * 2.0);
    }

    @Override
    public int getPosition(int round) {
        return (int) Math.round(Math.random() * 3.0);
    }

}
