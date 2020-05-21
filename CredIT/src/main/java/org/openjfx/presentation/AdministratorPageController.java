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

    //FXML Attributes
    //region
    @FXML
    private AnchorPane basePane;
    @FXML
    private Label administrateProductionCompanies;
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
    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setText(LoginSystemController.getUsernameString());

        App.handleMoveWindow(basePane);
    }

    /**
     * All these handlers work to change the scene of the primary stage based on the label that is being clicked on
     *
     * @author Sarah
     */
    //region
    @FXML
    public void handleAdministrateProductionCompanies(MouseEvent event) {
        App.handleModifyProductionCompanyStage();
    }

    @FXML
    private void handleAdministrateCast(MouseEvent event) {
        App.handleModifyCastPage();
    }

    @FXML
    private void handleAdministrateProduction(MouseEvent event) {
        App.handleModifyProductionPage();
    }

    @FXML
    private void handleAdministrateBroadcast(MouseEvent event) {
        App.handleModifyBroadcastPage();
    }

    @FXML
    private void handleAdministrateMovie(MouseEvent event) {
        App.handleModifyMoviePage();
    }

    @FXML
    public void handleHelp(MouseEvent event) {
        App.handleHelpStage();
    }

    @FXML
    public void handleClose(MouseEvent event) {
        App.closeWindow();
    }

    //endregion
}
