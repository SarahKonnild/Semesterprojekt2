package org.openjfx.domain;

import org.openjfx.interfaces.IBroadcast;
import org.openjfx.interfaces.IProduction;

import java.util.ArrayList;

public class Production implements IProduction {

    private int id;
    private String name;
    private String year;
    private ArrayList<IBroadcast> broadcasts;
    private String productionCompany;
    private int numberOfSeasons;
    private int numberOfEpisodes;

    public Production(int id, String name, ArrayList<IBroadcast> broadcasts, String year, String productionCompany){
        this.id = id;
        this.name = name;
        this.broadcasts = broadcasts;
        this.year = year;
        this.productionCompany = productionCompany;
        this.numberOfEpisodes = 1;
        this.numberOfSeasons = 5;

    }

    @Override
    public boolean saveProduction(Production production) {
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
        return numberOfSeasons;
    }

    @Override
    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }
}
