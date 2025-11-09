package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class DirectionButtons extends BorderPane {

    private final Button northBtn;
    private final Button southBtn;
    private final Button eastBtn;
    private final Button westBtn;

    public DirectionButtons(TextArea output) {
        // Create arrow buttons
        northBtn = new Button("↑");
        southBtn = new Button("↓");
        eastBtn = new Button("→");
        westBtn = new Button("←");

        // Set preferred sizes
        northBtn.setPrefSize(60, 60);
        southBtn.setPrefSize(60, 60);
        eastBtn.setPrefSize(60, 60);
        westBtn.setPrefSize(60, 60);

        // Add simple test actions
        northBtn.setOnAction(e -> output.appendText("\nYou moved North."));
        southBtn.setOnAction(e -> output.appendText("\nYou moved South."));
        eastBtn.setOnAction(e -> output.appendText("\nYou moved East."));
        westBtn.setOnAction(e -> output.appendText("\nYou moved West."));
    }

    public Button getNorthButton() { return northBtn; }
    public Button getSouthButton() { return southBtn; }
    public Button getEastButton() { return eastBtn; }
    public Button getWestButton() { return westBtn; }
}
