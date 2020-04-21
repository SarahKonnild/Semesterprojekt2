package org.openjfx.presentation;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.stage.Stage;
import org.openjfx.interfaces.IBroadcast;
import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IProduction;

public class LandingPageController implements Initializable {
    @FXML
    private Button createNewCast;
    @FXML
    private Button deleteCast;
    @FXML
    private Button mergeCast;
    @FXML
    private Button saveChangesCast;
    @FXML
    private Button searchCast;
    @FXML
    private Button createNewProduction;
    @FXML
    private Button deleteProduction;
    @FXML
    private Button saveChangesProduction;
    @FXML
    private Button searchProduction;
    @FXML
    private Button assignCast;
    @FXML
    private Button removeCast;
    @FXML
    private Button createBroadcast;
    @FXML
    private Button deleteBroadcast;
    @FXML
    private Button saveChangesBroadcast;
    @FXML
    private Button searchBroadcast;
    @FXML
    private Label errorMsgCastSearch;
    @FXML
    private Label errorMsgProductionSearch;
    @FXML
    private Label errorMsgBroadcastSearch;
    @FXML
    private Label userInfo;
    @FXML
    private Label errorMsgBroadcast;
    @FXML
    private Label errorMsgProduction;
    @FXML
    private Label errorMsgCast;
    @FXML
    private ListView searchResult;
    @FXML
    private ListView roleList;
    @FXML
    private MenuBar menubar;
    @FXML
    private Tab castTab;
    @FXML
    private Tab productionTab;
    @FXML
    private Tab broadcastTab;
    @FXML
    private TextField searchFieldCast;
    @FXML
    private TextField searchFieldProduction;
    @FXML
    private TextField searchFieldBroadcast;
    @FXML
    private TextField castName;
    @FXML
    private TextField regDKField;
    @FXML
    private TextField productionName;
    @FXML
    private TextField productionReleaseYear;
    @FXML
    private TextField amountOfSeasons;
    @FXML
    private TextField producerName;
    @FXML
    private TextField amountOfBroadcasts;
    @FXML
    private TextField broadcastName;
    @FXML
    private TextField broadcastProduction;
    @FXML
    private TextField broadcastSeason;
    @FXML
    private TextField broadcastEpisodeNumber;
    @FXML
    private TextField broadcastAirDateDay;
    @FXML
    private TextField broadcastAirDateMonth;
    @FXML
    private TextField broadcastAirDateYear;

    private Stage assignStage = new Stage();
    private Stage unassignStage = new Stage();

    private ArrayList<IProduction> productionSearchResult;
    private ArrayList<ICast> castSearchResult;
    private ArrayList<IBroadcast> broadcastSearchResult;

    private ObservableList<ICast> castObservableList;
    private ObservableList<IProduction> productionObservableList;
    private ObservableList<IBroadcast> broadcastObservableList;

    private boolean creationState;

    private static IBroadcast broadcastChosen = null;

    private boolean allowOpenNewWindow;

