package team.emergence._15puzzle.core;

import javafx.animation.PathTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import team.emergence._15puzzle.model.GameConfig;
import team.emergence._15puzzle.util.Constants;
import team.emergence._15puzzle.util.animation.LineToAbs;
import team.emergence._15puzzle.util.animation.MoveToAbs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends Pane {
    private final GameConfig config;
    private final BoardState state;
    private final List<Cell> cells = new ArrayList<>();


    public Board(GameConfig config, BoardState state) {
        this.config = config;
        this.state = state;
        initializeBoard();
    }

    private void initializeBoard() {
        int tileCount = config.getDifficulty();
        Image image = null;
        try {
            image = new Image(new FileInputStream(config.getFilePath()), 600, 600, false, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        for (int x = 0; x < tileCount; x++) {
            for (int y = 0; y < tileCount; y++) {
                ImageView tile = new ImageView(image);
                Rectangle2D rect = new Rectangle2D(
                        config.getTileSize() * x,
                        config.getTileSize() * y,
                        config.getTileSize(),
                        config.getTileSize()
                );
                tile.setViewport(rect);

                if (x == (tileCount - 1) && y == (tileCount - 1)) {
                    tile = null;
                }

                cells.add(new Cell(x, y, tile, config.getTileSize()));
            }
        }

        shuffleCells();

        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            Node imageView = cell.getImageView();

            if (imageView == null)
                continue;

            imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                moveCell((Node) mouseEvent.getSource());

            });

            imageView.relocate(cell.getLayoutX(), cell.getLayoutY());
            this.getChildren().add(cell.getImageView());
        }
    }

    public void shuffleCells() {
        Random rand = new Random();

        for (int i = 0; i < 1000; i++) {
            int a = rand.nextInt(cells.size());
            int b = rand.nextInt(cells.size());

            if (a == b)
                continue;

            if (cells.get(a).isEmpty() || cells.get(b).isEmpty())
                continue;

            swapCell(cells.get(a), cells.get(b));
        }
    }

    private void swapCell(Cell a, Cell b) {
        ImageView tmp = a.getImageView();
        a.setImageView(b.getImageView());
        b.setImageView(tmp);
    }

    public void moveCell(Node node) {
        if(config.isPaused())
            return;

        Cell currentCell = cells.stream()
                .filter(c -> c.getImageView() == node)
                .findFirst()
                .orElse(null);

        Cell emptyCell = cells.stream()
                .filter(Cell::isEmpty)
                .findFirst()
                .orElse(null);

        if (currentCell == null || emptyCell == null)
            return;

        int steps = Math.abs(currentCell.getX() - emptyCell.getX()) + Math.abs(currentCell.getY() - emptyCell.getY());
        if (steps != 1)
            return;

        System.out.println("Transition: " + currentCell + " -> " + emptyCell);

        Path path = new Path();
        path.getElements()
                .add(new MoveToAbs(currentCell.getImageView(), currentCell.getLayoutX(), currentCell.getLayoutY()));
        path.getElements()
                .add(new LineToAbs(currentCell.getImageView(), emptyCell.getLayoutX(), emptyCell.getLayoutY()));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(100));
        pathTransition.setNode(currentCell.getImageView());
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);

        final Cell a = currentCell;
        final Cell b = emptyCell;
        pathTransition.setOnFinished(actionEvent -> {
            swapCell(a, b);
            state.onBoardClicked();
            if (checkSolved()) {
                state.onBoardSolved();
            }
            ;
        });

        pathTransition.play();
    }

    private boolean checkSolved() {
        boolean allSolved = true;
        for (Cell cell : cells) {
            if (!cell.isSolved()) {
                allSolved = false;
                break;
            }
        }

        System.out.println("Solved: " + allSolved);
        return allSolved;
    }
}