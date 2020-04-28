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
    //FXML Attributes
    //region
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
    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);
    }

    //Everything to do with the ListView (search, choose)
    //region
    @FXML
    public void handleSearch(MouseEvent event){

    }

    @FXML
    public void handleResultChosen(MouseEvent event){

    }

    //endregion


    //Create Movie
    //region
    @FXML
    public void handleCreateNew(MouseEvent event){

    }
    //endregion

    //Delete Movie
    //region
    @FXML
    public void handleDelete(MouseEvent event){

    }
    //endregion

    //Save Movie
    //region
    @FXML
    public void handleSave(MouseEvent event){

    }
    //endregion


    //All the methods which change the scene, open the help stage or close the program.
    //region
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

    @FXML
    public void handleChangeCast(ActionEvent event){
        App.setAssignCastModifier("movie");
        App.handleUnassignAssignStage();
    }
    //endregion
}