    private ICast chosenCast;
    private ArrayList<ICast> chosenCastList;
    private Object obj;
    private ArrayList<Object> chosenObjectsList;
    private String searchText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userInfo.setText("Administrator");
    }

    //HANDLER FOR THE SEARCH LISTVIEW

    /**
     * Creates a new Object to which the chosen object in the ListView gets saved to. This object is then checked for its actual type, using the InstanceOf
     * and based on which object type it is, the chosen object in the ListView gets cast into the appropriate type, from which its attribute values can then be
     * printed into the relevant textfields.
     * @param event
     */
    @FXML
    public void handleSearchResultChosen(MouseEvent event) {
        obj = searchResult.getSelectionModel().getSelectedItem();
        if (obj instanceof ICast) {
            searchText = searchFieldCast.getText();
            searchResult.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            chosenCast = (ICast) obj;
            castName.setText(chosenCast.getName());
            regDKField.setText(String.valueOf(chosenCast.getRegDKID()));
            createNewCast.setDisable(false);
            deleteCast.setDisable(false);
            mergeCast.setDisable(false);
            saveChangesCast.setDisable(false);
        } else if (obj instanceof IProduction) {
            searchText = searchFieldProduction.getText();
            IProduction chosenProduction = (IProduction) obj;
            productionName.setText(chosenProduction.getName());
            producerName.setText(chosenProduction.getProductionCompany());
            productionReleaseYear.setText(chosenProduction.getYear());
            createNewProduction.setDisable(false);
        } else if (obj instanceof IBroadcast) {
            searchText = searchFieldBroadcast.getText();
            obj = searchResult.getSelectionModel().getSelectedItem();
            IBroadcast chosenBroadcast = (IBroadcast) obj;
            broadcastName.setText(chosenBroadcast.getName());
            broadcastEpisodeNumber.setText(String.valueOf(chosenBroadcast.getEpisodeNumber()));
            broadcastSeason.setText(String.valueOf(chosenBroadcast.getSeasonNumber()));
            String[] airDateInput = chosenBroadcast.getAirDate();
//            broadcastAirDateDay.setDisable(true);
            broadcastAirDateDay.setText(airDateInput[0]);
//            broadcastAirDateMonth.setDisable(true);
            broadcastAirDateMonth.setText(airDateInput[1]);
//            broadcastAirDateYear.setDisable(true);
            broadcastAirDateYear.setText(airDateInput[2]);
            broadcastProduction.setText(chosenBroadcast.getProductionName());
            assignCast.setDisable(false);
            removeCast.setDisable(false);
            createBroadcast.setDisable(false);

        }
    }

    //FXML HANDLERS FOR "MEDVIRKENDE" TAB

    /**
     * Clears the ListView for items.
     * Uses the information written in the castSearchField as the keyword in the searchmethod that is run to search through the persistence layer
     * for matching results. This result, if there is any, will be written to an ObservableList, which can then be printed to the ListView.
     * If there are no matching results, the user is presented with an error message.
     * @param event
     */
    @FXML
    public void handleSearchCast(MouseEvent event) {
        String searchText = searchFieldCast.getText();
        searchResult.getItems().clear();
        castSearchResult = App.getSystemInstance().searchCast(searchText);
        if (castSearchResult != null && !searchFieldCast.getText().isEmpty()) {
            castObservableList = FXCollections.observableArrayList(castSearchResult);
            searchResult.setItems(castObservableList);
            searchFieldCast.clear();
        } else {
            errorMsgCastSearch.setText("Fejl opstået, medvirkende ikke fundet");
        }
    }

    /**
     * Uses the information that is provided in the textfields in the cast-tab, to create a new cast object in the database. If the cast member is
     * created, the user is informed of the succesful creation and clears the fields that the text/information was received from. If not, the user
     * is presented with an error message.
     * @param event
     */
    @FXML
    public void handleCreateNewCast(MouseEvent event) {
        ICast cast = LoginController.getAdminUser().addNewCastToDatabase(castName.getText(), Integer.parseInt(regDKField.getText()));
        if (cast != null) {
            errorMsgCast.setText("Medvirkende oprettet");
            castSearchResult.add(cast);
            //Todo Decide if we wanna do a filtered update. Does it show up if the user have searched for Hans, but creates a new Cast named Sarah? 
            searchResult.setItems(FXCollections.observableArrayList(castSearchResult));
            searchResult.refresh();
            clearCastFields();
        } else {
            errorMsgCast.setText("Fejl opstået, medvirkende blev ikke oprettet");
        }
        searchResult.refresh();
    }

    /**
     * Checks if the observablelist that is printed to the ListView contains elements and is the correct observable list used for the cast members.
     * If correct, the chosen cast member from the list will be selected, and the method deleteCast is run on the cast object that has been chosen.
     * If the run is succesful, the user is presented with a success message. If not, an error message is printed.
     * @param event
     */
    @FXML
    public void handleDeleteCast(MouseEvent event) {
        if (!castObservableList.isEmpty()) {
            chosenCast = (ICast)searchResult.getSelectionModel().getSelectedItem();
            castSearchResult.remove(chosenCast);
            creationState = chosenCast.deleteCast();
            if (creationState) {
                errorMsgCast.setText("Medvirkende slettet");
                //updates the list view so it no longers shows the deleted cast
                if(castSearchResult.isEmpty()) {
                    searchResult.getItems().clear();
                }else{
                    searchResult.setItems(FXCollections.observableArrayList(castSearchResult));
                }

                clearCastFields();
            } else {
                errorMsgCast.setText("Fejl opstået, medvirkende blev ikke slettet");
            }
        } else {
            errorMsgCast.setText("Ingen medvirkende er valgt");
        }
        searchResult.refresh();
    }

    /**
     * Checks the elements that have been chosen in the ListView, and if there are exactly 2 elements chosen, the merge-method is run on the object,
     * which combines the second element into the first element chosen. If successful, a success message is written.
     * If there are less or more cast members chosen, the user will be presented with an appropriate error message.
     * @param event
     */
    @FXML
    public void handleMergeCast(MouseEvent event) {
        if (!castObservableList.isEmpty()) {
            ObservableList<ICast> chosenCastList = searchResult.getSelectionModel().getSelectedItems();
            if (chosenCastList.size() == 2) {
                creationState = chosenCastList.get(0).mergeCastMembers(chosenCastList.get(1));
                if (creationState) {
                    errorMsgCast.setText("Medvirkende sammenflettet");
                    clearCastFields();
//                    castSearchResult = App.getSystemInstance().searchCast(searchText);
//                    searchResult.setItems(FXCollections.observableArrayList(castSearchResult));
                    searchResult.refresh();
                } else {
                    errorMsgCast.setText("Fejl opstået, medvirkende blev ikke sammenflettet");
                }
            } else if (chosenCastList.size() == 1) {
                errorMsgCast.setText("Fejl opstået, for få medvirkende valgt");
            } else {
                errorMsgCast.setText("Fejl opstået, for mange medvirkende valgt");
            }
        } else {
            errorMsgCast.setText("Ingen medvirkende er valgt");
        }
    }

    /**
     * Checks if the observablelist with casts is empty. If not, the chosen element will be updated to overwrite the past information it was given,
     * receiving the new input from the textfields.
     * If the method is succesfully run, the user will be presented with a success message. If not, the user is presented with an error message.
     * Same applies, if no cast member has been chosen.
     * @param event
     */
    @FXML
    public void handleSaveCastChanges(MouseEvent event) {
        if (!castObservableList.isEmpty()) {
            chosenCast = (ICast) searchResult.getSelectionModel().getSelectedItem();
            creationState = chosenCast.updateCast(castName.getText(), Integer.parseInt(regDKField.getText()));
            if (creationState) {
                errorMsgCast.setText("Medvirkende opdateret");
                clearCastFields();
            } else {
                errorMsgCast.setText("Fejl opstået, medvirkende blev ikke opdateret");
            }
        } else {
            errorMsgCast.setText("Ingen medvirkende er valgt");
        }
        searchResult.refresh();
    }

    //FXML HANDLERS FOR "PRODUKTION" TAB

    /**
     * Clears the ListView for items.
     * Fetches the information that is written to the searchField in the production tab, and uses that String to search the persistence layer for information
     * that corresponds to the input. If it successfully fetched the information, the results are printed to an observable list, which is then printed to the
     * ListView. The searchfield is cleared.
     * @param event
     */
    @FXML
    public void handleSearchProduction(MouseEvent event) {
        String searchText = searchFieldProduction.getText();
        searchResult.getItems().clear();
        productionSearchResult = App.getSystemInstance().searchProduction(searchText);
        if (productionSearchResult != null && !searchFieldProduction.getText().isEmpty()) {
            productionObservableList = FXCollections.observableArrayList(productionSearchResult);
            searchResult.setItems(productionObservableList);
            searchFieldProduction.clear();
        } else {
            errorMsgProductionSearch.setVisible(true);
        }
    }

    /**
     * Uses the information that is written in the three relevant production textfields, and uses them to create a new production object in the persistence layer.
     * If successful, a success message is printed to the user, and the fields are cleared. If not, an error message is printed.
     * @param event
     */
    @FXML
    public void handleCreateNewProduction(MouseEvent event) {
        IProduction production = (IProduction)LoginController.getAdminUser().addNewProductionToDatabase(productionName.getText(), productionReleaseYear.getText(), producerName.getText());
        if (production != null) {
            errorMsgProduction.setText("Produktionen oprettet");
            productionSearchResult.add(production);
            searchResult.setItems(FXCollections.observableArrayList(productionSearchResult));
            clearProductionFields();
        } else {
            errorMsgProduction.setText("Fejl opstået, produktionen blev ikke oprettet");
        }
        searchResult.refresh();
    }

    //Commented out since it is not part of the initial must-have requirements
