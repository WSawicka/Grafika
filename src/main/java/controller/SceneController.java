package controller;

import PFile.ReadFile;
import algorithm.Binarization;
import algorithm.Filters;
import algorithm.Histogram;
import algorithm.PointByPoint;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import lombok.Setter;
import model.Cube;
import model.Image;
import model.Mask;

@Setter
public class SceneController implements Initializable {

    private Mask mask = new Mask();
    int[][] maskTable;
    private Image image;
    private String nazwa;
    private Stage stage;
    private String valueR = "";
    private String valueG = "";
    private String valueB = "";
    private String oneValue;

    @FXML
    private ImageView imageView;
    @FXML
    private ImageView imageHelpView;

    @FXML
    private void doOpen(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PPM files (*.ppm)", "*.ppm");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(this.stage);
        if (file != null) {
            Path path = Paths.get(file.getPath());
            this.nazwa = path.getFileName().toString();
            this.image = ReadFile.readImage(path.toString());
            javafx.scene.image.Image img = SwingFXUtils.toFXImage(this.image.getContent(), null);
            this.imageView.setImage(img);
            this.imageHelpView.setImage(img);
        }
    }

    @FXML
    private void doSave(ActionEvent event) throws IOException {
        int pos = this.nazwa.lastIndexOf(".");
        String nowyPlik = pos > 0 ? this.nazwa.substring(0, pos) : this.nazwa;
        File fileToSave = new File(nowyPlik + ".jpg");
        ImageIO.write(image.getContent(), "jpg", fileToSave);
    }

    @FXML
    private void doDrawShapes(ActionEvent event) throws IOException {
        showDrawShapes();
    }

    @FXML
    private void doCmykRgbColours(ActionEvent event) throws IOException {
        showCmykRgb();
    }

    @FXML
    private void doShowCube(ActionEvent event) {
        Cube cube = new Cube();
    }

    @FXML
    private void doBezier(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BezierCurve.fxml"));
        Parent root = (Parent) loader.load();
        BezierCurveController bcc = loader.getController();

        Scene newScene = new Scene(root);
        newScene.getStylesheets().add("/styles/Styles.css");
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        bcc.setSceneController(this);
        newStage.showAndWait();
    }

    @FXML
    private void doAdd(ActionEvent event) throws IOException {
        showAndSetThreeValues();
        PointByPoint pbp = new PointByPoint(this.image);
        try {
            float r = Float.parseFloat(this.valueR);
            float g = Float.parseFloat(this.valueG);
            float b = Float.parseFloat(this.valueB);
            this.image = pbp.add(r, g, b);
            javafx.scene.image.Image img = SwingFXUtils.toFXImage(this.image.getContent(), null);
            this.imageView.setImage(img);
        } catch (NumberFormatException nfex) {
            System.out.println("Wrong input! It should be number.");
        }
    }

    @FXML
    private void doLess(ActionEvent event) throws IOException {
        showAndSetThreeValues();
        PointByPoint pbp = new PointByPoint(image);
        try {
            float r = Float.parseFloat(this.valueR);
            float g = Float.parseFloat(this.valueG);
            float b = Float.parseFloat(this.valueB);
            this.image = pbp.less(r, g, b);
            javafx.scene.image.Image img = SwingFXUtils.toFXImage(this.image.getContent(), null);
            this.imageView.setImage(img);
        } catch (NumberFormatException nfex) {
            System.out.println("Wrong input! It should be number.");
        }
    }

    @FXML
    private void doMultiply(ActionEvent event) throws IOException {
        showAndSetThreeValues();
        PointByPoint pbp = new PointByPoint(this.image);
        try {
            float r = Float.parseFloat(this.valueR);
            float g = Float.parseFloat(this.valueG);
            float b = Float.parseFloat(this.valueB);
            this.image = pbp.multiply(r, g, b);
            javafx.scene.image.Image img = SwingFXUtils.toFXImage(this.image.getContent(), null);
            imageView.setImage(img);
        } catch (NumberFormatException nfex) {
            System.out.println("Wrong input! It should be number.");
        }
    }

