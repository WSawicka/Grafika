/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import algorithm.MatrixTransitions;
import java.awt.Point;
import java.io.IOException;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author mloda
 */
@Getter
@Setter
public class DrawShapesController implements Initializable {

    private SceneController controller;
    MatrixTransitions matrix = new MatrixTransitions();

    private Point start;
    private Point end;

    private Image image;
    private GraphicsContext gc;
    private Object lastShape;

    private Point vector;
    private double additionalValue;
    private boolean hasAdditionalValue;

    @FXML
    private ComboBox shape;
    @FXML
    private Canvas canvas;
    @FXML
    private Button clear;

    @FXML
    private void doMove(ActionEvent event) throws IOException {
        if (lastShape != null) {
            hasAdditionalValue = false;
            showAndSetEditShapeValues();
            matrix.setMoveMatrix(vector);
            Point newStart = matrix.getMovedPoint(start);
            Point newEnd = matrix.getMovedPoint(end);
            start = newStart;
            end = newEnd;
            drawShape(Color.RED);
        }
    }

    @FXML
    private void doTurn(ActionEvent event) throws IOException {
        if (lastShape != null) {
            hasAdditionalValue = true;
            showAndSetEditShapeValues();
            matrix.setTurnMatrix(vector, additionalValue);
            //gc.setStroke(Color.RED);
            //gc.strokeOval(vector.x, vector.y, 3, 3);
            Point newStart = matrix.getMovedPoint(start);
            Point newEnd = matrix.getMovedPoint(end);
            start = newStart;
            end = newEnd;
            drawShape(Color.BLUE);
        }
    }

    @FXML
    private void doScale(ActionEvent event) throws IOException {
        if (lastShape != null) {
            hasAdditionalValue = true;
            showAndSetEditShapeValues();
            matrix.setScaleMatrix(vector, additionalValue);
            Point newStart = matrix.getMovedPoint(start);
            Point newEnd = matrix.getMovedPoint(end);
            start = newStart;
            end = newEnd;
            drawShape(Color.BROWN);
        }
    }

    @FXML
    private void handleMousePressed(MouseEvent e) {
        start = new Point((int) e.getX(), (int) e.getY());
    }

    @FXML
    private void handleClear(ActionEvent e) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        lastShape = null;
    }

    @FXML
    private void handleMouseReleased(MouseEvent e) {
        end = new Point((int) e.getX(), (int) e.getY());
        drawShape(Color.BLACK);
    }

    private void drawShape(Paint paint){
        gc.setStroke(paint);
        switch (shape.getValue().toString()) {
            case "Linia":
                lastShape = new Line(start.x, start.y, end.x, end.y);
                gc.strokeLine(start.x, start.y, end.x, end.y);
                break;
            case "Koło":
                double r = sqrt(pow(end.x - start.x, 2) + pow(end.y - start.y, 2));
                lastShape = new Circle(start.x, start.y, r);
                gc.strokeOval(start.x, start.y, r, r);
                break;
            case "Prostokąt":
                lastShape = new Rectangle(start.x, start.y, abs(end.x - start.x), abs(end.y - start.y));
                gc.strokeRect(start.x, start.y, abs(end.x - start.x), abs(end.y - start.y));
                break;
            default:
                break;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gc = canvas.getGraphicsContext2D();
        shape.setItems(FXCollections.observableArrayList("Linia", "Koło", "Prostokąt"));
        shape.setValue("Linia");
    }

    private void showAndSetEditShapeValues() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditShapeValues.fxml"));
        Parent root = (Parent) loader.load();
        EditShapeValuesController esvc = loader.getController();

        Scene newScene = new Scene(root);
        newScene.getStylesheets().add("/styles/Styles.css");
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        esvc.setDrawShapesController(this);
        esvc.setAccesToAdditionalValue();
        newStage.showAndWait();
    }

    public void setSceneController(SceneController controller) {
        this.controller = controller;
    }

}
