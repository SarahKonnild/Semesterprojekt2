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

public class AssignUnassignCastController implements Initializable {

    @FXML
    private Button search;
    @FXML
    private Button save;
    @FXML
    private Button assignButton;
    @FXML
    private Button unassignButton;
    @FXML
    private Label help;
    @FXML
    private Label back;
    @FXML
    private Label assignCast;
    @FXML
    private Label unassignCast;
    @FXML
    private Label name;
    @FXML
    private Label regDKID;
    @FXML
    private Label role;
    @FXML
    private Label errorMessage;
    @FXML
    private ListView resultList;
    @FXML
    private TextField searchField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField regDKField;
    @FXML
    private TextField rolenameField;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleSearch(ActionEvent event){

    }

    @FXML
    public void handleAssignNewCast(ActionEvent event){

    }

    @FXML
    public void handleUnassignCast(ActionEvent event){

    }

    @FXML
    public void handleSave(ActionEvent event){

    }

    @FXML
    public void handleResultChosen(MouseEvent event){

    }

    @FXML
    public void handleHelp(MouseEvent event){
        App.handleHelpStage();
    }

    @FXML
    public void handleBack(MouseEvent event){
        if(App.getAssignCastModifier().equals("movie")) {
            App.handleModifyMoviePage();
        } else if(App.getAssignCastModifier().equals("broadcast")){
            App.handleModifyBroadcastPage();
        }
    }

    @FXML
    public void handleAssignCastOption(MouseEvent event){
        setFieldsAndLabelsVisible();
        assignButton.setVisible(true);
        unassignButton.setVisible(false);
        save.setVisible(false);
    }

    @FXML
    public void handleUnassignCastOption(MouseEvent event){
        setFieldsAndLabelsVisible();
        unassignButton.setVisible(true);
        save.setVisible(true);
        assignButton.setVisible(false);
    }

    public void setFieldsAndLabelsVisible(){
        search.setVisible(true);
        searchField.setVisible(true);
        name.setVisible(true);
        nameField.setVisible(true);
        regDKID.setVisible(true);
        regDKField.setEditable(false);
        regDKField.setVisible(true);
        role.setVisible(true);
        rolenameField.setVisible(true);
    }
}
