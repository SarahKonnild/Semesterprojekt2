package org.openjfx.presentation;

import javafx.collections.FXCollections;
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
    private ArrayList<IMovie> movieSearchResult;

    private ObservableList<ICast> castObservableList;
    private ObservableList<IProduction> productionObservableList;
    private ObservableList<IBroadcast> broadcastObservableList;
    private ObservableList<IMovie> movieObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);
            search.setDisable(true);
    }

    @FXML
    public void handleSearch(ActionEvent event){
        String searchText = searchField.getText();
        if(searchTopicChosen.equals("cast")){
            resultList.getItems().clear();
            castSearchResult = App.getSystemInstance().searchCast(searchText);
            if (castSearchResult != null && !searchField.getText().isEmpty()) {
                castObservableList = FXCollections.observableArrayList(castSearchResult);
                resultList.setItems(castObservableList);
                searchField.clear();
            } else {
                errorMessage.setText("Fejl opstået, medvirkende ikke fundet");
            }
        }else if(searchTopicChosen.equals("production")){
            resultList.getItems().clear();
            productionSearchResult = App.getSystemInstance().searchProduction(searchText);
            if(productionSearchResult != null && !searchField.getText().isEmpty()){
                productionObservableList = FXCollections.observableArrayList(productionSearchResult);
                resultList.setItems(productionObservableList);
                searchField.clear();
            }else {
                errorMessage.setText("Fejl opstået, produktion ikke fundet");
            }
        }else if(searchTopicChosen.equals("broadcast")){
            resultList.getItems().clear();
            broadcastSearchResult = App.getSystemInstance().searchBroadcast(searchText);
            if(broadcastSearchResult != null && !searchField.getText().isEmpty()){
                broadcastObservableList = FXCollections.observableArrayList(broadcastSearchResult);
                resultList.setItems(productionObservableList);
                searchField.clear();
            }else{
                errorMessage.setText("Fejl opstået, udsendelse ikke fundet");
            }
        }else if(searchTopicChosen.equals("movie")){
            resultList.getItems().clear();
            movieSearchResult = App.getSystemInstance().searchMovie(searchText);
            if(movieSearchResult != null && !searchField.getText().isEmpty()){
                movieObservableList = FXCollections.observableArrayList(movieSearchResult);
                resultList.setItems(movieObservableList);
                searchField.clear();
            }else{
                errorMessage.setText("Fejl opstået, udsendelse ikke fundet");
            }
        }else{
            errorMessage.setText("Intet søgeemne valgt");
        }
        changeFieldsVisible(searchTopicChosen);
    }

    @FXML
    public void handleSearchTopicChosen(MouseEvent event){
        cast.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Medvirkende");
                searchTopicChosen = "cast";
                search.setDisable(false);
            }
        });
        production.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Produktion");
                searchTopicChosen = "production";
                search.setDisable(false);
            }
        });
        broadcast.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Udsendelse");
                searchTopicChosen = "broadcast";

                search.setDisable(false);
            }
        });
        movie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Film");
                searchTopicChosen = "movie";
                search.setDisable(false);
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


    private void changeFieldsVisible(String searchTopicChosen) {
        this.searchTopicChosen = searchTopicChosen;
        if (searchTopicChosen.equals("cast")) {
            changeCastFields(true);
            changeBroadcastFields(false);
            changeMovieFields(false);
            changeProductionFields(false);
        }else if(searchTopicChosen.equals("production")){
            changeCastFields(false);
            changeBroadcastFields(false);
            changeMovieFields(false);
            changeProductionFields(true);
        }else if(searchTopicChosen.equals("broadcast")){
            changeCastFields(false);
            changeBroadcastFields(true);
            changeMovieFields(false);
            changeProductionFields(false);
        }else if(searchTopicChosen.equals("movie")){
            changeCastFields(false);
            changeBroadcastFields(false);
            changeMovieFields(true);
            changeProductionFields(false);
        }
    }

    private void changeCastFields(boolean value){
            castName.setVisible(value);
            castNameField.setVisible(value);
            castRoleList.setVisible(value);
        }
        private void changeProductionFields(boolean value){
            productionName.setVisible(value);
            productionNameField.setVisible(value);
            productionProducer.setVisible(value);
            productionProducerField.setVisible(value);
            releaseYearProduction.setVisible(value);
            productionReleaseYearField.setVisible(value);
            amountOfSeasons.setVisible(value);
            productionSeasonsField.setVisible(value);
            amountOfEpisodes.setVisible(value);
            productionEpisodesField.setVisible(value);
        }

        private void changeBroadcastFields(boolean value){
            broadcastName.setVisible(value);
            broadcastNameField.setVisible(value);
            broadcastProducerName.setVisible(value);
            broadcastProducerNameField.setVisible(value);
            dateHeadline.setVisible(value);
            broadcastDay.setVisible(value);
            broadcastDayField.setVisible(value);
            broadcastMonth.setVisible(value);
            broadcastMonthField.setVisible(value);
            broadcastYear.setVisible(value);
            broadcastYearField.setVisible(value);
        }

        private void changeMovieFields(boolean value){
            movieName.setVisible(value);
            movieNameField.setVisible(value);
            movieProducerName.setVisible(value);
            movieProductionCompanyField.setVisible(value);
            releaseYearMovie.setVisible(value);
            movieReleaseYearField.setVisible(value);
        }

}
