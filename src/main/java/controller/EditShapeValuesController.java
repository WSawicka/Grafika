/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Point;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author mloda
 */
public class EditShapeValuesController implements Initializable {

    private DrawShapesController controller;
    private String redColor = "-fx-text-fill: red;";

    @FXML
    private TextField xVector;
    @FXML
    private TextField yVector;
    @FXML
    private TextField additionalValue;

    @FXML
    private void handleOK(ActionEvent event) {
        boolean ok = true;
        if (!this.additionalValue.isDisabled()) {
            try {
                this.controller.setAdditionalValue(Double.parseDouble(this.additionalValue.getText()));
            } catch (NumberFormatException nfex) {
                this.additionalValue.setStyle(redColor);
                ok = false;
            }
        }
        if (!this.xVector.getText().equals("") && !this.yVector.getText().equals("")) {
            try {
                this.controller.setVector(new Point(Integer.parseInt(this.xVector.getText()), Integer.parseInt(this.yVector.getText())));
            } catch (NumberFormatException nfex) {
                this.xVector.setStyle(redColor);
                this.yVector.setStyle(redColor);
                ok = false;
            }
        }
        if (ok) {
            Stage stage = (Stage) this.xVector.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setDrawShapesController(DrawShapesController controller) {
        this.controller = controller;
    }

    public void setAccesToAdditionalValue() {
        if (this.controller.isHasAdditionalValue()) {
            this.additionalValue.setDisable(false);
            this.additionalValue.setVisible(true);
        } else {
            this.additionalValue.setDisable(true);
            this.additionalValue.setVisible(false);
        }
    }
}
