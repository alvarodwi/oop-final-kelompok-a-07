package team.emergence._15puzzle.controller;

import javafx.application.Application;
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
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LauncherController implements Initializable {
    // elemen fxml
    @FXML
    private ComboBox<String> cmbDiff;
    @FXML
    private Button btnImg1;
    @FXML
    private Button btnImg2;
    @FXML
    private Button btnImg3;
    @FXML
    private Button btnStart;
    @FXML
    private ImageView ivPreview;

    // fungsi fxml
    @FXML
    private void onClickBtnStart() {
        moveToPuzzle();
    }

    @FXML
    private void onClickBtnImg1() {
        onClickBtnImg(1);
    }

    @FXML
    private void onClickBtnImg2() {
        onClickBtnImg(2);
    }

    @FXML
    private void onClickBtnImg3() {
        onClickBtnImg(3);
    }

    @FXML
    private void onClickBtnImport() {
        pickImage();
    }

    // inisialisasi data yang dipakai
    private int difficulty;
    private String filePath;
    private boolean useSample;
    private List<String> parameters;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // do nothing
    }

    // setter untuk parameter
    public void setParameters(Application.Parameters params) {
        parameters = params.getRaw();
        setupView();
    }

    public void setParameters(List<String> params) {
        this.parameters = params;
        setupView();
    }

    // fungsi untuk setup ui dari data
    private void setupView() {
        // init default value
        difficulty = 3;
        filePath = "";
        useSample = true;

        // ambil data dari args
        if (!parameters.isEmpty()) {
            difficulty = Integer.parseInt(parameters.get(0));

            if (parameters.size() > 1) {
                filePath = parameters.get(1);
                switch (filePath) {
                    case "sample1" -> onClickBtnImg(1);
                    case "sample2" -> onClickBtnImg(2);
                    case "sample3" -> onClickBtnImg(3);
                    default -> useSample = false;
                }
            }
        }

        // handling input difficulty
        if (difficulty < 2 || difficulty > 10) {
            throw new IllegalStateException("Puzzle grid < 2 atau > 10");
        }
        if (difficulty > 5 || difficulty < 3) {
            cmbDiff.setPromptText(String.format("Custom ( %1$dx%1$d )", difficulty));
        }

        // populate combobox
        String[] difficulties = {"Easy ( 3x3 )", "Normal ( 4x4 )", "Hard ( 5x5 )"};
        cmbDiff.setItems(FXCollections.observableArrayList(difficulties));
        cmbDiff.setOnAction(
                event -> parseDifficulty(cmbDiff.getValue())
        );

        // fetching image from resources folder
        Image sample1 = new Image(ResourceLoader.loadResource("team/emergence/_15puzzle/drawable/img_sample1.png"), 64, 64, false, true);
        Image sample2 = new Image(ResourceLoader.loadResource("team/emergence/_15puzzle/drawable/img_sample2.png"), 64, 64, false, true);
        Image sample3 = new Image(ResourceLoader.loadResource("team/emergence/_15puzzle/drawable/img_sample3.png"), 64, 64, false, true);

        // setting said image to btnImg
        btnImg1.setGraphic(new ImageView(sample1));
        btnImg2.setGraphic(new ImageView(sample2));
        btnImg3.setGraphic(new ImageView(sample3));

        // set default image that is selected...
        if (filePath == null || filePath.isEmpty()) {
            onClickBtnImg1();
        } else {
            setSelectedImage(filePath);
        }
    }

    // fungsi untuk parsing difficulty untuk combobox
    private void parseDifficulty(String str) {
        switch (str) {
            case "Hard ( 5x5 )" -> difficulty = 5;
            case "Easy ( 3x3 )" -> difficulty = 3;
            default -> difficulty = 4;
        }
    }

    // fungsi untuk button sample berdasarkan nomornya
    private void onClickBtnImg(int number) {
        String path = switch (number) {
            case 2 -> "team/emergence/_15puzzle/drawable/img_sample2.png";
            case 3 -> "team/emergence/_15puzzle/drawable/img_sample3.png";
            default -> "team/emergence/_15puzzle/drawable/img_sample1.png";
        };
        useSample = true;
        setSelectedImage(path);
    }

    // fungsi untuk import image dari file system
    private void pickImage() {
        FileChooser fileChooser = new FileChooser();

        // set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        // show open file dialog
        useSample = false;
        File file = fileChooser.showOpenDialog(null);
        setSelectedImage(file.getAbsolutePath());
    }

    // fungsi untuk memilih image (entah dari sample atau dari pickImage()
    private void setSelectedImage(String path) {
        Image img;
        if (useSample) {
            filePath = path;
            img = new Image(ResourceLoader.loadResource(path), 600, 600, false, true);
        } else {
            filePath = "file:///" + path;
            img = new Image(filePath, 600, 600, false, true);
        }
        ivPreview.setImage(img);
    }

    // fungsi untuk berpindah ke halaman Puzzle
    private void moveToPuzzle() {
        // init game config
        GameConfig config = new GameConfig(difficulty, filePath);

        // close scene Launcher
        Stage stage = (Stage) btnStart.getScene().getWindow();
        stage.close();
        // setup scene Puzzle
        try {
            FXMLLoader loader = new FXMLLoader(ResourceLoader.loadResourceURL("team/emergence/_15puzzle/fxml/Puzzle.fxml"));
            Parent root = loader.load();

            // set game config ke controller puzzle
            PuzzleController controller = loader.getController();
            controller.start(config);
            controller.setParameters(parameters);

            // tampilkan scene puzzle
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
