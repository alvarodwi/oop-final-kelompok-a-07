package team.emergence._15puzzle.core;

import javafx.scene.image.ImageView;

public class Cell {
    private final int x;
    private final int y;
    private double offsetX;
    private double offsetY;
    private double tileSize;

    private final ImageView initialImageView;
    private ImageView currentImageView;

    public Cell(int x, int y, ImageView initialImageView, double tileSize) {
        this.x = x;
        this.y = y;
        this.initialImageView = initialImageView;
        this.currentImageView = initialImageView;
        this.tileSize = tileSize;
    }

    public void setOffset(double x, double y) {
        this.offsetX = x;
        this.offsetY = y;
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