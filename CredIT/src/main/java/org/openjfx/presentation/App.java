package org.openjfx.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openjfx.interfaces.*;
import org.openjfx.domain.System;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;


    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        scene = new Scene(loadFXML("Base GUI"));
        stage.setScene(scene);
        stage.show();
        
        //region This is just for show, delete at some time.
        ISystem sys = new System();
        ArrayList<IBroadcast> list;
        list = sys.searchBroadcast("Something");
        list.get(1).saveBroadcast();

        //endregion
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

        launch();

        //Create an instance of System using ISystem (how, Idk)
    }

    public static Scene getScene(){
        return scene;
    }

    public static Stage getStage(){
        return stage;
    }
}