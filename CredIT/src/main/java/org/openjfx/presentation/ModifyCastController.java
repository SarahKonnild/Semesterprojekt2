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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCastController implements Initializable {

    @FXML
    private Button search;
    @FXML
    private Button seeRoleList;
    @FXML
    private Button createNew;
    @FXML
    private Button delete;
    @FXML
    private Button merge;
    @FXML
    private Button save;
    @FXML
    private Label help;
    @FXML
    private Label back;
    @FXML
    private ListView resultList;
    @FXML
    private TextField searchField;
    @FXML
    private TextField castName;
    @FXML
    private TextField regDKID;
    
    private static Stage helpStage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleSearch(ActionEvent event){

    }

    @FXML
    public void handleSeeRolelist(ActionEvent event){

    }

    @FXML
    public void handleCreateNew(ActionEvent event){

    }

    @FXML
    public void handleDelete(ActionEvent event){

    }

    @FXML
    public void handleMerge(ActionEvent event){

    }

    @FXML
    public void handleSave(ActionEvent event){

    }

    @FXML
    public void handleResultChosen(ActionEvent event){

    }

    @FXML
    public void handleHelp(MouseEvent event){
        App.handleHelpStage();
    }

    @FXML
    public void handleBack(MouseEvent event){
        App.handleAdminPage();
    }

    public static Stage getStage(){
        return helpStage;
    }
    
}
