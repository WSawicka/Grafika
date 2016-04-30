/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 *
 * @author mloda
 */
public class CmykRgbController implements Initializable{
    
    private SceneController controller;
    
    @FXML AnchorPane anchorPane;
    @FXML private Canvas canvas;
    @FXML private Slider r;
    @FXML private Slider g;
    @FXML private Slider b;
    @FXML private Slider c;
    @FXML private Slider m;
    @FXML private Slider y;
    @FXML private Slider k;

    @FXML
    private void handleSetRGB(MouseEvent event) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.rgb((int) r.getValue(), (int) g.getValue(), (int) b.getValue(), 1));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(canvas);

        double rr = r.getValue() / 255;
        double gg = g.getValue() / 255;
        double bb = b.getValue() / 255;

        double max = Math.max(bb, Math.max(rr, gg));
        double kVal = (1 - max) * 100;
        k.setValue(kVal);
        double cVal = ((1 - rr - kVal) / (1 - kVal)) * 100;
        c.setValue(cVal);
        double mVal = ((1 - gg - kVal) / (1 - kVal)) * 100;
        m.setValue(mVal);
        double yVal = ((1 - bb - kVal) / (1 - kVal)) * 100;
        y.setValue(yVal);
    }

    @FXML
    private void handleSetCMYK(MouseEvent event) {
        double cc = c.getValue() / 100;
        double mm = m.getValue() / 100;
        double yy = y.getValue() / 100;
        double kk = k.getValue() / 100;

        double rVal = 255 * (1 - cc) * (1 - kk);
        r.setValue(rVal);
        double gVal = 255 * (1 - mm) * (1 - kk);
        g.setValue(gVal);
        double bVal = 255 * (1 - yy) * (1 - kk);
        b.setValue(bVal);
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.rgb((int) rVal, (int) gVal, (int) bVal, 1));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(canvas);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        r.setMin(0.0);
        r.setMax(255.0);
        g.setMin(0.0);
        g.setMax(255.0);
        b.setMin(0.0);
        b.setMax(255.0);

        c.setMin(0.0);
        c.setMax(100.0);
        m.setMin(0.0);
        m.setMax(100.0);
        y.setMin(0.0);
        y.setMax(100.0);
        k.setMin(0.0);
        k.setMax(100.0);
    }    
    
    public void setSceneController(SceneController controller) {
        this.controller = controller;
    }
}
