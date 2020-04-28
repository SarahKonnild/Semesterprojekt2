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
    private static Stage helpStage;
    private ArrayList<IProduction> searchResult;
    private ObservableList<IProduction> observableList;

    private IProduction chosenProduction;
    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.handleMoveWindow(basePane);
    }

    //Everything to do with the ListView (search, choose)
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
        searchResult = App.getSystemInstance().searchProduction(searchText);
        if (searchResult != null && !searchField.getText().isEmpty()) {
            observableList = FXCollections.observableArrayList(searchResult);
            resultList.setItems(observableList);
            searchField.clear();
        } else {
            errorMessageSearch.setVisible(true);
        }
    }

    /**
     * When the user chooses an object from the search list, this method is run. It will always write the
     * data associated with the LAST object chosen to the fields.
     * @param event
     */
    @FXML
    public void handleSearchResultChosen(MouseEvent event){
        resultList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        observableList = resultList.getSelectionModel().getSelectedItems();
        chosenProduction = (IProduction) resultList.getSelectionModel().getSelectedItem();
        productionName.setText(chosenProduction.getName());
        productionCompany.setText(String.valueOf(chosenProduction.getProductionCompany()));
        amountOfSeasons.setText(String.valueOf(chosenProduction.getNumberOfSeasons()));
        amountOfEpisodes.setText(String.valueOf(chosenProduction.getNumberOfEpisodes()));
        releaseYear.setText(chosenProduction.getYear());
    }
    //endregion

    //Create Production
    //region
    /**
     * Takes the information written in the fields and uses those as the parameters for the createProduction method
     * in the domain layer. Thus creates a new entry into the database.
     * @param event
     */
    @FXML
    public void handleCreateNew(MouseEvent event){
        IProduction production = LoginController.getAdminUser().addNewProductionToDatabase(productionName.getText(), releaseYear.getText(), productionCompany.getText());
        if (production != null) {
            errorMessage.setText("Produktionen oprettet");
            searchResult = new ArrayList<>();
            searchResult.add(production);
            resultList.setItems(FXCollections.observableArrayList(searchResult));
            clearFields();
        } else {
            errorMessage.setText("Fejl opstået, produktionen blev ikke oprettet");
        }
        resultList.refresh();
    }
    //endregion

    //Delete Production
    //region
    /**
     *
     * @param event
     */
    @FXML
    public void handleDelete(MouseEvent event){

    }
    //endregion

    //Save Production
    //region
    /**
     *
     * @param event
     */
    @FXML
    public void handleSave(MouseEvent event){

    }
    //endregion

    //Clears the fields
    //region
    private void clearFields(){
        productionName.clear();
        productionCompany.clear();
    }
    //endregion

    //Changes the scene of the primary stage, opens the new Help-stage and closes the entire program.
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
    //endregion

}
