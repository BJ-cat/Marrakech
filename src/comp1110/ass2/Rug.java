package comp1110.ass2;

/**
 * Author: Bei Jin
 * Description: This is a Java program about string representation of Rug.
 */


public class Rug {

    private final char Color;
    private final int id;
    private int x1;
    private int y1;
    private int x2;
    private int y2;



    public Rug(char Color, int id, int x1,int y1,int x2,int y2) {
        this.Color = Color;
        this.id = id;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

    }

    //create Rug string representation
    public Rug(String string) {
        this.Color = string.charAt(0);
        this.id = Integer.parseInt(string.substring(1, 3));
        this.x1 = Integer.parseInt(string.substring(3, 4));
        this.y1 = Integer.parseInt(string.substring(4, 5));
        this.x2 = Integer.parseInt(string.substring(5, 6));
        this.y2 = Integer.parseInt(string.substring(6, 7));

    }



    public char getColor() { return this.Color; }
    public int getId(){ return this.id;}
    public int getX1(){ return x1;}
    public int getY1(){ return y1; }
    public int getX2(){ return x2; }
    public int getY2(){ return y2; }


    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();

        r.append(this.Color);
        r.append(String.format("%02d", id));
        r.append(this.x1);
        r.append(this.y1);
        r.append(this.x2);
        r.append(this.y2);
        return r.toString();
    }


}












