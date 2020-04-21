package org.openjfx.presentation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdministratorPageController implements Initializable {
    @FXML
    private Label administrateUsers;
    @FXML
    private Label administrateCast;
    @FXML
    private Label administrateProduction;
    @FXML
    private Label administrateBroadcast;
    @FXML
    private Label administrateMovie;
    @FXML
    private Label username;
    @FXML
    private Label help;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setText(LoginSystemController.getUsernameString());
        administrateUsers.setVisible(false); //set invisible due to low importance of use case.
    }

    @FXML
    public void handleAdministrateUsers(MouseEvent event){

    }

    @FXML
    private void handleAdministrateCast(MouseEvent event){
        try {
            Parent value = FXMLLoader.load(LoginSystemController.class.getResource("ModifyCast.fxml"));
            App.getScene().setRoot(value);
            App.getStage().setHeight(420);
            App.getStage().setWidth(600);
            App.getStage().setResizable(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleAdministrateProduction(MouseEvent event){
        try {
            Parent value = FXMLLoader.load(LoginSystemController.class.getResource("ModifyProduction.fxml"));
            App.getScene().setRoot(value);
            App.getStage().setHeight(420);
            App.getStage().setWidth(600);
            App.getStage().setResizable(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleAdministrateBroadcast(MouseEvent event){
        try {
            Parent value = FXMLLoader.load(LoginSystemController.class.getResource("ModifyBroadcast.fxml"));
            App.getScene().setRoot(value);
            App.getStage().setHeight(540);
            App.getStage().setWidth(602);
            App.getStage().setResizable(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleAdministrateMovie(MouseEvent event){
        try {
            Parent value = FXMLLoader.load(LoginSystemController.class.getResource("ModifyMovie.fxml"));
            App.getScene().setRoot(value);
            App.getStage().setHeight(420);
            App.getStage().setWidth(600);
            App.getStage().setResizable(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleHelp(MouseEvent event){
        Parent root;
        try {
            root = FXMLLoader.load(LoginSystemController.class.getResource("Help.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setHeight(400);
            stage.setWidth(600);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
