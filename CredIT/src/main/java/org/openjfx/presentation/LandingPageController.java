package org.openjfx.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



}
