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
import team.emergence._15puzzle.model.Cell;
import team.emergence._15puzzle.model.GameConfig;
import team.emergence._15puzzle.util.animation.LineToAbs;
import team.emergence._15puzzle.util.animation.MoveToAbs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Board extends Pane {
    private final GameConfig config;
    private final BoardListener listener;
    private final ArrayList<ImageView> puzzleImages;
    private CopyOnWriteArrayList<Cell> initialCells = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Cell> cells = new CopyOnWriteArrayList<>();
    private boolean isPaused = false;

    // constructor
    public Board(GameConfig config, BoardListener listener) {
        this.config = config;
        this.listener = listener;
        int tileCount = (config.getDifficulty() * config.getDifficulty());
        puzzleImages = new ArrayList<>(Collections.nCopies(tileCount, null));
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
        initialCells = new CopyOnWriteArrayList<>();
        cells = new CopyOnWriteArrayList<>();

        populateImages();

        shuffleCells();

        for (Cell cell : cells) {
            Node imageView = puzzleImages.get(cell.getValue());

            if (imageView == null)
                continue;

            imageView.addEventFilter(
                    MouseEvent.MOUSE_CLICKED,
                    mouseEvent -> moveCell((Node) mouseEvent.getSource())
            );

            imageView.relocate(cell.getLayoutX(), cell.getLayoutY());
            this.getChildren().add(imageView);
        }

        try {
            for (Cell cell : cells) {
                initialCells.add((Cell) cell.clone());
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void restartBoard() {
        this.getChildren().clear();

        populateImages();

        cells = new CopyOnWriteArrayList<>();
        try {
            for (Cell initialCell : initialCells) {
                cells.add((Cell) initialCell.clone());
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        for (Cell cell : cells) {
            Node imageView = puzzleImages.get(cell.getValue());

            if (imageView == null)
                return;

            imageView.addEventFilter(
                    MouseEvent.MOUSE_CLICKED,
                    mouseEvent -> moveCell((Node) mouseEvent.getSource())
            );

            imageView.relocate(cell.getLayoutX(), cell.getLayoutY());
            this.getChildren().add(imageView);
        }
    }

    private void populateImages() {
        int tileCount = config.getDifficulty();
        Image image = new Image(config.getFilePath(), 600, 600, false, true);

        for (int y = 0; y < tileCount; y++) {
            for (int x = 0; x < tileCount; x++) {
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
                puzzleImages.set(pos, tile);
                cells.add(new Cell(x, y, pos, config.getDifficulty(), config.getTileSize()));
            }
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

                int emptyCellIndex = puzzleImages.indexOf(null);
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

    private void moveCell(Node node) {
        if (isPaused)
            return;

        int currentCellIndex = puzzleImages.indexOf((ImageView) node);
        int emptyCellIndex = puzzleImages.indexOf(null);

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

        int steps = Math.abs(currentCell.getX() - emptyCell.getX()) + Math.abs(currentCell.getY() - emptyCell.getY());
        if (steps != 1)
            return;


        Path path = new Path();
        path.getElements()
                .add(new MoveToAbs(puzzleImages.get(currentCell.getValue()), currentCell.getLayoutX(), currentCell.getLayoutY()));
        path.getElements()
                .add(new LineToAbs(puzzleImages.get(currentCell.getValue()), emptyCell.getLayoutX(), emptyCell.getLayoutY()));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(100));
        pathTransition.setNode(puzzleImages.get(currentCell.getValue()));
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);

        final Cell a = currentCell;
        final Cell b = emptyCell;
        pathTransition.setOnFinished(actionEvent -> {
            swapCell(a, b);
            listener.onBoardMoved();
            if (checkSolved()) {
                listener.onBoardSolved();
            }
        });

        pathTransition.play();
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
        int inv = sumInversions();
        if (tileCount % 2 == 1) {
            return (inv % 2 == 0);
        } else {
            if (emptyRow % 2 == 1) {
                return (inv % 2 == 0);
            } else {
                return (inv % 2 == 1);
            }
        }
    }

    private boolean checkSolved() {
        boolean allSolved = true;
        for (Cell cell : cells) {
            if (!cell.isSolved()) {
                allSolved = false;
                break;
            }
        }

        return allSolved;
    }
}
