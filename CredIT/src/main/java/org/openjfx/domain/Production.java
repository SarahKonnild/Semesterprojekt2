package org.openjfx.domain;

import org.openjfx.interfaces.IProduction;

import java.util.ArrayList;

public class Production implements IProduction {

    private int id;
    private String name;
    private String year;
    private ArrayList<Broadcast> broadcasts;
    private String productionCompany;
    private int numberOfSeasons;
    private int numberOfEpisodes;

    public Production(String name, String year, String productionCompany){
        this.name = name;
        this.year = year;
        this.productionCompany = productionCompany;
    }

    public Production(int id, String name, String year, String productionCompany){
        this.id = id;
        this.name = name;
        this.year = year;
        this.productionCompany = productionCompany;
        loadBroadcastArray();
        numberOfEpisodes = broadcasts.size() + 1;
    }
    private void loadBroadcastArray(){
        this.broadcasts = System.instance.searchBroadcast(this.id);
    }

    @Override
    public boolean saveProduction() {
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
    public ArrayList<Broadcast> getBroadcasts() {
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
