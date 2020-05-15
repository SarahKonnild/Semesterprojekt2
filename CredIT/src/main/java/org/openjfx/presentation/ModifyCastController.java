package org.openjfx.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.openjfx.interfaces.IBroadcast;
import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IMovie;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ModifyCastController implements Initializable {

    //FXML Attributes
    //region
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
    //endregion

    //Attributes for the class
    //region
    private boolean creationState;

    private ObservableList<ICast> castObservableList;
    private ObservableList<ICast> chosenCastObservable;
    private ICast chosenCast;
    private ArrayList<ICast> chosenCastArray;
    private ArrayList<ICast> castSearchResult;
    private ArrayList<String> roleArray;
    private Object obj;
    private ArrayList<Object> chosenObjectsList;
    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);
    }

    //Everything do do with manipulating the ListView
    //region
    /**
     * Takes the text that is written in the searchfield and uses that to run the searchCast
     * method in the domain layer's System class. If the list has items, and the searchfield isn't empty,
     * the items returned from the persistence layer will be written to a list which can be printed into
     * the ListView.
     * @param event
     */
    @FXML
    public void handleSearch(ActionEvent event){
        String searchText = searchField.getText();
        resultList.getItems().clear();
        castSearchResult = App.getSystemInstance().searchCast(searchText);
        if(castSearchResult != null && !searchField.getText().isEmpty()){
            resultList.setDisable(false);
            castObservableList = FXCollections.observableArrayList(castSearchResult);
            resultList.setItems(castObservableList);
            searchField.clear();
        }
        else{
            errorMessageSearch.setVisible(true);
        }
    }

    /**
     * Enables the ability to choose multiple items from the ListView, and writes the ObservableList
     * to it. From this list of multiple choices, the first chosen object will have its information
     * written to the related fields.
     * @param event
     */
    @FXML
    public void handleResultChosen(MouseEvent event){
            resultList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            chosenCastObservable = resultList.getSelectionModel().getSelectedItems();
            chosenCast = (ICast) resultList.getSelectionModel().getSelectedItem();
            castName.setText(chosenCast.getName());
            regDKID.setText(String.valueOf(chosenCast.getRegDKID()));

            delete.setDisable(false);
            merge.setDisable(false);
            save.setDisable(false);
            seeRoleList.setDisable(false);
    }

    /**
     * Displays the rolelist for each cast member in the resultList view.
     * @param event
     */
    @FXML
    public void handleSeeRoleList(MouseEvent event){
        HashMap<IBroadcast, String> broadcastRoles = App.getSystemInstance().getCastRolesBroadcast(chosenCast);
        HashMap<IMovie, String> movieRoles = App.getSystemInstance().getCastRolesMovies(chosenCast);

        for(IMovie movie : movieRoles.keySet()){
            String temp = movie.getTitle() + movieRoles.get(movie);
            roleArray.add(temp);
        }
        for(IBroadcast broadcast : broadcastRoles.keySet()){
            String temp = broadcast.getName() + broadcastRoles.get(broadcast);
            roleArray.add(temp);
        }
        resultList.setItems(FXCollections.observableArrayList(roleArray));

    }
    //endregion

    //Create Cast
    //region
    /**
     * Takes the information written in the fields and uses those as the parameters for the createCast method
     * in the domain layer. Thus creates a new entry into the database.
     * @param event
     */
    @FXML
    public void handleCreateNew(ActionEvent event){
        ICast cast = LoginSystemController.getAdminUser().addNewCastToDatabase(castName.getText(),regDKID.getText());
        if(cast != null){
            resultList.setDisable(false);
            errorMessage.setText(cast.getName() + " oprettet");
            if(castSearchResult == null) {
                castSearchResult = new ArrayList<>();
            }
            castSearchResult.add(cast);
            //TODO perhaps implement filtered update, i.e. if user searched for Hans but made a new person named Jens, it will clear the Listview and add the new element. If another Hans is made, append.
            resultList.setItems(FXCollections.observableArrayList(castSearchResult));
            clearFields();
        }else{
            errorMessage.setText("Medvirkende blev ikke oprettet");
        }
        resultList.refresh();
    }
    //endregion

    //Merge Cast
    //region
    /**
     * Checks how many cast members are chosen in the resultslist;
     * If less than 2, an error message will be printed stating that there is not enough cast members chosen
     * If more than 2, an error message will be printed stating that there is too many cast members chosen
     * If exactly 2, the mergeCast method is run on the object, and merges the second cast member into the
     * first cast member that was chosen. If successful, the cast members will be merged; if unsuccessful, an
     * error message is printed.
     * @param event
     */
    @FXML
    public void handleMerge(ActionEvent event){
        if(!castObservableList.isEmpty()){
            chosenCastObservable = resultList.getSelectionModel().getSelectedItems();
            if(chosenCastObservable.size() == 2){
                creationState = chosenCastObservable.get(0).mergeCastMembers(chosenCastObservable.get(1));
                if(creationState){
                    errorMessage.setText(chosenCastObservable.get(1).getName() + " sammenflettet med " + chosenCastObservable.get(0).getName());
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

    //endregion

    //Delete Cast
    //region
    /**
     * Deletes the object that has been chosen in the list of search results, by running the Domain method on
     * the object.
     * If successful, the user is given a success message.
     * If unsuccessful, the user is given an error message.
     * If no objects are chosen and the button is pressed, an error message is written.
     * @param event
     */
    @FXML
    public void handleDelete(ActionEvent event){
        if(!castObservableList.isEmpty()){
            chosenCast = (ICast) resultList.getSelectionModel().getSelectedItem();
            creationState = chosenCast.delete();
            if(creationState){
                errorMessage.setText(chosenCast.getName() + " slettet");
                //TODO Check if it updates the list after it gets deleted
                castObservableList.remove(chosenCast);
                resultList.setItems(FXCollections.observableArrayList(castSearchResult));
                clearFields();
            }else{
                errorMessage.setText("Fejl, " + chosenCast.getName() + " blev ikke slettet");
            }
        }else{
            errorMessage.setText("Fejl, ingen medvirkende valgt");
        }
        resultList.refresh();
    }
    //endregion

    //Save Cast
    //region
    /**
     * Saves the changes that are made to a cast object into the databases. Reads the values of all the fields
     * and saves the changes to the object that was originally chosen.
     * @param event
     */
    @FXML
    public void handleSave(ActionEvent event){
        if(!castObservableList.isEmpty()){
            chosenCast = (ICast) resultList.getSelectionModel().getSelectedItem();
            creationState = chosenCast.update(castName.getText(), regDKID.getText());
            if(creationState){
                errorMessage.setText(chosenCast.getName() + " opdateret");
                clearFields();
            }else{
                errorMessage.setText("Fejl, "+ chosenCast.getName() + " blev ikke opdateret");
            }
        }else{
            errorMessage.setText("Fejl, ingen medvirkende valgt");
        }
        resultList.refresh();
    }
    //endregion


    //Methods that are responsible for changing the scene, closing the stage or opening the help stage.
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

    //endregion


    //Clears the fields that are available for this specific object
    //region
    private void clearFields(){
        castName.clear();
        regDKID.clear();
    }
    //endregion
    
}
