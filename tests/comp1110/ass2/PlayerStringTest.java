package comp1110.ass2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerStringTest{
    // Author: Madison Wright U7314519
    @Test
    public void testPlayerStringParsing() {
        // Test parsing a player string for a red player who is in the game
        String playerString = "Pr00803i";
        Player player = new Player(playerString);

        assertEquals('r', player.getColor());
        assertEquals(8, player.getDirhams());
        assertEquals(3, player.getRugsRemaining());
        assertTrue(player.isInGame());
    }

    @Test
    public void testPlayerStringParsingYellow() {
        // Test parsing a player string for a yellow player who is out of the game
        String playerString = "Py01302o";
        Player player = new Player(playerString);

        assertEquals('y', player.getColor());
        assertEquals(13, player.getDirhams());
        assertEquals(2, player.getRugsRemaining());
        assertFalse(player.isInGame());
    }

    @Test
    public void testPlayerStringParsingCyan() {
        // Test parsing a player string for a cyan player who is in the game
        String playerString = "Pc00601i";
        Player player = new Player(playerString);
        

        // Check the parsed values
        assertEquals('c', player.getColor());
        assertEquals(6, player.getDirhams());
        assertEquals(1, player.getRugsRemaining());
        assertTrue(player.isInGame());
    }

    @Test
    public void testPlayerStringParsingPurple() {
        // Test parsing a player string for a purple player who is out of the game
        String playerString = "Pp00205o";
        Player player = new Player(playerString);

        assertEquals('p', player.getColor());
        assertEquals(2, player.getDirhams());
        assertEquals(5, player.getRugsRemaining());
        assertFalse(player.isInGame());
    }

}
