package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Viewer extends Application {

    private final Group root = new Group();

    // The width and height of the window
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

    private Color getColorFromChar(char inputChar) {
        switch (inputChar) {
            case 'r':
                return Color.RED;
            case 'p':
                return Color.PURPLE;
            case 'c':
                return Color.CYAN;
            case 'y':
                return Color.YELLOW;
            default:
                return Color.TRANSPARENT;
        }
    }


    // Group containing all the assets corresponding to pieces of the board.
    private final Group board = new Group();


    private final double boardWidth = Board.BOARD_WIDTH * Tile_Size;
    private final double boardHeight = Board.BOARD_HEIGHT * Tile_Size;

    // Group containing all the buttons, sliders and text used in this application.
    private final Group controls = new Group();

    private TextField boardTextField;


    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param state an array of two strings, representing the current game state
     */
    void displayState(String state) {
        board.getChildren().clear();
        root.getChildren().removeIf(node -> node instanceof Label);
        Marrakech marrakech = new Marrakech(state);

        //Rug
        // creating rectangle to represent the blue background of the board
        Tile[][] tile = marrakech.tile;
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

        // creating the grey rectangles to indicate where the tiles are
        for (int x = 0; x < Board.BOARD_WIDTH; x++) {
            for (int y = 0; y < Board.BOARD_HEIGHT; y++) {
                Rectangle tileShadow = new Rectangle(
                        START_X + (x * Tile_Size) + (BOARD_TILE_SHADOW_GAP / 2),
                        START_Y + (y * Tile_Size) + (BOARD_TILE_SHADOW_GAP / 2),
                        Tile_Size - BOARD_TILE_SHADOW_GAP,
                        Tile_Size - BOARD_TILE_SHADOW_GAP);
                if (tile[x][y] != null) {
                    var c = tile[x][y].getColor();
                    tileShadow.setFill(getColorFromChar(c));
                } else {
                    tileShadow.setFill(Color.TRANSPARENT);
                }
                tileShadow.setStrokeWidth(3);
                tileShadow.setStroke(Color.GREY);
                tileShadow.setOpacity(0.5);
                board.getChildren().add(tileShadow);

                // Assuming that Tile is a class with a copy constructor
            }
        }

        //Assam
        Assam assam = marrakech.assam;
        Image chessPieceImage = new Image("comp1110/ass2/gui/assets/Assam.png");
        ImageView chessPieceView = new ImageView(chessPieceImage);

        //set the size of image
        chessPieceView.setFitWidth(50);
        chessPieceView.setFitHeight(50);


        // Set the position of Assam on the board
        double assamX = START_X + assam.getX() * Tile_Size;
        double assamY = START_Y + assam.getY() * Tile_Size;

        char assamOrientation = marrakech.assam.getOrientation();

        chessPieceView.setX(assamX);
        chessPieceView.setY(assamY);
        switch (assamOrientation) {
            case 'N':
                // No rotation needed
                break;
            case 'E':
                chessPieceView.setRotate(90); // Rotate 90 degrees for East
                break;
            case 'S':
                chessPieceView.setRotate(180); // Rotate 180 degrees for South
                break;
            case 'W':
                chessPieceView.setRotate(270); // Rotate 270 degrees for West
                break;
            default:
                // Handle invalid orientation
                break;
        }
        // Add Assam to the board group
        board.getChildren().add(chessPieceView);

        //Player
        //create a label to present the information of players
        for (int i = 0; i < marrakech.players.length; i++) {
            char colorAbbreviation = marrakech.players[i].getColor();
            String colorName = null;

            if (colorAbbreviation == 'r') {
                colorName = "Red";
            } else if (colorAbbreviation == 'p') {
                colorName = "Purple";
            } else if (colorAbbreviation == 'c') {
                colorName = "Cyan";
            } else if (colorAbbreviation == 'y') {
                colorName = "Yellow";
            }
            String players = "Player " + (i + 1) + ": Color: " + colorName +
                    ", Dirhams: " + marrakech.players[i].getDirhams() +
                    ", Rugs Remaining: " + marrakech.players[i].getRugsRemaining()+
                    ", Status: " + (marrakech.players[i].isInGame() ? "In" : "Out");
            Label playerInfoLabel = new Label(players);
            playerInfoLabel.setLayoutX(700);
            playerInfoLabel.setLayoutY(200 + i * 20);

            // Set Label style
            playerInfoLabel.setStyle("-fx-font-size: 14px;"); // Set font size as needed
            this.root.getChildren().add(playerInfoLabel);
        }

    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Game State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(800);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displayState(boardTextField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel,
                boardTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(WINDOW_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Marrakech Viewer");
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        root.getChildren().add(controls);
        //this.makeBoard();
        /*
          A "Scene" is the window corresponding to the JavaFX application.
          Notice that the last two parameters of a Scene are the width and
          height of the window. The other (first) parameter is a Group,
          which holds all the assets (images, buttons, et cetera) in the application.
          Chat to your lecturer/tutor about any of this if it's unclear.
        */
        /*
          Here we add smaller Groups of assets to our main Group. The `board`
          Group contains board-related assets, `controls` contains buttons, text fields et cetera, and `pieces`
          contains the pieces.
         */
        this.root.getChildren().add(this.board);


        makeControls();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

