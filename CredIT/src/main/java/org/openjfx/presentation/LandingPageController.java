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

    }

    @FXML
    public void handleCreateNewCast(MouseEvent event){

    }

    @FXML
    public void handleDeleteCast(MouseEvent event){

    }

    @FXML
    public void handleMergeCast(MouseEvent event){

    }

    @FXML
    public void handleSaveCastChanges(MouseEvent event){

    }

    //FXML HANDLERS FOR "PRODUKTION" TAB

    @FXML
    public void handleSearchProduction(MouseEvent event){

    }

    @FXML
    public void handleCreateNewProduction(MouseEvent event){

    }

    @FXML
    public void handleDeleteProduction(MouseEvent event){

    }

    //FXML HANDLERS FOR "UDSENDELSE" TAB

    @FXML
    public void handleSearchBroadcast(MouseEvent event){

    }

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

    }

    @FXML
    public void handleDeleteBroadcast(MouseEvent event){

    }

}
