package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start(Stage stage) {
        // Output area
        TextArea output = new TextArea("Welcome to ZorkUL!");
        output.setEditable(false);

        // Input box
        TextField input = new TextField();
        input.setPromptText("Enter a command...");
        input.setPrefHeight(40);

        // Create the arrow buttons
        DirectionButtons directionButtons = new DirectionButtons(output);

        // Center North button horizontally
        HBox northBox = new HBox(directionButtons.getNorthButton());
        northBox.setAlignment(Pos.CENTER);
        northBox.setPadding(new Insets(10));

        // Center South button horizontally ABOVE input box
        HBox southBox = new HBox(directionButtons.getSouthButton());
        southBox.setAlignment(Pos.CENTER);
        southBox.setPadding(new Insets(10));

        // Stack South button + input field together at the bottom
        VBox bottomBox = new VBox(5, southBox, input);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));

        // Left and right (center vertically)
        VBox leftBox = new VBox(directionButtons.getWestButton());
        leftBox.setAlignment(Pos.CENTER);

        VBox rightBox = new VBox(directionButtons.getEastButton());
        rightBox.setAlignment(Pos.CENTER);

        // Main layout
        BorderPane border = new BorderPane();
        border.setCenter(output);
        border.setTop(northBox);
        border.setLeft(leftBox);
        border.setRight(rightBox);
        border.setBottom(bottomBox);

        Scene scene = new Scene(border, 1540, 800);
        stage.setTitle("ZorkUL Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
