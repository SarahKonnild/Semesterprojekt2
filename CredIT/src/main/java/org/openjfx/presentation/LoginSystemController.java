package org.openjfx.presentation;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.openjfx.interfaces.IUser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginSystemController implements Initializable {

    @FXML
    private AnchorPane basePane;
    @FXML
    private Button login;
    @FXML
    private ImageView tv2Logo;
    @FXML
    private Label close;
    @FXML
    private Label guestUserLogin;
    @FXML
    private Label adminUserLogin;
    @FXML
    private Label errorMessage;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    private static IUser adminUser;
    private static String usernameString;
    private double dragX;
    private double dragY;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);
    }

    @FXML
    public void handleBypassLogin(MouseEvent event){
        usernameString = "admin";
        adminUser = App.getSystemInstance().createNewUser(username.getText(), password.getText());
        try {
            Parent value = FXMLLoader.load(LoginSystemController.class.getResource("AdministratorPage.fxml"));
            App.getScene().setRoot(value);
            App.getStage().sizeToScene();
            App.getStage().setResizable(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleUserLogin(MouseEvent event) {
        try {
            Parent value = FXMLLoader.load(LoginSystemController.class.getResource("GuestUserPage.fxml"));
            App.getScene().setRoot(value);
            App.getStage().sizeToScene();
            App.getStage().setResizable(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleAdminLoginClicked(MouseEvent event) {
        guestUserLogin.setVisible(false);
        adminUserLogin.setVisible(false);
        usernameLabel.setVisible(true);
        username.setVisible(true);
        passwordLabel.setVisible(true);
        password.setVisible(true);
        login.setVisible(true);
    }

    @FXML
    public void handleLoginButton(ActionEvent event) {
        if(username.getText().equals("admin") && password.getText().equals("admin")){
            //TODO make a decision on whether the user should be created here, or if it should be system that creates the class and then returns IUser
            usernameString = "admin";
            adminUser = App.getSystemInstance().createNewUser(username.getText(), password.getText());
            System.out.println("hej3");
            App.handleAdminPage();
    } else{
            errorMessage.setVisible(true);
       }
    }

    @FXML
    public void handleClose(MouseEvent event){
        App.closeWindow();
    }


    public static String getUsernameString(){
        return usernameString;
    }

    public static IUser getAdminUser(){
        return adminUser;
    }
}
