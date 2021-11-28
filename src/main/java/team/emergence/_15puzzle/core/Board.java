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
import team.emergence._15puzzle.util.animation.LineToAbs;
import team.emergence._15puzzle.util.animation.MoveToAbs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Board extends Pane {
    private final GameConfig config;
    private final BoardState state;
    private final ArrayList<Cell> cells = new ArrayList<>();
    private final ArrayList<Cell> initialCells = cells;
    private final ArrayList<ImageView> puzzleImgs;
    private boolean isPaused = false;


    public Board(GameConfig config, BoardState state) {
        this.config = config;
        this.state = state;
        int tileCount = (config.getDifficulty() * config.getDifficulty());
        puzzleImgs = new ArrayList<>(Collections.nCopies(tileCount, null));
        initializeBoard();
    }

    public void pauseBoard(Boolean isPaused) {
        this.isPaused = isPaused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void initializeBoard() {
        this.getChildren().clear();
        cells.clear();

        int tileCount = config.getDifficulty();
        Image image;
        image = new Image(config.getFilePath(), 600, 600, false, true);

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

                int pos = y * config.getDifficulty() + x;
                puzzleImgs.set(pos, tile);
                cells.add(new Cell(x, y, pos, config.getDifficulty(), config.getTileSize()));
                System.out.printf("[%d,%d] with initialValue = %d\n", x, y, y * config.getDifficulty() + x);
            }
        }

        shuffleCells();

        for (Cell cell : cells) {
            Node imageView = puzzleImgs.get(cell.getValue());

            if (imageView == null)
                continue;

            imageView.addEventFilter(
                    MouseEvent.MOUSE_CLICKED,
                    mouseEvent -> moveCell((Node) mouseEvent.getSource())
            );

            imageView.relocate(cell.getLayoutX(), cell.getLayoutY());
            this.getChildren().add(imageView);
        }
    }

    public void restartBoard() {
        this.getChildren().clear();

        for (Cell cell : cells) {
            Node imageView = puzzleImgs.get(cell.getValue());

            if (imageView == null)
                continue;

            imageView.addEventFilter(
                    MouseEvent.MOUSE_CLICKED,
                    mouseEvent -> moveCell((Node) mouseEvent.getSource())
            );

            imageView.relocate(cell.getLayoutX(), cell.getLayoutY());
            this.getChildren().add(imageView);
        }
    }

    private void shuffleCells() {
        Random rand = new Random();
        boolean shuffleDone;

        do {
            for (int i = 0; i < 1000; i++) {
                int a = rand.nextInt(cells.size());
                int b = rand.nextInt(cells.size());

                if (a == b)
                    continue;

                int emptyCellIndex = puzzleImgs.indexOf(null);
                if (a == emptyCellIndex || b == emptyCellIndex)
                    continue;

                swapCell(cells.get(a), cells.get(b));
            }

            shuffleDone = isSolvable(config.getDifficulty() - 1) && !this.checkSolved();
        } while (!shuffleDone);
    }

    private void swapCell(Cell a, Cell b) {
        int tmpValue = a.getValue();
        a.setValue(b.getValue());
        b.setValue(tmpValue);
    }

    private int countInversions(int x, int y) {
        int inversions = 0;
        int tileCount = config.getDifficulty();
        int tileNum = y * tileCount + x;
        int lastTile = tileCount * tileCount;
        int tileValue = cells.get(tileNum).getValue();

        for (int q = tileNum + 1; q < lastTile; ++q) {
            int k = q % tileCount;
            int l = q / tileCount;

            int compTileNum = l * tileCount + k;
            int compTileValue = cells.get(compTileNum).getValue();
            if (tileValue > compTileValue && tileValue != (lastTile - 1)) {
                ++inversions;
            }
        }
        return inversions;
    }

    private int sumInversions() {
        int inversions = 0;
        for (int j = 0; j < config.getDifficulty(); ++j) {
            for (int i = 0; i < config.getDifficulty(); ++i) {
                inversions += countInversions(i, j);
            }
        }
        return inversions;
    }

    private boolean isSolvable(int emptyRow) {
        int tileCount = config.getDifficulty();
        System.out.println("sumInversions : " + sumInversions());
        if (tileCount % 2 == 1) {
            return (sumInversions() % 2 == 0);
        } else {
            return ((sumInversions() + tileCount - emptyRow) % 2 == 0);
        }
    }

    public void moveCell(Node node) {
        if (isPaused)
            return;

        int currentCellIndex = puzzleImgs.indexOf((ImageView) node);
        int emptyCellIndex = puzzleImgs.indexOf(null);
        System.out.println(currentCellIndex);

        Cell currentCell = cells.stream()
                .filter(c -> c.getValue() == currentCellIndex)
                .findFirst()
                .orElse(null);

        Cell emptyCell = cells.stream()
                .filter(c -> c.getValue() == emptyCellIndex)
                .findFirst()
                .orElse(null);

        if (currentCell == null || emptyCell == null)
            return;

        System.out.printf("[%d,%d] -> current || [%d,%d] -> empty\n", currentCell.getX(), currentCell.getY(), emptyCell.getX(), emptyCell.getY());
        int steps = Math.abs(currentCell.getX() - emptyCell.getX()) + Math.abs(currentCell.getY() - emptyCell.getY());
        System.out.println("Steps is " + steps);
        if (steps != 1)
            return;

        System.out.println("Transition: " + currentCell + " -> " + emptyCell);

        Path path = new Path();
        path.getElements()
                .add(new MoveToAbs(puzzleImgs.get(currentCell.getValue()), currentCell.getLayoutX(), currentCell.getLayoutY()));
        path.getElements()
                .add(new LineToAbs(puzzleImgs.get(currentCell.getValue()), emptyCell.getLayoutX(), emptyCell.getLayoutY()));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(100));
        pathTransition.setNode(puzzleImgs.get(currentCell.getValue()));
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
