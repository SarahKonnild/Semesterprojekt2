package org.openjfx.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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

        //Create an instance of System using ISystem (how, Idk)
    }

    public static Scene getScene(){
        return scene;
    }

    public static Stage getStage(){
        return stage;
    }

    public static ISystem getSystemInstance(){
        return sys;
    }

    public static void handleAdminPage(){
        try {
            Parent value = FXMLLoader.load(LoginSystemController.class.getResource("AdministratorPage.fxml"));
            App.getScene().setRoot(value);
            App.getStage().setHeight(271);
            App.getStage().setWidth(601);
            App.getStage().setResizable(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void handleHelpStage(){
        Parent root;
        try {
            root = FXMLLoader.load(LoginSystemController.class.getResource("Help.fxml"));
            helpStage = new Stage();
            helpStage.setScene(new Scene(root));
            helpStage.setHeight(400);
            helpStage.setWidth(600);
            helpStage.setResizable(false);
            helpStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
}