    @FXML
    private void doDivide(ActionEvent event) throws IOException {
        showAndSetThreeValues();
        PointByPoint pbp = new PointByPoint(image);
        try {
            float r = Float.parseFloat(this.valueR);
            float g = Float.parseFloat(this.valueG);
            float b = Float.parseFloat(this.valueB);
            this.image = pbp.divide(r, g, b);
            javafx.scene.image.Image img = SwingFXUtils.toFXImage(this.image.getContent(), null);
            this.imageView.setImage(img);
        } catch (NumberFormatException nfex) {
            System.out.println("Wrong input! It should be number.");
        }
    }

    @FXML
    private void doChangeBrightness(ActionEvent event) throws IOException {
        showAndSetOneValue();
        PointByPoint pbp = new PointByPoint(this.image);
        this.image = pbp.changeBrightness(Float.parseFloat(this.oneValue));
        javafx.scene.image.Image img = SwingFXUtils.toFXImage(this.image.getContent(), null);
        this.imageView.setImage(img);
    }

    @FXML
    private void doGrayNormal(ActionEvent event) {
        PointByPoint pbp = new PointByPoint(this.image);
        this.image = pbp.grayNormal();
        javafx.scene.image.Image img = SwingFXUtils.toFXImage(this.image.getContent(), null);
        this.imageView.setImage(img);
    }

    @FXML
    private void doGrayNatural(ActionEvent event) {
        PointByPoint pbp = new PointByPoint(this.image);
        this.image = pbp.grayNatural();
        javafx.scene.image.Image img = SwingFXUtils.toFXImage(this.image.getContent(), null);
        this.imageView.setImage(img);
    }

    private void setAndShowImage(int[][] maskTable, int maskSummary) {
        Filters filter = new Filters(image.getContent(), maskTable, maskSummary);
        javafx.scene.image.Image img = SwingFXUtils.toFXImage(filter.setBufferedImage(), null);
        this.imageView.setImage(img);
    }

    @FXML
    private void doAveragingMask3x3(ActionEvent event) {
        maskTable = mask.getAveragingMask3x3();
        setAndShowImage(maskTable, mask.getSummary(maskTable));
    }

    @FXML
    private void doAveragingMask5x5(ActionEvent event) {
        maskTable = mask.getAveragingMask5x5();
        setAndShowImage(maskTable, mask.getSummary(maskTable));
    }

    @FXML
    private void doMedianMask(ActionEvent event) throws IOException {
        showAndSetOneValue();
        Filters filter = new Filters(image.getContent(), Integer.parseInt(this.oneValue));
        javafx.scene.image.Image img = SwingFXUtils.toFXImage(filter.setMedian(), null);
        this.imageView.setImage(img);
    }

    @FXML
    private void doSobelHorizontal(ActionEvent event) {
        maskTable = mask.getSobelHorizontal();
        setAndShowImage(maskTable, mask.getSummary(maskTable));
    }

    @FXML
    private void doSobelVertical(ActionEvent event) {
        maskTable = mask.getSobelVertical();
        setAndShowImage(maskTable, mask.getSummary(maskTable));
    }

    @FXML
    private void doMeanRemoval(ActionEvent event) {
        maskTable = mask.getMeanRemoval();
        setAndShowImage(maskTable, mask.getSummary(maskTable));
    }

    @FXML
    private void doHP1(ActionEvent event) {
        maskTable = mask.getHp1();
        setAndShowImage(maskTable, mask.getSummary(maskTable));
    }

    @FXML
    private void doHP2(ActionEvent event) {
        maskTable = mask.getHp2();
        setAndShowImage(maskTable, mask.getSummary(maskTable));
    }

    @FXML
    private void doHP3(ActionEvent event) {
        maskTable = mask.getHp3();
        setAndShowImage(maskTable, mask.getSummary(maskTable));
    }

    @FXML
    private void doGauss1(ActionEvent event) {
        maskTable = mask.getGauss1();
        setAndShowImage(maskTable, mask.getSummary(maskTable));
    }

    @FXML
    private void doGauss2(ActionEvent event) {
        maskTable = mask.getGauss2();
        setAndShowImage(maskTable, mask.getSummary(maskTable));
    }

    @FXML
    private void doGauss3(ActionEvent event) {
        maskTable = mask.getGauss3();
        setAndShowImage(maskTable, mask.getSummary(maskTable));
    }

    @FXML
    private void doGauss4(ActionEvent event) {
        maskTable = mask.getGauss4();
        setAndShowImage(maskTable, mask.getSummary(maskTable));
    }

