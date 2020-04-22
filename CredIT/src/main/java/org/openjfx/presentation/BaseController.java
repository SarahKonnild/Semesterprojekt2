package org.openjfx.presentation;

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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BaseController implements Initializable {
    private static Stage loginStage = new Stage();
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
    private ArrayList<IProduction> productionSearchResult;
    private ArrayList<ICast> castSearchResult;
    private ArrayList<IBroadcast> broadcastSearchResult;

    private ObservableList<ICast> castObservableList;
    private ObservableList<IProduction> productionObservableList;
    private ObservableList<IBroadcast> broadcastObservableList;

    public static Stage getLoginStage() {
        return loginStage;
    }

    //THIS MOTHERFUCKER TOOK 2 HOURS TO MAKE WORK BECAUSE OF THE root = FXMLLoader.... LINE NOT WORKING. IDK HOW IT WORKS
    //BUT IT DOES, SO LEAVE HER ALONE :'(

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setVisible(false);
        userInfo.setText("Gæstebruger");
    }

    /**
     * Opens a new stage, which contains a scene that uses a username and a password to log the user into the actual program. It is however not a high priority
     * assignment to implement a proper login system, why it is a pseudo-login system that just takes a specific string username and password.
     *
     * @param event
     */
    @FXML
    public void handleLoginClicked(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(BaseController.class.getResource("LoginGUI.fxml"));
            loginStage.setScene(new Scene(root));
            loginStage.setResizable(false);
            loginStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Opens a new Stage, which contains a scene that could provide the user information on how to use the program. It is very low priority to properly implement
     * that controller.
     *
     * @param event
     */
    @FXML
    public void handleHelpClicked(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(BaseController.class.getResource("HelpGUI.fxml"));
            loginStage.setScene(new Scene(root));
            loginStage.setResizable(false);
            loginStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Registers the object that has been selected in the ListView and prints its information/toString to the textarea.
     *
     * @param event
     */
    @FXML
    public void handleSearchResultClicked(MouseEvent event) {
        Object obj;
        if (searchTopicChosen == "cast") {
            obj = searchList.getSelectionModel().getSelectedItem();
            ICast chosenCast = (ICast) obj;
            searchResultArea.setText(chosenCast.toString());
        } else if (searchTopicChosen == "production") {
            obj = searchList.getSelectionModel().getSelectedItem();
            IProduction chosenProduction = (IProduction) obj;
            searchResultArea.setText(chosenProduction.toString());
        } else if (searchTopicChosen == "broadcast") {
            obj = searchList.getSelectionModel().getSelectedItem();
            IBroadcast chosenBroadcast = (IBroadcast) obj;
            searchResultArea.setText(chosenBroadcast.toString());
        } else {
            errorMessage.setVisible(true);
            errorMessage.setText("Intet søgeresultat");
        }
    }

    /**
     * Uses the information about the chosen topic from the MenuButton to specify which method in the domain that should be used
     * to search the persistence layer. It uses the text that has been entered into the searchfield to specify the keyword that is passed
     * into the method.
     * It will then create an observablelist with the results, which is then printed to the ListView.
     *
     * @param event
     */
    @FXML
    public void handleSearchButtonClicked(MouseEvent event) {
        String searchString = searchField.getText();
        if (searchTopicChosen == "cast") {
            castSearchResult = App.getSystemInstance().searchCast(searchString);
            castObservableList = FXCollections.observableArrayList(castSearchResult);
            searchList.setItems(castObservableList);
        } else if (searchTopicChosen == "production") {
            productionSearchResult = App.getSystemInstance().searchProduction(searchString);
            productionObservableList = FXCollections.observableArrayList(productionSearchResult);
            searchList.setItems(productionObservableList);
        } else if (searchTopicChosen == "broadcast") {
            broadcastSearchResult = App.getSystemInstance().searchBroadcast(searchString);
            broadcastObservableList = FXCollections.observableArrayList(broadcastSearchResult);
            searchList.setItems(broadcastObservableList);
        } else {
            errorMessage.setVisible(true);
            errorMessage.setText("Intet søgeemne valgt");
        }
    }

    /**
     * Upon having clicked the MenuButton on the screen, the user will be presented with 3 options which can be chosen (not encoded here but in the FXML).
     * For each of the options, a new handler is created in this method, where the text in the MenuButton is changed to the search topic that has been chosen,
     * and changes the value of a String object that can be used in the searchHandler to specify the topic that is chosen, and thus which search-method in the domain,
     * that must be used to fetch the information.
     *
     * @param event
     */
    @FXML
    public void handleSearchTopicClicked(MouseEvent event) {
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
