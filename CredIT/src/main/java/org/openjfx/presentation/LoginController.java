package org.openjfx.presentation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button loginButton;
    @FXML
    private Label errorMsg;
    @FXML
    private TextField username;
    @FXML
    private TextField password;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMsg.setVisible(false);
    }

    @FXML
    public void handleLoginClicked(MouseEvent event){

    }

}
