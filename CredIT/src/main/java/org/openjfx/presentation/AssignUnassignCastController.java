package org.openjfx.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.openjfx.interfaces.IBroadcast;
import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IMovie;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AssignUnassignCastController implements Initializable {

    //FXML Attributes
    //region
    @FXML
    private AnchorPane basePane;
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

    private IMovie movie;
    private IBroadcast broadcast;
    private ArrayList<ICast> castSearchResult;
    private ObservableList<ICast> castObservableList;
    private ICast chosenCast;

    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);
        if(App.getAssignCastModifier().equals("movie")){
            movie = ModifyMovieController.getChosenMovie();
            broadcast = null;
        }else if(App.getAssignCastModifier().equals("broadcast")){
            broadcast = ModifyBroadcastController.getChosenBroadcast();
            movie = null;
        }
    }

    //Everything do do with manipulating the ListView (search,choose)
    //region
    @FXML
    public void handleSearch(ActionEvent event){
        String searchText = searchField.getText();
        resultList.getItems().clear();
        castSearchResult = App.getSystemInstance().searchCast(searchText);
        if(castSearchResult != null && !searchField.getText().isEmpty()){
            castObservableList = FXCollections.observableArrayList(castSearchResult);
            resultList.setItems(castObservableList);
            searchField.clear();
            resultList.setDisable(false);
        }
        else{
            errorMessage.setVisible(true);
        }
    }

    @FXML
    public void handleResultChosen(MouseEvent event){
        chosenCast = (ICast) resultList.getSelectionModel().getSelectedItem();
        name.setText(chosenCast.getName());
        regDKID.setText(chosenCast.getRegDKID());
    }
    //endregion

    //Assign/Unassign Cast
    //region
    @FXML
    public void handleAssignNewCast(ActionEvent event){
        if(App.getAssignCastModifier().equals("movie")){
            ModifyMovieController.getChosenMovie().assignCast(chosenCast, rolenameField.getText());
        }else if(App.getAssignCastModifier().equals("broadcast")){
            ModifyBroadcastController.getChosenBroadcast().assignCast(chosenCast,rolenameField.getText());
        }
    }

    @FXML
    public void handleUnassignCast(ActionEvent event){
        if(App.getAssignCastModifier().equals("movie")){
            ModifyMovieController.getChosenMovie().unassignCast(chosenCast,rolenameField.getText());
        }else if(App.getAssignCastModifier().equals("broadcast")){
            ModifyBroadcastController.getChosenBroadcast().unassignCast(chosenCast,role.getText());
        }

    }
    //endregion

    //Save Changes
    //region
    @FXML
    public void handleSave(ActionEvent event){
        if(App.getAssignCastModifier().equals("movie")){
            //To update the assigned cast's role for the movie
        }else if(App.getAssignCastModifier().equals("broadcast")){
            //To update the assigned cast's role for the broadcast
        }
    }

    //endregion

    //Change the scenes and close the stage
    //region
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
    public void handleClose(ActionEvent event){
        App.closeWindow();
    }
    //endregion

    //Show the desired Labels and Fields/Buttons
    //region
    @FXML
    public void handleAssignCastOption(MouseEvent event){
        setFieldsAndLabelsVisible(true);
        assignButton.setVisible(true);
        unassignButton.setVisible(false);
        save.setVisible(false);
    }


    @FXML
    public void handleUnassignCastOption(MouseEvent event){
        setFieldsAndLabelsVisible(true);
        unassignButton.setVisible(true);
        save.setVisible(true);
        assignButton.setVisible(false);
    }

    public void setFieldsAndLabelsVisible(boolean value){
        search.setVisible(value);
        searchField.setVisible(value);
        name.setVisible(value);
        nameField.setVisible(value);
        regDKID.setVisible(value);
        regDKField.setEditable(value);
        regDKField.setVisible(value);
        role.setVisible(value);
        rolenameField.setVisible(value);
    }
    //endregion
}
