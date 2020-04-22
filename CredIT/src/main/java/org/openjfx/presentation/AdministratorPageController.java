package org.openjfx.presentation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdministratorPageController implements Initializable {
    @FXML
    private AnchorPane basePane;
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

        App.handleMoveWindow(basePane);
    }

    @FXML
    public void handleAdministrateUsers(MouseEvent event){    }

    @FXML
    private void handleAdministrateCast(MouseEvent event){
        App.handleModifyCastPage();
    }

    @FXML
    private void handleAdministrateProduction(MouseEvent event){
        App.handleModifyProductionPage();
    }

    @FXML
    private void handleAdministrateBroadcast(MouseEvent event){
        App.handleModifyBroadcastPage();
    }

    @FXML
    private void handleAdministrateMovie(MouseEvent event){
        App.handleModifyMoviePage();
    }

    @FXML
    public void handleHelp(MouseEvent event){ App.handleHelpStage();}

    @FXML
    public void handleClose(MouseEvent event){ App.closeWindow();}

}
