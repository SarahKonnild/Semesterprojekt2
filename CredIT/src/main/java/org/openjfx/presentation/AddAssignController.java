package org.openjfx.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddAssignController implements Initializable {
    @FXML
    private Button searchButton;
    @FXML
    private Button assignButton;
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

    }

    @FXML
    public void handleAssignButton(MouseEvent event){

    }

    @FXML
    public void handleSearchResultChosen(MouseEvent event){

    }
}
