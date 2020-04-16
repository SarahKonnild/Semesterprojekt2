package org.openjfx.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class AddAssignController implements Initializable {
    @FXML
    private Button searchButton;
    @FXML
    private Button assignButton;
    @FXML
    private Label errorMsg;
    @FXML
    private ListView searchResult;
    @FXML
    private TextField roleName;
    @FXML
    private TextField nameField;
    @FXML
    private TextField regDKField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleSearchButton(MouseEvent event){
        //Search the database for a cast member specified and show the results in the searchResult ListView
        //Present the Cast-object's information in the textfields
        //IF FAIL: update errorMsg.setVisible(true) and errorMsg.setText("Fejl, medvirkende blev ikke fundet")

    }

    @FXML
    public void handleAssignButton(MouseEvent event){
        //Use the object referenced in the ListView and assign it to the broadcast's list of
        //cast members
        //IF FAIL: update errorMsg.setVisible(true) and errorMsg.setText("Fejl, medvirkende blev ikke tilknyttet")
    }

    /*
    @FXML
    public void handleSearchResultChosen(MouseEvent event){

    }*/
}
