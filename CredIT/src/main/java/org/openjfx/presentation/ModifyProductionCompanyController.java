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
import org.openjfx.interfaces.IProduction;
import org.openjfx.interfaces.IProductionCompany;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ModifyProductionCompanyController implements Initializable {

    //FXML Attributes
    //region
    @FXML
    private AnchorPane basePane;
    @FXML
    private Button search;
    @FXML
    private Button createNew;
    @FXML
    private Button delete;
    @FXML
    private Button saveChanges;
    @FXML
    private Label errorMessageSearch;
    @FXML
    private Label errorMessage;
    @FXML
    private Label help;
    @FXML
    private Label back;
    @FXML
    private Label close;
    @FXML
    private Label changeToMovie;
    @FXML
    private Label changeToProduction;
    @FXML
    private Label goTo;
    @FXML
    private ListView resultList;
    @FXML
    private TextField searchField;
    @FXML
    private TextField nameField;
    //endregion

    //Class Attributes
    //region
    private ArrayList<IProductionCompany> searchList;
    private ArrayList<IMovie> movieList;
    private ArrayList<IProduction> productionList;
    private Object obj;
    private static IProductionCompany chosenProductionCompany;
    private static IProduction chosenProduction;
    private static IMovie chosenMovie;
    private static String chosenItem;
    private boolean status;

    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);
        delete.setDisable(true);
        saveChanges.setDisable(true);
    }

    //Methods to handle the search (searching using a string and picking an option from list
    //region
    /**
     * Takes the text that is written in the searchfield and uses that to run the searchProductionCompany
     * method in the domain layer's System class. If the list has items, and the searchfield isn't empty,
     * the items returned from the persistence layer will be written to a list which can be printed into
     * the ListView.
     *
     * @author Sarah
     */
    @FXML
    private void handleSearch(MouseEvent event) {
        String searchText = searchField.getText();
        resultList.getItems().clear();
        searchList = new ArrayList<>(App.getSystemInstance().searchProductionCompany(searchText));
        if (searchList != null && !searchField.getText().isEmpty()) {
            resultList.setDisable(false);
            resultList.setItems(FXCollections.observableArrayList(searchList));
            searchField.clear();
        } else {
            errorMessageSearch.setVisible(true);
            if (resultList.getItems().isEmpty()) {
                resultList.setDisable(true);
            }
        }
    }

    /**
     * Performs a check on the object-type of the item that is found in the list.
     * If the objects are instances of IProductionCompany, the item chosen will be saved
     * into a static variable.
     * If the objects are instances of either IProduction or IMovie, the GoTo-label becomes visible
     * with the appropriate text, enabling them to be clicked.
     *
     * @author Sarah
     */
    @FXML
    private void handleSearchResultChosen(MouseEvent event) {
        obj = resultList.getSelectionModel().getSelectedItem();
        if (obj instanceof IProductionCompany) {
            goTo.setVisible(false);
            chosenProductionCompany = (IProductionCompany) obj;
            nameField.setText(chosenProductionCompany.getName());
            changeToMovie.setVisible(true);
            changeToProduction.setVisible(true);
            toggleButtons(false);
            movieList = new ArrayList<>(chosenProductionCompany.getMovieList());
            productionList = new ArrayList<>(chosenProductionCompany.getProductionList());
            delete.setDisable(false);
            saveChanges.setDisable(false);
        } else if (obj instanceof IProduction) {
            chosenProduction = (IProduction) obj;
            goTo.setVisible(true);
            goTo.setText("Gå til Produktion");
            toggleButtons(true);
            chosenItem = "production";
        } else if (obj instanceof IMovie) {
            chosenMovie = (IMovie) obj;
            goTo.setVisible(true);
            goTo.setText("Gå til Film");
            toggleButtons(true);
            chosenItem = "movie";
        }
    }
    //endregion

    //Method to handle the creation of a ProductionCompany
    //region
    /**
     * Checks if the Name-Field is empty, and if not, a search is conducted on the Company-name. If there is a company in the
     * Database with that name already, it cannot be created, as only one company with the same name is permitted. If the company
     * does not exist, it can be created.
     *
     * @author Sarah
     */
    @FXML
    private void handleCreateNew(MouseEvent event) {
        resultList.setDisable(false);
        if (!nameField.getText().isEmpty()) {
            ArrayList<IProductionCompany> search = new ArrayList<>(App.getSystemInstance().searchProductionCompany(nameField.getText()));
            if (search.isEmpty()) {
                IProductionCompany productionCompany = LoginSystemController.getAdminUser().addNewProductionCompanyToDatabase(nameField.getText());
                if (productionCompany != null) {
                    if (searchList == null) {
                        searchList = new ArrayList<>();
                    }
                    errorMessage.setText(productionCompany.getName() + " oprettet");
                    searchList.clear();
                    searchList.add(productionCompany);
                    resultList.setItems(FXCollections.observableArrayList(searchList));
                    nameField.clear();
                } else {
                    errorMessage.setText("Fejl, " + productionCompany.getName() + " blev ikke oprettet");
                }
                resultList.refresh();
            } else {
                errorMessage.setText("Fejl, " + nameField.getText() + " eksisterer allerede");
                nameField.clear();
            }
        } else {
            errorMessage.setText("Fejl, navnefelt er tomt");
        }
    }
    //endregion

    //Method to handle the deletion of a ProductionCompany
    //region
    /**
     * Takes the productionCompany that was chosen from the ListView and deletes it first in the instance of the application,
     * and if that was succesfully deleted, it is also removed from the database, along with all of the movies and productions
     * that are associated with it.
     *
     * @author Sarah
     */
    @FXML
    private void handleDelete(MouseEvent event) {
        if (chosenProductionCompany != null) {
            status = chosenProductionCompany.delete();
            if (status) {
                searchList.remove(chosenProductionCompany);
                errorMessage.setText(chosenProductionCompany.getName() + " slettet");
                if (searchList.isEmpty()) {
                    resultList.getItems().clear();
                } else {
                    resultList.setItems(FXCollections.observableArrayList(searchList));
                }
                nameField.clear();
            } else {
                errorMessage.setText("Fejl, " + chosenProductionCompany.getName() + " blev ikke slettet");
            }
        } else {
            errorMessage.setText("Intet produktionsfirma valgt");
        }
        resultList.refresh();
    }
    //endregion

    //Method to handle the update of a ProductionCompany
    //region
    /**
     * Provided that a productionCompany has been chosen, the name of the ProductionCompany can be updated,
     * by registering the new name written in the NameField into the database.
     *
     * @author Sarah
     */
    @FXML
    private void handleSaveChanges(MouseEvent event) {
        if (chosenProductionCompany != null && !nameField.getText().isEmpty()) {
            status = chosenProductionCompany.update(nameField.getText());
            if (status) {
                errorMessage.setText(chosenProductionCompany.getName() + " opdateret");
                nameField.clear();
            } else {
                errorMessage.setText("Fejl, " + chosenProductionCompany.getName() + " blev ikke opdateret");
            }
        } else {
            errorMessage.setText("Intet produktionsfirma valgt");
        }
        resultList.refresh();
    }
    //endregion

    //Methods to change the contents that are displayed in the ListView
    //region
    /**
     * If a ProductionCompany has been chosen, the user can press this, and it will show the list of movies
     * that are associated with that Company in the ListView. If the company has no registered movies, an error
     * message is written to the user.
     *
     * @author Sarah
     */
    @FXML
    private void handleChangeToMovies(MouseEvent event) {
        if (chosenProductionCompany != null) {
            if (movieList != null) {
                resultList.setItems(FXCollections.observableArrayList(movieList));
                changeToMovie.setVisible(false);
                changeToProduction.setVisible(true);
            } else {
                errorMessage.setText(chosenProductionCompany.getName() + " laver ikke film");
            }
        } else {
            errorMessage.setText("Fejl opstået, intet produktionsfirma valgt");
        }
    }

    /**
     * If a ProductionCompany has been chosen, the user can press this, and it will show the list of Productions
     * that are associated with that Company in the ListView. If the company has no registered Productions, an error
     * message is written to the user.
     *
     * @author Sarah
     */
    @FXML
    private void handleChangeToProductions(MouseEvent event) {
        if (chosenProductionCompany != null) {
            if (productionList != null) {
                resultList.setItems(FXCollections.observableArrayList(productionList));
                changeToMovie.setVisible(true);
                changeToProduction.setVisible(false);
            } else {
                errorMessage.setText(chosenProductionCompany.getName() + " laver ikke produktioner");
            }
        } else {
            errorMessage.setText("Fejl opstået, intet produktionsfirma valgt");
        }
    }
    //endregion

    //Methods to change the stage
    //region
    /**
     * Checks if the element from the ListView that is checked is either a Movie or a Production, and based on that,
     * it will specify which scene to go to, showing that chosen movie/production's information first thing in the
     * scene's fields.
     *
     * @author Sarah
     */
    @FXML
    private void handleGoTo(MouseEvent event) {
        if (chosenItem.equals("movie") && chosenMovie != null) {
            App.handleModifyMoviePage();
            ModifyMovieController.setChosenMovie(chosenMovie);
        } else if (chosenItem.equals("production") && chosenProduction != null) {
            App.handleModifyProductionPage();
            ModifyProductionController.setChosenProduction(chosenProduction);
        }
    }
    //endregion

    //Toggle methods
    //region
    private void toggleButtons(boolean value) {
        createNew.setDisable(value);
        delete.setDisable(value);
        saveChanges.setDisable(value);
    }
    //endregion

    //Getters for the static variables
    //region
    public static IMovie getChosenMovie() {
        return chosenMovie;
    }

    public static void setChosenMovie(IMovie newValue) {
        chosenMovie = newValue;
    }

    public static IProduction getChosenProduction() {
        return chosenProduction;
    }

    public static void setChosenProduction(IProduction newValue) {
        chosenProduction = newValue;
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
    private void handleHelp(MouseEvent event) {
        App.handleHelpStage();
    }

    @FXML
    private void handleBack(MouseEvent event) {
        App.handleAdminPage();
    }

    @FXML
    private void handleClose(MouseEvent event) {
        App.closeWindow();
    }
    //endregion
}
