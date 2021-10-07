package oop.boxingmatch.boxer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShyBoxerTest {

    private BoxerInterface shyBoxer;

    @BeforeEach
    void setup() {
        // Arrange
        shyBoxer = new ShyBoxer();
    }

    @Test
    void name_shouldBeFeigling() {
        // Arrange
        BoxerInterface shyBoxer = new ShyBoxer();

        // Act
        var actualName = shyBoxer.name();

        // Assert
        assertEquals( "Feigling", actualName, "The name of the boxer is different!" );
    }

    @Test
    void getMovement_shouldAlwaysBe() {
        // Arrange - is in setup()
        //BoxerInterface shyBoxer = new ShyBoxer();

        for( int i = 0; i < 10; i++ ) {
            // Act
            var actualMovement = shyBoxer.getMovement(i);

            // Assert
            assertEquals(Movements.BLOCK.ordinal(), actualMovement);
        }
    }

    @Test
    @DisplayName("Always acts on position BODY")
    void getPosition_shouldAlwysBeBody() {
        // Arrange - is in setup()
        //BoxerInterface shyBoxer = new ShyBoxer();

        for( int i = 0; i < 10; i++ ) {
            // Act
            var actualPosition = shyBoxer.getPosition(i);

            // Assert
            assertEquals(Positions.BODY.ordinal(), actualPosition);
        }
    }
}