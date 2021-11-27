package team.emergence._15puzzle.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import team.emergence._15puzzle.core.Board;
import team.emergence._15puzzle.core.BoardState;
import team.emergence._15puzzle.model.GameConfig;
import team.emergence._15puzzle.model.Session;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
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
    private Button btnRestart;
    @FXML
    private ImageView ivSample;
    @FXML
    private BorderPane container;
    private Board board;

    public void start(int difficulty, String imagePath) {
        Session session = new Session(difficulty, imagePath);
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                event -> txtTimer.setText(session.getStopwatch().toString())));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        if (board != null) {
            container.getChildren().remove(board);
        }
        board = new Board(
                new GameConfig(difficulty, imagePath),
                new BoardState() {
                    @Override
                    public void onBoardClicked() {
                        session.getCounter().count();
                        txtMoveCount.setText(String.valueOf(session.getCounter().getMove()));
                    }

                    @Override
                    public void onBoardSolved() {
                        System.out.println("Board is solved!");
                    }
                }
        );
        container.getChildren().add(board);

        //        ivSample.setImage(mini);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image icReset = new Image("C:\\files\\code\\unpad\\s_iii\\prak_pbo\\oop-final-kelompok-a-07\\app\\src\\main\\resources\\team\\emergence\\_15puzzle\\drawable\\ic_reset.png", 36, 36, false, true);
//        Image icReset = new Image("drawable/ic_reset.png", 36, 36, false, true);
        btnReset.setGraphic(new ImageView(icReset));

//        Image icRestart = new Image(Objects.requireNonNull(getClass().getResourceAsStream("drawable/ic_restart.png")), 36, 36, false, true);
//        btnRestart.setGraphic(new ImageView(icRestart));

        start(3, "C:\\files\\code\\unpad\\s_iii\\prak_pbo\\oop-final-kelompok-a-07\\app\\src\\main\\resources\\team\\emergence\\_15puzzle\\drawable\\img_sample1.png");
    }

    @FXML
    private void onClickBtnAction() {

    }

    @FXML
    private void onClickBtnReset() {
        board.initializeBoard(); // reshuffle board
    }

    @FXML
    private void onClickBtnBack() {

    }

    @FXML
    private void onClickBtnRestart(){

    }
}
