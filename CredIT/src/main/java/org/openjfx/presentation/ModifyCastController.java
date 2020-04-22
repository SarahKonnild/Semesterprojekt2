package org.openjfx.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.openjfx.interfaces.ICast;

import java.net.URL;
import java.util.ArrayList;
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
        //else{
//            //TODO errorMessage.setVisible(true)
//        }
    }

    @FXML
    public void handleSeeRolelist(ActionEvent event){
        searchField.setText("You gon' goofed");
    }

    @FXML
    public void handleCreateNew(ActionEvent event){
        System.out.println("hej4");
        ICast cast = LoginSystemController.getAdminUser().addNewCastToDatabase(castName.getText(),Integer.parseInt(regDKID.getText()));
        if(cast != null){
            //TODO errormessage.setText("Medvirkende Oprettet");
            castSearchResult = new ArrayList<>();
            castSearchResult.add(cast);
            //TODO perhaps implement filtered update, i.e. if user searched for Hans but made a new person named Jens, it will clear the Listview and add the new element. If another Hans is made, append.
            resultList.setItems(FXCollections.observableArrayList(castSearchResult));
            clearFields();
        }else{
            searchField.setText("WRONG");
            //TODO errormessage.setText("Fejl, medvirkende blev ikke oprettet");
        }
        resultList.refresh();
    }

    @FXML
    public void handleDelete(ActionEvent event){
        if(!castObservableList.isEmpty()){
            chosenCast = (ICast) resultList.getSelectionModel().getSelectedItem();
            creationState = chosenCast.deleteCast();
            if(creationState){
                //TODO errormessage.setText("Medvirkende slettet");
                clearFields();
            }else{
                //TODO errormessage.setText("Fejl, medvirkende blev ikke slettet");
            }
        }else{
            //TODO errormessage.setText("Fejl, ingen medvirkende valgt");
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
                    //TODO errormessage.setText("Medvirkende sammenflettet");
                    clearFields();
                    resultList.refresh();
                }else{
                    //TODO errormessage.setText("Fejl, medvirkende blev ikke sammenflettet");
                }
            }else if(chosenCastObservable.size() <= 1){
                //TODO errormessage.setText("Fejl, for få medvirkende valgt");
            } else{
                //TODO errormessage.setText("Fejl, for mange medvirkende valgt);
            }
        } else{
            //TODO errormessage.setText("Fejl, ingen medvirkende valgt");
        }

    }

    @FXML
    public void handleSave(ActionEvent event){
        if(!castObservableList.isEmpty()){
            chosenCast = (ICast) resultList.getSelectionModel().getSelectedItem();
            creationState = chosenCast.updateCast(castName.getText(), Integer.parseInt(regDKID.getText()));
            if(creationState){
                //TODO errormessage.setText("Medvirkende opdateret");
                clearFields();
            }else{
                //TODO errormessage.setText("Fejl, medvirkende blev ikke opdateret");
            }
        }else{
            //TODO errormessage.setText("Fejl, ingen medvirkende valgt");
        }
        resultList.refresh();
    }

    @FXML
    public void handleResultChosen(MouseEvent event){
        chosenCastObservable = resultList.getSelectionModel().getSelectedItems();
        chosenCast = chosenCastObservable.get(0);
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

    private void clearFields(){
        castName.clear();
        regDKID.clear();
    }
    
}
