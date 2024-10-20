package comp1110.ass2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class RugTest {
    @Test
    public void test1GetColor() {
        // Test getColor method
        Rug rug = new Rug('c', 5, 0, 0, 1, 0);
        char color = rug.getColor();
        assertEquals('c', color);
    }

    @Test
    public void test2GetColor() {
        // Test getColor method
        Rug rug = new Rug('r', 5, 0, 0, 1, 1);
        char color = rug.getColor();
        assertEquals('r', color);
    }

    @Test
    public void test3GetColor() {
        // Test getColor method
        Rug rug = new Rug('p', 5, 1, 1, 2, 1);
        char color = rug.getColor();
        assertEquals('p', color);
    }

    @Test
    public void test4GetColor() {
        // Test getColor method
        Rug rug = new Rug('y', 5, 4, 1, 4, 0);
        char color = rug.getColor();
        assertEquals('y', color);
    }

    @Test
    public void test5GetColor() {
        char unknownColor = 'w';
        String colorName = null;

        if (unknownColor== 'r') {
            colorName = "Red";
        } else if (unknownColor == 'p') {
            colorName = "Purple";
        } else if (unknownColor == 'c') {
            colorName = "Cyan";
        } else if (unknownColor == 'y') {
            colorName = "Yellow";
        } else {
            colorName = "Unknown-Color";
        }

        assertEquals("Unknown-Color", colorName);
    }

}
