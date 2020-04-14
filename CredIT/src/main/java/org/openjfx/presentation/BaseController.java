package org.openjfx.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;

public class BaseController implements Initializable {
    @FXML
    private Menu login;
    @FXML
    private Menu help;
    @FXML
    private ListView searchList;
    @FXML
    private TextField searchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
