package oop.boxingmatch.boxer;

/**
 * ShyBoxer will always do the blocking move and never attacks.
 *
 * In the implementation of this class the concept of enums is introduced.
 */
public class ShyBoxer implements BoxerInterface {

    @Override
    public String name() {
        return "Feigling";
    }

    @Override
    public String imageFileName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getMovement(int round) {
        return BLOCK;
    }

    public Movements getMovement2(int round) {
        return Movements.HIT;
    }

    @Override
    public int getPosition(int round) {
        return BODY;
    }
}
