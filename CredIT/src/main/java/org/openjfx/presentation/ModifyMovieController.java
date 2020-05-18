package org.openjfx.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.openjfx.interfaces.IMovie;
import org.openjfx.interfaces.IProductionCompany;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ModifyMovieController implements Initializable {
    private static IMovie chosenMovie;
    private static IMovie givenMovie;
    //FXML Attributes
    //region
    @FXML
    private AnchorPane basePane;
    @FXML
    private Button search;
    @FXML
    private Button changeCast;
    @FXML
    private Button createNew;
    @FXML
    private Button delete;
    @FXML
    private Label close;
    @FXML
    private Button save;
    @FXML
    private Label help;
    @FXML
    private Label back;
    @FXML
    private Label errorMessageSearch;
    @FXML
    private Label errorMessage;
    @FXML
    private ListView resultList;
    @FXML
    private TextField searchField;
    @FXML
    private TextField movieName;
    //endregion
    @FXML
    private TextField productionCompany;
    @FXML
    private TextField releaseYear;
    //Class Attributes
    //region
    private ArrayList<IMovie> searchResult;
    private ObservableList<IMovie> observableList;
    private boolean status;
    //endregion

    public static IMovie getChosenMovie() {
        return chosenMovie;
    }

    //Everything to do with the ListView (search, choose)
    //region

    public static void setChosenMovie(IMovie chosenMovie) {
        givenMovie = chosenMovie;
    }

    /**
     * Checks if the scene has been given a movie from the previous scene. If so, it will write that Movie's information
     * to the fields.
     *
     * @param location
     * @param resources
     * @author Sarah
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);

        if (ModifyProductionCompanyController.getChosenMovie() != null) {
            givenMovie = ModifyProductionCompanyController.getChosenMovie();
            movieName.setText(givenMovie.getTitle());

            IProductionCompany retrievedProductionCompany = App.retrieveProductionCompanyForMovie(givenMovie);
            productionCompany.setText(retrievedProductionCompany.getName());
            String[] release = givenMovie.getReleaseDate();
            releaseYear.setText(release[2]);
        }
    }

    //endregion


    //Create Movie
    //region

    /**
     * Searches the database for entries that match the search field's information in the database.
     * Writes all results into the list, which can then be chosen by the user.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleSearch(MouseEvent event) {
        String searchText = searchField.getText();
        resultList.getItems().clear();
        searchResult = App.getSystemInstance().searchMovie(searchText);
        if (searchResult != null && !searchField.getText().isEmpty()) {
            resultList.setDisable(false);
            observableList = FXCollections.observableArrayList(searchResult);
            resultList.setItems(observableList);
            searchField.clear();
        } else {
            errorMessageSearch.setVisible(true);
        }

    }
    //endregion

    //Delete Movie
    //region

    /**
     * When the user chooses an object from the search list, this method is run. It will always write the
     * data associated with the LAST object chosen to the fields.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleResultChosen(MouseEvent event) {
        resultList.setDisable(false);
        chosenMovie = (IMovie) resultList.getSelectionModel().getSelectedItem();
        movieName.setText(chosenMovie.getTitle());
        String[] airDate = chosenMovie.getReleaseDate();
        releaseYear.setText(String.valueOf(airDate[2]));

        IProductionCompany retrievedProductionCompany = App.retrieveProductionCompanyForMovie(chosenMovie);
        productionCompany.setText(retrievedProductionCompany.getName());

        changeCast.setDisable(false);
        delete.setDisable(false);
        save.setDisable(false);
    }
    //endregion

    //Save Movie
    //region

    /**
     * Takes the entered ProductionCompany's name and performs a search in the database for that company. If it exists,
     * the Movie can be created, adding it to that Company's list of created movies. If the Company does not exist,
     * an error message is written to the user.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleCreateNew(MouseEvent event) {
        resultList.setDisable(false);
        if (!movieName.getText().isEmpty() && !productionCompany.getText().isEmpty() && !releaseYear.getText().isEmpty()) {
            String companySearch = productionCompany.getText();
            ArrayList<IProductionCompany> results = new ArrayList<>();
            results.addAll(App.getSystemInstance().searchProductionCompany(companySearch));
            if (results.get(0).getName().equalsIgnoreCase(companySearch)) {
                resultList.setDisable(false);
                IMovie movie = LoginSystemController.getAdminUser().addNewMovieToDatabase(movieName.getText(), results.get(0).getId(), releaseYear.getText());
                clearFields();
                if (movie != null) {
                    errorMessage.setText(movie.getTitle() + " tilføjet");
                    if (searchResult == null) {
                        searchResult = new ArrayList<>();
                    }
                    searchResult.clear();
                    searchResult.add(movie);
                    resultList.setItems(FXCollections.observableArrayList(searchResult));
                } else {
                    errorMessage.setText("Fejl opstået, " + movie.getTitle() + " blev ikke tilføjet");
                }
                searchResult.clear();
                searchResult.add(movie);
                resultList.setItems(FXCollections.observableArrayList(searchResult));
            }else {
                errorMessage.setText("Fejl opstået, intet firma at tilføje til");
            }
        }else{
            errorMessage.setText("Fejl, venligst udfyld alle felter");

        }
    }
    //endregion

    /**
     * Takes the movie that has been chosen from the ListView and deletes the object that is chosen. Provided that the
     * Movie is deleted in the instance of the program, it is also removed from the database, and unassigned from the
     * company's page.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleDelete(MouseEvent event) {
        status = chosenMovie.delete();
        if (status) {
            searchResult.remove(chosenMovie);
            errorMessage.setText(chosenMovie.getTitle() + " slettet");
            if (chosenMovie == null) {
                resultList.getItems().clear();
            } else {
                resultList.setItems(FXCollections.observableArrayList(searchResult));
            }
            clearFields();
        }
        clearFields();
        resultList.refresh();
    }

    /**
     * Takes the movie that was chosen from the ListView and updates its attributes in the database with the text that is
     * entered into the TextFields.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleSave(MouseEvent event){
        if(!movieName.getText().isEmpty() && !productionCompany.getText().isEmpty() && !releaseYear.getText().isEmpty()) {
            status = chosenMovie.update(movieName.getText(), releaseYear.getText());
            if (status) {
                resultList.setItems(FXCollections.observableArrayList(searchResult));
                errorMessage.setText(chosenMovie.getTitle() + " opdateret");
                clearFields();
            } else {
                errorMessage.setText("Fejl opstået, " + chosenMovie.getTitle() + " blev ikke opdateret");
            }
            resultList.refresh();
        }else{
            errorMessage.setText("Fejl, udfyld venligst alle felter");
        }
    }

    //All the methods which change the scene, open the help stage or close the program.
    //region
    @FXML
    public void handleHelp(MouseEvent event) {
        App.handleHelpStage();
    }

    @FXML
    public void handleBack(MouseEvent event) {
        if (ModifyProductionCompanyController.getChosenMovie() != null) {
            ModifyProductionCompanyController.setChosenMovie(null);
        }
        App.handleAdminPage();
    }
    //endregion

    @FXML
    public void handleClose(MouseEvent event) {
        App.closeWindow();
    }

    /**
     * Sets a String variable that ensures that the assign/unassign cast methods are run correctly with the Movie-related
     * methods.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleChangeCast(ActionEvent event) {
        App.setAssignCastModifier("movie");
        App.handleUnassignAssignStage();
    }

    private void clearFields() {
        movieName.clear();
        releaseYear.clear();
        productionCompany.clear();
    }

}
