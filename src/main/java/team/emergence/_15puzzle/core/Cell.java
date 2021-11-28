package team.emergence._15puzzle.core;

import javafx.scene.image.ImageView;
import team.emergence._15puzzle.util.Constants;

public class Cell {
    private final int x;
    private final int y;
    private final double offsetX;
    private final double offsetY;
    private final double tileSize;

    private final int initialValue;
    private int currentValue;

    public Cell(int x, int y, int initialValue, int tileCount, double tileSize) {
        this.x = x;
        this.y = y;
        this.initialValue = initialValue;
        this.currentValue = initialValue;
        this.tileSize = tileSize;
        this.offsetX = (Constants.SCENE_WIDTH - tileCount * tileSize) / 2;
        this.offsetY = (Constants.SCENE_HEIGHT - tileCount * tileSize) / 2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getLayoutX() {
        return x * tileSize + offsetX;
    }

    public double getLayoutY() {
        return y * tileSize + offsetY;
    }

    public int getValue() {
        return currentValue;
    }

    public void setValue(int value) {
        this.currentValue = value;
    }

    public boolean isSolved() {
        return this.currentValue == this.initialValue;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", initialValue=" + initialValue +
                ", currentValue=" + currentValue +
                '}';
    }
}