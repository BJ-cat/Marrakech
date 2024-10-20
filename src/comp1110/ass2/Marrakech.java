package comp1110.ass2;

import java.util.Random;


public class Marrakech {
    public Assam assam;
    public Player[] players;
    public Tile[][] tile;
    public Rug rug;
    private boolean[][] visited;

    /*
     * Author: Bei Jin
     * Description: This is a Java program about Find P in whole string codes.
     */
    //find all the P
    private int findP(String inputString) {
        int p = 0;
        //use for() to find each codes Start with P
        for (int i = 0; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            if (currentChar == 'P') {
                p += 1;
            }
        }
        return p;
    }

    /*
     * Author: Bei Jin
     * Description: This is a Java program about string representation of GameState .
     */
    public Marrakech(String state) {

        int n = this.findP(state); //number of all player
        this.players = new Player[n];
        //get the information of players from string codes
        for (int i = 0; i < n; i++) {
            players[i] = new Player(state.substring(i * 8, i * 8 + 8));
        }

        //Assam string represent
        int AssamIndex = state.indexOf("A");
        //get the information of Assam from string codes
        this.assam = new Assam(state.substring(AssamIndex, AssamIndex + 4));


        //the abbreviated rug string
        //since board size is 7*7
        this.tile = new Tile[7][7];
        this.visited = new boolean[7][7];
        int TileIndex = state.indexOf("B");

        String tileString = state.substring(TileIndex + 1, TileIndex + 49 * 3 + 1);

        //get the information of Rug from string codes
        for (int i = 0; i < tileString.length() / 3; i++) {
            var s = tileString.substring(i * 3, i * 3 + 3);
            this.tile[i / 7][i % 7] = new Tile(s, i / 7, i % 7);
        }


    }


    /**
     * @param gameString A String representing the current state of the game as per the README
     * @param rug        A String representing the rug you are checking
     * @return true if the rug is valid, and false otherwise.
     */

    /*
     * Author: Arnav Kaher and Bei Jin
     * Description: Program to check if the gameString being passed is valid
     */
    //the method to string
    public static boolean isRugValid(String gameString, String rug) {

        Marrakech marrakech = new Marrakech(gameString);
        Rug rugNew = new Rug(rug);
        return marrakech.isRugValid(rugNew);


    }

    //the method to object
    public boolean isRugValid(Rug rugNew) {
        Marrakech marrakech = this;

        Tile[][] tile = marrakech.tile;
        int rugX1 = rugNew.getX1();
        int rugY1 = rugNew.getY1();
        int rugX2 = rugNew.getX2();
        int rugY2 = rugNew.getY2();


        //check the rug's color
        if (rugNew.getColor() != 'c' && rugNew.getColor() != 'y' && rugNew.getColor() != 'r' && rugNew.getColor() != 'p')
            return false;

        if ((rugX1 >= 7 || rugX1 < 0)
                || (rugX2 >= 7 || rugX2 < 0)
                || (rugY1 >= 7 || rugY1 < 0)
                || (rugY2 >= 7 || rugY2 < 0))
            return false;

        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 7; j++) {
                if (tile[i][j].getColor() == 'n') continue;
                else if ((rugNew.getColor() == tile[i][j].getColor())
                        && (rugNew.getId() == tile[i][j].getId()))
                    return false;

            }

