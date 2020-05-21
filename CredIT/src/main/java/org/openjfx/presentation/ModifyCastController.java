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
    private ObservableList<ICast> chosenCastObservable;
    private ICast chosenCast;
    private ArrayList<ICast> castSearchResult;
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
     *
     * @author Sarah
     */
    @FXML
    public void handleSearch(ActionEvent event) {
        String searchText = searchField.getText();
        resultList.getItems().clear();
        castSearchResult = new ArrayList<>(App.getSystemInstance().searchCast(searchText));
        if (!castSearchResult.isEmpty() && !searchField.getText().isEmpty()) {
            resultList.setDisable(false);
            resultList.setItems(FXCollections.observableArrayList(castSearchResult));
            searchField.clear();
        } else {
            errorMessageSearch.setVisible(true);
        }
    }

    /**
     * Enables the ability to choose multiple items from the ListView, and writes the ObservableList
     * to it. From this list of multiple choices, the first chosen object will have its information
     * written to the related fields.
     *
     * @author Sarah
     */
    @FXML
    public void handleResultChosen(MouseEvent event) {
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
     *
     * @author Sarah
     */
    @FXML
    public void handleSeeRoleList(MouseEvent event) {
        if (!App.getAllCastRoles(chosenCast).isEmpty()) {
            resultList.setItems(FXCollections.observableArrayList(App.getAllCastRoles(chosenCast)));
            resultList.setDisable(true);
        } else {
            errorMessage.setText("Fejl, ingen roller fundet");
        }

    }
    //endregion

    //Create Cast
    //region

    /**
     * Takes the information written in the fields and uses those as the parameters for the createCast method
     * in the domain layer. Thus creates a new entry into the database.
     *
     * @author Sarah
     */
    @FXML
    public void handleCreateNew(ActionEvent event) {
        if (!castName.getText().isEmpty() && !regDKID.getText().isEmpty()) {
            ICast cast = LoginSystemController.getAdminUser().addNewCastToDatabase(castName.getText(), regDKID.getText());
            if (cast != null) {
                resultList.setDisable(false);
                errorMessage.setText(cast.getName() + " oprettet");
                if (castSearchResult == null) {
                    castSearchResult = new ArrayList<>();
                }
                castSearchResult.clear();
                castSearchResult.add(cast);
                resultList.setItems(FXCollections.observableArrayList(castSearchResult));
                clearFields();
            } else {
                errorMessage.setText("Medvirkende blev ikke oprettet");
            }
            resultList.refresh();
        } else {
            errorMessage.setText("Fejl, venligst udfyld alle felter");
        }
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
     *
     * @author Sarah
     */
    @FXML
    public void handleMerge(ActionEvent event) {
        if (chosenCastObservable.size() == 2) {
            int tempCastId = chosenCastObservable.get(0).getId();
            creationState = chosenCastObservable.get(0).mergeCastMembers(chosenCastObservable.get(1));
            if (creationState) {
                errorMessage.setText(chosenCastObservable.get(1).getName() + " sammenflettet med " + chosenCastObservable.get(0).getName());
                ArrayList<ICast> newCast = new ArrayList<>(App.getSystemInstance().searchCast(tempCastId));
                if (newCast != null) {
                    castSearchResult.clear();
                    castSearchResult.add((ICast) newCast.get(0));
                    resultList.setItems(FXCollections.observableArrayList(castSearchResult));
                }
                clearFields();
                resultList.refresh();
            } else {
                errorMessage.setText("Fejl, medvirkende blev ikke sammenflettet");
            }
        } else if (chosenCastObservable.size() <= 1) {
            errorMessage.setText("Fejl, for få medvirkende valgt");
        } else {
            errorMessage.setText("Fejl, for mange medvirkende valgt");
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
     *
     * @author Sarah
     */
    @FXML
    public void handleDelete(ActionEvent event) {
        creationState = chosenCast.delete();
        if (creationState) {
            errorMessage.setText(chosenCast.getName() + " slettet");
            resultList.getItems().remove(chosenCast);
            clearFields();
        } else {
            errorMessage.setText("Fejl, " + chosenCast.getName() + " blev ikke slettet");
        }
        resultList.refresh();
    }
    //endregion

    //Save Cast
    //region

    /**
     * Saves the changes that are made to a cast object into the databases. Reads the values of all the fields
     * and saves the changes to the object that was originally chosen.
     *
     * @author Sarah
     */
    @FXML
    public void handleSave(ActionEvent event) {
        if (!castName.getText().isEmpty() && !regDKID.getText().isEmpty()) {
            creationState = chosenCast.update(castName.getText(), regDKID.getText());
            if (creationState) {
                errorMessage.setText(chosenCast.getName() + " opdateret");
                clearFields();
            } else {
                errorMessage.setText("Fejl, " + chosenCast.getName() + " blev ikke opdateret");
            }
            resultList.refresh();
        } else {
            errorMessage.setText("Fejl, udfyld venligst alle felter");
        }
    }
    //endregion

    //Clears the fields that are available for this specific object
    //region
    /**
     * Empties the contents of all the TextFields
     *
     * @author Sarah
     */
    private void clearFields() {
        castName.clear();
        regDKID.clear();
    }
    //endregion

    /**
     * Methods which handle the changing of the FXMLs. Includes:
     * - Close the window (and thus the main process)
     * - Open the help-window
     * - Return to the past scene
     *
     * @author Sarah
     */
    //region
    @FXML
    public void handleHelp(MouseEvent event) {
        App.handleHelpStage();
    }

    @FXML
    public void handleBack(MouseEvent event) { App.handleAdminPage(); }

    @FXML
    public void handleClose(MouseEvent event) {
        App.closeWindow();
    }

    //endregion

}
