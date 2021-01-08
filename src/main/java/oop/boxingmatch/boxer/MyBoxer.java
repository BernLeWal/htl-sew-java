package oop.boxingmatch.boxer;

/**
 * MyBoxer implements my own specific movements
 */
public class MyBoxer implements BoxerInterface {
    //R.:1.    2.     3.     4.     5.     6.     7.     8.     9.     10.
    public static final int[] MOVEMENTS = {
            HIT, BLOCK, HIT, HIT, BLOCK, BLOCK, HIT, HIT, BLOCK, BLOCK
    };

    public static final int[] POSITIONS = {
            HEAD, BODY, HEAD, FOOT, BODY, HEAD, HEAD, FOOT, FOOT, BODY
    };

    @Override
    public String name() {
        return "Sheldon";
    }

    @Override
    public String imageFileName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getMovement(int round) {
        return MOVEMENTS[round - 1];
    }

    @Override
    public int getPosition(int round) {
        return POSITIONS[round - 1];
    }


    public void init() {
        // Deklaration eines Arrays
        int[] movements;
        // Erzeugen des Containers mit genau 10 Elementen
        movements = new int[10];

        // Elemente des Arrays gezielt befüllen:
        movements[0] = 0; // BLOCK
        movements[1] = 0; // BLOCK
        movements[2] = 1; // HIT
    }
}
