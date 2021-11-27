package team.emergence._15puzzle.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import team.emergence._15puzzle.util.Helper;
import team.emergence._15puzzle.util.Stopwatch;

import java.net.URL;
import java.util.ResourceBundle;

public class PuzzleController implements Initializable {
    @FXML
    private Text txtMoveCount;
    @FXML
    private Text txtTimer;
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
    private Session session;

    public void start(int difficulty, String imagePath) {
        session = new Session(difficulty, imagePath);
        final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10),
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

        Image mini = new Image(imagePath, 150, 150, false, true);
        ivSample.setImage(mini);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image icReset = new Image(
                Helper.loadResource("team/emergence/_15puzzle/drawable/ic_reset.png")
                , 36, 36, false, true);
        Image icRestart = new Image(
                Helper.loadResource("team/emergence/_15puzzle/drawable/ic_restart.png")
                , 36, 36, false, true);

        btnReset.setGraphic(new ImageView(icReset));
        btnRestart.setGraphic(new ImageView(icRestart));
        toggleBtnAction(false);

        start(3, "file:///E:\\sem 3\\@praktikum\\OOP\\oop-final-kelompok-a-07\\src\\main\\resources\\team\\emergence\\_15puzzle\\drawable\\img_sample3.png");
    }

    private void toggleBtnAction(Boolean isPaused) {
        if (isPaused) {
            Image icPlay = new Image(
                    Helper.loadResource("team/emergence/_15puzzle/drawable/ic_play.png")
                    , 36, 36, false, true);
            btnAction.setGraphic(new ImageView(icPlay));
            btnAction.setStyle("-fx-background-color: #a3be8c; -fx-border-radius: 8;");
        } else {
            Image icPause = new Image(
                    Helper.loadResource("team/emergence/_15puzzle/drawable/ic_pause.png")
                    , 36, 36, false, true);
            btnAction.setGraphic(new ImageView(icPause));
            btnAction.setStyle("-fx-background-color: #bf616a; -fx-border-radius: 8;");
        }
    }

    @FXML
    private void onClickBtnAction() {
        if(!board.isPaused()){
            toggleBtnAction(true);
            board.pauseBoard(true);
            session.getStopwatch().pause();
        } else {
            toggleBtnAction(false);
            board.pauseBoard(false);
            session.getStopwatch().resume();
        }
    }

    @FXML
    private void onClickBtnReset() {
        session.refresh();
        txtMoveCount.setText("0");
        board.initializeBoard();
    }

    @FXML
    private void onClickBtnBack() {

    }

    @FXML
    private void onClickBtnRestart() {

    }
}
