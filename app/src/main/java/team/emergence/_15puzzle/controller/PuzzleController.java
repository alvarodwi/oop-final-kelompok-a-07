package team.emergence._15puzzle.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class PuzzleController implements Initializable {
    @FXML
    private Text txtOutput;

    @FXML
    private void incrementValue(){
        int num = Integer.parseInt(txtOutput.getText());
        num++;
        txtOutput.setText(String.valueOf(num));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // nothing here
    }
}
