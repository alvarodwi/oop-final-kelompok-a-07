package team.emergence._15puzzle.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import team.emergence._15puzzle.core.DialogListener;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultController implements Initializable {
    // elemen fxml
    @FXML
    private Button btnBack;
    @FXML
    private Text txtMoveCount;
    @FXML
    private Text txtTimer;

    // fungsi fxml
    @FXML
    private void onClickBtnBack() {
        moveToLauncher();
    }

    private DialogListener listener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // do nothing
    }

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

    public void setResult(String time, String move) {
        txtMoveCount.setText(move);
        txtTimer.setText(time);
    }

    private void moveToLauncher() {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
        listener.onClose();
    }
}