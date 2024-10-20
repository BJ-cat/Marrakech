package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

import comp1110.ass2.Marrakech;


/**
 * task 7(the board info, the initial players info and present player state, the assam initial state)
 * task 15(the action of placeRug on board and roll dice part)
 * Author: Bei Jin
 */

/**
 * incomplete part:
 * assam move according to dice, make turn automatic when face to wall;
 * transfer the game state to gameString, it will be change with game state, for later judgement;
 * then check the valid of placement, refuse incorrect action with Pop-ups;
 * the playerInfo(rug, money) change with game;
 * the backcolor of player info will change to black when the player is over;
 */

/*
 * Author: Bei Jin
 * Description: basic board
 */

public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    // Height and width of each tile
    private static final double Tile_Size = 80;

    // Pixel gap between the grey rectangles that indicates where the tiles are on the board
    private static final double BOARD_TILE_SHADOW_GAP = 3;

    // how much the blue background extends past the tiles
    private static final int BOARD_BORDER = 3;

    // The start of the board in the x-direction (ie: x = 0)
    private static final double START_X = 70.0;

    // The start of the board in the y-direction (ie: y = 0)
    private static final double START_Y = 50.0;
    private ImageView AssamPieceView;
    private Scene startScene;
    private Label diceResult;
    private ArrayList<Rectangle> playerRectangles = new ArrayList<>();
    private int currentPlayer = 0;//for count the present player
    private IntPair startPoint = null;
    private IntPair endPoint = null;
    private final int[][] boardState = new int[Board.BOARD_WIDTH][Board.BOARD_HEIGHT];

    private String gameString = "Pc03015iPy03015iPr03015iPp03015iA33NBn00n00n00n00n00n00n00n00n00n00n00" +
            "n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00" +
            "n00n00n00n00n00n00n00n00";
    private Marrakech marrakech;


    public Game() {
        Image gameImage = new Image("comp1110/ass2/gui/assets/Marrakech.png");
        ImageView gameImageView = new ImageView(gameImage);
        gameImageView.setFitWidth(1200);
        gameImageView.setFitHeight(700);

        createChessBoard();
        root.getChildren().add(gameImageView);  // Add the background image to the root
        root.getChildren().add(board);
        assam();
        displayPlayerInfo();
        rollDice();
        marrakech = new Marrakech(gameString);

    }

    // Group containing all the assets corresponding to pieces of the board.
    private final Group board = new Group();

    private final double boardWidth = Board.BOARD_WIDTH * Tile_Size;
    private final double boardHeight = Board.BOARD_HEIGHT * Tile_Size;


    //use tile to draw the board
    private void createChessBoard() {
        Rectangle boardBack = new Rectangle(
                START_X - BOARD_BORDER + (BOARD_TILE_SHADOW_GAP / 2),
                START_Y - BOARD_BORDER + (BOARD_TILE_SHADOW_GAP / 2),
                boardWidth + (2 * BOARD_BORDER) - BOARD_TILE_SHADOW_GAP,
                boardHeight + (2 * BOARD_BORDER) - BOARD_TILE_SHADOW_GAP);
        boardBack.setFill(Color.TAN);
        boardBack.setArcHeight(30.0d);
        boardBack.setArcWidth(30.0d);
        // adding the rectangle to the board group
        board.getChildren().add(boardBack);

        for (int x = 0; x < Board.BOARD_WIDTH; x++) {
            for (int y = 0; y < Board.BOARD_HEIGHT; y++) {
                Rectangle tileShadow = new Rectangle(
                        START_X + (x * Tile_Size) + (BOARD_TILE_SHADOW_GAP / 2),
                        START_Y + (y * Tile_Size) + (BOARD_TILE_SHADOW_GAP / 2),
                        Tile_Size - BOARD_TILE_SHADOW_GAP,
                        Tile_Size - BOARD_TILE_SHADOW_GAP);
                /*
                 * Author: Bei Jin */
                // Set the background color to transparent as beginning
                tileShadow.setFill(Color.TRANSPARENT);
                tileShadow.setStrokeWidth(3);
                tileShadow.setStroke(Color.GREY);
                tileShadow.setOpacity(0.5);
                board.getChildren().add(tileShadow);
                /*
                 * Author: Bei Jin */
                //for action of placeRug
                tileShadow.setOnMousePressed(event -> {
                    if (!marrakech.players[currentPlayer].isInGame()) {
                        return; // Immediately exit if the current player is not in the game
                    }
                    int x1 = (int) ((event.getX() - START_X) / Tile_Size);
                    int y1 = (int) ((event.getY() - START_Y) / Tile_Size);
                    startPoint = new IntPair(x1, y1);
                });

                tileShadow.setOnMouseReleased(event -> {
                    Marrakech marrakech = new Marrakech(gameString);
                    Player[] player = marrakech.players;
                    int x2 = (int) ((event.getX() - START_X) / Tile_Size);
                    int y2 = (int) ((event.getY() - START_Y) / Tile_Size);
                    endPoint = new IntPair(x2, y2);
                    if (isValidPlacement(startPoint, endPoint)) {
                        Rug rug = new Rug(
                                getPlayerId(currentPlayer),
                                marrakech.players[currentPlayer].rugsRemaining,
                                startPoint.getX(),
                                startPoint.getY(),
                                endPoint.getX(),
                                endPoint.getY());

                        if (marrakech.isPlacementValid(rug)) {
                            placeRug(startPoint, endPoint, getPlayerColor(currentPlayer));
                            gameString = marrakech.makePlacement(rug);
                            updatePlayerString(player[currentPlayer]);
                            updatePlayerInfoLabel(player[currentPlayer],
                                    playerInfoLabels.get(currentPlayer));
                        } else {
                            showInvalidPlacementAlert();
                        }

                    }
                });
            }
        }

    }
    /*
     * Author: Bei Jin */
    private void showInvalidPlacementAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Invalid Placement");
        alert.showAndWait();
    }
    /*
     * Author: Bei Jin */
    //for judge the valid of placement
    private boolean isValidPlacement(IntPair start, IntPair end) {
        if (start == null || end == null) return false;
        // make sure rug is connected
        return ((start.getX() == end.getX() && Math.abs(start.getY() - end.getY()) == 1)
                || (start.getY() == end.getY() && Math.abs(start.getX() - end.getX()) == 1));
    }

    /*
     * Author: Bei Jin */
    //placement
    private void placeRug(IntPair start, IntPair end, Color color) {
        Rectangle rug = new Rectangle(Tile_Size, Tile_Size * 2, color);
        if (start.getX() == end.getX()) {
            //for x line action
            rug.setX(START_X + start.getX() * Tile_Size);
            rug.setY(START_Y + Math.min(start.getY(), end.getY()) * Tile_Size);
        } else {
            //for y line action
            rug.setWidth(Tile_Size * 2);
            rug.setHeight(Tile_Size);
            rug.setX(START_X + Math.min(start.getX(), end.getX()) * Tile_Size);
            rug.setY(START_Y + start.getY() * Tile_Size);
        }
        rug.setMouseTransparent(true);  // This ensures the rug doesn't block mouse events.
        board.getChildren().add(rug);

    }

    /*
     * Author: Bei Jin */
    //the color of rug changes with players
    private Color getPlayerColor(int playerIndex) {
        switch (playerIndex) {
            case 0:
                return Color.CYAN;
            case 1:
                return Color.YELLOW;
            case 2:
                return Color.RED;
            case 3:
                return Color.PURPLE;
            default:
                return Color.BLACK; // Default color
        }
    }

    /*
     * Author: Bei Jin */
    private char getPlayerId(int playerIndex) {
        switch (playerIndex) {
            case 0:
                return 'c';
            case 1:
                return 'y';
            case 2:
                return 'r';
            case 3:
                return 'p';
            default:
                return 'b'; // Default color
        }
    }


    //Assam
    /*
     * Author: Bei Jin */
    private String currentAssam = "A33N";
    private void assam() {

        Image AssamPieceImage = new Image("comp1110/ass2/gui/assets/Assam.png");
        AssamPieceView = new ImageView(AssamPieceImage);

        AssamPieceView.setFitWidth(Tile_Size);
        AssamPieceView.setFitHeight(Tile_Size);

        // Initial position of Assam (adjust as needed)
        AssamPieceView.setX(310);
        AssamPieceView.setY(290);
        board.getChildren().add(AssamPieceView);

        /*
         * Author: Arnav*/
        // Button for changing orientation
        Button rotateAssamClockwiseButton = new Button("→");
        rotateAssamClockwiseButton.setLayoutX(800);  // Adjust as needed
        rotateAssamClockwiseButton.setLayoutY(550);  // Adjust as needed
        rotateAssamClockwiseButton.setOnAction(e -> {
            String newAssam = marrakech.assam.rotateAssam(90);
            currentAssam = newAssam;
            updateAssamOrientation(AssamPieceView);
        });

        Button rotateAssamAntiClockwiseButton = new Button("←");
        rotateAssamAntiClockwiseButton.setLayoutX(700);  // Adjust as needed
        rotateAssamAntiClockwiseButton.setLayoutY(550);  // Adjust as needed
        rotateAssamAntiClockwiseButton.setOnAction(e -> {
            String newAssam = marrakech.assam.rotateAssam(270);
            currentAssam = newAssam;
            updateAssamOrientation(AssamPieceView);
        });

        rotateAssamClockwiseButton.setStyle("-fx-font-size: 25px;");
        rotateAssamAntiClockwiseButton.setStyle("-fx-font-size: 25px;");

        this.root.getChildren().addAll(rotateAssamClockwiseButton, rotateAssamAntiClockwiseButton);

    }


    /*
     * Author: Arnav*/
    // Button for changing orientation
    // Helper function to use the information we get from currentAssam to change its orientation
    private void updateAssamOrientation(ImageView AssamPieceView) {
        Assam assam = new Assam(currentAssam);
        gameString = gameString.replaceFirst("A\\d\\d[NESW]",currentAssam );
        // Extract the orientation
        char orientation = assam.getOrientation();
        // Default rotation
        int rotationAngle = 0;

        if (orientation == 'E') {
            rotationAngle = 90;
        } else if (orientation == 'S') {
            rotationAngle = 180;
        } else if (orientation == 'W') {
            rotationAngle = -90;
        }


        AssamPieceView.setRotate(rotationAngle);

    }
    /*
     * Author: Bei*/
    //Player
    //player's position
    private List<Label> playerInfoLabels = new ArrayList<>();
    private List<Player> players = new ArrayList<>();

    private void displayPlayerInfo() {
        GridPane grid = new GridPane();
        //distance between grid
        grid.setHgap(20);
        grid.setVgap(20);
        int cols = 2;
        char[] color = {'c', 'y', 'r', 'p'};
        Color[] backColor = {Color.CYAN, Color.YELLOW, Color.RED, Color.PURPLE};
        //initial info
        for (int i = 0; i < 4; i++) {
            Player player = new Player(color[i], 30, 15, true);

            Label playerInfo = new Label();
            updatePlayerInfoLabel(player, playerInfo);

            playerInfo.setTextFill(Color.BLACK);
            playerInfo.setStyle("-fx-font-size: 14px;");
            Rectangle rectangle = new Rectangle(130, 130);
            //the color of background
            if (!player.isInGame()) {
                rectangle.setFill(Color.GRAY);
            } else {
                rectangle.setFill(backColor[i]);
            }
            //the color of stroke will change when present player rolling
            rectangle.setStroke(i == currentPlayer ? Color.BLACK : Color.GREY);
            rectangle.setStrokeWidth(3);

            //for the action of changing color
            playerRectangles.add(rectangle);

            // Combine the rectangle and label in a stack pane
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(rectangle, playerInfo);

            //for make sure the number of column and row
            grid.add(stackPane, i % cols, i / cols);
            grid.setLayoutX(750);
            grid.setLayoutY(50);
            playerInfoLabels.add(playerInfo);  // Save the label for future updates

        }
        this.root.getChildren().add(grid);

    }
    /*
     * Author: Bei*/
    private void updatePlayerBackColor(int playerIndex) {
        // Define an array of background colors
        Color[] backColor = {Color.CYAN, Color.YELLOW, Color.RED, Color.PURPLE};

        // Ensure the index is valid
        if (playerIndex < 0 || playerIndex >= players.size() || playerIndex >= playerRectangles.size()) {
            System.err.println("Invalid player index: " + playerIndex);
            return;
        }

        // Get the player and rectangle
        Player player = players.get(playerIndex);
        Rectangle rectangle = playerRectangles.get(playerIndex);

        // Set the color based on whether the player is in the game
        if (!player.isInGame()) {
            rectangle.setFill(Color.GRAY);
        } else {
            rectangle.setFill(backColor[playerIndex]);
        }
    }

    /*
     * Author: Bei*/
    private void updatePlayerInfoLabel(Player player, Label playerInfo) {
        playerInfo.setText("Player" + (currentPlayer + 1) + "\n" +
                "Dirhams: " + player.getDirhams() + "\n" +
                "Rugs Remain: " + player.getRugsRemaining() + "\n" +
                "Status: " + (player.isInGame() ? "In" : "Out"));;
    }

    /*
     * Author: Bei*/
    private void rollDice() {
        Button rollDiceButton = new Button("Roll");
        //the position of rollButton
        rollDiceButton.setLayoutX(1020);
        rollDiceButton.setLayoutY(600);

        //the position of screen of dice
        Label diceResult = new Label("");
        diceResult.setLayoutX(1000);
        diceResult.setLayoutY(500);
        diceResult.setMinWidth(80);
        diceResult.setMinHeight(80);
        diceResult.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-font-size: 20px;");

        diceResult.setTextAlignment(TextAlignment.CENTER);
        diceResult.setAlignment(Pos.CENTER);
        diceResult.setTextFill(Color.WHITE);

        //the color of stroke will change when present player rolling
        rollDiceButton.setOnAction(e -> {
            Assam assam = new Assam(currentAssam);

            // the boolean gameover
            if (marrakech.isGameOver()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game");
                //the winner part
                char winner = marrakech.getWinner();
                alert.setContentText("Game is over! The winner is " + winner + ".");
                alert.showAndWait();
                return;
            }

            int result = Marrakech.rollDie();
            diceResult.setText(String.valueOf(result));

            // Move Assam based on the dice roll
            currentAssam = assam.moveAssam(result);
            assam = new Assam(currentAssam);
            int x = assam.getX();
            int y = assam.getY();
            assam.setX(x);
            assam.setX(y);

            // Calculating the pixel position on the board from the tile position.
            double newX = START_X + (x * Tile_Size);
            double newY = START_Y + (y * Tile_Size);
            // Updating the ImageView position on the GUI.
            AssamPieceView.setX(newX);
            AssamPieceView.setY(newY);
            updateAssamOrientation(AssamPieceView);

            gameString = gameString.replaceFirst("A\\d\\d[NESW]",currentAssam );
            Marrakech marrakech = new Marrakech(gameString);
            updatePlayerInfoLabel(marrakech.players[currentPlayer], playerInfoLabels.get(currentPlayer));

            playerRectangles.get(currentPlayer).setStroke(Color.GREY);

            /*
             * Author: Bei*/
            int payment = marrakech.getPaymentAmount();

            // If payment is more than 0, update dirhams of the players
            if (payment > 0) {
                char rugColor = marrakech.tile[x][y].getColor();

                // Identify the owner of the carpet
                int rugOwner = getPlayerByColor(rugColor);
                if (rugOwner >= 0 && rugOwner < marrakech.players.length) {
                    if (currentPlayer != rugOwner) { // Ensure a player doesn't pay himself
                        marrakech.players[currentPlayer].reductDirhams(payment);
                        updatePlayerInfoLabel(marrakech.players[currentPlayer], playerInfoLabels.get(currentPlayer));
                        marrakech.players[rugOwner].addDirhams(payment);
                        updatePlayerInfoLabel(marrakech.players[rugOwner], playerInfoLabels.get(rugOwner));
                        updatePlayerString(marrakech.players[currentPlayer]);
                        updatePlayerString(marrakech.players[rugOwner]);


                        // Optional: Display some message or update the GUI to reflect this change.
                    }
                } else {
                    payment=0;
                }
                System.out.println(gameString);

            }


            currentPlayer = (currentPlayer + 1) % 4;
            while (!marrakech.players[currentPlayer].isInGame()) {
                currentPlayer = (currentPlayer + 1) % 4;
            }
            updatePlayerBackColor(currentPlayer);
            playerRectangles.get(currentPlayer).setStroke(Color.BLACK);

        });
        this.root.getChildren().addAll(rollDiceButton, diceResult);
    }
    /*
     * Author: Bei*/
    public void updatePlayerString(Player player) {
        char playerColor = player.getColor();
        String playerInfoString = "P" + playerColor; // Prefix for player information

        // Find the substring related to a specific player in the game string
        int startIndex = gameString.indexOf(playerInfoString);

        if (startIndex != -1) {
            String updatedPlayerInfoString = player.toString();

            // Replace the updated player information string in the game string
            gameString = gameString.substring(0, startIndex) + updatedPlayerInfoString + gameString.substring(startIndex + updatedPlayerInfoString.length());
        }

    }
    private int getPlayerByColor(char color) {
        // Return the player index based on the color of the rug
        // This is just a placeholder. You should map colors to player indices as per your game setup.
        switch (color) {
            case 'c': return 0;
            case 'y': return 1;
            case 'r': return 2;
            case 'p': return 3;
            default: return -1; // Invalid color or a default case
        }
    }

    @Override
    public void start(Stage stage) throws Exception {

        Pane pane = new Pane();

        // Load the image for the background
        Image startPageImage = new Image("comp1110/ass2/gui/assets/1.png");
        ImageView startPageImageView = new ImageView(startPageImage);
        startPageImageView.setFitWidth(1200);
        startPageImageView.setFitHeight(700);

        Button startGameButton = new Button("Start Game");
        Button rulesButton = new Button("Game's Rule");
        Button exitGameButton = new Button("Exit Game");

        startGameButton.setStyle("-fx-font-size: 20px;");
        rulesButton.setStyle("-fx-font-size: 20px;");
        exitGameButton.setStyle("-fx-font-size: 20px;");

        startGameButton.setPrefWidth(150);
        startGameButton.setPrefHeight(50);
        exitGameButton.setPrefWidth(150);
        exitGameButton.setPrefHeight(50);
        rulesButton.setPrefWidth(150);
        rulesButton.setPrefHeight(50);


        startGameButton.setLayoutX(525);
        startGameButton.setLayoutY(400);
        rulesButton.setLayoutX(525);
        rulesButton.setLayoutY(470);
        exitGameButton.setLayoutX(525);
        exitGameButton.setLayoutY(540);

        pane.getChildren().addAll(startPageImageView, startGameButton, rulesButton, exitGameButton);

        this.startScene = new Scene(pane, 1200, 700);
        stage.setScene(startScene);
        stage.setTitle("Main Menu");
        stage.show();

        startGameButton.setOnAction(e -> {
            //enter game
            Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
            stage.setScene(scene);
            stage.setTitle("Marrakech");
        });
        rulesButton.setOnAction(e -> {
            Scene rulesScene = RulesScene(stage);
            stage.setScene(rulesScene);
            stage.setTitle("Game's Rule");
        });

        exitGameButton.setOnAction(e -> {
            //exit
            stage.close();
        });

    }

    private Scene RulesScene(Stage stage) {
        VBox rulesLayout = new VBox(10);

        Text title = new Text("Game's Rule");
        title.setFont(new Font(20));

        Text rule1 = new Text(
                "Assam is a key piece in the game of Marrakech, as he defines where players may place their\n " +
                        "rugs, as well as when players must pay other players.\n" +
                        "Assam starts the game in the centre of the board (i.e at position (3,3)), and may be facing\n " +
                        "in any direction (since the board is a square).\n" +
                        "At the beginning of a player's turn, they choose to rotate Assam either 90 degrees to the\n " +
                        "right or left, or not at all (i.e., leave him facing the same direction).\n" +
                        "Then, a special die is rolled to determine how far Assam moves on this turn.\n" +
                        "The die is 6-sided, but the sides are numbered 1-4, with the numbers not equally common.\n" +
                        "It has:\n" +
                        "\n" +
                        "One face which shows a one\n" +
                        "Two faces which show a two\n" +
                        "Two faces which show a three\n" +
                        "One face which shows a four\n" +
                        "\n" +
                        "This means that the die is twice as likely to show a 2 or 3 as it is to show a 1 or 4.\n" +
                        "After the die has been rolled, Assam is moved in the direction he is currently facing with\n" +
                        "the number of spaces he moves matching the number shown on the die. If he goes off the board\n " +
                        "at any time, he follows a pre-defined mosaic track on the edge of the board in order to be \n" +
                        "placed back on it and continue his movement.");
        Text rule2 = new Text("Rug Placement\n" +
                "After Assam has moved and any required payment is completed, the current player must place\n" +
                "a rug on the board according to the following conditions:\n" +
                "\n" +
                "At least one of the squares of the rug must be adjacent to (share an edge with) the square\n " +
                "that Assam occupies.\n" +
                "The rug must not be placed under Assam.\n" +
                "The rug may not cover both squares of another rug that is already on the board, if both \n" +
                "squares of that rug are visible (not already covered).\n" +
                "\n" +
                "To clarify the third condition, it is acceptable for a rug to cover up part of one or more\n " +
                "rugs on the board, but not to be placed directly on top of another rug and cover both squares\n " +
                "of it in one turn.\n" +
                "A rug can be placed exactly on top of a previously placed rug if at least one square of the\n " +
                "previous rug is already covered by another rug already on the board.");

        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e -> {

            stage.setScene(this.startScene);
            stage.setTitle("Main Menu");
        });
        rulesLayout.getChildren().addAll(title, rule1, rule2, backButton);
        rulesLayout.setAlignment(Pos.CENTER);

        return new Scene(rulesLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
    }


}
