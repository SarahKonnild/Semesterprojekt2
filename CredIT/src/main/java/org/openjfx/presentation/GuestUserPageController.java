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
import org.openjfx.interfaces.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
    private Label searchTextInfo;
    @FXML
    private Label showMovies;
    @FXML
    private Label showProductions;
    @FXML
    private Label productionCompanyName;
    @FXML
    private Label goTo;
    @FXML
    private Label showEpisodes;
    @FXML
    private Label showAllCastMovie;
    @FXML
    private Label showAllCastBroadcast;
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
    @FXML
    private TextField productionCompanyNameField;
    //endregion

    //Variables/attributes used in this controller
    //region
    private String searchTopicChosen;

    private ArrayList<IProduction> productionList;
    private ArrayList<IMovie> movieList;

    private Object obj;
    private String searchText;
    private ICast chosenCast;
    private IProduction chosenProduction;
    private IBroadcast chosenBroadcast;
    private IMovie chosenMovie;
    private IProductionCompany chosenCompany;

    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);
    }

    //Everything do do with manipulating the ListView (search,choose)
    //region

    /**
     * Checks the searchtopic that has been chosen from the dropdown menu. This is used to manage which
     * search-method that is being run in the domain layer into the persistence.
     * Takes the text that is written in the searchfield and uses that to run the appropriate search-
     * method in the domain layer's System class. If the list has items, and the searchfield isn't empty,
     * the items returned from the persistence layer will be written to a list which can be printed into
     * the ListView.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    private void handleSearch(MouseEvent event) {
        String searchText = searchField.getText();
        if (searchTopicChosen != null) {
            if (searchTopicChosen.equals("cast")) {
                resultList.getItems().clear();
                ArrayList<ICast> castSearchResult = App.getSystemInstance().searchCast(searchText);
                if (castSearchResult != null && !searchField.getText().isEmpty()) {
                    resultList.setItems(FXCollections.observableArrayList(castSearchResult));
                    searchField.clear();
                } else {
                    errorMessage.setText("Fejl opstået, medvirkende ikke fundet");
                }
            } else if (searchTopicChosen.equals("production")) {
                resultList.getItems().clear();
                ArrayList<IProduction> productionSearchResult = App.getSystemInstance().searchProduction(searchText);
                if (productionSearchResult != null && !searchField.getText().isEmpty()) {
                    resultList.setItems(FXCollections.observableArrayList(productionSearchResult));
                    searchField.clear();
                } else {
                    errorMessage.setText("Fejl opstået, produktion ikke fundet");
                }
            } else if (searchTopicChosen.equals("broadcast")) {
                resultList.getItems().clear();
                ArrayList<IBroadcast> broadcastSearchResult = App.getSystemInstance().searchBroadcast(searchText);
                if (broadcastSearchResult != null && !searchField.getText().isEmpty()) {
                    resultList.setItems(FXCollections.observableArrayList(broadcastSearchResult));
                    searchField.clear();
                } else {
                    errorMessage.setText("Fejl opstået, udsendelse ikke fundet");
                }
            } else if (searchTopicChosen.equals("movie")) {
                resultList.getItems().clear();
                ArrayList<IMovie> movieSearchResult = App.getSystemInstance().searchMovie(searchText);
                if (movieSearchResult != null && !searchField.getText().isEmpty()) {
                    resultList.setItems(FXCollections.observableArrayList(movieSearchResult));
                    searchField.clear();
                } else {
                    errorMessage.setText("Fejl opstået, udsendelse ikke fundet");
                }
            } else if (searchTopicChosen.equals("productioncompany")) {
                resultList.getItems().clear();
                ArrayList<IProductionCompany> companySearchResult = App.getSystemInstance().searchProductionCompany(searchText);
                if (companySearchResult != null && !searchField.getText().isEmpty()) {
                    resultList.setItems(FXCollections.observableArrayList(companySearchResult));
                    searchField.clear();
                } else {
                    errorMessage.setText("Fejl opstået, produktionsfirma ikke fundet");
                }
            }
        } else {
            errorMessage.setText("Intet søgeemne valgt");
        }
        searchTextInfo.setText("Du søger på: " + searchText);
        resultList.setDisable(false);
    }

    /**
     * Writes the chosen result to an Object variable and performs a check on the Object-variable's nature, to see
     * which fields that should be made available/visible and then written to with the object's attributes
     *
     * @param event
     * @author Sarah
     */
    @FXML
    private void handleSearchResultChosen(MouseEvent event) {
        obj = resultList.getSelectionModel().getSelectedItem();
        if (resultList.getItems().isEmpty()) {
            resultList.setDisable(true);
        } else {
            resultList.setDisable(false);
            if (obj instanceof ICast) {
                searchText = searchField.getText();
                chosenCast = (ICast) obj;
                castNameField.setText(chosenCast.getName());
                if (App.getAllCastRoles(chosenCast).isEmpty()) {
                    App.getAllCastRoles(chosenCast).add("Listen er tom");
                }
                castRoleList.setItems(FXCollections.observableArrayList(App.getAllCastRoles(chosenCast)));

                changeFieldsVisible("cast");

            } else if (obj instanceof IProduction) {
                searchText = searchField.getText();
                chosenProduction = (IProduction) obj;
                IProductionCompany retrievedProductionCompany = App.retrieveProductionCompanyForProduction(chosenProduction);
                productionNameField.setText(chosenProduction.getName());
                productionProducerField.setText(retrievedProductionCompany.getName());
                productionReleaseYearField.setText(chosenProduction.getYear());
                productionEpisodesField.setText(String.valueOf(chosenProduction.getNumberOfEpisodes()));
                productionSeasonsField.setText(String.valueOf(chosenProduction.getNumberOfSeasons()));

                changeFieldsVisible("production");

            } else if (obj instanceof IBroadcast) {
                searchText = searchField.getText();
                chosenBroadcast = (IBroadcast) obj;
                broadcastNameField.setText(chosenBroadcast.getName());
                String[] airDateInput = chosenBroadcast.getAirDate();
                broadcastDayField.setText(airDateInput[0]);
                broadcastMonthField.setText(airDateInput[1]);
                broadcastYearField.setText(airDateInput[2]);
                IProduction retrievedProduction = App.retrieveProduction(chosenBroadcast);
                broadcastProducerNameField.setText(retrievedProduction.getName());

                changeFieldsVisible("broadcast");

            } else if (obj instanceof IMovie) {
                searchText = searchField.getText();
                chosenMovie = (IMovie) obj;
                movieNameField.setText(chosenMovie.getTitle());
                String[] releaseDateInput = chosenMovie.getReleaseDate();
                movieReleaseYearField.setText(releaseDateInput[2]);
                IProductionCompany retrievedProductionCompany = App.retrieveProductionCompanyForMovie(chosenMovie);
                movieProductionCompanyField.setText(retrievedProductionCompany.getName());

                changeFieldsVisible("movie");

            } else if (obj instanceof IProductionCompany) {
                searchField.setText(searchText);
                chosenCompany = (IProductionCompany) obj;
                movieList = chosenCompany.getMovieList();
                productionList = chosenCompany.getProductionList();
                productionCompanyNameField.setText(chosenCompany.getName());

                changeFieldsVisible("productioncompany");
            }
        }
    }

    //endregion

    //Choose searchtopic
    //region

    /**
     * When the user chooses a searchTopic, a String variable is written to, which specifies in the Search-handler, which
     * method that should be run on the input from the user.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    private void handleSearchTopicChosen(MouseEvent event) {
        cast.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Medvirkende");
                searchTopicChosen = "cast";
                search.setDisable(false);
                changeFieldsVisible(searchTopicChosen);
            }
        });
        production.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Produktion");
                searchTopicChosen = "production";
                search.setDisable(false);
                changeFieldsVisible(searchTopicChosen);
            }
        });
        broadcast.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Udsendelse");
                searchTopicChosen = "broadcast";
                search.setDisable(false);
                changeFieldsVisible(searchTopicChosen);
            }
        });
        movie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Film");
                searchTopicChosen = "movie";
                search.setDisable(false);
                changeFieldsVisible(searchTopicChosen);
            }
        });
        productionCompany.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Producentfirma");
                searchTopicChosen = "productioncompany";
                search.setDisable(false);
                changeFieldsVisible(searchTopicChosen);
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
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleClose(MouseEvent event) {
        App.closeWindow();
    }

    @FXML
    public void handleHelp(MouseEvent event) {
        App.handleHelpStage();
    }

    @FXML
    public void handleBack(MouseEvent event) {
        App.handleLoginSystemPage();
    }
    //endregion

    //Changing visible fields
    //region

    /**
     * Method that can be used to specify which fields should be rendered visible in the GUI based on the search topic.
     * This method can then be called upon to specify the fields and labels that should be shown to the user based on the search topic.
     * Utilises the changeXFields-methods.
     *
     * @param searchTopicChosen referring to the variable that gets set using the dropdown menu
     * @author Sarah
     */
    private void changeFieldsVisible(String searchTopicChosen) {
        this.searchTopicChosen = searchTopicChosen;
        if (searchTopicChosen.equals("cast")) {
            changeCastFields(true);
            changeBroadcastFields(false);
            changeMovieFields(false);
            changeProductionFields(false);
            changeProductionCompanyFields(false);
        } else if (searchTopicChosen.equals("production")) {
            changeCastFields(false);
            changeBroadcastFields(false);
            changeMovieFields(false);
            changeProductionFields(true);
            changeProductionCompanyFields(false);
        } else if (searchTopicChosen.equals("broadcast")) {
            changeCastFields(false);
            changeBroadcastFields(true);
            changeMovieFields(false);
            changeProductionFields(false);
            changeProductionCompanyFields(false);
        } else if (searchTopicChosen.equals("movie")) {
            changeCastFields(false);
            changeBroadcastFields(false);
            changeMovieFields(true);
            changeProductionFields(false);
            changeProductionCompanyFields(false);
        } else if (searchTopicChosen.equals("productioncompany")) {
            changeCastFields(false);
            changeBroadcastFields(false);
            changeMovieFields(false);
            changeProductionFields(false);
            changeProductionCompanyFields(true);
        }
    }

    /**
     * Adds the list of productions to the resultList, and changes the visible fields
     * so that the production that may be chosen's information can be written to them.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleShowProductions(MouseEvent event) {
        resultList.setItems(FXCollections.observableArrayList(productionList));
        changeFieldsVisible("production");
        chosenCompany = null;
    }

    /**
     * Adds the list of movies to the resultList, and changes the visible fields so that
     * the movie that may be chosen's information can be written to them.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleShowMovies(MouseEvent event) {
        resultList.setItems(FXCollections.observableArrayList(movieList));
        changeFieldsVisible("movie");
        chosenCompany = null;
    }

    /**
     * Creates a local ArrayList containing the list of broadcasts that are assigned to the
     * production that has been chosen. From this, the Arraylist's elements are added to the
     * resultList, and can then be chosen to show information in fields.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleShowEpisodes(MouseEvent event) {
        ArrayList<IBroadcast> broadcastList = chosenProduction.getBroadcasts();
        resultList.setItems(FXCollections.observableArrayList(broadcastList));
        chosenProduction = null;
    }

    /**
     * Calls the App-class to fetch the method for iterating through the hashmap of roles
     * that are assigned to the broadcast, and then adds the resulting arrayList to the ListView.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleShowCastBroadcast(MouseEvent event) {
        resultList.setItems(FXCollections.observableArrayList(App.getBroadcastRoleArray()));
        chosenBroadcast = null;
    }

    /**
     * Calls the App-class to fetch the method for iterating through the hashmap of roles
     * that are assigned to the movie, and then adds the resulting arrayList to the ListView.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleShowCastMovie(MouseEvent event) {
        resultList.setItems(FXCollections.observableArrayList(App.getMovieRoleArray()));
        chosenMovie = null;
    }

    /**
     * This region creates a method that can be called which just toggles the visibility of the fields and labels that are
     * associated with one of the classes/searchtopic that has been chosen.
     *
     * @param value a boolean value that allows for toggling the fields/labels
     * @author Sarah
     */
    //region
    private void changeCastFields(boolean value) {
        castName.setVisible(value);
        castNameField.setVisible(value);
        castRoleList.setVisible(value);
    }

    private void changeProductionFields(boolean value) {
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
        showEpisodes.setVisible(value);
    }

    private void changeBroadcastFields(boolean value) {
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
        showAllCastBroadcast.setVisible(value);
    }

    private void changeMovieFields(boolean value) {
        movieName.setVisible(value);
        movieNameField.setVisible(value);
        movieProducerName.setVisible(value);
        movieProductionCompanyField.setVisible(value);
        releaseYearMovie.setVisible(value);
        movieReleaseYearField.setVisible(value);
        showAllCastMovie.setVisible(value);
    }

    private void changeProductionCompanyFields(boolean value) {
        productionCompanyName.setVisible(value);
        productionCompanyNameField.setVisible(value);
        showMovies.setVisible(value);
        showProductions.setVisible(value);
    }

    //endregion
}
