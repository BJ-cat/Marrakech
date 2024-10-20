package comp1110.ass2;

import javafx.scene.paint.Color;

/**
 * Author: Bei Jin and Madison
 * Description: This is a Java program about string representation of Player.
 * Madison give the basic information without string representation
 * Bei refined this part.
 */

public class Player {
    // char -> Color
    // final
    private final char color;

    // should equal 30
    private int dirhams;

    // should equal 15
    public int rugsRemaining;
    public boolean inGame;

    // need score
    public Player(char color, int dirhams, int rugsRemaining, boolean inGame) {

        this.color = color;
        this.dirhams = dirhams;
        this.rugsRemaining = rugsRemaining;
        this.inGame = inGame;
        // above initialises default values for these variables
    }

    public Player(String string) {
        this.color = string.charAt(1);//the first Player string code means
        //Player string code from the second to 4th means
        this.dirhams= Integer.parseInt(string.substring(2,5));
        //Player string code from the 5th to 6th means
        this.rugsRemaining = Integer.parseInt(string.substring(5,7));
        this.inGame = string.charAt(7)=='i';//the 7th Player string code means
    }


    public char getColor() {
        return color;
    }

    public int getDirhams() {
        return dirhams;
    }

    public int getRugsRemaining() {
        return rugsRemaining;
    }

    public void reductDirhams(int amount) {
        if (amount <= dirhams) {
            dirhams -= amount;
        } else {
            inGame=false;
            dirhams = 0;
        }
    }

    public void addDirhams(int amount) {
        dirhams += amount;
    }

    public boolean isInGame() {
        return (rugsRemaining != 0) ? inGame : false;
    }

    @Override
    public String toString() {
        StringBuilder p = new StringBuilder();
        p.append('P');
        p.append(this.color);
        p.append(String.format("%03d", dirhams));
        p.append(String.format("%02d", rugsRemaining));
        p.append(inGame ? "i" : "o");
        return p.toString();
    }

}