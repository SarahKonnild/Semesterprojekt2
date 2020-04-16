package org.openjfx.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import org.openjfx.persistence.Persistence;

public class BaseController implements Initializable {
    @FXML
    private Button searchButton;
    @FXML
    private Label errorMessage;
    @FXML
    private Label userInfo;
    @FXML
    private ListView searchList;
    @FXML
    private Menu login;
    @FXML
    private Menu help;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuButton searchTopic;
    @FXML
    private MenuItem castOption;
    @FXML
    private MenuItem productionOption;
    @FXML
    private MenuItem broadcastOption;
    @FXML
    private MenuItem aboutMenu;
    @FXML
    private MenuItem loginMenu;
    @FXML
    private TextArea searchResultArea;
    @FXML
    private TextField searchField;

    private String searchTopicChosen;
    private Stage loginStage = new Stage();

    private ArrayList<IProduction> productionSearchResult;
    private ArrayList<ICast> castSearchResult;
    private ArrayList<IBroadcast> broadcastSearchResult;

    private ObservableList<ICast> castObservableList;
    private ObservableList<IProduction> productionObservableList;
    private ObservableList<IBroadcast> broadcastObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setVisible(false);
        userInfo.setText("Gæstebruger");
    }

    //THIS MOTHERFUCKER TOOK 2 HOURS TO MAKE WORK BECAUSE OF THE root = FXMLLoader.... LINE NOT WORKING. IDK HOW IT WORKS
    //BUT IT DOES, SO LEAVE HER ALONE :'(
    /**
     * This method creates a pop-up window that refers the user into the login scene, which allows them to log into the system
     * if they are some kind of administrating unit.
     * @param event
     */
    @FXML
    public void handleLoginClicked(ActionEvent event){
        try {
            Parent root;
            root = FXMLLoader.load(BaseController.class.getResource("LoginGUI.fxml"));
            loginStage.setScene(new Scene(root));
            loginStage.setResizable(false);
            loginStage.show();
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleHelpClicked(ActionEvent event){
        try {
            Parent root;
            root = FXMLLoader.load(BaseController.class.getResource("HelpGUI.fxml"));
            loginStage.setScene(new Scene(root));
            loginStage.setResizable(false);
            loginStage.show();
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * En eller anden dag bliver der en tekst her
     * @param event
     */
    @FXML
    public void handleSearchResultClicked(MouseEvent event){
        searchResultArea.setText(searchList.getSelectionModel().getSelectedItems().toString());
    }

    /**
     * Handles the search methods, ensuring that the correct method for searching
     * the persistence is triggered by the searchTopicChosen-attribute.
     * Creates a temporary String to store the searchfield's text into.
     * @param event
     */
    @FXML
    public void handleSearchButtonClicked(MouseEvent event){
        String searchString = searchField.getText();
        if(searchTopicChosen == "cast"){
            castSearchResult = App.getSystemInstance().searchCast(searchString);
            castObservableList = FXCollections.observableArrayList(castSearchResult);
            searchList.setItems(castObservableList);
        } else if(searchTopicChosen == "production"){
            productionSearchResult = App.getSystemInstance().searchProduction(searchString);
            productionObservableList = FXCollections.observableArrayList(productionSearchResult);
            searchList.setItems(productionObservableList);
        } else if(searchTopicChosen == "broadcast"){
            broadcastSearchResult = App.getSystemInstance().searchBroadcast(searchString);
            broadcastObservableList = FXCollections.observableArrayList(broadcastSearchResult);
            searchList.setItems(broadcastObservableList);
        }
    }

    /**
     * Handles the triggering of different search-topics, which enables the searchbutton
     * to perform different search-methods for the given topic.
     * Changes the value of attribute searchTopicChosen to a String value, which is checked
     * in the searchButton-handler, to ensure the correct method is chosen.
     * @param event
     */
    @FXML
    public void handleSearchTopicClicked(MouseEvent event){
        castOption.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Medvirkende");
                searchTopicChosen = "cast";
            }
        });
        productionOption.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Produktion");
                searchTopicChosen = "production";
            }
        });
        broadcastOption.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Udsendelse");
                searchTopicChosen = "broadcast";
            }
        });
    }

}
