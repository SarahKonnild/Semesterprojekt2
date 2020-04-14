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

public class AddAssignController implements Initializable {
    @FXML
    private Button searchButton;
    @FXML
    private Button assignButton;
    @FXML
    private ListView searchResult;
    @FXML
    private Spinner roleName;
    @FXML
    private TextField nameField;
    @FXML
    private TextField regDKField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
