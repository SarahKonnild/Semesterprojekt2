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

    //FXML Attributes
    //region
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
    private MenuItem productionCompany;
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
    //endregion

    //Variables/attributes used in this controller
    //region
    private String searchTopicChosen;

    private ArrayList<IProduction> productionSearchResult;
    private ArrayList<ICast> castSearchResult;
    private ArrayList<IBroadcast> broadcastSearchResult;
//    private ArrayList<IMovie> movieSearchResult;
//    private ArrayList<IProductionCompany> companySearchResult;

    private ObservableList<ICast> castObservableList;
    private ObservableList<IProduction> productionObservableList;
    private ObservableList<IBroadcast> broadcastObservableList;
//    private ObservableList<IMovie> movieObservableList;
//    private ObservableList<IProductionCompany> companyObservableList;

    private Object obj;
    private String searchText;
    private ICast chosenCast;
//    private IMovie chosenMovie;
//    private IProduction chosenProduction;
    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);
        search.setDisable(true);
    }

    //Everything do do with manipulating the ListView (search,choose)
    //region
    /**
     * Checks the subject that has been chosen in the dropdown menu, and specifies the search-method in the domain layer that is being
     * run on the searchfield's input
     * @param event
     */
    @FXML
    private void handleSearch(MouseEvent event){
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
        }else if(searchTopicChosen.equals("broadcast")) {
            resultList.getItems().clear();
            broadcastSearchResult = App.getSystemInstance().searchBroadcast(searchText);
            if (broadcastSearchResult != null && !searchField.getText().isEmpty()) {
                broadcastObservableList = FXCollections.observableArrayList(broadcastSearchResult);
                resultList.setItems(productionObservableList);
                searchField.clear();
            } else {
                errorMessage.setText("Fejl opstået, udsendelse ikke fundet");
            }
//        }else if(searchTopicChosen.equals("movie")){
//            resultList.getItems().clear();
//            movieSearchResult = App.getSystemInstance().searchMovie(searchText);
//            if(movieSearchResult != null && !searchField.getText().isEmpty()){
//                movieObservableList = FXCollections.observableArrayList(movieSearchResult);
//                resultList.setItems(movieObservableList);
//                searchField.clear();
//            }else{
//                errorMessage.setText("Fejl opstået, udsendelse ikke fundet");
//            }
        }else if(searchTopicChosen.equals("productioncompany")){
//            resultList.getItems().clear();
//            companySearchResult = App.getSystemInstance().searchProductionCompany(searchText);
//            if (companySearchResult != null && !searchField.getText().isEmpty()) {
//                companyObservableList = FXCollections.observableArrayList(companySearchResult);
//                resultList.setItems(companyObservableList);
//                searchField.clear();
//            } else {
//                errorMessage.setText("Fejl opstået, udsendelse ikke fundet");
//            }
        }else{
            errorMessage.setText("Intet søgeemne valgt");
        }
        changeFieldsVisible(searchTopicChosen);
    }

    /**
     * Handles the picking of an item in the searchlist and writes that object's information to the relevant fields.
     * @param event
     */
    @FXML
    private void handleSearchResultChosen(MouseEvent event){
        obj = resultList.getSelectionModel().getSelectedItem();
        if (obj instanceof ICast) {
            searchText = searchField.getText();
            resultList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            chosenCast = (ICast) obj;
            castNameField.setText(chosenCast.getName());
        } else if (obj instanceof IProduction) {
            searchText = searchField.getText();
            IProduction chosenProduction = (IProduction) obj;
            productionNameField.setText(chosenProduction.getName());
            productionProducerField.setText(chosenProduction.getProductionCompany().getName());
            productionReleaseYearField.setText(chosenProduction.getYear());
        } else if (obj instanceof IBroadcast) {
            searchText = searchField.getText();
            obj = resultList.getSelectionModel().getSelectedItem();
            IBroadcast chosenBroadcast = (IBroadcast) obj;
            broadcastNameField.setText(chosenBroadcast.getName());
            String[] airDateInput = chosenBroadcast.getAirDate();
            broadcastDayField.setText(airDateInput[0]);
            broadcastMonthField.setText(airDateInput[1]);
            broadcastYearField.setText(airDateInput[2]);
            broadcastProducerNameField.setText(chosenBroadcast.getProduction().getName());
        }
//        else if(obj instanceof IMovie){
//            searchText = searchField.getText();
//            obj = resultList.getSelectionModel().getSelectedItem();
//            IMovie chosenMovie = (IMovie) obj;
//            movieNameField.setText(chosenMovie.getName());
//            movieReleaseYearField.setText(chosenMovie.getReleaseYear());
//            movieProductionCompanyField.setText(chosenMovie.getProductionCompany());
//        }
//        else if(obj instanceof IProductionCompany){
//            searchField.setText(searchText);
//            //TODO insert code which prints the ProductionCompany's list of productions/movies into the resultList
//            obj = resultList.getSelectionModel().getSelectedItem();
//            if(obj instanceof IMovie){
//                changeFieldsVisible("movie");
//                chosenMovie = (IMovie) obj;
//                movieProductionCompanyField.setText(chosenMovie.getProductionCompany().getName());
//
//            }else if(obj instanceof IProduction){
//                changeFieldsVisible("production");
//            }
//        }
    }

    //endregion

    //Choose searchtopic
    //region
    /**
     * A method which creates a String-value that can be used to specify the search method used and the fields that should be
     * visible
     * @param event
     */
    @FXML
    private void handleSearchTopicChosen(MouseEvent event){
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
        productionCompany.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Producentfirma");
                searchTopicChosen = "productioncompany";
                search.setDisable(false);
            }
        });
    }
    //endregion

    //All the methods which change the scene, open the help stage or close the program.
    //region
    /**
     * Methods which handle the changing of the FXMLs. Includes:
     * - Close the window (and thus the main process)
     * - Open the help-window
     * - Return to the past scene
     * @param event
     */
    @FXML
    public void handleClose(MouseEvent event){
        App.closeWindow();}

    @FXML
    public void handleHelp(MouseEvent event){
        App.handleHelpStage();
    }
    @FXML
    public void handleBack(MouseEvent event){
        App.handleLoginSystemPage();
    }
    //endregion

    //Changing visible fields
    //region
    /**
     * Method that can be used to specify which fields should be rendered visible in the GUI based on the search topic.
     * This method can then be called upon to specify the fields and labels that should be shown to the user based on the search topic.
     * Utilises the changeXFields-methods.
     * @param searchTopicChosen referring to the variable that gets set using the dropdown menu
     */
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

    /**
     * This region creates a method that can be called which just toggles the visibility of the fields and labels that are
     * associated with one of the classes/searchtopic that has been chosen.
     * @param value a boolean value that allows for toggling the fields/labels
     */
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

        //endregion
}
