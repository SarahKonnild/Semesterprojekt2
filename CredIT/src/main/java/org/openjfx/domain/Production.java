package org.openjfx.domain;

import org.openjfx.interfaces.*;

import java.io.IOException;
import java.util.ArrayList;

public class Production implements IProduction {

    private int id;
    private String name;
    private String year;
    private ArrayList<IBroadcast> broadcasts;
    private String productionCompany;
    private int numberOfSeasons;
    private int numberOfEpisodes;

    public Production(String name, String year, String productionCompany){
        this.name = name;
        this.year = year;
        this.productionCompany = productionCompany;
    }

    public Production(int id, String name, ArrayList<IBroadcast> broadcasts, String productionCompany, String year){
        this.id = id;
        this.name = name;
        this.year = year;
        this.productionCompany = productionCompany;
        this.broadcasts = broadcasts;
        numberOfEpisodes = broadcasts.size();
    }

    /**
     * Calls the search method in System, to get an arraylist of the broadcasts that this production have associted with it.
     *
     */
//
//    private void loadBroadcastArray(){
//        this.broadcasts = System.instance.searchBroadcast(this.id);
//    }
//

    @Override
    public boolean saveProduction() {
        boolean saveStatus;
        //Method call to the persistenceLayer to send this object and save the data in it
        try{
        saveStatus = CredITSystem.getPersistence().createNewProductionInDatabase(this);
        if(!saveStatus){
            //Do some stuff here to deal with the fail maybe?
            return false;
        } else {
            return true;
        }
        } catch(IOException ex){
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getYear() {
        return this.year;
    }

    @Override
    public ArrayList<IBroadcast> getBroadcasts() {
        return this.broadcasts;
    }

    @Override
    public String getProductionCompany() {
        return this.productionCompany;
    }

    @Override
    public int getNumberOfSeasons() {
        return this.numberOfSeasons;
    }

    @Override
    public int getNumberOfEpisodes() {
        return this.numberOfEpisodes;
    }
}
