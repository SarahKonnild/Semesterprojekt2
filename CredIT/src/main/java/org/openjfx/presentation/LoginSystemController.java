package org.openjfx.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);
    }

    /**
     * Allows for bypassing the login system to speed shit up
     *
     * @author Sarah
     */
    @FXML
    public void handleBypassLogin(MouseEvent event) {
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
     *
     * @author Sarah
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
     *
     * @author Sarah
     */
    @FXML
    public void handleAdminLoginClicked(MouseEvent event) {
        changeChooseSystemVisible(false);
        changeLoginFieldsVisible(true);
    }

    /**
     * Checks if the pseudo-login criteria are met, and if so, the scene will change to the admin-page.
     *
     * @author Sarah
     */
    @FXML
    public void handleLoginButton(ActionEvent event) {
        if (username.getText().equals("admin") && password.getText().equals("admin")) {
            usernameString = "admin";
            adminUser = App.getSystemInstance().createNewUser(username.getText(), password.getText());
            App.handleAdminPage();
        } else {
            errorMessage.setVisible(true);
        }
    }

    /**
     * Allows for toggling the fields and Labels in the scene
     *
     * @param value which specifies the boolean visibility-modifier for the fields/labels/buttons
     * @author Sarah
     */
    private void changeLoginFieldsVisible(boolean value) {
        usernameLabel.setVisible(value);
        username.setVisible(value);
        passwordLabel.setVisible(value);
        password.setVisible(value);
        login.setVisible(value);
        cancel.setVisible(value);
    }

    /**
     * Allows for toggling the fields and Labels in the scene, if they are logging in or not
     *
     * @param value which specifies the boolean visibility-modifier for the fields/labels/buttons
     * @author Sarah
     */
    private void changeChooseSystemVisible(boolean value) {
        guestUserLogin.setVisible(value);
        adminUserLogin.setVisible(value);
    }

    public static String getUsernameString() {
        return usernameString;
    }

    public static IUser getAdminUser() {
        return adminUser;
    }

    /**
     * Methods which handle the changing of the FXMLs. Includes:
     * - Close the window (and thus the main process)
     * - Cancelling the login action
     *
     * @author Sarah
     */
    //region
    @FXML
    public void handleClose(MouseEvent event) {
        App.closeWindow();
    }

    @FXML
    public void handleCancel(MouseEvent event) {
        changeChooseSystemVisible(true);
        changeLoginFieldsVisible(false);
    }
    //endregion
}
