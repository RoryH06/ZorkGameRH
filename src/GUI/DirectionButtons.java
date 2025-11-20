package GUI;

import Game.ZorkULGame;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class DirectionButtons {

    private final Button northBtn;
    private final Button southBtn;
    private final Button eastBtn;
    private final Button westBtn;

    private final GridPane dPadGrid;
    private final ZorkULGame game;
    private final Map map; // reference to map

    public DirectionButtons(TextArea output, ZorkULGame gameInstance, Map mapInstance) {
        this.game = gameInstance;
        this.map = mapInstance;

        northBtn = new Button("N");
        southBtn = new Button("S");
        eastBtn = new Button("E");
        westBtn = new Button("W");

        northBtn.setPrefSize(50, 50);
        southBtn.setPrefSize(50, 50);
        eastBtn.setPrefSize(50, 50);
        westBtn.setPrefSize(50, 50);

        northBtn.setOnAction(e -> move("north", output));
        southBtn.setOnAction(e -> move("south", output));
        eastBtn.setOnAction(e -> move("east", output));
        westBtn.setOnAction(e -> move("west", output));

        dPadGrid = new GridPane();
        dPadGrid.setAlignment(Pos.CENTER);
        dPadGrid.setHgap(5);
        dPadGrid.setVgap(5);

        dPadGrid.add(northBtn, 1, 0);
        dPadGrid.add(westBtn, 0, 1);
        dPadGrid.add(eastBtn, 2, 1);
        dPadGrid.add(southBtn, 1, 2);
    }

    private void move(String direction, TextArea output) {
        String result = game.go(direction);
        output.appendText("\n" + result);

        // Refresh map highlight
        map.updateMap(game.player.getCurrentRoom());
    }

    public GridPane getDPad() {
        return dPadGrid;
    }
}