package org.openjfx.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class RemoveChangeController implements Initializable {
    @FXML
    private Button saveChanges;
    @FXML
    private Button removeCast;
    @FXML
    private Label errorMsg;
    @FXML
    private ListView castList;
    @FXML
    private TextField castName;
    @FXML
    private TextField regDKField;
    @FXML
    private TextField roleNameField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //implement code which finds the list of cast for the broadcast object that was chosen
        //in the previous stage (e.g. using a get-method) and puts that into the ListView
    }

    @FXML
    public void handleSaveChanges(MouseEvent event){
        //IF FAIL: update errorMsg.setVisible(true) and errorMsg.setText("Fejl, ændringerne blev ikke gemt")

    }

    @FXML
    public void handleRemoveCast(MouseEvent event){
        //IF FAIL: update errorMsg.setVisible(true) and errorMsg.setText("Fejl, medvirkende blev ikke slettet")

    }

}
