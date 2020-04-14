package org.openjfx.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

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
    private MenuButton searchTopic;
    @FXML
    private MenuItem castOption;
    @FXML
    private MenuItem productionOption;
    @FXML
    private MenuItem broadcastOption;
    @FXML
    private TextArea searchString;
    @FXML
    private TextField searchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setVisible(false);
        userInfo.setText("Gæstebruger");
    }

    @FXML
    public void handleLogInClicked(MouseEvent event){

    }

    @FXML
    public void handleHelpClicked(MouseEvent event){

    }

    @FXML
    public void handleSearchResultClicked(MouseEvent event){

    }

    @FXML
    public void handleSearchButtonClicked(ActionEvent event){

    }

    @FXML
    public void handleCastChosen(MouseEvent event){
        searchTopic.setText("Medvirkende");
    }

    @FXML
    public void handleProductionChosen(MouseEvent event){
        searchTopic.setText("Produktion");
    }

    @FXML
    public void handleBroadcastChosen(MouseEvent event){
        searchTopic.setText("Udsendelse");
    }

}
