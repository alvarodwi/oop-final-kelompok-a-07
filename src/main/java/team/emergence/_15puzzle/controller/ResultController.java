package team.emergence._15puzzle.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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

    public void setParameters(List<String> params) {
        this.parameters = params;
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