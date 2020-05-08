package org.openjfx.presentation;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpController implements Initializable {

    @FXML
    private AnchorPane basePane;
    @FXML
    private Label titleLabel;
    @FXML
    private Label textLabel;
    @FXML
    private Label close;
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
            final double[] dragX = new double[1];
            final double[] dragY = new double[1];
            basePane.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton() == MouseButton.PRIMARY){
                        dragX[0] = mouseEvent.getSceneX();
                        dragY[0] = mouseEvent.getSceneY();
                    }
                }
            });
            basePane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton() == MouseButton.PRIMARY){
                        App.getHelpStage().getScene().getWindow().setX(mouseEvent.getScreenX() - dragX[0]);
                        App.getHelpStage().getScene().getWindow().setY(mouseEvent.getScreenY() - dragY[0]);
                    }
                }
            });
    }

    @FXML
    public void handleClose(MouseEvent event){
        App.getHelpStage().close();
    }

}
