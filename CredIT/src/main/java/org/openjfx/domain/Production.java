package org.openjfx.domain;

import org.openjfx.interfaces.IBroadcast;
import org.openjfx.interfaces.IPersistence;
import org.openjfx.interfaces.IProduction;
import org.openjfx.interfaces.IProductionCompany;

import java.io.IOException;
import java.util.ArrayList;

public class Production implements IProduction {
    private final IPersistence persistence = CredITSystem.getPersistence();
    private final CredITSystem system = CredITSystem.getInstance();
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

        //Todo should we pass a productionCompany to it?
    }

    public Production(int id, String name, int productionCompanyID, String year) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.productionCompany = system.searchProductionCompany(productionCompanyID);
        numberOfEpisodes = broadcasts.size();
        loadBroadcastArray();
    }

    /**
     * Calls the search method in System, to get an arraylist of the broadcasts that this production have associted with it.
     */

    private void loadBroadcastArray(){
        this.broadcasts = system.searchBroadcast(this.id);
        int tempSeasonNumber = broadcasts.get(0).getSeasonNumber();
        for(IBroadcast broadcast : this.broadcasts){
            if(tempSeasonNumber < broadcast.getSeasonNumber()){
                tempSeasonNumber = broadcast.getSeasonNumber();
            }
        }
        this.numberOfSeasons = tempSeasonNumber;
    }

    @Override
    public boolean save() {
        int idNumber = persistence.createNewProductionInDatabase(this);
        if(idNumber != -1) this.id = idNumber;
        return (idNumber == -1) ? false : true;
    }

    @Override
    public boolean delete() {
        return persistence.removeProductionFromDatabase(this.id);
        //Todo What to do about the connections it has?
    }

    @Override
    public boolean update(String name, String year) {
        String tempName = this.name;
        String tempYear = this.year;

        this.name = name;
        this.year = year;
        if(persistence.updateProduction(this))
            return true;
        else
        {
            this.name = tempName;
            this.year = tempYear;
            return false;
        }
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
