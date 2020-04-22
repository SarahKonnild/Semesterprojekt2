package org.openjfx.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.openjfx.interfaces.ICast;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ModifyCastController implements Initializable {

    @FXML
    private AnchorPane basePane;
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
    private Label close;
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
    private TextField castName;
    @FXML
    private TextField regDKID;

    private boolean creationState;

    private ObservableList<ICast> castObservableList;
    private ObservableList<ICast> chosenCastObservable;
    private ICast chosenCast;
    private ArrayList<ICast> chosenCastArray;
    private ArrayList<ICast> castSearchResult;
    private Object obj;
    private ArrayList<Object> chosenObjectsList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);
    }

    @FXML
    public void handleSearch(ActionEvent event){
        String searchText = searchField.getText();
        resultList.getItems().clear();
        castSearchResult = App.getSystemInstance().searchCast(searchText);
        if(castSearchResult != null && !searchField.getText().isEmpty()){
            castObservableList = FXCollections.observableArrayList(castSearchResult);
            resultList.setItems(castObservableList);
            searchField.clear();
        }
        else{
            errorMessageSearch.setVisible(true);
        }
    }

    @FXML
    public void handleSeeRolelist(ActionEvent event){
        searchField.setText("You gon' goofed");
    }

    @FXML
    public void handleCreateNew(ActionEvent event){
        ICast cast = LoginSystemController.getAdminUser().addNewCastToDatabase(castName.getText(),Integer.parseInt(regDKID.getText()));
        if(cast != null){
            errorMessage.setText("Medvirkende oprettet");
            castSearchResult = new ArrayList<>();
            castSearchResult.add(cast);
            //TODO perhaps implement filtered update, i.e. if user searched for Hans but made a new person named Jens, it will clear the Listview and add the new element. If another Hans is made, append.
            resultList.setItems(FXCollections.observableArrayList(castSearchResult));
            clearFields();
        }else{
            searchField.setText("WRONG");
            errorMessage.setText("Medvirkende blev ikke oprettet");
        }
        resultList.refresh();
    }

    @FXML
    public void handleDelete(ActionEvent event){
        if(!castObservableList.isEmpty()){
            chosenCast = (ICast) resultList.getSelectionModel().getSelectedItem();
            creationState = chosenCast.deleteCast();
            if(creationState){
                errorMessage.setText("Medvirkende slettet");
                clearFields();
            }else{
                errorMessage.setText("Fejl, medvirkende blev ikke slettet");
            }
        }else{
            errorMessage.setText("Fejl, ingen medvirkende valgt");
        }
        resultList.refresh();
    }

    @FXML
    public void handleMerge(ActionEvent event){
        if(!castObservableList.isEmpty()){
            chosenCastObservable = resultList.getSelectionModel().getSelectedItems();
            if(chosenCastObservable.size() == 2){
                creationState = chosenCastObservable.get(0).mergeCastMembers(chosenCastObservable.get(1));
                if(creationState){
                    errorMessage.setText("Medvirkende sammenflettet");
                    clearFields();
                    resultList.refresh();
                }else{
                    errorMessage.setText("Fejl, medvirkende blev ikke sammenflettet");
                }
            }else if(chosenCastObservable.size() <= 1){
                errorMessage.setText("Fejl, for få medvirkende valgt");
            } else{
                errorMessage.setText("Fejl, for mange medvirkende valgt");
            }
        } else{
            errorMessage.setText("Fejl, ingen medvirkende valgt");
        }

    }

    @FXML
    public void handleSave(ActionEvent event){
        if(!castObservableList.isEmpty()){
            chosenCast = (ICast) resultList.getSelectionModel().getSelectedItem();
            creationState = chosenCast.updateCast(castName.getText(), Integer.parseInt(regDKID.getText()));
            if(creationState){
                errorMessage.setText("Medvirkende opdateret");
                clearFields();
            }else{
                errorMessage.setText("Fejl, medvirkende blev ikke opdateret");
            }
        }else{
            errorMessage.setText("Fejl, ingen medvirkende valgt");
        }
        resultList.refresh();
    }

    @FXML
    public void handleResultChosen(MouseEvent event){
        resultList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        chosenCastObservable = resultList.getSelectionModel().getSelectedItems();
        chosenCast = (ICast) resultList.getSelectionModel().getSelectedItem();
        castName.setText(chosenCast.getName());
        regDKID.setText(String.valueOf(chosenCast.getRegDKID()));
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

    private void clearFields(){
        castName.clear();
        regDKID.clear();
    }
    
}
