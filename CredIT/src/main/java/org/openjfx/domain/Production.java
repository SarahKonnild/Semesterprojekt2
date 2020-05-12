package org.openjfx.domain;

import org.openjfx.interfaces.IBroadcast;
import org.openjfx.interfaces.IPersistence;
import org.openjfx.interfaces.IProduction;
import org.openjfx.interfaces.IProductionCompany;

import java.util.ArrayList;

public class Production implements IProduction {
    private final IPersistence persistence = CredITSystem.getPersistence();
    private final CredITSystem system = CredITSystem.getInstance();
    private final IProductionCompany productionCompany;
    private int id;
    private String name;
    private String year;
    private ArrayList<IBroadcast> broadcasts;
    private int numberOfSeasons;
    private int numberOfEpisodes;

    public Production(String name, String year, IProductionCompany productionCompany) {
        this.name = name;
        this.year = year;
        this.productionCompany = productionCompany;
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

    private void loadBroadcastArray() {
        this.broadcasts = system.searchBroadcast(this.id);
        int tempSeasonNumber = broadcasts.get(0).getSeasonNumber();
        for (IBroadcast broadcast : this.broadcasts) {
            if (tempSeasonNumber < broadcast.getSeasonNumber()) {
                tempSeasonNumber = broadcast.getSeasonNumber();
            }
        }
        this.numberOfSeasons = tempSeasonNumber;
        //Todo I believe that number of seasons can be found in the database
    }

    @Override
    public boolean save() {
        int idNumber = persistence.createNewProductionInDatabase(this);
        if (idNumber != -1) this.id = idNumber;
        return idNumber != -1;
    }

    @Override
    public boolean delete() {
        return persistence.removeProductionFromDatabase(this.id);
    }

    @Override
    public boolean update(String name, String year) {
        String tempName = this.name;
        String tempYear = this.year;

        this.name = name;
        this.year = year;
        if (persistence.updateProduction(this))
            return true;
        else {
            this.name = tempName;
            this.year = tempYear;
            return false;
        }
    }

    @Override
    public boolean assignBroadcast(IBroadcast broadcast) {
        ArrayList<IBroadcast> tempList = this.broadcasts;
        if (this.broadcasts.contains(broadcast)) {
            return true;

        } else {
            this.broadcasts.add(broadcast);
            if (persistence.updateProduction(this))
                return true;
            else {
                this.broadcasts = tempList;
                return false;
            }
        }
    }

    @Override
    public boolean unassignBroadcast(IBroadcast broadcast) {
        if (this.broadcasts.contains(broadcast)) {
            //Saves the list so it can revert back if anything goes wrong.
            ArrayList<IBroadcast> tempList = this.broadcasts;
            this.broadcasts.remove(broadcast);
            if (persistence.updateProduction(this))
                return true;
            else {
                //Reverts the changes because something went wrong
                this.broadcasts = tempList;
                return false;
            }

        } else
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