    @FXML
    private void doGauss5(ActionEvent event) {
        maskTable = mask.getGauss5();
        setAndShowImage(maskTable, mask.getSummary(maskTable));
    }

    @FXML
    private void doStretchHistogram(ActionEvent event) {
        Histogram histogram = new Histogram(this.image.getContent());
        histogram.setStretchLUT();
        javafx.scene.image.Image img = SwingFXUtils.toFXImage(histogram.applyLutOnHistogram(), null);
        this.imageView.setImage(img);
    }

    @FXML
    private void doEqualHistogram(ActionEvent event) {
        Histogram histogram = new Histogram(this.image.getContent());
        histogram.setEqualizingLUT();
        javafx.scene.image.Image img = SwingFXUtils.toFXImage(histogram.applyLutOnHistogram(), null);
        this.imageView.setImage(img);
    }

    private void setImageToGrayscale() {
        PointByPoint pbp = new PointByPoint(this.image);
        this.image = pbp.grayNatural();
    }

    @FXML
    private void binarCustom(ActionEvent event) throws IOException {
        setImageToGrayscale();
        showAndSetOneValue();
        Binarization binar = new Binarization(this.image);
        try {
            this.image = binar.custom(Float.parseFloat(this.oneValue));
        } catch (NumberFormatException nfex) {
            System.out.println("Wrong input! It shoud be a number.");
        }
        javafx.scene.image.Image img = SwingFXUtils.toFXImage(this.image.getContent(), null);
        this.imageView.setImage(img);
    }

    @FXML
    private void binarBlackSelection(ActionEvent event) throws IOException {
        setImageToGrayscale();
        showAndSetOneValue();
        Binarization binar = new Binarization(this.image);
        try {
            this.image = binar.blackSelection(Integer.parseInt(this.oneValue));
        } catch (NumberFormatException nfex) {
            System.out.println("Wrong input! It shoud be an integer.");
        }
        javafx.scene.image.Image img = SwingFXUtils.toFXImage(this.image.getContent(), null);
        this.imageView.setImage(img);
    }

    @FXML
    private void binarMediumSelection(ActionEvent event) {
        setImageToGrayscale();
        Binarization binar = new Binarization(this.image);
        this.image = binar.meanSelection();
        javafx.scene.image.Image img = SwingFXUtils.toFXImage(this.image.getContent(), null);
        this.imageView.setImage(img);
    }

    @FXML
    private void binarEntropySelection(ActionEvent event) {
        Histogram histogram = new Histogram(this.image.getContent());
        histogram.setEqualizingLUT();
        this.image.setContent(histogram.applyLutOnHistogram());
        setImageToGrayscale();

        Binarization binar = new Binarization(this.image);
        this.image = binar.entropySelection();

        javafx.scene.image.Image img = SwingFXUtils.toFXImage(this.image.getContent(), null);
        this.imageView.setImage(img);
    }

    private void showDrawShapes() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DrawShapes.fxml"));
        Parent root = (Parent) loader.load();
        DrawShapesController dsc = loader.getController();

        Scene newScene = new Scene(root);
        newScene.getStylesheets().add("/styles/Styles.css");
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        dsc.setSceneController(this);
        newStage.showAndWait();
    }
    
    private void showCmykRgb() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CmykRgb.fxml"));
        Parent root = (Parent) loader.load();
        CmykRgbController crc = loader.getController();

        Scene newScene = new Scene(root);
        newScene.getStylesheets().add("/styles/Styles.css");
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        crc.setSceneController(this);
        newStage.showAndWait();
    }

    private void showAndSetThreeValues() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ThreeValues.fxml"));
        Parent root = (Parent) loader.load();
        ThreeValuesController tvc = loader.getController();

        Scene newScene = new Scene(root);
        newScene.getStylesheets().add("/styles/Styles.css");
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        tvc.setSceneController(this);
        newStage.showAndWait();
    }

    private void showAndSetOneValue() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/OneValue.fxml"));
        Parent root = (Parent) loader.load();
        OneValueController ovc = loader.getController();

        Scene newScene = new Scene(root);
        newScene.getStylesheets().add("/styles/Styles.css");
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        ovc.setSceneController(this);
        newStage.showAndWait();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
