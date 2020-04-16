package org.openjfx.presentation;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.openjfx.interfaces.ISystem;
import org.openjfx.interfaces.IUser;

import java.io.IOException;
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

    private static IUser adminUser;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMsg.setVisible(false);
    }

    /**
     * Method which checks for a "fake" login, and otherwise prints an error message.
     * If successful login, the App-scene (original base stage) will change to that fxml of the LandingPageGUI, and the
     * user will now have access to administrating features.
     * @param event
     */
    @FXML
    public void handleLoginClicked(MouseEvent event){
        if(username.getText().equals("admin") && password.getText().equals("admin")){
            adminUser = App.getSystemInstance().createNewUser(username.getText(), password.getText());
            try {
                Parent value = FXMLLoader.load(LoginController.class.getResource("LandingPageGUI.fxml"));
                App.getScene().setRoot(value);
                App.getStage().setHeight(505);
                App.getStage().setWidth(655);
                App.getStage().setResizable(false);
            } catch(IOException ex){
                ex.printStackTrace();
            }finally{
                //TODO implement code to close the login window upon having changed the root scene
            }
            //errorMsg.setText("Login Succesfuldt");
            //errorMsg.setVisible(true);
        } else{
            errorMsg.setText("Forkert brugernavn og adganskode");
            errorMsg.setVisible(true);
        }

    }

    public static IUser getAdminUser(){
        return adminUser;
    }

}
