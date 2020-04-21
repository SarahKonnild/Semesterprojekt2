package org.openjfx.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyBroadcastController implements Initializable {

    @FXML
    private Button search;
    @FXML
    private Button modifyCast;
    @FXML
    private Button createNew;
    @FXML
    private Button delete;
    @FXML
    private Button save;
    @FXML
    private Label help;
    @FXML
    private Label back;
    @FXML
    private ListView searchResultList;
    @FXML
    private TextField searchField;
    @FXML
    private TextField broadcastName;
    @FXML
    private TextField productionCompany;
    @FXML
    private TextField season;
    @FXML
    private TextField episode;
    @FXML
    private TextField day;
    @FXML
    private TextField month;
    @FXML
    private TextField year;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    public void handleSearch(ActionEvent event){

    }

    @FXML
    public void handleChangeCast(ActionEvent event){

    }

    @FXML
    public void handleDeleteCast(ActionEvent event){

    }

    @FXML
    public void handleSaveCast(ActionEvent event){

    }

    @FXML
    public void handleCreateCast(ActionEvent event){

    }

    @FXML
    public void handleSearchResultChosen(MouseEvent event){

    }

    @FXML
    public void handleBack(MouseEvent event){
        try {
            Parent value = FXMLLoader.load(LoginSystemController.class.getResource("AdministratorPage.fxml"));
            App.getScene().setRoot(value);
            App.getStage().setHeight(271);
            App.getStage().setWidth(601);
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
