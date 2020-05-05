package org.openjfx.domain;

import org.openjfx.interfaces.IBroadcast;
import org.openjfx.interfaces.IProduction;
import org.openjfx.interfaces.IProductionCompany;

import java.io.IOException;
import java.util.ArrayList;

public class Production implements IProduction {

    private int id;
    private String name;
    private String year;
    private ArrayList<IBroadcast> broadcasts;
    private IProductionCompany productionCompany;
    private int numberOfSeasons;
    private int numberOfEpisodes;

    public Production(String name, String year, IProductionCompany productionCompany) {
        this.name = name;
        this.year = year;
        this.productionCompany = productionCompany;
    }

    public Production(int id, String name, IProductionCompany productionCompany, String year) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.productionCompany = productionCompany;
        numberOfEpisodes = broadcasts.size();
        loadBroadcastArray();
    }

    /**
     * Calls the search method in System, to get an arraylist of the broadcasts that this production have associted with it.
     */

    private void loadBroadcastArray(){
        this.broadcasts = CredITSystem.instance.searchBroadcast(this.id);
    }

    @Override
    public boolean save() {
        int idNumber = -1;
        try {
            idNumber = CredITSystem.getPersistence().createNewProductionInDatabase(this);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (idNumber != -1) {
                this.id = idNumber;
                return true;
            } else
                return false;
        }
    }

    @Override
    public boolean delete() {
        //Todo call a method in persistence to delete the production in database
        return false;
    }

    @Override
    public boolean update(String name, String year, IProductionCompany productionCompany) {
        this.name = name;
        this.year = year;
        this.productionCompany = productionCompany;
        //Todo call a method in persistence to save the changes.
        return false;
    }

    @Override
    public String toString() {

        return this.name + ": " + this.year;
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
    public IProductionCompany getProductionCompany() {
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
