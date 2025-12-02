package GUI;

import Game.Command;
import Game.Parser;
import Game.ZorkULGame;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.PrintStream;

public class GUI extends Application {
    private TextField input;
    private TextArea output;
    private Map map;
    private ZorkULGame game;
    private ProgressBar drinkBar;
    private HBox mapWrapper;

    @Override
    public void start(Stage stage) {
        game = new ZorkULGame();
        redirectSystemOut();

        output = new TextArea("Welcome to Game.ZorkUL!\n");
        output.setEditable(false);
        output.setWrapText(true);
        output.setPrefHeight(150);

        input = new TextField();
        input.setPromptText("Enter a command...");
        input.setOnAction(e -> {
            handleInput();
            map.updateMap(game.player.getCurrentRoom());
        });

        GridPane dPad = createDPad();
        VBox drinkBox = createDrinkBox();
        VBox imageBox = createImageBox();

        map = new Map(game.getAllRooms());
        map.updateMap(game.player.getCurrentRoom());
        mapWrapper = new HBox(map);
        mapWrapper.setAlignment(Pos.TOP_RIGHT);
        HBox.setHgrow(mapWrapper, Priority.ALWAYS);

        HBox topPanel = new HBox(20, drinkBox, imageBox, mapWrapper);
        topPanel.setPadding(new Insets(0, 0, 10, 0));
        topPanel.setAlignment(Pos.TOP_CENTER);

        VBox bottomPanel = new VBox(10, output, input);
        bottomPanel.setPadding(new Insets(10, 0, 0, 0));

        BorderPane border = new BorderPane();
        border.setPadding(new Insets(15));
        border.setTop(topPanel);
        border.setLeft(new VBox(20, dPad));
        border.setBottom(bottomPanel);

        Scene scene = new Scene(border, 800, 600);
        stage.setTitle("ZorkUL Game");
        stage.setScene(scene);
        stage.show();
    }

    private void redirectSystemOut() {
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

    private void process(String inputText) {
        output.appendText("> " + inputText + "\n");
        String result = game.processGuiCommand(inputText);
        output.appendText(result + "\n");

        if (inputText.equalsIgnoreCase("resume")) {
            resumeGame(game);
            System.out.println("GUI resumeGame called, current room: " + game.player.getCurrentRoom().getDescription());
        }

        map.updateMap(game.player.getCurrentRoom());
        drinkBar.setProgress(game.player.getDrinkCount() / 12.0);
    }

    public void resumeGame(ZorkULGame loadedGame) {
        this.game = loadedGame;

        map = new Map(game.getAllRooms());
        map.updateMap(game.player.getCurrentRoom());

        mapWrapper.getChildren().clear();
        mapWrapper.getChildren().add(map);

        drinkBar.setProgress(game.player.getDrinkCount() / 12.0);
        output.appendText("\nGame resumed!\n");

        System.out.println("Player room: " + game.player.getCurrentRoom().getName());
        System.out.println("Map rooms contains player room? " +
                game.getAllRooms().contains(game.player.getCurrentRoom()));
    }

    private GridPane createDPad() {
        Button north = new Button("N");
        Button south = new Button("S");
        Button east  = new Button("E");
        Button west  = new Button("W");

        north.setPrefSize(50, 50);
        south.setPrefSize(50, 50);
        east.setPrefSize(50, 50);
        west.setPrefSize(50, 50);

        north.setOnAction(e -> process("go north"));
        south.setOnAction(e -> process("go south"));
        east.setOnAction(e -> process("go east"));
        west.setOnAction(e -> process("go west"));

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.add(north, 1, 0);
        grid.add(west, 0, 1);
        grid.add(east, 2, 1);
        grid.add(south, 1, 2);

        return grid;
    }

    private VBox createDrinkBox() {
        Label label = new Label("Drink Progress Bar");
        drinkBar = new ProgressBar(0);
        drinkBar.setPrefWidth(200);
        VBox box = new VBox(5, label, drinkBar);
        box.setAlignment(Pos.TOP_LEFT);
        return box;
    }

    private VBox createImageBox() {
        Image image = new Image("file:/C:/Users/roryh/Pictures/Screenshots/computerimage.jpg");
        ImageView view = new ImageView(image);
        view.setFitHeight(290);
        view.setPreserveRatio(true);
        VBox box = new VBox(view);
        box.setAlignment(Pos.TOP_CENTER);
        HBox.setHgrow(box, Priority.ALWAYS);
        return box;
    }

    public static void main(String[] args) {
        launch(args);
    }
}