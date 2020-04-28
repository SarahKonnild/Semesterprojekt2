package org.openjfx.presentation;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.openjfx.interfaces.*;
import org.openjfx.domain.CredITSystem;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;
    private static ISystem sys;
    private static Stage helpStage;
    private static String assignCastModifier;


    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        scene = new Scene(loadFXML("LoginSystem"));
        App.getStage().initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public void changeScene(String fxml){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(fxml));
            scene.setRoot(parent);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sys = new CredITSystem(); //creates a new object of system
        launch();
    }

    /**
     * These methods are used to be called in other controllers. They are used to change the scene of the primary stage
     * (or open the help-stage) in any given controller.
     */
    //region
    public static void handleAdminPage(){
        try {
            Parent value = FXMLLoader.load(LoginSystemController.class.getResource("AdministratorPage.fxml"));
            scene.setRoot(value);
            stage.sizeToScene();
            stage.setResizable(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void handleModifyCastPage(){
        try {
            Parent value = FXMLLoader.load(LoginSystemController.class.getResource("ModifyCast.fxml"));
            scene.setRoot(value);
            stage.sizeToScene();
            stage.setResizable(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void handleModifyBroadcastPage(){
        try {
            Parent value = FXMLLoader.load(LoginSystemController.class.getResource("ModifyBroadcast.fxml"));
            scene.setRoot(value);
            stage.sizeToScene();
            stage.setResizable(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void handleModifyProductionPage(){
        try {
            Parent value = FXMLLoader.load(LoginSystemController.class.getResource("ModifyProduction.fxml"));
            scene.setRoot(value);
            stage.sizeToScene();
            stage.setResizable(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void handleModifyMoviePage(){
        try {
            Parent value = FXMLLoader.load(LoginSystemController.class.getResource("ModifyMovie.fxml"));
            scene.setRoot(value);
            stage.sizeToScene();
            stage.setResizable(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void handleHelpStage(){
        Parent root;
        try {
            root = FXMLLoader.load(LoginSystemController.class.getResource("Help.fxml"));
            helpStage = new Stage();
            helpStage.initStyle(StageStyle.TRANSPARENT);
            helpStage.setScene(new Scene(root));
            App.getStage().sizeToScene();
            helpStage.setResizable(false);
            helpStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void handleUnassignAssignStage(){
        try{
            Parent value = FXMLLoader.load(LoginSystemController.class.getResource("AssignUnassignCast.fxml"));
            scene.setRoot(value);
            stage.sizeToScene();
            stage.setResizable(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void handleLoginSystemPage(){
        try{
            Parent value = FXMLLoader.load(App.class.getResource("LoginSystem.fxml"));
            scene.setRoot(value);
            stage.sizeToScene();
            stage.setResizable(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    //Set- and get-methods
    //region
    public static Scene getScene(){
        return scene;
    }

    public static Stage getStage(){
        return stage;
    }

    public static ISystem getSystemInstance(){
        return sys;
    }

    public static Stage getHelpStage(){
        return helpStage;
    }

    public static String getAssignCastModifier(){
        return assignCastModifier;
    }

    public static void setAssignCastModifier(String newAssignCastModifier){
        assignCastModifier = newAssignCastModifier;
    }
    //endregion

    /**
     * This method can be called upon in all the other controllers. It allows for moving the borderless application window.
     * @param basePane refers to the AnchorPane that is clickable and movable in the Stage (usually the darker blue panel at
     *                 the top of the stage.
     */
    public static void handleMoveWindow(AnchorPane basePane){
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
                    App.getScene().getWindow().setX(mouseEvent.getScreenX() - dragX[0]);
                    App.getScene().getWindow().setY(mouseEvent.getScreenY() - dragY[0]);
                }
            }
        });
    }


    public static void closeWindow(){
        App.getStage().close();
    }
}