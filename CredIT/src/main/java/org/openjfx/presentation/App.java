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
}