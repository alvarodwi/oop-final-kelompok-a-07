package team.emergence._15puzzle.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import team.emergence._15puzzle.core.DialogListener;
import team.emergence._15puzzle.util.ResourceLoader;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResultController implements Initializable{ 
    @FXML
    private Button btnBack;
    @FXML
    private Text txtMoveCount;
    @FXML
    private Text txtTimer;
    private List<String> parameters;
    private DialogListener listener;

    public void setParameters(List<String> params) {
        this.parameters = params;
    }

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // do nothing
    }

    public void setResult(String time, String move){
        txtMoveCount.setText(move);
        txtTimer.setText(time);
    }

    @FXML
    private void onClickBtnBack() {
        moveToLauncher();
    }

    private void moveToLauncher() {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
        listener.onClose();
    }
}