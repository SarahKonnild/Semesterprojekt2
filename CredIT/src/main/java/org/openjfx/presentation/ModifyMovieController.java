package org.openjfx.presentation;

import javafx.collections.FXCollections;
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
    @FXML
    private TextField productionCompany;
    @FXML
    private TextField releaseYear;
    //endregion

    //Class Attributes
    //region
    private ArrayList<IMovie> searchResult;
    private boolean status;
    private static IMovie chosenMovie;
    private static IMovie givenMovie;
    //endregion

    //Everything to do with the ListView (search, choose)
    //region
    /**
     * Checks if the scene has been given a movie from the previous scene. If so, it will write that Movie's information
     * to the fields.
     *

     * @author Sarah
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);

        if (ModifyProductionCompanyController.getChosenMovie() != null) {
            chosenMovie = ModifyProductionCompanyController.getChosenMovie();
            setFieldsText(chosenMovie);
            changeCast.setDisable(false);
            delete.setDisable(false);
            save.setDisable(false);
        }
    }

    //endregion


    //Everything do do with manipulating the ListView (search,choose)
    //region
    /**
     * Searches the database for entries that match the search field's information in the database.
     * Writes all results into the list, which can then be chosen by the user.
     *
     * @author Sarah
     */
    @FXML
    public void handleSearch(MouseEvent event) {
        String searchText = searchField.getText();
        resultList.getItems().clear();
        searchResult = new ArrayList<>(App.getSystemInstance().searchMovie(searchText));
        if (!searchResult.isEmpty() && !searchField.getText().isEmpty()) {
            resultList.setDisable(false);
            resultList.setItems(FXCollections.observableArrayList(searchResult));
            searchField.clear();
        } else {
            errorMessageSearch.setVisible(true);
        }

    }

    /**
     * When the user chooses an object from the search list, this method is run. It will always write the
     * data associated with the LAST object chosen to the fields.
     *
     * @author Sarah
     */
    @FXML
    public void handleResultChosen(MouseEvent event) {
        resultList.setDisable(false);
        chosenMovie = (IMovie) resultList.getSelectionModel().getSelectedItem();
        System.out.println(chosenMovie);
        setFieldsText(chosenMovie);
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
     * @author Sarah
     */
    @FXML
    public void handleCreateNew(MouseEvent event) {
        resultList.setDisable(false);
        if (!movieName.getText().isEmpty() && !productionCompany.getText().isEmpty() && !releaseYear.getText().isEmpty()) {
            String companySearch = productionCompany.getText();
            ArrayList<IProductionCompany> results = new ArrayList<>();
            results.addAll(App.getSystemInstance().searchProductionCompany(companySearch));
            if (!results.isEmpty()) {
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
                }
            } else {
                errorMessage.setText("Fejl opstået, intet firma at tilføje til");
            }
        } else {
            errorMessage.setText("Fejl, venligst udfyld alle felter");

        }
    }
    //endregion

    //Delete Movie
    //region
    /**
     * Takes the movie that has been chosen from the ListView and deletes the object that is chosen. Provided that the
     * Movie is deleted in the instance of the program, it is also removed from the database, and unassigned from the
     * company's page.
     *
     * @author Sarah
     */
    @FXML
    public void handleDelete(MouseEvent event) {
        if (givenMovie != null) {
            chosenMovie = givenMovie;
        }
        status = chosenMovie.delete();
        if (status) {
            if (givenMovie == null) {
                searchResult.remove(chosenMovie);
            }
            errorMessage.setText(chosenMovie.getTitle() + " slettet");
            if (givenMovie == null) {
                resultList.setItems(FXCollections.observableArrayList(searchResult));
            }
            clearFields();
        }
        clearFields();
        resultList.refresh();
    }
    //endregion

    /**
     * Takes the movie that was chosen from the ListView and updates its attributes in the database with the text that is
     * entered into the TextFields.
     *
     * @author Sarah
     */
    @FXML
    public void handleSave(MouseEvent event) {
        if (givenMovie != null) {
            chosenMovie = givenMovie;
        }
        if (!movieName.getText().isEmpty() && !productionCompany.getText().isEmpty() && !releaseYear.getText().isEmpty()) {
            status = chosenMovie.update(movieName.getText(), releaseYear.getText());
            if (status) {
                if (givenMovie == null) {
                    resultList.setItems(FXCollections.observableArrayList(searchResult));
                }
                errorMessage.setText(chosenMovie.getTitle() + " opdateret");
                clearFields();
            } else {
                errorMessage.setText("Fejl opstået, " + chosenMovie.getTitle() + " blev ikke opdateret");
            }
            resultList.refresh();
        } else {
            errorMessage.setText("Fejl, udfyld venligst alle felter");
        }
    }

    //region

    /**
     * Method to standardise the retrieval of a production company for the production, and then setting the production's
     * values/attribute values to the textfields that they are related to.
     *
     * @author Sarah
     * @param movie specifies which movie that should have its information written to the fields
     */
    private void setFieldsText(IMovie movie){
        movieName.setText(movie.getTitle());

        IProductionCompany retrievedProductionCompany = App.retrieveProductionCompanyForMovie(movie);
        productionCompany.setText(retrievedProductionCompany.getName());
        String[] release = movie.getReleaseDate();
        releaseYear.setText(release[2]);
    }
    //endregion

    /**
     * Method for handling the opening of the assign/unassign cast-page, provided that a movie has been chosen
     *
     * @author Sarah
     */
    @FXML
    public void handleChangeCast(MouseEvent event) {
        if (chosenMovie != null) {
            App.setAssignCastModifier("movie");
            App.handleUnassignAssignStage();
        } else {
            errorMessage.setText("Ingen film valgt");
        }
    }

    /**
     * Empties the contents of all the TextFields
     *
     * @author Sarah
     */
    private void clearFields() {
        movieName.clear();
        releaseYear.clear();
        productionCompany.clear();
    }

    //Getters and Setters
    //region
    public static IMovie getChosenMovie() {
        return chosenMovie;
    }

    public static void setChosenMovie(IMovie chosenMovie) {
        givenMovie = chosenMovie;
    }
    //endregion

    /**
     * Methods which handle the changing of the FXMLs. Includes:
     * - Close the window (and thus the main process)
     * - Open the help-window
     * - Return to the past scene
     *
     * @author Sarah
     */
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

    @FXML
    public void handleClose(MouseEvent event) {
        App.closeWindow();
    }
    //endregion

}