//    @FXML
//    public void handleDeleteProduction(MouseEvent event){
//        //fetch the information searched for in the persistence layer from the searchResults ListView
//        //(by choosing a Production-object from the ListView) and find and delete that information from
//        //the persistence layer
//        //Update the Listview to reflect the change
//        //Potentially: Limit to choosing just one element of the ListView.
//        //IF SUCCESS: update errorMsgProduction to text black/green and message:
//        //"Produktionen slettet"
//        //IF FAIL: update errorMsgProduction to text red and message:
//        //"Fejl opstået, produktionen blev ikke slettet"
//    }

    //FXML HANDLERS FOR "UDSENDELSE" TAB

    /**
     * Clears the ListView for items.
     * Fetches the information that is written to the searchField in the broadcast tab, and uses that String to search the persistence layer for information
     * that corresponds to the input. If it successfully fetched the information, the results are printed to an observable list, which is then printed to the
     * ListView. The searchfield is cleared.
     * @param event
     */
    @FXML
    public void handleSearchBroadcast(MouseEvent event) {
        String searchText = searchFieldBroadcast.getText();
        searchResult.getItems().clear();
        broadcastSearchResult = App.getSystemInstance().searchBroadcast(searchText);
        if (broadcastSearchResult != null && !searchFieldBroadcast.getText().isEmpty()) {
            broadcastObservableList = FXCollections.observableArrayList(broadcastSearchResult);
            searchResult.setItems(broadcastObservableList);
            clearBroadcastFields();
        } else {
            errorMsgBroadcastSearch.setVisible(true);
        }
    }

    /**
     * Upon pressing this button, a new Stage is opened, which contains the scene from the AddAssignCastGUI.fxml. Along with this stage,
     * a static object is created for the broadcast that is selected in the ListView, provided that the broadcastChosen attribute does not
     * reference null.
     * @param event
     */
    @FXML
    public void handleAssignCast(MouseEvent event) {
        Object object = searchResult.getSelectionModel().getSelectedItem();
        broadcastChosen = (IBroadcast) object;
        if(broadcastChosen != null) {
            try {
                Parent root;
                root = FXMLLoader.load(BaseController.class.getResource("AddAssignCastGUI.fxml"));
                assignStage.setScene(new Scene(root));
                assignStage.setResizable(false);
                assignStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else{
            errorMsgBroadcast.setText("Fejl, ingen udsendelse valgt");
        }
    }

    /**
     * Upon pressing this button, a new Stage is opened, which contains the scene from the RemoveChangeCastGUI.fxml. Along with this stage,
     * a static object is created for the broadcast that is selected in the ListView, provided that the broadcastChosen attribute does not
     * reference null.
     * @param event
     */
    @FXML
    public void handleUnassignCast(MouseEvent event) {
        Object object = searchResult.getSelectionModel().getSelectedItem();
        broadcastChosen = (IBroadcast) object;
        if(broadcastChosen != null) {
            try {
                Parent root;
                root = FXMLLoader.load(BaseController.class.getResource("RemoveChangeCastGUI.fxml"));
                assignStage.setScene(new Scene(root));
                assignStage.setResizable(false);
                assignStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else{
            errorMsgBroadcast.setText("Fejl, ingen udsendelse valgt");
        }
    }

    /**
     * A temporary variable containing a String with all the text that is entered to represent the date in the relevant textfields is created.
     * Along with this variable, the other textfields are also used to create a new broadcast object in the database, provided that the user
     * has written exactly 2 chars (atm it doesn't have to be numbers...) into the date and month fields, and exactly 4 chars into the year-
     * textfield.
     * If successful, the user is presented with a success message. If not, the user is given an error message.
     * @param event
     */
    @FXML
    public void handleCreateBroadcast(MouseEvent event) {

        String dateVariable = broadcastAirDateDay.getText() + "-" + broadcastAirDateMonth.getText() + "-" + broadcastAirDateYear.getText();
        if (broadcastAirDateDay.getText().length() != 2 && broadcastAirDateMonth.getText().length() != 2 && broadcastAirDateYear.getText().length() != 4) {
            errorMsgBroadcast.setText("Fejl opstået, ugyldig datoindtastning");
        } else {
            IBroadcast broadcast = LoginController.getAdminUser().addNewBroadcastToDatabase(broadcastName.getText(), Integer.parseInt(broadcastSeason.getText()),
                    Integer.parseInt(broadcastEpisodeNumber.getText()), dateVariable);
            clearBroadcastFields();
            if (broadcast != null) {
                errorMsgBroadcast.setText("Udsendelsen tilføjet");
                if(!broadcastSearchResult.isEmpty()) {
                    broadcastSearchResult.add(broadcast);
                    searchResult.setItems(FXCollections.observableArrayList(broadcastSearchResult));
                }
            } else {
                errorMsgBroadcast.setText("Fejl opstået, udsendelsen blev ikke tilføjet");
            }
        }
        searchResult.refresh();
        //TODO LOW PRIORITY Insert check if any of the textfields are empty. If so, print errormessage.
    }

    //Commented out since it is not part of the initial must-have requirements
//    @FXML
//    public void handleDeleteBroadcast(MouseEvent event){
//        //fetch the information searched for in the persistence layer from the searchResults ListView
//        //(by choosing a Broadcast-object from the ListView) and find and delete that information from
//        //the persistence layer
//        //Update the Listview to reflect the change
//        //Potentially: Limit to choosing just one element of the ListView.
//        //IF SUCCESS: update errorMsgBroadcast to text black/green and message:
//        //"Udsendelsen slettet"
//        //IF FAIL: update errorMsgBroadcast to text red and message:
//        //"Fejl opstået, udsendelsen blev ikke slettet"
//    }

    /**
     * A collection of methods which are just used to more easily clear the relevant fields for each tab.
     */
    public void clearCastFields(){
        castName.clear();
        regDKField.clear();
    }

    public void clearBroadcastFields(){
        broadcastName.clear();
        broadcastProduction.clear();
        broadcastSeason.clear();
        broadcastEpisodeNumber.clear();
        broadcastAirDateDay.clear();
        broadcastAirDateMonth.clear();
        broadcastAirDateYear.clear();
    }

    public void clearProductionFields(){
        productionName.clear();
        producerName.clear();
        productionReleaseYear.clear();
    }

    public static IBroadcast getChosenBroadcast(){
        return broadcastChosen;
    }

}
