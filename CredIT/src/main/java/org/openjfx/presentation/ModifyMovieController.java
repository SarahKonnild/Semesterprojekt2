package org.openjfx.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IMovie;
import org.openjfx.interfaces.IProductionCompany;

import java.lang.reflect.Array;
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
    private ObservableList<IMovie> observableList;

    private static IMovie chosenMovie;
    private static IMovie givenMovie;
    private boolean status;
    //endregion

    /**
     * Checks if the scene has been given a movie from the previous scene. If so, it will write that Movie's information
     * to the fields.
     * @author Sarah
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);

        if(ModifyProductionCompanyController.getChosenMovie() != null) {
            givenMovie = ModifyProductionCompanyController.getChosenMovie();
            movieName.setText(givenMovie.getTitle());

            IProductionCompany retrievedProductionCompany = App.retrieveProductionCompanyForMovie(givenMovie);
            productionCompany.setText(retrievedProductionCompany.getName());
            String[] release = givenMovie.getReleaseDate();
            releaseYear.setText(release[2]);

        }
    }

    //Everything to do with the ListView (search, choose)
    //region
    /**
     * Searches the database for entries that match the search field's information in the database.
     * Writes all results into the list, which can then be chosen by the user.
     * @author Sarah
     * @param event
     */
    @FXML
    public void handleSearch(MouseEvent event){
        String searchText = searchField.getText();
        resultList.getItems().clear();
        searchResult = App.getSystemInstance().searchMovie(searchText);
        if(searchResult != null && !searchField.getText().isEmpty()){
            resultList.setDisable(false);
            observableList = FXCollections.observableArrayList(searchResult);
            resultList.setItems(observableList);
            searchField.clear();
        }else{
            errorMessageSearch.setVisible(true);
        }

    }

    /**
     * When the user chooses an object from the search list, this method is run. It will always write the
     * data associated with the LAST object chosen to the fields.
     * @author Sarah
     * @param event
     */
    @FXML
    public void handleResultChosen(MouseEvent event){
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


    //Create Movie
    //region

    /**
     * Takes the entered ProductionCompany's name and performs a search in the database for that company. If it exists,
     * the Movie can be created, adding it to that Company's list of created movies. If the Company does not exist,
     * an error message is written to the user.
     * @author Sarah
     * @param event
     */
    @FXML
    public void handleCreateNew(MouseEvent event){
        String companySearch = productionCompany.getText();
        ArrayList<IProductionCompany> results = new ArrayList<>();
        results.addAll(App.getSystemInstance().searchProductionCompany(companySearch));
        if(results.get(0).getName().equalsIgnoreCase(companySearch)) {
            resultList.setDisable(false);
            IMovie movie = LoginSystemController.getAdminUser().addNewMovieToDatabase(movieName.getText(), results.get(0).getId(), releaseYear.getText());
            clearFields();
            if(movie != null){
                errorMessage.setText(movie.getTitle() + " tilføjet");
                if(searchResult == null) {
                    searchResult = new ArrayList<>();
                }
                searchResult.add(movie);
                resultList.setItems(FXCollections.observableArrayList(searchResult));
            }else{
                errorMessage.setText("Fejl opstået, " + movie.getTitle() + " blev ikke tilføjet");
            }
            resultList.refresh();
        }else{
            errorMessage.setText("Fejl opstået, intet firma at tilføje til");
        }
    }
    //endregion

    //Delete Movie
    //region

    /**
     * Takes the movie that has been chosen from the ListView and deletes the object that is chosen. Provided that the
     * Movie is deleted in the instance of the program, it is also removed from the database, and unassigned from the
     * company's page.
     * @author Sarah
     * @param event
     */
    @FXML
    public void handleDelete(MouseEvent event){
            status = chosenMovie.delete();
            if(status){
                searchResult.remove(chosenMovie);
                IProductionCompany retrievedProductionCompany = App.retrieveProductionCompanyForMovie(chosenMovie);
                retrievedProductionCompany.unassignMovie(chosenMovie);
                errorMessage.setText(chosenMovie.getTitle() + " slettet");
                if(chosenMovie == null){
                    resultList.getItems().clear();
                } else{
                    resultList.setItems(FXCollections.observableArrayList(searchResult));
                }
                clearFields();
            }
        resultList.refresh();
    }
    //endregion

    //Save Movie
    //region
    /**
     * Takes the movie that was chosen from the ListView and updates its attributes in the database with the text that is
     * entered into the TextFields.
     * @author Sarah
     * @param event
     */
    @FXML
    public void handleSave(MouseEvent event){
            String[] airDate = chosenMovie.getReleaseDate();
            status = chosenMovie.update(movieName.getText(), airDate[2]);
            if(status){
                errorMessage.setText(chosenMovie.getTitle() + " opdateret");
                clearFields();
            }else{
                errorMessage.setText("Fejl opstået, " + chosenMovie.getTitle() + " blev ikke opdateret");
            }
        resultList.refresh();
    }
    //endregion


    //All the methods which change the scene, open the help stage or close the program.
    //region
    @FXML
    public void handleHelp(MouseEvent event){
        App.handleHelpStage();
    }

    @FXML
    public void handleBack(MouseEvent event){
        App.handleAdminPage();
    }

    @FXML
    public void handleClose(MouseEvent event){App.closeWindow();}

    /**
     * Sets a String variable that ensures that the assign/unassign cast methods are run correctly with the Movie-related
     * methods.
     * @author Sarah
     * @param event
     */
    @FXML
    public void handleChangeCast(ActionEvent event){
        App.setAssignCastModifier("movie");
        App.handleUnassignAssignStage();
    }
    //endregion

    private void clearFields(){
        movieName.clear();
        releaseYear.clear();
        productionCompany.clear();
    }

    public static IMovie getChosenMovie(){
        return chosenMovie;
    }

    public static void setChosenMovie(IMovie chosenMovie){
        givenMovie = chosenMovie;
    }

}
