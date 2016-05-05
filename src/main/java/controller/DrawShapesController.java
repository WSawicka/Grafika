/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Point;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author mloda
 */
public class DrawShapesController implements Initializable {

    private SceneController controller;
    private Point start;
    private Point end;
    private Image image;
    private GraphicsContext gc;

    @FXML
    private ComboBox shape;
    @FXML
    private Canvas canvas;
    @FXML
    private Button clear;

    @FXML
    private void handleMousePressed(MouseEvent e) {
        start = new Point((int) e.getX(), (int) e.getY());
    }

    @FXML
    private void handleClear(ActionEvent e) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    private void handleMouseReleased(MouseEvent e) {
        end = new Point((int) e.getX(), (int) e.getY());
        switch (shape.getValue().toString()) {
            case "Linia":
                gc.strokeLine(start.x, start.y, end.x, end.y);
                break;
            case "Koło":
                double r = sqrt(pow(end.x - start.x, 2) + pow(end.y - start.y, 2));
                gc.strokeOval(start.x, start.y, r, r);
                break;
            case "Prostokąt":
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
    
    public void setSceneController(SceneController controller) {
        this.controller = controller;
    }

}
