package GUI;

import Game.Command;
import Game.Parser;
import Game.ZorkULGame;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.PrintStream;

public class GUI extends Application {
    private TextField input;
    private TextArea output;
    private Map map;            // shared map reference
    private ZorkULGame game;    // shared game reference

    private void  redirectSystemOut() {
        PrintStream printStream = new PrintStream(System.out) {
            @Override
            public void println(String x) {
                super.println(x);
                output.appendText(x + "\n");
            }
        };
        System.setOut(printStream);
    }

    private void handleInput() {
        String text = input.getText().trim();
        if (!text.isEmpty()) {
            process(text);
            input.clear();
        }
    }

    private void process(String input) {
        /*String result = game.processGuiCommand(input);

        // Print input and result
        output.appendText("> " + input + "\n");
        output.appendText(result + "\n");

        // Update map
        map.updateMap(game.player.getCurrentRoom());*/
        Parser parser = new Parser();
        Command command = parser.getCommand(input);

        output.appendText("> " + input + "\n");
        String result = game.processGuiCommand(input);

        output.appendText(result + "\n");

        map.updateMap(game.player.getCurrentRoom());
    }

    @Override
    public void start(Stage stage) {
        game = new ZorkULGame();

        output = new TextArea("Welcome to Game.ZorkUL!\n");
        input = new TextField();
        output.setEditable(false);
        output.setWrapText(true);
        output.setPrefHeight(150);

        redirectSystemOut();

        input = new TextField();
        input.setPromptText("Enter a command...");
        input.setOnAction(e -> {
            handleInput();

            map.updateMap(game.player.getCurrentRoom());
            input.clear();
        });

        //------------------------------------------
        // CREATE YOUR D-PAD (ONLY ONCE!)
        //------------------------------------------
        Button northBtn = new Button("N");
        Button southBtn = new Button("S");
        Button eastBtn  = new Button("E");
        Button westBtn  = new Button("W");

        northBtn.setPrefSize(50, 50);
        southBtn.setPrefSize(50, 50);
        eastBtn.setPrefSize(50, 50);
        westBtn.setPrefSize(50, 50);

        northBtn.setOnAction(e -> process("go north"));
        southBtn.setOnAction(e -> process("go south"));
        eastBtn.setOnAction(e -> process("go east"));
        westBtn.setOnAction(e -> process("go west"));

        GridPane dPadGrid = new GridPane();
        dPadGrid.setAlignment(Pos.CENTER);
        dPadGrid.setHgap(5);
        dPadGrid.setVgap(5);

        dPadGrid.add(northBtn, 1, 0);
        dPadGrid.add(westBtn, 0, 1);
        dPadGrid.add(eastBtn, 2, 1);
        dPadGrid.add(southBtn, 1, 2);

        //------------------------------------------

        map = new Map(game.getAllRooms());
        map.updateMap(game.player.getCurrentRoom());

        BorderPane border = new BorderPane();
        border.setPadding(new Insets(15));

        // LEFT SIDE = D-Pad
        VBox leftPanel = new VBox(20, dPadGrid);
        leftPanel.setAlignment(Pos.CENTER);
        border.setLeft(leftPanel);

        // TOP = Map
        HBox topPanel = new HBox(map);
        topPanel.setAlignment(Pos.TOP_RIGHT);
        topPanel.setPadding(new Insets(0, 0, 10, 0));
        border.setTop(topPanel);

        // BOTTOM = Output + Input
        VBox bottomPanel = new VBox(10, output, input);
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