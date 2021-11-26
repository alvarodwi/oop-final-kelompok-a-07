package team.emergence._15puzzle.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import team.emergence._15puzzle.core.Board;
import team.emergence._15puzzle.core.BoardState;
import team.emergence._15puzzle.model.GameConfig;
import team.emergence._15puzzle.model.Session;

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

    public void start(int difficulty, String imagePath) {
        Session session = new Session(difficulty, imagePath);
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                (EventHandler) event -> txtTimer.setText(session.getStopwatch().toString())));

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
        start(3, "E:\\sem 3\\@praktikum\\OOP\\Projekan akhir\\oop-final-kelompok-a-07\\app\\src\\main\\resources\\team\\emergence\\_15puzzle\\drawable\\img_sample1.png");
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
