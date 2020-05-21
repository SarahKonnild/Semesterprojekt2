package org.openjfx.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    //endregion

    //Local variables
    //region
    private IMovie movie;
    private IBroadcast broadcast;
    private ArrayList<ICast> castSearchResult;
    private ObservableList<ICast> castObservableList;
    private ICast chosenCast;
    private ArrayList<PCast> roleArray;
    private PCast chosenPCast;

    //endregion

    /**
     * Checks if the controller has been given either a movie or broadcast from the previous scene, to ensure that
     * the correct object is being assigned/unassigned to/from.
     *
     * @param location
     * @param resources
     * @author Sarah
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);
        if (App.getAssignCastModifier().equals("movie")) {
            movie = ModifyMovieController.getChosenMovie();
            broadcast = null;
        } else if (App.getAssignCastModifier().equals("broadcast")) {
            broadcast = ModifyBroadcastController.getChosenBroadcast();
            movie = null;
        }
        roleArray = new ArrayList<>();
    }

    //Everything do do with manipulating the ListView (search,choose)
    //region

    /**
     * Takes the string that the user has entered into the searchfield and runs a search in the database, using the
     * searchCast()-method, and adding the ArrayList of results into an ObservableList, so the results can be presented
     * to the user in the ListView.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleSearch(MouseEvent event) {
        String searchText = searchField.getText();
        resultList.getItems().clear();
        castSearchResult = new ArrayList<>(App.getSystemInstance().searchCast(searchText));
        if (!castSearchResult.isEmpty() && !searchField.getText().isEmpty()) {
            resultList.setDisable(false);
            resultList.setItems(FXCollections.observableArrayList(castSearchResult));
            searchField.clear();
        } else {
            errorMessage.setVisible(true);
        }
    }

    /**
     * Takes the Object that has been selected in the ListView and casts it to an ICast type-object, and sets the
     * object's attributes to be written into the TextFields that are related to the attributes.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleResultChosen(MouseEvent event) {
        Object obj = resultList.getSelectionModel().getSelectedItem();
        if (obj instanceof ICast) {
            chosenCast = (ICast) resultList.getSelectionModel().getSelectedItem();
            nameField.setText(chosenCast.getName());
            regDKField.setText(chosenCast.getRegDKID());
        } else if (obj instanceof PCast) {
            chosenPCast = (PCast) obj;
            nameField.setText(chosenPCast.getCast().getName());
            regDKField.setText(chosenPCast.getCast().getRegDKID());
            rolenameField.setText(chosenPCast.getRole());
        }
    }
    //endregion

    //Assign/Unassign Cast
    //region

    /**
     * First performs a check on whether or not the object that was chosen in the prior scene was a movie or a broadcast
     * and then performs the assignment of the cast-member to that specific object, using the appropriate method.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleAssignNewCast(MouseEvent event) {
        if (App.getAssignCastModifier().equals("movie")) {
            if (ModifyMovieController.getChosenMovie().getCastMap().containsKey(chosenCast)) {
                errorMessage.setText(chosenCast.getName() + "er allerede på filmen");
            } else {
                ModifyMovieController.getChosenMovie().assignCast(chosenCast, rolenameField.getText());
                errorMessage.setText(chosenCast.getName() + " tilføjet");
            }
        } else if (App.getAssignCastModifier().equals("broadcast")) {
            if (ModifyBroadcastController.getChosenBroadcast().getCastMap().containsKey(chosenCast)) {
                errorMessage.setText(chosenCast.getName() + "er allerede på udsendelsen");
            } else {
                ModifyBroadcastController.getChosenBroadcast().assignCast(chosenCast, rolenameField.getText());
                errorMessage.setText(chosenCast.getName() + " tilføjet");
            }
        }
        clearFields();
    }

    /**
     * First performs a check on whether or not the object that was chosen in the prior scene was a movie or a broadcast
     * and then performs the unassignment of the cast-member from that specific object, using the appropriate method.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleUnassignCast(MouseEvent event) {
        if (App.getAssignCastModifier().equals("movie")) {
            ModifyMovieController.getChosenMovie().unassignCast(chosenPCast.getCast(), chosenPCast.getRole());
        } else if (App.getAssignCastModifier().equals("broadcast")) {
            ModifyBroadcastController.getChosenBroadcast().unassignCast(chosenPCast.getCast(), chosenPCast.getRole());
        }
        roleArray.remove(chosenPCast);
        resultList.setItems(FXCollections.observableArrayList(roleArray));
        errorMessage.setText(chosenPCast.getCast().getName() + " fjernet");
        clearFields();
    }
    //endregion

    //Save Changes
    //region

    /**
     * Uses the AssignCastModifier that is given in App to check which methods to run on the cast, in order to reassign
     * the cast member with the new role they have on the movie/broadcast.
     * With the current setup, the cast must first be unassigned from the movie/broadcast and then reassigned
     * with their new role, taking the value that is written in the role-TextField.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleSave(MouseEvent event) {
        //must unassign the cast with the role they had and then reassign with the current setup.
        if (App.getAssignCastModifier().equals("movie")) {
            ModifyMovieController.getChosenMovie().unassignCast(chosenPCast.getCast(), chosenPCast.getRole());
            ModifyMovieController.getChosenMovie().assignCast(chosenPCast.getCast(), rolenameField.getText());
        } else if (App.getAssignCastModifier().equals("broadcast")) {
            ModifyBroadcastController.getChosenBroadcast().unassignCast(chosenPCast.getCast(), chosenPCast.getRole());
            ModifyBroadcastController.getChosenBroadcast().assignCast(chosenPCast.getCast(), rolenameField.getText());
        }
        errorMessage.setText(chosenPCast.getCast().getName() + " opdateret");

        //Setting the new role on the PCast so it updates on the fly
        chosenPCast.setRole(rolenameField.getText());
        resultList.setItems(FXCollections.observableArrayList(roleArray));
        clearFields();

    }

    //endregion

    //Change the scenes and close the stage
    //region

    /**
     * Opens the Help-stage.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleHelp(MouseEvent event) {
        App.handleHelpStage();
    }

    /**
     * Performs a check on whether it was a movie or a broadcast that was chosen in the prior stage, and returns to
     * the scene that is related to the result of that check.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleBack(MouseEvent event) {
        if (App.getAssignCastModifier().equals("movie")) {
            App.handleModifyMoviePage();
        } else if (App.getAssignCastModifier().equals("broadcast")) {
            App.handleModifyBroadcastPage();
        }
    }

    /**
     * Closes the program/primary stage.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleClose(MouseEvent event) {
        App.closeWindow();
    }
    //endregion

    //Show the desired Labels and Fields/Buttons
    //region

    /**
     * Sets visible the fields and Buttons that are related to the assign-cast operations
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleAssignCastOption(MouseEvent event) {
        resultList.getItems().clear();

        clearFields();
        setFieldsAndLabelsVisible(true);

        assignButton.setVisible(true);
        unassignButton.setVisible(false);
        save.setVisible(false);
        search.setVisible(true);
        searchField.setVisible(true);
    }

    /**
     * Sets visible the fields and Buttons that are related to the unassign-cast operations.
     * Also runs the App-methods for fetching the rolelist that is attached to the movie/broadcast,
     * to set the resultList to show the list of casts on it.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleUnassignCastOption(MouseEvent event) {
        resultList.getItems().clear();

        if (App.getAssignCastModifier().equals("movie")) {
            resultList.getItems().clear();
            resultList.setItems(FXCollections.observableArrayList(App.getMovieRoleArray()));
        } else if (App.getAssignCastModifier().equals("broadcast")) {
            resultList.getItems().clear();
            resultList.setItems(FXCollections.observableArrayList(App.getBroadcastRoleArray()));
        }

        clearFields();
        searchField.setVisible(false);
        search.setVisible(false);
        unassignButton.setVisible(true);
        save.setVisible(true);
        assignButton.setVisible(false);
    }
    //endregion

    /**
     * Allows for toggling the fields and Labels in the scene
     *
     * @param value
     * @author Sarah
     */
    //region
    public void setFieldsAndLabelsVisible(boolean value) {
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

    public void clearFields() {
        nameField.clear();
        regDKField.clear();
        rolenameField.clear();
    }
    //endregion
}
