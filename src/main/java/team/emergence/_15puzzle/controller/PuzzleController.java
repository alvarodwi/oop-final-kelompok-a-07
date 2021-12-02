package team.emergence._15puzzle.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import team.emergence._15puzzle.core.Board;
import team.emergence._15puzzle.core.BoardListener;
import team.emergence._15puzzle.model.GameConfig;
import team.emergence._15puzzle.model.Session;
import team.emergence._15puzzle.util.ResourceLoader;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PuzzleController implements Initializable {
    // elemen fxml
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
    private Button btnBack;
    @FXML
    private ImageView ivSample;
    @FXML
    private BorderPane container;

    // fungsi fxml
    @FXML
    private void onClickBtnAction() {
        if (!board.isPaused()) {
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
        board.pauseBoard(false);
        toggleBtnAction(false);
        board.initializeBoard();
    }

    @FXML
    private void onClickBtnBack() {
        moveToLauncher();
    }

    @FXML
    private void onClickBtnRestart() {
        session.refresh();
        txtMoveCount.setText("0");
        toggleBtnAction(false);
        board.pauseBoard(false);
        board.restartBoard();
    }

    // inisialisasi data yang dipakai
    private Board board;
    private Session session;
    private List<String> parameters;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // fetch icon image untuk button
        Image icReset = new Image(
                ResourceLoader.loadResource("team/emergence/_15puzzle/drawable/ic_reset.png")
                , 36, 36, false, true);
        Image icRestart = new Image(
                ResourceLoader.loadResource("team/emergence/_15puzzle/drawable/ic_restart.png")
                , 36, 36, false, true);

        // set image tadi ke button
        btnReset.setGraphic(new ImageView(icReset));
        btnRestart.setGraphic(new ImageView(icRestart));
        toggleBtnAction(false);
    }

    // setter untuk parameters
    public void setParameters(List<String> params) {
        this.parameters = params;
    }

    // fungsi yang dipanggil untuk memulai game
    public void start(GameConfig config) {
        // membuat session baru
        session = new Session(config);
        System.out.println("===\nStarted a game with config : \n" + session.getConfig());

        // menyiapkan text timer (auto-refresh)
        final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10),
                event -> txtTimer.setText(session.getStopwatch().toString())));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // membuat board dan menambahkannya ke scene
        if (board != null) {
            container.getChildren().remove(board);
        }
        board = new Board(
                config,
                new BoardListener() {
                    @Override
                    public void onBoardMoved() {
                        session.getCounter().count();
                        txtMoveCount.setText(String.valueOf(session.getCounter().getMove()));
                    }

                    @Override
                    public void onBoardSolved() {
                        System.out.println("Puzzle solved!\n===\n");
                        session.end();
                        moveToResult();
                    }
                }
        );
        container.getChildren().add(board);

        // set preview image
        Image mini = new Image(config.getFilePath(), 150, 150, false, true);
        ivSample.setImage(mini);
    }

    // fungsi untuk btnAction (pause/resume)
    private void toggleBtnAction(Boolean isPaused) {
        if (isPaused) {
            Image icPlay = new Image(
                    ResourceLoader.loadResource("team/emergence/_15puzzle/drawable/ic_play.png")
                    , 36, 36, false, true);
            btnAction.setGraphic(new ImageView(icPlay));
            btnAction.setStyle("-fx-background-color: #a3be8c; -fx-border-radius: 8;");
        } else {
            Image icPause = new Image(
                    ResourceLoader.loadResource("team/emergence/_15puzzle/drawable/ic_pause.png")
                    , 36, 36, false, true);
            btnAction.setGraphic(new ImageView(icPause));
            btnAction.setStyle("-fx-background-color: #bf616a; -fx-border-radius: 8;");
        }
    }

    // fungsi untuk membuka dialog result
    private void moveToResult() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("15 Puzzle");
        dialogStage.getIcons().add(new Image(ResourceLoader.loadResource("team/emergence/_15puzzle/icon.png")));
        dialogStage.setResizable(false);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setOnCloseRequest(event -> {
            closeStage();
        });

        try {
            FXMLLoader dialogLoader = new FXMLLoader(ResourceLoader.loadResourceURL("team/emergence/_15puzzle/fxml/Result.fxml"));
            Parent dialogRoot = dialogLoader.load();

            ResultController controller = dialogLoader.getController();
            controller.setResult(session.getStopwatch().toString(), String.valueOf(session.getCounter().getMove()));
            controller.setListener(
                    this::closeStage
            );

            Scene scene = new Scene(dialogRoot);
            dialogStage.setScene(scene);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // fungsi untuk menutup scene ini
    private void closeStage() {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(ResourceLoader.loadResourceURL("team/emergence/_15puzzle/fxml/Launcher.fxml"));
            Parent root = loader.load();

            LauncherController controller1 = loader.getController();
            controller1.setParameters(parameters);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // fungsi untuk pindah ke tampilan Launcher
    private void moveToLauncher() {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(ResourceLoader.loadResourceURL("team/emergence/_15puzzle/fxml/Launcher.fxml"));
            Parent root = loader.load();

            LauncherController controller = loader.getController();
            controller.setParameters(parameters);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
