package org.openjfx.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    private TextArea searchString;
    @FXML
    private TextField searchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setVisible(false);
        userInfo.setText("Gæstebruger");
    }

    @FXML
    public void handleMenuBarClicked(MouseEvent event){

    }

    @FXML
    public void handleSearchResultClicked(MouseEvent event){

    }

    @FXML
    public void handleSearchButtonClicked(MouseEvent event){

    }


    @FXML
    public void handleSearchTopicClicked(MouseEvent event){
        castOption.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Medvirkende");
            }
        });
        productionOption.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Produktion");
            }
        });
        broadcastOption.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTopic.setText("Udsendelse");
            }
        });
    }

}
