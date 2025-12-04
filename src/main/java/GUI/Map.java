package GUI;

import Game.Room;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class Map extends Pane {

    private final Canvas canvas;
    private final List<Room> rooms;
    private Room currentRoom;

    private static final int PADDING = 20;
    private static final int ROOM_SIZE = 36;
    private static final Font LABEL_FONT = Font.font(10);

    public Map(List<Room> rooms) {
        this.rooms = rooms;
        this.canvas = new Canvas(200, 200);
        getChildren().add(canvas);
    }

    public void updateMap(Room currentRoom) {
        this.currentRoom = currentRoom;
        refresh();
    }

    public void refresh() {
        if (rooms == null || rooms.isEmpty()) return;

        int minX = rooms.stream().mapToInt(Room::getMapX).min().orElse(0);
        int minY = rooms.stream().mapToInt(Room::getMapY).min().orElse(0);
        int maxX = rooms.stream().mapToInt(Room::getMapX).max().orElse(0);
        int maxY = rooms.stream().mapToInt(Room::getMapY).max().orElse(0);

        int canvasWidth = PADDING * 2 + ROOM_SIZE * (maxX - minX + 1);
        int canvasHeight = PADDING * 2 + ROOM_SIZE * (maxY - minY + 1);

        canvas.setWidth(canvasWidth);
        canvas.setHeight(canvasHeight);
        setPrefSize(canvasWidth, canvasHeight);

        drawMap(minX, minY, maxY);
    }

    private void drawMap(int minX, int minY, int maxY) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFont(LABEL_FONT);
        gc.setLineWidth(2);

        for (Room r : rooms) {
            double rx = PADDING + (r.getMapX() - minX) * ROOM_SIZE;
            double ry = PADDING + (maxY - r.getMapY()) * ROOM_SIZE;

            gc.setStroke(Color.BLACK);
            gc.strokeRect(rx, ry, ROOM_SIZE, ROOM_SIZE);

            gc.setFill(r == currentRoom ? Color.RED : Color.rgb(240, 240, 240));
            gc.fillRect(rx, ry, ROOM_SIZE, ROOM_SIZE);
        }
    }
}