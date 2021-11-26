package team.emergence._15puzzle.core;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import javax.swing.*;

public class Piece extends Button {
    private int posX;
    private int posY;
    private final int solutionPosX;
    private final int solutionPosY;

    public Piece(int posX, int posY, ImageView img) {
        this.solutionPosX = posX;
        this.solutionPosY = posY;
        this.posX = posX;
        this.posY = posY;

        this.setGraphic(img);
        this.setPrefSize(img.getFitWidth(),img.getFitHeight());
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public int getSolutionPosX() {
        return this.solutionPosX;
    }

    public int getSolutionPosY() {
        return this.solutionPosY;
    }

    public void setPos(int x, int y) {
        this.posX = x;
        this.posY = y;
    }
}
