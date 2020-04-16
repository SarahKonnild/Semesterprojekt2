package org.openjfx.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.openjfx.interfaces.IBroadcast;
import org.openjfx.interfaces.ICast;

public class AddAssignController implements Initializable {
    @FXML
    private Button searchButton;
    @FXML
    private Button assignButton;
    @FXML
    private Label errorMsg;
    @FXML
    private ListView searchResult;
    @FXML
    private TextField roleName;
    @FXML
    private TextField nameField;
    @FXML
    private TextField regDKField;

    private ArrayList<ICast> castSearchResult;
    private ObservableList<ICast> castObservableList;

    private ICast chosenCast;

    private boolean creationState;

    private IBroadcast chosenBroadcast;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chosenBroadcast = LandingPageController.getChosenBroadcast();
    }
    //TODO Add some comments for the methods to explain what they do
    /**
     * Method acts when the search button is pressed. It will take the name in the <code>nameField</code> and pass it to the search
     * method in <code>System</code> that then will return an ArrayList over the castMembers. This method will pass it to an <code>ObservableList</code>
     * and show it in the listView.
     * @param event
     */
    @FXML
    public void handleSearchButton(MouseEvent event){
        String searchText = nameField.getText();
        searchResult.getItems().clear();
        castSearchResult = App.getSystemInstance().searchCast(searchText);
        if (castSearchResult != null) {
            castObservableList = FXCollections.observableArrayList(castSearchResult);
            searchResult.setItems(castObservableList);
            nameField.clear();
        } else {
            errorMsg.setVisible(true);
            errorMsg.setText("Ingen søgeresultater fundet");
        }
    }

    @FXML
    public void handleSearchResultChosen(MouseEvent event){
        Object obj = searchResult.getSelectionModel().getSelectedItem();
        if(obj instanceof ICast){
            chosenCast = (ICast) obj;
            nameField.setText(chosenCast.getName());
            regDKField.setText(String.valueOf(chosenCast.getRegDKID()));
            roleName.setDisable(false);
            assignButton.setDisable(false);
        }
    }

    @FXML
    public void handleAssignButton(MouseEvent event){
        creationState = LandingPageController.getChosenBroadcast().assignCast(chosenCast, roleName.getText());
        if(creationState){
            errorMsg.setText("Medvirkende tilknyttet");
            assignButton.setDisable(true);
            nameField.clear();
            regDKField.clear();
            roleName.clear();
        } else{
            errorMsg.setText("Fejl, medvirkende blev ikke tilknyttet");
        }
    }

}
