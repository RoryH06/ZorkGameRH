import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class DirectionButtons {

    private final Button northBtn;
    private final Button southBtn;
    private final Button eastBtn;
    private final Button westBtn;

    // Reference to your game
    private final ZorkULGame game;

    public DirectionButtons(TextArea output, ZorkULGame gameInstance) {
        this.game = gameInstance;

        // Create buttons
        northBtn = new Button("↑");
        southBtn = new Button("↓");
        eastBtn = new Button("→");
        westBtn = new Button("←");

        // Set sizes
        northBtn.setPrefSize(60, 60);
        southBtn.setPrefSize(60, 60);
        eastBtn.setPrefSize(60, 60);
        westBtn.setPrefSize(60, 60);

        // Connect buttons to game logic
        northBtn.setOnAction(e -> move("north", output));
        southBtn.setOnAction(e -> move("south", output));
        eastBtn.setOnAction(e -> move("east", output));
        westBtn.setOnAction(e -> move("west", output));
    }

    private void move(String direction, TextArea output) {
        // Call your ZorkULGame's go method for direction
        String result = game.go(direction); // You may need to create a 'go' method in ZorkULGame that returns the description
        output.appendText("\n" + result);
    }

    // Getter methods so GUI can add buttons
    public Button getNorthButton() { return northBtn; }
    public Button getSouthButton() { return southBtn; }
    public Button getEastButton() { return eastBtn; }
    public Button getWestButton() { return westBtn; }
}
