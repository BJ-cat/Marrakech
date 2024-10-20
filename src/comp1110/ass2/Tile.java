package comp1110.ass2;

/**
 * Author: Bei Jin
 * Description: This is a Java program about string representation of Tile(for Abbreviated Rug).
 */

public class Tile {
    private char Color;
    private int id;
    private IntPair Coordinates;


    public Tile(char Color, int id, IntPair Coordinates) {
        this.Color = Color;
        this.id = id;
        this.Coordinates = Coordinates;

    }

    //create Tile string representation(Abbreviated Rug Strings)
    public Tile(String string, int x0, int y0) {
        this.Color = string.charAt(0);
        this.id = Integer.parseInt(string.substring(1, 3));
        this.Coordinates = new IntPair(x0, y0);
    }

    //get these method to use in Marrakech
    public char getColor() { return this.Color; }
    public IntPair getCoordinates(){ return Coordinates; }
    public int getId(){ return this.id; }


}

