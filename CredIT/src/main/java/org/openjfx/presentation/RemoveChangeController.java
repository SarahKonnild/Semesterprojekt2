package org.openjfx.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.openjfx.interfaces.IBroadcast;
import org.openjfx.interfaces.ICast;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RemoveChangeController implements Initializable {
    @FXML
    private Button saveChanges;
    @FXML
    private Button removeCast;
    @FXML
    private Label errorMsg;
    @FXML
    private ListView castList;
    @FXML
    private TextField castName;
    @FXML
    private TextField regDKField;
    @FXML
    private TextField roleNameField;

    private boolean creationState;

    private IBroadcast chosenBroadcast;

    private ICast chosenCast;

    private ObservableList<ICast> broadcastCastObsList;

    private Object obj;


    /**
     * Initializes the scene with a list of broadcast members already in the list (if any), providing an overview of the people that are already assigned
     * to the broadcast that was chosen in the LandingPage.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chosenBroadcast = LandingPageController.getChosenBroadcast();
        ArrayList<ICast> broadcastCastMembers = new ArrayList<>();
        for (String key : chosenBroadcast.getCastMap().keySet()) {
            ArrayList<ICast> peoplePerRole = chosenBroadcast.getCastMap().get(key);
            for (ICast cast : peoplePerRole) {
                cast.setRole(key);
                broadcastCastMembers.add(cast);
            }
        }
        broadcastCastObsList = FXCollections.observableArrayList(broadcastCastMembers);
        castList.setItems(broadcastCastObsList);
    }

    //Might not be relevant for now. It would change the role name of the people that are in the broadcast right now, and save that to the ICast that is
    //assigned to that role right now. This is a nice to have thing and not crucial for the demo on tuesday.
    @FXML
    public void handleSaveChanges(MouseEvent event) {
        //IF FAIL: update errorMsg.setVisible(true) and errorMsg.setText("Fejl, ændringerne blev ikke gemt")

    }

    /**
     * Fetches the cast member that has been chosen on the ListView and removes/unassigns them from their role on that broadcast.
     * If successful, a success message is written to the user. If not, an error message will appear.
     *
     * @param event
     */
    @FXML
    public void handleRemoveCast(MouseEvent event) {
        creationState = chosenBroadcast.unassignCast(chosenCast, roleNameField.getText());
        if (creationState) {
            broadcastCastObsList.remove(chosenCast);
            castList.setItems(broadcastCastObsList);
            errorMsg.setText("Medvirkende fjernet");
        } else {
            errorMsg.setText("Fejl opstået, medvirkende ikke fjernet");
        }
    }

    /**
     * Creates a new object from the element of the ListView/Observable list that has been chosen, and casts that to an instance of a ICast object.
     * This means that the information on the attributes can then be printed to the relevant textfields, and thus presented to the user.
     *
     * @param event
     */
    @FXML
    public void handleSearchResultChosen(MouseEvent event) {
        obj = castList.getSelectionModel().getSelectedItem();
        if (obj instanceof ICast) {
            chosenCast = (ICast) obj;
            castName.setText(chosenCast.getName());
            regDKField.setText(String.valueOf(chosenCast.getRegDKID()));
            roleNameField.setText(chosenCast.getRole());
        }
    }

}
