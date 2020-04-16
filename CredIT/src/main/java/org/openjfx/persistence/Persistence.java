package org.openjfx.persistence;

import org.openjfx.interfaces.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class Persistence implements IPersistence {


    private File castFile;
    private File productionFile;
    private File broadcastFile;
    private File userFile;
    private PrintWriter writer;
    private Scanner reader;

    static Persistence instance = null;



    private Persistence(){
        this.castFile = new File("PersistenceFiles\\castFile.csv");
        this.broadcastFile = new File("PersistenceFiles\\broadcastFile.csv");
        this.productionFile = new File("PersistenceFiles\\productionFile.csv");
        this.userFile = new File("PersistenceFiles\\userFile.csv");
        this.writer = null;
        this.reader = null;
    }


    @Override
    public boolean createNewUserInDatabase(IUser user) {
        try {
            writer = new PrintWriter(userFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }


        return false;
    }

    @Override
    public boolean removeUserFromDatabase(int id) {
        return false;
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

//    @Override
//    public List<String> getBroadcastFromDatabase(int keyword){return null;}

    @Override
    public List<String> getCastFromDatabase(String keyword) {
        return null;
    }

//    @Override
//    public List<String> getCastFromDatabase(int keyword) {
//        return null;
//    }

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

    public static void main(String[] args){
        Persistence persistence = Persistence.getInstance();
        //persistence.deleteBroadcast(19);
    }

}



