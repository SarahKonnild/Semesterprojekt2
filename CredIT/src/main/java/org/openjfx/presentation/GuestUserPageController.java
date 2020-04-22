package org.openjfx.presentation;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.openjfx.interfaces.IBroadcast;
import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IProduction;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GuestUserPageController implements Initializable {

    @FXML
    private AnchorPane basePane;
    @FXML
    private Button search;
    @FXML
    private Label help;
    @FXML
    private Label errorMessage;
    @FXML
    private Label castName;
    @FXML
    private Label broadcastName;
    @FXML
    private Label broadcastProducerName;
    @FXML
    private Label dateHeadline;
    @FXML
    private Label broadcastDay;
    @FXML
    private Label broadcastMonth;
    @FXML
    private Label broadcastYear;
    @FXML
    private Label productionName;
    @FXML
    private Label productionProducer;
    @FXML
    private Label releaseYearProduction;
    @FXML
    private Label amountOfSeasons;
    @FXML
    private Label amountOfEpisodes;
    @FXML
    private Label movieName;
    @FXML
    private Label releaseYearMovie;
    @FXML
    private Label movieProducerName;
    @FXML
    private ListView resultList;
    @FXML
    private ListView castRoleList;
    @FXML
    private MenuButton searchTopic;
    @FXML
    private MenuItem cast;
    @FXML
    private MenuItem broadcast;
    @FXML
    private MenuItem production;
    @FXML
    private MenuItem movie;
    @FXML
    private TextField searchField;
    @FXML
    private TextField castNameField;
    @FXML
    private TextField broadcastNameField;
    @FXML
    private TextField broadcastProducerNameField;
    @FXML
    private TextField broadcastDayField;
    @FXML
    private TextField broadcastMonthField;
    @FXML
    private TextField broadcastYearField;
    @FXML
    private TextField productionNameField;
    @FXML
    private TextField productionProducerField;
    @FXML
    private TextField productionSeasonsField;
    @FXML
    private TextField productionEpisodesField;
    @FXML
    private TextField productionReleaseYearField;
    @FXML
    private TextField movieNameField;
    @FXML
    private TextField movieReleaseYearField;
    @FXML
    private TextField movieProductionCompanyField;

    private String searchTopicChosen;

    private ArrayList<IProduction> productionSearchResult;
    private ArrayList<ICast> castSearchResult;
    private ArrayList<IBroadcast> broadcastSearchResult;

    private ObservableList<ICast> castObservableList;
    private ObservableList<IProduction> productionObservableList;
    private ObservableList<IBroadcast> broadcastObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);
    }

    @FXML
    public void handleSearch(ActionEvent event){

    }

    @FXML
    public void handleSearchTopicChosen(MouseEvent event){
        cast.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Medvirkende");
                searchTopicChosen = "cast";
                setCastFieldsVisible();
                setBroadcastFieldsInvisible();
                setMovieFieldsInvisible();
                setProductionFieldsInvisible();
            }
        });
        production.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Produktion");
                searchTopicChosen = "production";
                setCastFieldsInvisible();
                setBroadcastFieldsInvisible();
                setMovieFieldsInvisible();
                setProductionFieldsVisible();
            }
        });
        broadcast.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Udsendelse");
                searchTopicChosen = "broadcast";
                setCastFieldsInvisible();
                setBroadcastFieldsVisible();
                setMovieFieldsInvisible();
                setProductionFieldsInvisible();
            }
        });
        movie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Film");
                searchTopicChosen = "movie";
                setCastFieldsInvisible();
                setBroadcastFieldsInvisible();
                setMovieFieldsVisible();
                setProductionFieldsInvisible();
            }
        });
    }

    @FXML
    public void handleClose(MouseEvent event){App.closeWindow();}

    @FXML
    public void handleHelp(MouseEvent event){
        App.handleHelpStage();
    }

    @FXML
    public void handleBack(MouseEvent event){App.handleLoginSystemPage();}

    private void setCastFieldsVisible(){
        castName.setVisible(true);
        castNameField.setVisible(true);
        castRoleList.setVisible(true);
    }

    private void setCastFieldsInvisible(){
        castName.setVisible(false);
        castNameField.setVisible(false);
        castRoleList.setVisible(false);
    }

    private void setBroadcastFieldsVisible(){
        broadcastName.setVisible(true);
        broadcastNameField.setVisible(true);
        broadcastProducerName.setVisible(true);
        broadcastProducerNameField.setVisible(true);
        dateHeadline.setVisible(true);
        broadcastDay.setVisible(true);
        broadcastDayField.setVisible(true);
        broadcastMonth.setVisible(true);
        broadcastMonthField.setVisible(true);
        broadcastYear.setVisible(true);
        broadcastYearField.setVisible(true);
    }

    private void setBroadcastFieldsInvisible(){
        broadcastName.setVisible(false);
        broadcastNameField.setVisible(false);
        broadcastProducerName.setVisible(false);
        broadcastProducerNameField.setVisible(false);
        dateHeadline.setVisible(false);
        broadcastDay.setVisible(false);
        broadcastDayField.setVisible(false);
        broadcastMonth.setVisible(false);
        broadcastMonthField.setVisible(false);
        broadcastYear.setVisible(false);
        broadcastYearField.setVisible(false);
    }

    private void setProductionFieldsVisible(){
        productionName.setVisible(true);
        productionNameField.setVisible(true);
        productionProducer.setVisible(true);
        productionProducerField.setVisible(true);
        amountOfEpisodes.setVisible(true);
        productionEpisodesField.setVisible(true);
        amountOfSeasons.setVisible(true);
        productionSeasonsField.setVisible(true);
        releaseYearProduction.setVisible(true);
        productionReleaseYearField.setVisible(true);
    }

    private void setProductionFieldsInvisible(){
        productionName.setVisible(false);
        productionNameField.setVisible(false);
        productionProducer.setVisible(false);
        productionProducerField.setVisible(false);
        amountOfEpisodes.setVisible(false);
        productionEpisodesField.setVisible(false);
        amountOfSeasons.setVisible(false);
        productionSeasonsField.setVisible(false);
        releaseYearProduction.setVisible(false);
        productionReleaseYearField.setVisible(false);
    }

    private void setMovieFieldsVisible(){
        movieName.setVisible(true);
        movieNameField.setVisible(true);
        movieProducerName.setVisible(true);
        movieProductionCompanyField.setVisible(true);
        releaseYearMovie.setVisible(true);
        movieReleaseYearField.setVisible(true);
    }

    private void setMovieFieldsInvisible(){
        movieName.setVisible(false);
        movieNameField.setVisible(false);
        movieProducerName.setVisible(false);
        movieProductionCompanyField.setVisible(false);
        releaseYearMovie.setVisible(false);
        movieReleaseYearField.setVisible(false);
    }

}
