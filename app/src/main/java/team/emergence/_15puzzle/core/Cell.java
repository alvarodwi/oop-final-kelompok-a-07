package team.emergence._15puzzle.core;

import javafx.scene.image.ImageView;
import team.emergence._15puzzle.util.Constants;

public class Cell {
    private final int x;
    private final int y;
    private final double offsetX;
    private final double offsetY;
    private final double tileSize;

    private final ImageView initialImageView;
    private ImageView currentImageView;
    private final int initialValue;
    private int currentValue;

    public Cell(int x, int y, ImageView initialImageView,int initialValue, int tileCount, double tileSize) {
        this.x = x;
        this.y = y;
        this.initialImageView = initialImageView;
        this.currentImageView = initialImageView;
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

    public ImageView getImageView() {
        return currentImageView;
    }

    public void setImageView(ImageView imageView) {
        this.currentImageView = imageView;
    }

    public int getValue() { return currentValue; }

    public void setValue(int value) { this.currentValue = value; }

    public boolean isEmpty() {
        return currentImageView == null;
    }

    public boolean isSolved() {
        return this.initialImageView == currentImageView;
    }

    public String toString() {
        return "[" + x + "," + y + "]";
    }
}