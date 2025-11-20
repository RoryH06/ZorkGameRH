package GUI;

import Game.ZorkULGame;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GUI extends Application {

    private Map map;            // shared map reference
    private ZorkULGame game;    // shared game reference

    @Override
    public void start(Stage stage) {
        game = new ZorkULGame();

        // Output terminal
        TextArea output = new TextArea("Welcome to Game.ZorkUL!\n");
        output.setEditable(false);
        output.setWrapText(true);
        output.setPrefHeight(150);

        // Input field
        TextField input = new TextField();
        input.setPromptText("Enter a command...");
        input.setOnAction(e -> {
            String command = input.getText();
            output.appendText("> " + command + "\n");

            // Use the public GUI command processor
            String result = game.processGuiCommand(command);
            output.appendText(result + "\n");

            // Refresh map highlight
            map.updateMap(game.player.getCurrentRoom());

            input.clear();
        });

        // Map setup
        map = new Map(game.getAllRooms());
        map.updateMap(game.player.getCurrentRoom());

        // Direction buttons wired to game + map
        DirectionButtons directionButtons = new DirectionButtons(output, game, map);

        // Layout
        BorderPane border = new BorderPane();
        border.setPadding(new Insets(15));

        VBox leftPanel = new VBox(directionButtons.getDPad());
        leftPanel.setAlignment(Pos.CENTER);
        leftPanel.setPadding(new Insets(0, 20, 0, 0));
        border.setLeft(leftPanel);

        HBox topPanel = new HBox(map);
        topPanel.setAlignment(Pos.TOP_RIGHT);
        topPanel.setPadding(new Insets(0, 0, 10, 0));
        border.setTop(topPanel);

        VBox bottomPanel = new VBox(10);
        bottomPanel.getChildren().addAll(output, input);
        bottomPanel.setPadding(new Insets(10, 0, 0, 0));
        border.setBottom(bottomPanel);

        Scene scene = new Scene(border, 800, 600);
        stage.setTitle("ZorkUL Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}