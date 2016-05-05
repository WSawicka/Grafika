/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Point;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.Bezier;

/**
 *
 * @author mloda
 */
public class BezierCurveController implements Initializable {

    private SceneController controller;
    private Bezier bezier = new Bezier();
    GraphicsContext gc;

    @FXML
    private Canvas canvas;

    @FXML
    private void handleMouseClick(MouseEvent event) {
        bezier.addPoint(new Point((int) event.getX(), (int) event.getY()));
        gc.setStroke(Color.RED);
        gc.strokeOval((int) event.getX() - 2, (int) event.getY() - 2, 3, 3);
    }

    @FXML
    private void doDrawBezier(ActionEvent event) {
        List<Point> points = bezier.getBezierPoints(30);

        gc.setStroke(Color.BLACK);
        for (int i = 1; i < points.size(); i++) {
            gc.strokeLine(points.get(i - 1).x, points.get(i - 1).y, points.get(i).x, points.get(i).y);
        }
        bezier.getPoints().clear();
    }

    @FXML
    private void doClear(ActionEvent event) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        bezier.getPoints().clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
    }

    public void setSceneController(SceneController controller) {
        this.controller = controller;
    }
}
