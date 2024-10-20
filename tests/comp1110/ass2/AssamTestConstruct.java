package comp1110.ass2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AssamTestConstruct {

    @Test
    public void position() {
        Assam assam = new Assam("A01N");
        assertEquals(0, assam.getX());
        assertEquals(1, assam.getY());

        assam = new Assam("A33N");
        assertEquals(3, assam.getX());
        assertEquals(3, assam.getY());


        assam = new Assam("A66N");
        assertEquals(6, assam.getX());
        assertEquals(6, assam.getY());

        // Check if x and y are within the range (0, 6)
        assertTrue(assam.getX() >= 0 && assam.getX() <= 6);
        assertTrue(assam.getY() >= 0 && assam.getY() <= 6);

        // Check if orientation is one of the four valid directions
        assertTrue("NSEW".indexOf(assam.getOrientation()) >= 0);
    }

    @Test
    public void Orientation() {
        Assam assam = new Assam("A55N");
        assertEquals('N', assam.getOrientation());
        assam = new Assam("A55W");
        assertEquals('W', assam.getOrientation());
        assam = new Assam("A55S");
        assertEquals('S', assam.getOrientation());
        assam = new Assam("A55E");
        assertEquals('E', assam.getOrientation());


        // Check if orientation is one of the four valid directions
        assertTrue("NSEW".indexOf(assam.getOrientation()) >= 0);
    }


}
