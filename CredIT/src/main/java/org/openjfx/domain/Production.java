package org.openjfx.domain;

import org.openjfx.interfaces.IProduction;

import java.util.ArrayList;

public class Production implements IProduction {

    private int id;
    private String name;
    private int year;
    private ArrayList<Broadcast> broadcasts;
    private String productionCompany;
    private int numberOfSeasons;
    private int numberOfEpisodes;

    @Override
    public boolean saveProduction(Production production) {
        return false;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getYear() {
        return 0;
    }

    @Override
    public ArrayList<Broadcast> getBroadcasts() {
        return null;
    }

    @Override
    public String getProductionCompany() {
        return null;
    }

    @Override
    public int getNumberOfSeasons() {
        return 0;
    }

    @Override
    public int getNumberOfEpisodes() {
        return 0;
    }
}
