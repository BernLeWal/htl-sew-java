package oop.boxingmatch.boxer;

/**
 * The interface all boxers have to implement
 * <p>
 * Jeder Boxer hat die folgenden Aktionsmöglichkeiten:
 * -	Der Boxer kann schlagen („hit“) oder verteidigen („block“)
 * -	Eine der beiden Möglichkeiten kann er pro Runde an einer der folgenden Positionen durchführen: Kopf („head“), Körper („body“) oder Beine („foot“).
 */
public interface BoxerInterface {
    /**
     * @return The full-name of the boxer.
     */
    String name();


    /**
     * @return The file-name of the image of the boxer.
     * This is shown later in the apps with graphical output.
     */
    String imageFileName();


    /**
     * Returns the kind of movement the boxer performs at a specific round.
     *
     * @param round the number of the current round
     * @return 0..block / 1..hit
     */
    int getMovement(int round);

    // Konstante für getMovement
    final int BLOCK = 0;    // verteidigen
    final int HIT = 1;      // schlagen


    /**
     * Returns the position of the movement during a specific round.
     *
     * @param round the number of the current round
     * @return 0..head / 1..body / 2..foot
     */
    int getPosition(int round);

    // Konstante für getPosition
    final int HEAD = 0;     // Kopf
    final int BODY = 1;     // Körper
    final int FOOT = 2;     // Beine
}
