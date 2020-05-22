package org.openjfx.presentation;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.openjfx.interfaces.*;
import org.openjfx.domain.CredITSystem;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * JavaFX App
 */
public class App extends Application {

    //Local variables
    //region
    private static Scene scene;
    private static Stage stage;
    private static ISystem sys;
    private static Stage helpStage;
    private static String assignCastModifier;

    private static IProductionCompany retrievedProductionCompany;

    private static ArrayList<PCast> roleArray = new ArrayList<>();
    private static ArrayList<String> castRoles = new ArrayList<>();
    //endregion

    //The standard App-methods for starting the Application Window
    //region
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
        sys = CredITSystem.getInstance(); //creates a new object of system
        launch();
    }
    //endregion

    /**
     * These methods are used to be called in other controllers. They are used to change the scene of the primary stage
     * (or open the help-stage) in any given controller.
     *
     * @author Sarah
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

    public static void handleModifyProductionCompanyStage(){
        try{
            Parent value = FXMLLoader.load(LoginSystemController.class.getResource("ModifyProductionCompany.fxml"));
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

    //Movement of the borderless window
    //region
    /**
     * This method can be called upon in all the other controllers. It allows for moving the borderless application window.
     *
     * @author Sarah
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
    //endregion

    //Standardised retrieval of production/production company methods
    //region
    /**
     * Retrieves the IProduction for the IBroadcast so its information can be retrieved
     *
     * @author Sarah
     * @param broadcast which is given by the controller, to choose which broadcast to retrieve the production for
     * @return returns the IProduction that the broadcast is found to belong to
     */
    public static IProduction retrieveProduction(IBroadcast broadcast){
        IProduction retrievedProduction = getSystemInstance().searchProductionOnBroadcast(broadcast.getId());
        return retrievedProduction;
    }

    /**
     * Retrieves the IProductionCompany for the IMovie so its information can be retrieved
     *
     * @param movie which is given by the controller, to choose which ProductionCompany to retrieve the movie from
     * @return returns the IProductionCompany that the movie is found to belong to
     */
    public static IProductionCompany retrieveProductionCompanyForMovie(IMovie movie){
        retrievedProductionCompany = getSystemInstance().searchProductionCompanyOnMovie(movie.getId());
        return retrievedProductionCompany;
    }

    /**
     * Retrieves the IProductionCompany for the IProduction so its informaion can be retrieved
     *
     * @param production which is given by the controller, to choose which ProductionCompany to retrieve the Production from
     * @return returns the IProductionCompany that the production is found to belong to
     */
    public static IProductionCompany retrieveProductionCompanyForProduction(IProduction production){
        retrievedProductionCompany = getSystemInstance().searchProductionCompanyOnProduction(production.getId());
        return retrievedProductionCompany;
    }
    //endregion

    //Methods for fetching the arraylist with PCasts for the relevant controllers
    //region
    /**
     * Iterates through the HashMap that is received of Cast-members and their roles on the movie that has been
     * chosen, adding them to the static roleArray-ArrayList. This can be fetched by the different controllers.
     *
     * @author Sarah
     * @return returns the roleArray to be used for setting items in the ListView displaying all movie roles
     */
    public static ArrayList<PCast> getMovieRoleArray(IMovie chosenMovie){
        if(!roleArray.isEmpty()) {
            roleArray.clear();
        }else{
            HashMap<ICast, String> movieRoles = new HashMap<>(chosenMovie.getCastMap());

            for (ICast cast : movieRoles.keySet()) {
                PCast newCast = new PCast(cast, movieRoles.get(cast));
                roleArray.add(newCast);
            }
        }
            return roleArray;
    }

    /**
     * Iterates through the HashMap that is received of Cast-members and their roles on the broadcast that has been
     * chosen, adding them to the static roleArray-ArrayList. This can be fetched by the different controllers.
     *
     * @author Sarah
     * @return returns the roleArray to be used for setting items in the ListView displaying all broadcast roles
     */
    public static ArrayList<PCast> getBroadcastRoleArray(IBroadcast chosenBroadcast){
        if(!roleArray.isEmpty()) {
            roleArray.clear();
        }else {
            HashMap<ICast, String> broadcastRoles = new HashMap<>(chosenBroadcast.getCastMap());

            for (ICast cast : broadcastRoles.keySet()) {
                PCast newCast = new PCast(cast, broadcastRoles.get(cast));
                roleArray.add(newCast);
            }
        }
        return roleArray;
    }

    /**
     * Method to iterate through both the movie and broadcast hashmaps, adding the items to the castRoles arrayList.
     *
     * @author Sarah
     * @param chosenCast takes an ICast-variable from the controller calling this method
     * @return returns the static castRoles-array, so this method can be used to be set the result into a list.
     */
    public static ArrayList<String> getAllCastRoles(ICast chosenCast){
        if(!castRoles.isEmpty()){
            castRoles.clear();
        }
        //Creates local hashmap that is iterated through and then added broadcast-roles to the castRoles-ArrayList
        HashMap<IBroadcast, String> broadcastRoles = new HashMap<>(App.getSystemInstance().getCastRolesBroadcast(chosenCast));
        if(!broadcastRoles.isEmpty()) {
            for (IBroadcast broadcast : broadcastRoles.keySet()) {
                String temp = broadcast.getName() + " : " + broadcastRoles.get(broadcast);
                castRoles.add(temp);
            }
        }
        //Creates local HashMap that is iterated through and then added movie-roles to the castRoles-ArrayList
        HashMap<IMovie, String> movieRoles = new HashMap<>(App.getSystemInstance().getCastRolesMovies(chosenCast));
        if(!movieRoles.isEmpty()){
            for(IMovie movie : movieRoles.keySet()){
                String temp = movie.getTitle() + " : " + movieRoles.get(movie);
                castRoles.add(temp);
            }
        }
        return castRoles;
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

    public static void closeWindow(){
        App.getStage().close();
    }
}