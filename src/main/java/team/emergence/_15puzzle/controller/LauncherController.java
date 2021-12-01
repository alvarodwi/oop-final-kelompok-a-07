package team.emergence._15puzzle.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import team.emergence._15puzzle.model.GameConfig;
import team.emergence._15puzzle.util.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class LauncherController implements Initializable {
    @FXML
    public ComboBox<String> cmbDiff;
    @FXML
    public Button btnImg1;
    @FXML
    public Button btnImg2;
    @FXML
    public Button btnImg3;
    @FXML
    public Button btnStart;
    @FXML
    public ImageView ivPreview;

    private int difficulty;
    private String filePath;
    private GameConfig config;

    public LauncherController() {
        difficulty = 3;
        filePath = "";
    }

    public LauncherController(int difficulty, String filePath) {
        this.difficulty = difficulty;
        this.filePath = filePath;
    }

    @FXML
    public void onClickBtnStart() {
        moveToPuzzle();
    }

    @FXML
    public void onClickBtnImg1() {
        onClickBtnImg(1);
    }

    @FXML
    public void onClickBtnImg2() {
        onClickBtnImg(2);
    }

    @FXML
    public void onClickBtnImg3() {
        onClickBtnImg(3);
    }

    private void onClickBtnImg(int number) {
        String path = switch (number) {
            case 2 -> "team/emergence/_15puzzle/drawable/img_sample2.png";
            case 3 -> "team/emergence/_15puzzle/drawable/img_sample3.png";
            default -> "team/emergence/_15puzzle/drawable/img_sample1.png";
        };

        File target;
        try {
            target = Paths.get(ResourceLoader.loadResourceURL(path).toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        setSelectedImage(target.getAbsolutePath());
    }

    @FXML
    public void onClickBtnImport() {
        pickImage();
    }

    private void setSelectedImage(String path) {
        filePath = "file:///" + path;
        Image img = new Image(filePath, 600, 600, false, true);
        ivPreview.setImage(img);
    }

    private void parseDifficulty(String str) {
        switch (str) {
            case "Hard ( 5x5 )" -> difficulty = 5;
            case "Easy ( 3x3 )" -> difficulty = 3;
            default -> difficulty = 4;
        }
    }

    private void pickImage() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        setSelectedImage(file.getAbsolutePath());
    }

    private void moveToPuzzle() {
        config = new GameConfig(2, filePath);
        Stage stage = (Stage) btnStart.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(ResourceLoader.loadResourceURL("team/emergence/_15puzzle/fxml/Puzzle.fxml"));
            Parent root = loader.load();

            PuzzleController controller = loader.getController();
            controller.start(config);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (difficulty > 5) {
            cmbDiff.setPromptText(String.format("Custom ( %1$dx%1$d", difficulty));
        }
        String[] difficulties = {"Easy ( 3x3 )", "Normal ( 4x4 )", "Hard ( 5x5 )"};
        cmbDiff.setItems(FXCollections.observableArrayList(difficulties));
        cmbDiff.setOnAction(
                event -> parseDifficulty(cmbDiff.getValue())
        );

        Image sample1 = new Image(ResourceLoader.loadResource("team/emergence/_15puzzle/drawable/img_sample1.png"), 64, 64, false, true);
        Image sample2 = new Image(ResourceLoader.loadResource("team/emergence/_15puzzle/drawable/img_sample2.png"), 64, 64, false, true);
        Image sample3 = new Image(ResourceLoader.loadResource("team/emergence/_15puzzle/drawable/img_sample3.png"), 64, 64, false, true);

        btnImg1.setGraphic(new ImageView(sample1));
        btnImg2.setGraphic(new ImageView(sample2));
        btnImg3.setGraphic(new ImageView(sample3));

        onClickBtnImg1();
    }
}
