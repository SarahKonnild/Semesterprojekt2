package org.openjfx.persistence;

import org.openjfx.interfaces.*;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Persistence implements IPersistence {


    private File castFile;
    private File productionFile;
    private File broadcastFile;
    private File userFile;
    private PrintWriter writer;
    //private BufferedWriter bw;
    private FileWriter fw = null;
    private Scanner reader;

    //ID variables. needed because java cant make a serial like a database.
    private static int userId;
    private static int broadcastId;
    private static int productionId;
    private static int castId;

    static Persistence instance = null;


    private Persistence(){
        this.castFile = new File("PersistenceFiles\\castFile.csv");
        this.broadcastFile = new File("PersistenceFiles\\broadcastFile.csv");
        this.productionFile = new File("PersistenceFiles\\productionFile.csv");
        this.userFile = new File("PersistenceFiles\\userFile.csv");
        this.writer = null;
        this.reader = null;

        initializeUserId();
    }


    @Override
    public boolean createNewUserInDatabase(IUser user) throws IOException {
        boolean returnBool = false;
        try {
            fw = new FileWriter(userFile, true);
            writer = new PrintWriter(fw);

            writer.println(userId + "," + user.getName() + "," + user.getPassword() + "," +
                    user.getUsername() + "," + user.getRole());

            userId++;

            returnBool = true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            writer.close();
            fw.close();
        }

        return returnBool;
    }

    /**
     * Deletes a user from the persistence/layer(Database).
     * It reads every line in the file, if the current line not equals the the parsed id, we add it to temperary String, else we just skip that line.
     * Finally we write the new information to the file.
     * @param id
     * The ID on the user you want to delete in the persistence layer.
     * @return
     * returns the boolean value of the delete run.
     *
     */
    @Override
    public boolean removeUserFromDatabase(int id) {
        boolean returnBool = false;
        String newTxt = "";
        try {
            reader = new Scanner(userFile);
            writer = new PrintWriter(userFile);
            while(reader.hasNextLine()){
                String currentLine = reader.nextLine();
                String[] user = currentLine.split(",");
                if(Integer.parseInt(user[0]) != id){
                    newTxt += currentLine + "\n";

                } else{
                    returnBool = true;
                }
            }
            writer.write(newTxt);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
            writer.close();
        }
        return returnBool;
    }

    @Override
    public boolean createNewBroadcastInDatabase(IBroadcast broadcast) {
        return false;
    }

    @Override
    public boolean removeBroadcastFromDatabase(int id) {
        return false;
    }

    @Override
    public boolean createNewProductionInDatabase(IProduction production) {
        return false;
    }

    @Override
    public boolean removeProductionFromDatabase(int id) {
        return false;
    }

    @Override
    public boolean createNewCastInDatabase(ICast cast) {
        return false;
    }

    @Override
    public boolean removeCastFromDatabase(int id) {
        return false;
    }

    @Override
    public List<String> getBroadcastFromDatabase(String keyword) {
        try {
            reader = new Scanner(broadcastFile);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }


        return null;
    }

    @Override
    public List<String> getCastFromDatabase(String keyword) {
        return null;
    }

    @Override
    public List<String> getProductionFromDatabase(String keyword) {
        return null;
    }

    @Override
    public boolean mergeCastInDatabase(ICast cast1, ICast cast2) {
        return false;
    }

    @Override
    public boolean updateCastInDatabase(int id, String name, int regDKID) {
        return false;
    }


    public static Persistence getInstance() {
        if(instance == null){
            instance = new Persistence();
        }

        return instance;
    }

    /**
     * Loops through the userFile and finds the largest userId. The userId on the class is instantiated to one higher than this.
     */
    private void initializeUserId(){
        int id = 0;
        try {
            reader = new Scanner(userFile);
            while(reader.hasNextLine()){
                String currentLine = reader.nextLine();
                String[] user = currentLine.split(",");
                int currentId = Integer.parseInt(user[0]);
                if(currentId > id) {
                    id = currentId;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }

        userId = id + 1;
    }

    private void initializeCastId(){

    }

    private void initializeBroadcastId(){

    }

    private void initializeProductionId(){
        
    }

    public static void main(String[] args){
        Persistence persistence = Persistence.getInstance();
        persistence.removeProductionFromDatabase(19);
    }

}



