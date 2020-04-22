package org.openjfx.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


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

    private static Stage helpStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createNew.setVisible(true);
        delete.setVisible(true);
        save.setVisible(true);
        modifyCast.setVisible(true);
    }


    @FXML
    public void handleSearch(ActionEvent event){

    }

    @FXML
    public void handleChangeCast(ActionEvent event){
        App.setAssignCastModifier("broadcast");
        App.handleUnassignAssignStage();
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
        App.handleAdminPage();
    }

    @FXML
    public void handleHelp(MouseEvent event){
        App.handleHelpStage();
    }

    public static Stage getHelpStage(){
        return helpStage;
    }

}
