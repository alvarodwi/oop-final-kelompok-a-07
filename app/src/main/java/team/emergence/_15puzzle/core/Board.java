package team.emergence._15puzzle.core;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Board extends FlowPane {
    public static Cell[][] board;

    private ArrayList<Cell> completeBoard = new ArrayList<Cell>();
    public final int dimension;
    private int x, y;
    private final int pieceWidth, pieceHeight;

    public Board(int dimension, BufferedImage puzzle) {
        this.dimension = dimension;
        board = new Cell[dimension][dimension];
        x = 0;
        y = 0;
        pieceHeight = puzzle.getWidth() / dimension;
        pieceWidth = puzzle.getWidth() / dimension;
        this.setAlignment(Pos.TOP_LEFT);
        this.setPrefSize(600, 600);
        this.setPrefWrapLength(600);
        this.setVgap(0);
        this.setHgap(0);

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i == dimension - 1 && j == dimension - 1) {
                    continue;
                }
                Piece newPiece = new Piece(i, j, new ImageView(SwingFXUtils.toFXImage(puzzle.getSubimage(x, y, pieceWidth, pieceHeight), null)));
                newPiece.setOnAction(event -> movePiece(newPiece));
                completeBoard.add(new Cell(i, j, newPiece));
                x += pieceWidth;
            }
            x = 0;
            y += pieceHeight;
        }
        randomizeBoard();
    }

    public void randomizeBoard() {
        Random rand = new Random();
        ArrayList<Cell> cellStore = new ArrayList<>(completeBoard);

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i == dimension - 1 && j == dimension - 1) {
                    board[i][j] = new Cell(i, j);
                    continue;
                }
                int randomIndex = rand.nextInt(completeBoard.size());
                completeBoard.get(randomIndex).getPiece().setPos(i, j);
                board[i][j] = new Cell(i, j, completeBoard.get(randomIndex).getPiece());
                completeBoard.remove(randomIndex);
            }
        }
        completeBoard = cellStore;
        cleanBoard();
    }

    public void updateBoard() {
        System.out.println("Board size is " + board.length);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j].getPiece() == null) {
                    Label label = new Label();
                    label.setPrefSize(pieceWidth, pieceHeight);
                    this.getChildren().add(label);
                    continue;
                }
                System.out.println("Board is " + board[i][j].toString());
                this.getChildren().add(board[i][j].getPiece());
            }
        }
    }

    public void cleanBoard() {
        this.getChildren().removeAll();
        this.updateBoard();
    }

    private void movePiece(Piece p) {
        Cell[][] localBoard = Board.board;
        try {
            if (localBoard[p.getPosX() + 1][p.getPosY()].getPiece() == null) {
                Board.board[p.getPosX() + 1][p.getPosY()].setPiece(p);
                Board.board[p.getPosX()][p.getPosY()].setPiece(null);
                p.setPos(p.getPosX() + 1, p.getPosY());
                this.cleanBoard();
                checkAnswer();
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        try {
            if (localBoard[p.getPosX()][p.getPosY() + 1].getPiece() == null) {
                Board.board[p.getPosX()][p.getPosY() + 1].setPiece(p);
                Board.board[p.getPosX()][p.getPosY()].setPiece(null);
                p.setPos(p.getPosX(), p.getPosY() + 1);
                this.cleanBoard();
                checkAnswer();
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        try {
            if (localBoard[p.getPosX() - 1][p.getPosY()].getPiece() == null) {
                Board.board[p.getPosX() - 1][p.getPosY()].setPiece(p);
                Board.board[p.getPosX()][p.getPosY()].setPiece(null);
                p.setPos(p.getPosX() - 1, p.getPosY());
                this.cleanBoard();
                checkAnswer();
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        try {
            if (localBoard[p.getPosX()][p.getPosY() - 1].getPiece() == null) {
                Board.board[p.getPosX()][p.getPosY() - 1].setPiece(p);
                Board.board[p.getPosX()][p.getPosY()].setPiece(null);
                p.setPos(p.getPosX(), p.getPosY() - 1);
                this.cleanBoard();
                checkAnswer();
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void checkAnswer() {
        Piece piece = null;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                piece = Board.board[i][j].getPiece();
                if (piece == null)
                    continue;

                if (piece.getPosX() != piece.getSolutionPosX() || piece.getPosY() != piece.getSolutionPosY()) {
                    return;
                }
            }
        }
        System.out.println("Puzzle completed!");
    }
}