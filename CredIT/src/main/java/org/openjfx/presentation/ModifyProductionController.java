package org.openjfx.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.openjfx.interfaces.IBroadcast;
import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IProduction;
import org.openjfx.interfaces.IProductionCompany;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ModifyProductionController implements Initializable {

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
    private Button save;
    @FXML
    private Label close;
    @FXML
    private Label help;
    @FXML
    private Label seeBroadcasts;
    @FXML
    private Label goToBroadcast;
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
    private TextField productionName;
    @FXML
    private TextField releaseYear;
    @FXML
    private TextField productionCompany;
    @FXML
    private TextField amountOfSeasons;
    @FXML
    private TextField amountOfEpisodes;
    //endregion

    //Class Attributes
    //region
    private ArrayList<IProduction> searchResult;
    private static IProduction chosenProduction;
    private static IProduction givenProduction;
    private static IBroadcast chosenBroadcast;
    private boolean status;
    private Object obj;
    //endregion

    /**
     * Checks if a production has been given from the previous scene. If so, the fields will be set to show
     * the given production's information.
     *
     * @author Sarah
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);

        if (ModifyProductionCompanyController.getChosenProduction() != null) {
            chosenProduction = ModifyProductionCompanyController.getChosenProduction();
            setFieldsText(chosenProduction);

            save.setDisable(false);
            delete.setDisable(false);
        }
    }

    //Everything to do with the ListView (search, choose)
    //region
    /**
     * Searches the database for entries that match the search field's information in the database.
     * Writes all results into the list, which can then be chosen by the user.
     *
     * @author Sarah
     */
    @FXML
    private void handleSearch(MouseEvent event) {
        String searchText = searchField.getText();
        resultList.getItems().clear();
        searchResult = new ArrayList<>(App.getSystemInstance().searchProduction(searchText));
        if (searchResult != null && !searchField.getText().isEmpty()) {
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
    private void handleSearchResultChosen(MouseEvent event) {
        obj = resultList.getSelectionModel().getSelectedItem();
        if (obj instanceof IProduction) {
            chosenProduction = (IProduction) obj;
            setFieldsText(chosenProduction);
            save.setDisable(false);
            delete.setDisable(false);
            seeBroadcasts.setVisible(true);
        } else if (obj instanceof IBroadcast) {
            chosenBroadcast = (IBroadcast) obj;
            goToBroadcast.setVisible(true);
        }

    }
    //endregion

    //Create Production
    //region
    /**
     * Takes the information written in the fields and uses those as the parameters for the createProduction method
     * in the domain layer. Thus creates a new entry into the database, and assigns it to the Production Company that has
     * been entered into the company-name field, if it exists.
     *
     * @author Sarah
     */
    @FXML
    private void handleCreateNew(MouseEvent event) {
        if (givenProduction != null) {
            chosenProduction = givenProduction;
        }
        resultList.setDisable(false);
        if (!productionName.getText().isEmpty() && !releaseYear.getText().isEmpty() && !productionCompany.getText().isEmpty()) {
            String companySearch = productionCompany.getText();
            ArrayList<IProductionCompany> results = new ArrayList<>();
            results.addAll(App.getSystemInstance().searchProductionCompany(companySearch));
            if (!results.isEmpty()) {
                IProduction production = LoginSystemController.getAdminUser().addNewProductionToDatabase(productionName.getText(), releaseYear.getText(), results.get(0).getId());
                if (production != null) {
                    resultList.setDisable(false);
                    errorMessage.setText(production.getName() + " oprettet");
                    if (searchResult == null) {
                        searchResult = new ArrayList<>();
                    }
                    searchResult.clear();
                    searchResult.add(production);
                    resultList.setItems(FXCollections.observableArrayList(searchResult));
                    clearFields();
                }
            } else {
                errorMessage.setText("Fejl opstået, " + productionName.getText() + " blev ikke oprettet");
            }
            resultList.refresh();
        } else {
            errorMessage.setText("Fejl, venligst udfyld alle felter");
        }
    }
    //endregion

    //Delete Production
    //region
    /**
     * Takes the production that has been chosen from the ListView and deletes it from the application instance.
     * If that is successful, it will be removed from the database and unassigned from the Production.
     *
     * @author Sarah
     */
    @FXML
    private void handleDelete(MouseEvent event) {
        if (givenProduction != null) {
            chosenProduction = givenProduction;
        }
        status = chosenProduction.delete();
        if (status) {
            searchResult.remove(chosenProduction);
            errorMessage.setText(chosenProduction.getName() + " slettet");
            if (searchResult.isEmpty()) {
                resultList.getItems().clear();
            } else if (givenProduction == null) {
                resultList.setItems(FXCollections.observableArrayList(searchResult));
            }
            clearFields();
        } else {
            errorMessage.setText("Fejl opstået, " + chosenProduction.getName() + " blev ikke slettet");
        }
        resultList.refresh();
    }
    //endregion

    //Save Production
    //region
    /**
     * Takes the production that has been chosen from the ListView and updates its information with the text
     * that has been entered into the relevant Fields.
     *
     * @author Sarah
     */
    @FXML
    private void handleSave(MouseEvent event) {
        if (givenProduction != null) {
            chosenProduction = givenProduction;
        }
        if (!productionName.getText().isEmpty() && !releaseYear.getText().isEmpty()) {
            status = chosenProduction.update(productionName.getText(), releaseYear.getText());
            if (status) {
                errorMessage.setText(chosenProduction.getName() + " opdateret");
                clearFields();
            } else {
                errorMessage.setText("Fejl opstået, " + chosenProduction.getName() + " blev ikke opdateret");
            }
            resultList.refresh();
        } else {
            errorMessage.setText("Fejl, udfyld venligst alle felter");
        }
    }
    //endregion

    //Changes to view broadcasts for chosenProduction
    //region
    /**
     * Shows broadcasts that are registered to the Production in the ListView
     *
     * @author Sarah
     */
    @FXML
    private void handleShowBroadcasts(MouseEvent event) {
        ArrayList<IBroadcast> broadcastList = new ArrayList<>(chosenProduction.getBroadcasts());
        resultList.setItems(FXCollections.observableArrayList(broadcastList));
    }
    //endregion

    //Clears the fields
    //region
    /**
     * Empties the contents of all the TextFields
     *
     * @author Sarah
     */
    private void clearFields() {
        productionName.clear();
        productionCompany.clear();
        releaseYear.clear();
        amountOfEpisodes.clear();
        amountOfSeasons.clear();
    }
    //endregion

    //region
    /**
     * Method to standardise the retrieval of a production company for the production, and then setting the production's
     * values/attribute values to the textfields that they are related to.
     *
     * @author Sarah
     * @param production specifies which production that should have its information written to the fields
     */
    private void setFieldsText(IProduction production){
        productionName.setText(production.getName());
        releaseYear.setText(production.getYear());
        IProductionCompany retrievedProductionCompany = App.retrieveProductionCompanyForProduction(production);
        productionCompany.setText(retrievedProductionCompany.getName());
        amountOfEpisodes.setText(String.valueOf(production.getNumberOfEpisodes()));
        amountOfSeasons.setText(String.valueOf(production.getNumberOfSeasons()));
    }
    //endregion

    //Handler for navigating to the chosen broadcast
    //region
    @FXML
    private void goToBroadcast(MouseEvent event) {
        if (chosenBroadcast != null) {
            App.handleModifyBroadcastPage();
        } else {
            errorMessage.setText("Fejl, ingen udsendelse valgt");
        }
    }
    //endregion

    //Getters and Setters for the static variables
    //region
    public static IBroadcast getChosenBroadcast() {
        return chosenBroadcast;
    }

    public static void setChosenBroadcast(IBroadcast newValue) {
        chosenBroadcast = newValue;
    }

    public static void setChosenProduction(IProduction chosenProduction) {
        givenProduction = chosenProduction;
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
        if (ModifyProductionCompanyController.getChosenProduction() != null) {
            ModifyProductionCompanyController.setChosenProduction(null);
        }
        App.handleAdminPage();
    }

    @FXML
    private void handleClose(MouseEvent event) {
        App.closeWindow();
    }
    //endregion
}

