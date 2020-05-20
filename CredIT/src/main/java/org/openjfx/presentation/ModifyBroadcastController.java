package org.openjfx.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.openjfx.interfaces.IBroadcast;
import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IProduction;
import org.openjfx.interfaces.IProductionCompany;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ModifyBroadcastController implements Initializable {

    //FXML Attributes
    //region
    @FXML
    private AnchorPane basePane;
    @FXML
    private Button search;
    @FXML
    private Button modifyCast;
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
    private TextField broadcastName;
    @FXML
    private TextField production;
    @FXML
    private TextField season;
    @FXML
    private TextField episode;
    @FXML
    private TextField day;
    @FXML
    private TextField month;
    @FXML
    private TextField year;
    //endregion

    //Class Attributes
    //region
    private ObservableList<IBroadcast> observableList;
    private ArrayList<IBroadcast> searchList;
    private static IBroadcast chosenBroadcast;
    private static IBroadcast givenBroadcast;
    private boolean status;
    //endregion

    /**
     * Checks if the scene has been given an IBroadcast-object from the previous scene, and if so, it will write this
     * object's information to the relevant fields.
     *
     * @param location
     * @param resources
     * @author Sarah
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);

        if (ModifyProductionController.getChosenBroadcast() != null) {
            givenBroadcast = ModifyProductionController.getChosenBroadcast();
            chosenBroadcast = givenBroadcast;
            broadcastName.setText(givenBroadcast.getName());
            IProduction retrievedProduction = App.retrieveProduction(givenBroadcast);
            production.setText(retrievedProduction.getName());
            String[] airDate = givenBroadcast.getAirDate();
            day.setText(airDate[0]);
            month.setText(airDate[1]);
            year.setText(airDate[2]);
            season.setText(String.valueOf(givenBroadcast.getSeasonNumber()));
            episode.setText(String.valueOf(givenBroadcast.getEpisodeNumber()));
            modifyCast.setDisable(false);
            delete.setDisable(false);
            save.setDisable(false);
        }
    }

    //Everything do do with manipulating the ListView (search,choose)
    //region

    /**
     * Takes the text that is written in the searchfield and uses that to run the searchBroadcast
     * method in the domain layer's System class. If the list has items, and the searchfield isn't empty,
     * the items returned from the persistence layer will be written to a list which can be printed into
     * the ListView.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleSearch(MouseEvent event) {
        String searchText = searchField.getText();
        resultList.getItems().clear();
        searchList = new ArrayList<>(App.getSystemInstance().searchBroadcast(searchText));
        if (searchList != null && !searchField.getText().isEmpty()) {
            resultList.setDisable(false);
            observableList = FXCollections.observableArrayList(searchList);
            resultList.setItems(observableList);
            clearFields();
        } else {
            errorMessageSearch.setVisible(true);
        }

    }

    /**
     * When the user chooses an object from the search list, this method is run. It will always write the
     * data associated with the LAST object chosen to the fields.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleSearchResultChosen(MouseEvent event) {
        chosenBroadcast = (IBroadcast) resultList.getSelectionModel().getSelectedItem();
        broadcastName.setText(chosenBroadcast.getName());
        IProduction retrievedProduction = App.retrieveProduction(chosenBroadcast);
        production.setText(retrievedProduction.getName());
        String[] airDate = chosenBroadcast.getAirDate();
        day.setText(airDate[0]);
        month.setText(airDate[1]);
        year.setText(airDate[2]);
        season.setText(String.valueOf(chosenBroadcast.getSeasonNumber()));
        episode.setText(String.valueOf(chosenBroadcast.getEpisodeNumber()));

        modifyCast.setDisable(false);
        delete.setDisable(false);
        save.setDisable(false);
    }
    //endregion

    //Create Broadcast
    //region

    /**
     * First, a check on the Production that the user has entered as the "parent" for the Broadcast exists. If it exists,
     * it will take the first element in the ArrayList that is returned with Productions and add the Broadcast that is being
     * created to that Production's list of Broadcasts. If the Production doesn't exist, an error message is printed to the user.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleCreateBroadcast(MouseEvent event) {
        resultList.setDisable(false);
        if (!production.getText().isEmpty() && !broadcastName.getText().isEmpty() && !season.getText().isEmpty() && !episode.getText().isEmpty()) {
            String productionSearch = production.getText();
            ArrayList<IProduction> results = new ArrayList<>(App.getSystemInstance().searchProduction(productionSearch));
            if (results.get(0).getName().equalsIgnoreCase(productionSearch)) {
                String dateVariable = day.getText() + "-" + month.getText() + "-" + year.getText();
                if (day.getText().length() != 2 && month.getText().length() != 2 && year.getText().length() != 4) {
                    errorMessage.setText("Fejl, ugyldig datoindtastning");
                } else {
                    IBroadcast broadcast = LoginSystemController.getAdminUser().addNewBroadcastToDatabase(broadcastName.getText(), Integer.parseInt(season.getText()),
                            Integer.parseInt(episode.getText()), dateVariable, results.get(0).getId());
                    clearFields();
                    if (broadcast != null) {
                        resultList.setDisable(false);
                        errorMessage.setText(broadcast.getName() + " tilføjet");
                        if (searchList == null) {
                            searchList = new ArrayList<>();
                        }
                        searchList.clear();
                        searchList.add(broadcast);
                        resultList.setItems(FXCollections.observableArrayList(searchList));

                    } else {
                        errorMessage.setText("Fejl, udsendelsen blev ikke tilføjet");
                    }
                }
                resultList.refresh();
            } else {
                errorMessage.setText("Fejl, ingen produktion at tilføje til");
            }
        } else {
            errorMessage.setText("Fejl, venligst udfyld alle felter");
        }
    }
    //endregion

    //Delete Broadcast
    //region

    /**
     * When the user chooses an object from the search list, and they press the "delete"-button, this method
     * is run. It will delete the object that is chosen from the database, as well as unassigning it from the Production
     * that it was given upon creation.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleDeleteBroadcast(MouseEvent event) {
        if (givenBroadcast != null) {
            chosenBroadcast = givenBroadcast;
        }
        if (!observableList.isEmpty()) {
            chosenBroadcast = (IBroadcast) resultList.getSelectionModel().getSelectedItem();
            status = chosenBroadcast.delete();
            if (status) {
                searchList.remove(chosenBroadcast);
                errorMessage.setText(chosenBroadcast.getName() + " slettet");
                if (searchList.isEmpty()) {
                    resultList.getItems().clear();
                } else if (givenBroadcast == null) {
                    resultList.setItems(FXCollections.observableArrayList(searchList));
                }
                clearFields();
            } else {
                errorMessage.setText("Fejl, " + chosenBroadcast.getName() + " blev ikke slettet");
            }
        } else {
            errorMessage.setText("Ingen udsendelse valgt");
        }
        resultList.refresh();
    }
    //endregion

    //Save Broadcast
    //region

    /**
     * Takes the broadcast that has been chosen from the ListView by the user, and overwrites the information that is stored
     * about that broadcast in the database with the information that is entered into the TextFields.
     *
     * @param event
     * @author Sarah
     */
    @FXML
    public void handleSaveBroadcast(MouseEvent event) {
        if (givenBroadcast != null) {
            chosenBroadcast = givenBroadcast;
        }
        if (!broadcastName.getText().isEmpty() && !production.getText().isEmpty() && !season.getText().isEmpty() & !episode.getText().isEmpty()) {
            status = chosenBroadcast.update(broadcastName.getText(), Integer.parseInt(season.getText()), Integer.parseInt(episode.getText()), day.getText() + "-" + month.getText() + "-" + year.getText());
            if (status) {
                errorMessage.setText(chosenBroadcast.getName() + " opdateret");
                clearFields();
            } else {
                errorMessage.setText("Fejl, " + chosenBroadcast.getName() + " blev ikke opdateret");
            }
        } else {
            errorMessage.setText("Fejl, udfyld venligst alle felter");
        }
        resultList.getItems().clear();
    }

    //endregion

    //Changes the scene of the primary stage, opens the new Help-stage and closes the entire program.
    //region
    @FXML
    public void handleBack(MouseEvent event) {
        if (ModifyProductionController.getChosenBroadcast() != null) {
            ModifyProductionController.setChosenBroadcast(null);
            chosenBroadcast = null;
        }
        App.handleAdminPage();
    }

    @FXML
    public void handleHelp(MouseEvent event) {
        App.handleHelpStage();
    }

    @FXML
    public void handleClose(MouseEvent event) {
        App.closeWindow();
    }

    @FXML
    public void handleChangeCast(MouseEvent event) {
        if (chosenBroadcast != null) {
            App.setAssignCastModifier("broadcast");
            App.handleUnassignAssignStage();
        } else {
            errorMessage.setText("Ingen udsendelse valgt");
        }

    }
    //endregion

    //Clears the fields
    //region

    /**
     * A simple method that clears all the TextFields when run.
     *
     * @author Sarah
     */
    private void clearFields() {
        broadcastName.clear();
        production.clear();
        year.clear();
        day.clear();
        month.clear();
        season.clear();
        episode.clear();
    }
    //endregion

    //Retrieve the chosen broadcast for scene changes
    //region
    public static IBroadcast getChosenBroadcast() {
        return chosenBroadcast;
    }

    //endregion
}
