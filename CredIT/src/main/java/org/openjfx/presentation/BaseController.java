package org.openjfx.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class BaseController implements Initializable {
    @FXML
    private Button searchButton;
    @FXML
    private Label errorMessage;
    @FXML
    private Label userInfo;
    @FXML
    private ListView searchList;
    @FXML
    private Menu login;
    @FXML
    private Menu help;
    @FXML
    private MenuButton searchTopic;
    @FXML
    private TextArea searchString;
    @FXML
    private TextField searchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
