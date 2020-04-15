package org.openjfx.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LandingPageController implements Initializable {
    @FXML
    private Button createNewCast;
    @FXML
    private Button deleteCast;
    @FXML
    private Button mergeCast;
    @FXML
    private Button saveChangesCast;
    @FXML
    private Button searchCast;
    @FXML
    private Button createNewProduction;
    @FXML
    private Button deleteProduction;
    @FXML
    private Button saveChangesProduction;
    @FXML
    private Button searchProduction;
    @FXML
    private Button assignCast;
    @FXML
    private Button removeCast;
    @FXML
    private Button createBroadcast;
    @FXML
    private Button deleteBroadcast;
    @FXML
    private Button saveChangesBroadcast;
    @FXML
    private Button searchBroadcast;
    @FXML
    private DatePicker broadcastAirDate;
    @FXML
    private Label errorMsgCastSearch;
    @FXML
    private Label errorMsgProductionSearch;
    @FXML
    private Label errorMsgBroadcastSearch;
    @FXML
    private Label userInfo;
    @FXML
    private Label errorMsgBroadcast;
    @FXML
    private Label errorMsgProduction;
    @FXML
    private Label errorMsgCast;
    @FXML
    private ListView searchResult;
    @FXML
    private ListView roleList;
    @FXML
    private MenuBar menubar;
    @FXML
    private Tab castTab;
    @FXML
    private Tab productionTab;
    @FXML
    private Tab broadcastTab;
    @FXML
    private TextField searchFieldCast;
    @FXML
    private TextField searchFieldProduction;
    @FXML
    private TextField searchFieldBroadcast;
    @FXML
    private TextField castName;
    @FXML
    private TextField regDKField;
    @FXML
    private TextField productionName;
    @FXML
    private TextField productionReleaseYear;
    @FXML
    private TextField amountOfSeasons;
    @FXML
    private TextField producerName;
    @FXML
    private TextField amountOfBroadcasts;
    @FXML
    private TextField broadcastName;
    @FXML
    private TextField broadcastProduction;
    @FXML
    private TextField broadcastSeason;
    @FXML
    private TextField broadcastEpisodeNumber;

    private Stage assignStage = new Stage();
    private Stage unassignStage = new Stage();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userInfo.setText("Administrator");

    }

    //FXML HANDLERS FOR "MEDVIRKENDE" TAB

    @FXML
    public void handleSearchCast(MouseEvent event){

        //Clear the ListView.
        //Fetch the information from the persistence layer and show it into the searchResults
        //Listview, one Cast per line.
        //IF FAIL: update errorMsgCastSearch.setVisible(true);
    }

    @FXML
    public void handleCreateNewCast(MouseEvent event){
        //fetch the information from textfields:
        //castName and regDKField
        //and set them to be the values of the Cast-constructor's parameters.
        //IF SUCCESS: update errorMsgCast to text black/green and message:
        //"Medvirkende oprettet"
        //IF FAIL: update errorMsgCast to text red and message:
        //"Fejl opstået, medvirkende blev ikke oprettet"
    }

    @FXML
    public void handleDeleteCast(MouseEvent event){
        //fetch the information searched for in the persistence layer from the searchResults ListView
        //(by choosing a Cast-object from the ListView) and find and delete that information from
        //the persistence layer
        //Update the Listview to reflect the change
        //Potentially: Limit to choosing just one element of the ListView.
        //IF SUCCESS: update errorMsgCast to text black/green and message:
        //"Medvirkende slettet"
        //IF FAIL: update errorMsgCast to text red and message:
        //"Fejl opstået, medvirkende blev ikke slettet"
    }

    @FXML
    public void handleMergeCast(MouseEvent event){
        //IF SUCCESS: update errorMsgCast to text black/green and message:
        //"Medvirkende sammenflettet"
        //IF FAIL: update errorMsgCast to text red and message:
        //"Fejl opstået, medvirkende blev ikke sammenflettet"
    }

    @FXML
    public void handleSaveCastChanges(MouseEvent event){
        //fetch the information searched for in the persistence layer from the searchResults ListView
        //(by choosing a Cast-object from the ListView). Any changes made to that object's information
        //should be saved to it. it should use the set-methods in the class.
        //Update the Listview to reflect the change
        //Potentially: Limit to choosing just one element of the ListView.
        //IF SUCCESS: update errorMsgCast to text black/green and message:
        //"Medvirkende gemt"
        //IF FAIL: update errorMsgCast to text red and message:
        //"Fejl opstået, medvirkende blev ikke gemt"
    }

    //FXML HANDLERS FOR "PRODUKTION" TAB

    @FXML
    public void handleSearchProduction(MouseEvent event){
        //Clear the ListView.
        //Fetch the information from the persistence layer and show it into the searchResults
        //Listview, one production per line.
        //IF FAIL: set errorMsgProductionSearch.setVisible(true);
    }

    @FXML
    public void handleCreateNewProduction(MouseEvent event){
        //fetch the information from textfields:
        // productionName, productionReleaseYear, producerName
        // and set them to be the values of the Production-constructor's parameters.
        //In addition, set the values of the text fields amountOfSeasons and amountOfBroadcasts
        //to zero. Will update as broadcasts are added to that production.
        //IF SUCCESS: update errorMsgProduction to text black/green and message:
        //"Produktionen slettet"
        //IF FAIL: update errorMsgProduction to text red and message:
        //"Fejl opstået, produktionen blev ikke slettet"
    }

    @FXML
    public void handleDeleteProduction(MouseEvent event){
        //fetch the information searched for in the persistence layer from the searchResults ListView
        //(by choosing a Production-object from the ListView) and find and delete that information from
        //the persistence layer
        //Update the Listview to reflect the change
        //Potentially: Limit to choosing just one element of the ListView.
        //IF SUCCESS: update errorMsgProduction to text black/green and message:
        //"Produktionen slettet"
        //IF FAIL: update errorMsgProduction to text red and message:
        //"Fejl opstået, produktionen blev ikke slettet"
    }

    //FXML HANDLERS FOR "UDSENDELSE" TAB

    @FXML
    public void handleSearchBroadcast(MouseEvent event){
        //Clear the ListView.
        //Fetch the information from the persistence layer and show it into the searchResults
        //Listview, one broadcast per line.
        //IF FAIL: update errorMsgBroadcastSearch.setVisible(true);
    }

    /**
     * Opens the AddAssignCastGUI.fxml scene in a new window, from which they can search for
     * a cast member in the database and assign them to the broadcast.
     * @param event
     */
    @FXML
    public void handleAssignCast(MouseEvent event){
        try {
            Parent root;
            root = FXMLLoader.load(BaseController.class.getResource("AddAssignCastGUI.fxml"));
            assignStage.setScene(new Scene(root));
            assignStage.setResizable(false);
            assignStage.show();
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Opens the RemoveChangeCastGUI.fxml scene in a new window, from which they can see all the
     * cast members assigned to a broadcast in a listview, and then either make changes to their
     * role or unassign them from the broadcast
     * @param event
     */
    @FXML
    public void handleUnassignCast(MouseEvent event){
        try {
            Parent root;
            root = FXMLLoader.load(BaseController.class.getResource("RemoveChangeCastGUI.fxml"));
            unassignStage.setScene(new Scene(root));
            unassignStage.setResizable(false);
            unassignStage.show();
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleCreateBroadcast(MouseEvent event){
        //fetch the information from textfields:
        // broadcastName, broadcastProduction, broadcastSeason, broadcastEpisodeNumber & broadcastAirDate
        // and set them to be the values of the Broadcast-constructor's parameters.
        // Remember to parse the date to a String.
        //IF SUCCESS: update errorMsgBroadcast to text black/green and message:
        //"Udsendelsen tilføjet"
        //IF FAIL: update errorMsgBroadcast to text red and message:
        //"Fejl opstået, udsendelsen blev ikke tilføjet"
    }

    @FXML
    public void handleDeleteBroadcast(MouseEvent event){
        //fetch the information searched for in the persistence layer from the searchResults ListView
        //(by choosing a Broadcast-object from the ListView) and find and delete that information from
        //the persistence layer
        //Update the Listview to reflect the change
        //Potentially: Limit to choosing just one element of the ListView.
        //IF SUCCESS: update errorMsgBroadcast to text black/green and message:
        //"Udsendelsen slettet"
        //IF FAIL: update errorMsgBroadcast to text red and message:
        //"Fejl opstået, udsendelsen blev ikke slettet"
    }

}
