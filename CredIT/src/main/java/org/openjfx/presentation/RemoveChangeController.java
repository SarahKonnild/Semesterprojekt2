package org.openjfx.presentation;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chosenBroadcast = LandingPageController.getChosenBroadcast();
        ArrayList<ICast> broadcastCastMembers = new ArrayList<>();
        for(String key : chosenBroadcast.getCastMap().keySet()){
            ArrayList<ICast> peoplePerRole = chosenBroadcast.getCastMap().get(key);
            for(ICast cast : peoplePerRole){
                cast.setRole(key);
                broadcastCastMembers.add(cast);
            }
        }
        broadcastCastObsList = FXCollections.observableArrayList(broadcastCastMembers);
        castList.setItems(broadcastCastObsList);
    }

    @FXML
    public void handleSaveChanges(MouseEvent event){
        //IF FAIL: update errorMsg.setVisible(true) and errorMsg.setText("Fejl, ændringerne blev ikke gemt")

    }

    @FXML
    public void handleRemoveCast(MouseEvent event){
        creationState = chosenBroadcast.unassignCast(chosenCast, roleNameField.getText());
        if(creationState){
            errorMsg.setText("Medvirkende fjernet");
        } else{
            errorMsg.setText("Fejl opstået, medvirkende ikke fjernet");
        }
    }

    @FXML
    public void handleSearchResultChosen(MouseEvent event){
        obj = castList.getSelectionModel().getSelectedItem();
        if(obj instanceof ICast){
            chosenCast = (ICast) obj;
            castName.setText(chosenCast.getName());
            regDKField.setText(String.valueOf(chosenCast.getRegDKID()));
            roleNameField.setText(chosenCast.getRole());
        }
    }

}
