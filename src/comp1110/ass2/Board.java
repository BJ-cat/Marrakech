package comp1110.ass2;

/**
 * Author: Bei Jin
 * Description: This is a Java program about Board size.
 */

public class Board {
    public final static int BOARD_WIDTH = 7;

    // The height of the board (top to bottom)
    public final static int BOARD_HEIGHT = 7;

    // The matrix of tiles representing the puzzle board
    // For boardMatrix[x][y]:

    private Rug[][] board;

    public Board() {
        // Initialize the board with null rugs
        board = new Rug[7][7];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = null;
            }
        }
    }

    public void placeRug(Rug rug, int x, int y) {
        // Place a rug on the board at coordinates (x, y)
        // You'll need to implement bounds checking
        board[x][y] = rug;
    }

    public Rug getRug(int x, int y) {
        // Get the rug at coordinates (x, y)
        return board[x][y];
    }

}
