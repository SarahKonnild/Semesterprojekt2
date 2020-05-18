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
import javafx.scene.control.PasswordField;
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

    //FXML attributes
    //region
    @FXML
    private AnchorPane basePane;
    @FXML
    private Button login;
    @FXML
    private ImageView tv2Logo;
    @FXML
    private Label close;
    @FXML
    private Label cancel;
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
    private PasswordField password;
    //endregion

    //Class attributes
    //region
    private static IUser adminUser;
    private static String usernameString;
    private double dragX;
    private double dragY;
    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);
    }

    /**
     * Allows for bypassing the login system to speed shit up
     * @author Sarah
     * @param event
     */
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

    /**
     * Changes the stage for guest users when pressing the "Continue as Guest User" label
     * @author Sarah
     * @param event
     */
    @FXML
    public void handleGuestUserLogin(MouseEvent event) {
        try {
            Parent value = FXMLLoader.load(LoginSystemController.class.getResource("GuestUserPage.fxml"));
            App.getScene().setRoot(value);
            App.getStage().sizeToScene();
            App.getStage().setResizable(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Changes the visibility of the labels/textfields when pressing to continue as an admin.
     * @author Sarah
     * @param event
     */
    @FXML
    public void handleAdminLoginClicked(MouseEvent event) {
        changeChooseSystemVisible(false);
        changeLoginFieldsVisible(true);
    }

    /**
     * Checks if the pseudo-login criteria are met, and if so, the scene will change to the admin-page.
     * @author Sarah
     * @param event
     */
    @FXML
    public void handleLoginButton(ActionEvent event) {
        if(username.getText().equals("admin") && password.getText().equals("admin")){
            usernameString = "admin";
            adminUser = App.getSystemInstance().createNewUser(username.getText(), password.getText());
            System.out.println("hej3");
            App.handleAdminPage();
    } else{
            errorMessage.setVisible(true);
       }
    }

    @FXML
    public void handleCancel(MouseEvent event){
        changeChooseSystemVisible(true);
        changeLoginFieldsVisible(false);
    }

    /**
     * Permits closing of the borderless window.
     * @author Sarah
     */
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

    private void changeLoginFieldsVisible(boolean value){
        usernameLabel.setVisible(value);
        username.setVisible(value);
        passwordLabel.setVisible(value);
        password.setVisible(value);
        login.setVisible(value);
        cancel.setVisible(value);
    }

    private void changeChooseSystemVisible(boolean value){
        guestUserLogin.setVisible(value);
        adminUserLogin.setVisible(value);
    }
}
