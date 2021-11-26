package team.emergence._15puzzle.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import team.emergence._15puzzle.core.Board;
import team.emergence._15puzzle.core.Cell;
import team.emergence._15puzzle.core.Piece;

import javax.swing.*;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PuzzleController implements Initializable {
    private int dimension;

    @FXML
    private Text txtMoveCount;
    @FXML
    private Text txtTimer;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnAction;
    @FXML
    private Button btnReset;
    @FXML
    private ImageView ivSample;
    @FXML
    private BorderPane container;
    private Board board;

    public void start(WritableImage img, int level, WritableImage mini) {
        ivSample.setImage(mini);
        if (board != null) {
            container.getChildren().remove(board);
        }
        board = new Board(level, SwingFXUtils.fromFXImage(img,null));
        container.getChildren().add(board);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dimension = 3;
        Image test = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/emergence/_15puzzle/drawable/img_sample1.png")),978,848,true,true);
        start(
                new WritableImage(test.getPixelReader(), 300, 300),
                dimension,
                new WritableImage(test.getPixelReader(), 150, 150)
        );
    }

    @FXML
    private void onClickBtnAction() {

    }

    @FXML
    private void onClickBtnReset() {

    }

    @FXML
    private void onClickBtnBack() {

    }
}
