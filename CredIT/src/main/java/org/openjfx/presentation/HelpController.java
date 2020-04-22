package org.openjfx.presentation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpController implements Initializable {
    @FXML
    private Label titleLabel;
    @FXML
    private Label textLabel;
    @FXML
    private TreeView helpOptionsList;

    /**
     * Creates a list of items in the TreeView that can be chosen. Upon choosing, they will
     * produce a fitting help-screen that can help the user use the system.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Initial Label text be fun
        textLabel.setText("En eller anden dag kommer der relevant tekst her.\n" + "\n" + "\n" +
                "Ingen ved hvornår... But someday.");

        //Initialising the treeview
        TreeItem<String> rootItem = new TreeItem<>("Velkommen til CredIT");
        rootItem.setExpanded(true);
        helpOptionsList.setRoot(rootItem);

        //Initialising the items of the treeview
        TreeItem<String> search = new TreeItem<>("Sådan søger du");

        //Setting the children of the different roots
        rootItem.getChildren().add(search);

    }

}
