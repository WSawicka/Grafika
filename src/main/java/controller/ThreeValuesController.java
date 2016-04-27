/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
public class ThreeValuesController implements Initializable {

    private SceneController controller;

    @FXML
    private TextField r;
    @FXML
    private TextField g;
    @FXML
    private TextField b;

    @FXML
    public void handleOK(ActionEvent event) {
        this.controller.setValueR(this.r.getText());
        this.controller.setValueG(this.g.getText());
        this.controller.setValueB(this.b.getText());
        Stage stage = (Stage) this.r.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setSceneController(SceneController controller) {
        this.controller = controller;
    }
}
