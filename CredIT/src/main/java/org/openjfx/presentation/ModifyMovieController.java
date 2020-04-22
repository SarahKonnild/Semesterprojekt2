package org.openjfx.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyMovieController implements Initializable {

    @FXML
    private AnchorPane basePane;
    @FXML
    private Button search;
    @FXML
    private Button changeCast;
    @FXML
    private Button createNew;
    @FXML
    private Button delete;
    @FXML
    private Label close;
    @FXML
    private Button save;
    @FXML
    private Label help;
    @FXML
    private Label back;
    @FXML
    private Label errorMessageSearch;
    @FXML
    private Label errorMessage;
    @FXML
    private ListView resultList;
    @FXML
    private TextField searchField;
    @FXML
    private TextField movieName;
    @FXML
    private TextField productionCompany;
    @FXML
    private TextField releaseYear;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);
    }

    @FXML
    public void handleSearch(ActionEvent event){

    }

    @FXML
    public void handleChangeCast(ActionEvent event){
        App.setAssignCastModifier("movie");
        App.handleUnassignAssignStage();
    }

    @FXML
    public void handleCreateNew(ActionEvent event){

    }

    @FXML
    public void handleDelete(ActionEvent event){

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
        App.handleAdminPage();
    }

    @FXML
    public void handleClose(MouseEvent event){App.closeWindow();}

}