        return true;


    }


    /**
     * Roll the special Marrakech die and return the result.
     * Note that the die in Marrakech is not a regular 6-sided die, since there
     * are no faces that show 5 or 6, and instead 2 faces that show 2 and 3. That
     * is, of the 6 faces
     * - One shows 1
     * - Two show 2
     * - Two show 3
     * - One shows 4
     * As such, in order to get full marks for this task, you will need to implement
     * a die where the distribution of results from 1 to 4 is not even, with a 2 or 3
     * being twice as likely to be returned as a 1 or 4.
     *
     * @return The result of the roll of the die meeting the criteria above
     */

    /**
     * Author: Arnav kaher
     * Description: Program used for rolling the special Marrakech dice in the game
     */
    public static int rollDie() {
        int dice[] = {1, 2, 2, 3, 3, 4};
        Random random = new Random();
        int randomDiceNumber = random.nextInt(dice.length);
        int diceNumber = dice[randomDiceNumber];

        return diceNumber;
    }

    /**
     * Determine whether a game of Marrakech is over
     * Recall from the README that a game of Marrakech is over if a Player is about to enter the rotation phase of their
     * turn, but no longer has any rugs. Note that we do not encode in the game state String whose turn it is, so you
     * will have to think about how to use the information we do encode to determine whether a game is over or not.
     *
     * @param currentGame A String representation of the current state of the game.
     * @return true if the game is over, or false otherwise.
     */

    /**
     * Author: Madison Wright
     * Description: Program used for testing if the game is over
     */
    //the method to string
    public static boolean isGameOver(String currentGame) {
        Marrakech marrakech = new Marrakech(currentGame);
        Player[] players = marrakech.players;
        return marrakech.isGameOver();
    }
    //the method to object
    public boolean isGameOver() {
        Marrakech marrakech = this;
        Player[] players = marrakech.players;

        for (int i = 0; i < 4; i++) {
            if(players[i].isInGame() && players[i].getRugsRemaining() != 0 ){
                return false;
            }
        }
        return true;
    }



    /**
     * Implement Assam's rotation.
     * Recall that Assam may only be rotated left or right, or left alone -- he cannot be rotated a full 180 degrees.
     * For example, if he is currently facing North (towards the top of the board), then he could be rotated to face
     * East or West, but not South. Assam can also only be rotated in 90 degree increments.
     * If the requested rotation is illegal, you should return Assam's current state unchanged.
     *
     * @param currentAssam A String representing Assam's current state
     * @param rotation     The requested rotation, in degrees. This degree reading is relative to the direction Assam
     *                     is currently facing, so a value of 0 for this argument will keep Assam facing in his
     *                     current orientation, 90 would be turning him to the right, etc.
     * @return A String representing Assam's state after the rotation, or the input currentAssam if the requested
     * rotation is illegal.
     */

    /**
     * Author: Arnav kaher
     * Description: Program for rotating Assam in the game
     */
    public static String rotateAssam(String currentAssam, int rotation) {
        // Parse the gameState to extract relevant information
        Assam assam = new Assam(currentAssam);

        // Parse the rug placement
        int orientation = rotation;
        return assam.rotateAssam(orientation);

    }


    /**
     * @param gameState A game string representing the current state of the game
     * @param rug       A rug string representing the candidate rug which you must check the validity of.
     * @return true if the placement is valid, and false otherwise.
     */

    /*
     * Author: Bei Jin
     */
    //the method to string
    public static boolean isPlacementValid(String gameState, String rug) {
        // Parse the gameState to extract relevant information
        Marrakech marrakech = new Marrakech(gameState);
        // Parse the rug placement
        Rug rugNew = new Rug(rug);
        return marrakech.isPlacementValid(rugNew);
    }

    //the method to object
    public boolean isPlacementValid(Rug rugNew) {
        Marrakech marrakech = this;
        // Parse the gameState to extract relevant information
        //get assam position
        Assam assam = marrakech.assam;
        int assamX = assam.getX();
        int assamY = assam.getY();

        // Parse the rug placement
        Tile[][] tile = marrakech.tile;
        int rugX1 = rugNew.getX1();
        int rugY1 = rugNew.getY1();
        int rugX2 = rugNew.getX2();
        int rugY2 = rugNew.getY2();

        // Check if the rug placement is adjacent to Assam (not counting diagonals)
        boolean isAdjacent = (Math.abs(assamX - rugX1) == 1 && assamY == rugY1) ||
                (assamX == rugX1 && Math.abs(assamY - rugY1) == 1) ||
                (Math.abs(assamX - rugX2) == 1 && assamY == rugY2) ||
                (assamX == rugX2 && Math.abs(assamY - rugY2) == 1);

        boolean isCover = (assamX == rugX1 && assamY == rugY1) || (assamX == rugX2 && assamY == rugY2);


        if (!isAdjacent) {
            return false;
        }
        if (isCover) {
            return false;
        }

        //for the special case n00
        if (tile[rugX1][rugY1].getColor() != 'n' || tile[rugX2][rugY2].getColor() != 'n') {
            //check if there is a rug have same ID and color with another on the board
            if (tile[rugX1][rugY1].getId() == tile[rugX2][rugY2].getId() &&
                    tile[rugX1][rugY1].getColor() == tile[rugX2][rugY2].getColor()) {
                return false;
            }
        }
        return true;
    }


    /**
     * @param gameString A String representation of the current state of the game.
     * @return The amount of payment due, as an integer.
     */

    /*
     * Author: Bei Jin
     */
    //the method to string
    public static int getPaymentAmount(String gameString) {
        // Initialize the game state using the provided game string.
        Marrakech marrakech = new Marrakech(gameString);

        // An array to keep track of visited tiles during DFS traversal.
        marrakech.visited = new boolean[7][7];
        return marrakech.getPaymentAmount();


    }

    //the method to object
    public int getPaymentAmount() {
        // Initialize the game state using the provided game string.
        Marrakech marrakech = this;

        // An array to keep track of visited tiles during DFS traversal.
        marrakech.visited = new boolean[7][7];

        // Retrieve Assam's position and the color of the tile he is on.
        Assam assam = marrakech.assam;
        int initialX = assam.getX();
        int initialY = assam.getY();
        char color = marrakech.tile[initialX][initialY].getColor();

        // Start a DFS traversal from Assam's position to find connected tiles of the same color.
        return marrakech.dfs(initialX, initialY, color);
    }

    //the method of Depth-First-Search
    private int dfs(int x, int y, char color) {
        // Base cases: Check if the current tile is out of bounds, already visited, or has a different color.
        if (x < 0 || x >= 7 || y < 0 || y >= 7 || visited[x][y] || tile[x][y].getColor() != color) {
            return 0;
        }
        // Mark the current tile as visited.
        visited[x][y] = true;
        int count = 1;  // Count the current tile.

        // Recursively explore all adjacent tiles (right, left, down, up).
        count += dfs(x + 1, y, color);
        count += dfs(x - 1, y, color);
        count += dfs(x, y + 1, color);
        count += dfs(x, y - 1, color);

        return count;  // Return the total count of connected tiles.
    }

    /**
     * Determine the winner of a game of Marrakech.
     * For this task, you will be provided with a game state string and have to return a char representing the colour
     * of the winner of the game. So for example if the cyan player is the winner, then you return 'c', if the red
     * player is the winner return 'r', etc...
     * If the game is not yet over, then you should return 'n'.
     * If the game is over, but is a tie, then you should return 't'.
     * Recall that a player's total score is the sum of their number of dirhams and the number of squares showing on the
     * board that are of their colour, and that a player who is out of the game cannot win. If multiple players have the
     * same total score, the player with the largest number of dirhams wins. If multiple players have the same total
     * score and number of dirhams, then the game is a tie.
     *
     * @param gameState A String representation of the current state of the game
     * @return A char representing the winner of the game as described above.
     */

    /**
     * Author: Madison Wright and Arnav Kaher
     * Description: Program used for testing if the game is over
     */
    //the method to string
    public static char getWinner(String gameState) {
        Marrakech marrakech = new Marrakech(gameState);
        return marrakech.getWinner();
    }

    //the method to object
    public char getWinner() {
        Marrakech marrakech = this;
        Player[] players = marrakech.players;

        // Initialize highScore and winner to keep track of who is winning
        int highScore = 0;
        char winner = 'n';

        // Puts an index on every player in the list
        for (Player player : players) {

            // Calculate the score for each player using the helper function
            int score = calculatePlayerScore(player);

            // If a player's score is higher than the highScore
            if (score > highScore) {
                // Set the current player as the potential winner and update the highScore
                highScore = score;
                winner = player.getColor();
            }

            // If player's score is equal to high score (that is two players have the same highScore)
            else if (score == highScore) {
                // Compare the number of dirhams each player has
                int dirhams = player.getDirhams();
                int winnerDirhams = marrakech.players[0].getDirhams();

                // Player with higher dirahams is set as the potential winner
                if (dirhams > winnerDirhams) {
                    winner = player.getColor();
                } else if (dirhams == winnerDirhams) {
                    // If two players have the same score and dirhams, it's a tie
                    winner = 't';
                }
            }
        }
        return winner;
    }


    // Helper method to calculate total score of a Player
    public int calculatePlayerScore(Player player) {
        // Get amount of dirhams a player has
        int dirhams = player.getDirhams();

        // Initialize rugs to 0 to keep track of number of rugs a player has on the board
        int playerRugs = 0;

        Marrakech marrakech = this;
        Tile[][] board = marrakech.tile;

        // Find the colour of the player
        char plyrColor = player.getColor();

        // Iterate through all rows and all titles on the board
        for (Tile[] row : board) {
            for (Tile tile : row) {

                // Tile colour matches the colour of the Player
                if (tile.getColor() == plyrColor) {
                    // Add one to number of rugs the player has on the board
                    playerRugs++;
                }
            }
        }

        // Calculating total score
        return dirhams + playerRugs;
    }

    /**
     * Implement Assam's movement.
     * Assam moves a number of squares equal to the die result, provided to you by the argument dieResult. Assam moves
     * in the direction he is currently facing. If part of Assam's movement results in him leaving the board, he moves
     * according to the tracks diagrammed in the assignment README, which should be studied carefully before attempting
     * this task. For this task, you are not required to do any checking that the die result is sensible, nor whether
     * the current Assam string is sensible either -- you may assume that both of these are valid.
     *
     * @param currentAssam A string representation of Assam's current state.
     * @param dieResult    The result of the die, which determines the number of squares Assam will move.
     * @return A String representing Assam's state after the movement.
     */

    /**
     * Author: Arnav kaher
     * Description: Program for moving Assam on the game board
     */
    //the method to string
    public static String moveAssam(String currentAssam, int dieResult) {
        Assam assam = new Assam(currentAssam);
        return assam.moveAssam(dieResult);
    }


    /**
     * @param currentGame A String representation of the current state of the game.
     * @param rug         A String representation of the rug that is to be placed.
     * @return A new game string representing the game following the successful placement of this rug if it is valid,
     * or the input currentGame unchanged otherwise.
     */

    /*
     * Author: Bei Jin
     */
    //the method to string
    public static String makePlacement(String currentGame, String rug) {
        // Parse the gameState to extract relevant information
        Marrakech marrakech = new Marrakech(currentGame);

        // Parse the rug placement
        Rug rugNew = new Rug(rug);
        return marrakech.makePlacement(rugNew);
    }

    //the method to object
    public String makePlacement(Rug rugNew) {
        Marrakech marrakech = this;

        if (!marrakech.isRugValid(rugNew)) {
            return marrakech.toString();
        }
        if (!marrakech.isPlacementValid(rugNew)) {
            return marrakech.toString(); // Return unchanged game state if placement is invalid
        }

        Tile[][] tiles = marrakech.tile;
        Player[] player = marrakech.players;

        int rugX1 = rugNew.getX1();
        int rugY1 = rugNew.getY1();
        int rugX2 = rugNew.getX2();
        int rugY2 = rugNew.getY2();
        char rugColor = rugNew.getColor();
        int rugId = rugNew.getId();

        // Update the tiles with the new rug information
        tiles[rugX1][rugY1] = new Tile(rugColor, rugId, new IntPair(rugX1, rugY1));
        tiles[rugX2][rugY2] = new Tile(rugColor, rugId, new IntPair(rugX2, rugY2));
        for (Player players : player) {
            if (players.getColor() == rugNew.getColor()) {
                players.rugsRemaining -= 1;
            }
        }
        return marrakech.toString();

    }

    @Override
    public String toString() {
        Marrakech marrakech = this;
        Tile[][] tiles = marrakech.tile;

        // Generate the updated game state string
        StringBuilder updatedGameState = new StringBuilder();

        // Players info
        for (Player players : marrakech.players) {
            updatedGameState.append(players.toString());
        }

        // Assam info
        updatedGameState.append(marrakech.assam.toString());

        // Board info
        updatedGameState.append('B');
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                updatedGameState.append(tiles[i][j].getColor());
                updatedGameState.append(String.format("%02d", tiles[i][j].getId()));
            }
        }
        return updatedGameState.toString();
    }
}

