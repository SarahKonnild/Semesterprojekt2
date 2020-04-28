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
    private static Stage helpStage;
    private ObservableList<IBroadcast> observableList;
    private ArrayList<IBroadcast> searchList;
    private Object obj;
    private static IBroadcast chosenBroadcast;
    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createNew.setVisible(true);
        delete.setVisible(true);
        save.setVisible(true);
        modifyCast.setVisible(true);

        App.handleMoveWindow(basePane);
    }

    //Everything do do with manipulating the ListView (search,choose)
    //region
    /**
     * Searches the database for entries that match the search field's information in the database.
     * Writes all results into the list, which can then be chosen by the user.
     * @param event
     */
    @FXML
    public void handleSearch(MouseEvent event){
        String searchText = searchField.getText();
        resultList.getItems().clear();
        searchList = App.getSystemInstance().searchBroadcast(searchText);
        if (searchList != null && !searchField.getText().isEmpty()) {
            observableList = FXCollections.observableArrayList(searchList);
            resultList.setItems(observableList);
            clearFields();
        } else {
            errorMessage.setVisible(true);
        }
    }

    /**
     * When the user chooses an object from the search list, this method is run. It will always write the
     * data associated with the LAST object chosen to the fields.
     * @param event
     */
    @FXML
    public void handleSearchResultChosen(MouseEvent event){
        chosenBroadcast = (IBroadcast) resultList.getSelectionModel().getSelectedItem();
        broadcastName.setText(chosenBroadcast.getName());
        productionCompany.setText(chosenBroadcast.getProduction().getProductionCompany().getName());
        String[] airDate = chosenBroadcast.getAirDate();
        day.setText(airDate[0]);
        month.setText(airDate[1]);
        year.setText(airDate[2]);
        season.setText(String.valueOf(chosenBroadcast.getSeasonNumber()));
        episode.setText(String.valueOf(chosenBroadcast.getEpisodeNumber()));
    }
    //endregion

    //Create Broadcast
    //region
    /**
     *
     * @param event
     */
    @FXML
    public void handleCreateBroadcast(MouseEvent event){
        boolean status = false;
        String productionSearch = production.getText();
        ArrayList<IProduction> results = App.getSystemInstance().searchProduction(productionSearch);
        if(results.get(0).equals(productionSearch)){
            String dateVariable = day.getText() + "-" + month.getText() + "-" + year.getText();
            if (day.getText().length() != 2 && month.getText().length() != 2 && year.getText().length() != 4) {
                errorMessage.setText("Fejl opstået, ugyldig datoindtastning");
            } else {
                IBroadcast broadcast = LoginController.getAdminUser().addNewBroadcastToDatabase(broadcastName.getText(), Integer.parseInt(season.getText()),
                        Integer.parseInt(episode.getText()), dateVariable, results.get(0));
                clearFields();
                if (broadcast != null) {
                    errorMessage.setText("Udsendelsen tilføjet");
                    if(!searchList.isEmpty()) {
                        searchList = new ArrayList<>();
                        searchList.add(broadcast);
                        resultList.setItems(FXCollections.observableArrayList(searchList));
                    }
                } else {
                    errorMessage.setText("Fejl opstået, udsendelsen blev ikke tilføjet");
                }
            }
            resultList.refresh();
        }
    }
    //endregion

    //Delete Broadcast
    //region
    /**
     * When the user chooses an object from the search list, and they press the "delete"-button, this method
     * is run. It will delete the object that is chosen from the database.
     * @param event
     */
    @FXML
    public void handleDeleteBroadcast(MouseEvent event){

    }
    //endregion

    //Save Broadcast
    //region
    @FXML
    public void handleSaveBroadcast(MouseEvent event){

    }
    //endregion

    //Changes the scene of the primary stage, opens the new Help-stage and closes the entire program.
    //region
    @FXML
    public void handleBack(MouseEvent event){
        App.handleAdminPage();
    }

    @FXML
    public void handleHelp(MouseEvent event){
        App.handleHelpStage();
    }

    @FXML
    public void handleClose(MouseEvent event){App.closeWindow();}

    @FXML
    public void handleChangeCast(MouseEvent event){
        if(chosenBroadcast != null) {
            App.setAssignCastModifier("broadcast");
            App.handleUnassignAssignStage();
        } else{
            errorMessage.setText("Ingen udsendelse valgt");
        }

    }
    //endregion

    //Clears the fields
    //region
    private void clearFields(){
        broadcastName.clear();
        productionCompany.clear();
        year.clear();
        day.clear();
        month.clear();
        season.clear();
        episode.clear();
    }
    //endregion

    //Retrieve the chosen broadcast for scene changes
    //region
    public static IBroadcast getChosenBroadcast(){
        return chosenBroadcast;
    }
    //endregion
}
