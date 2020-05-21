package org.openjfx.domain;

import org.openjfx.interfaces.IPersistence;
import org.openjfx.interfaces.IProduction;

import java.util.ArrayList;

public class Production implements IProduction {
    private final IPersistence persistence = CredITSystem.getPersistence();
    private int id;
    private String name;
    private String year;
    private ArrayList<Broadcast> broadcasts;
    private int numberOfSeasons;
    private int numberOfEpisodes;

    public Production(String name, String year) {
        this.name = name;
        this.year = year;
        this.broadcasts = new ArrayList<>();
    }

    public Production(int id, String name, String year, int season, int episode, ArrayList<Broadcast> broadcasts) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.numberOfSeasons = season;
        this.numberOfEpisodes = episode;
        this.broadcasts = broadcasts;
    }

    /**
     * Calls the search method in System, to get an arraylist of the broadcasts that this production have associted with it.
     */

    @Override
    public boolean save(int productionCompanyID) {
        int idNumber = persistence.createNewProductionInDatabase(this, productionCompanyID);
        if (idNumber != -1) {
            this.id = idNumber;
        }
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
    public ArrayList<Broadcast> getBroadcasts() {
        return this.broadcasts;
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
