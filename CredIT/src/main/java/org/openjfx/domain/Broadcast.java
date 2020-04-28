package org.openjfx.domain;

import org.openjfx.interfaces.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Broadcast implements IBroadcast {
    private int id;
    private String name;
    private HashMap<ICast, String> castRoleMap;
    private int seasonNumber;
    private int episodeNumber;
    private String[] airDate;
    private IProduction production;
    private IProductionCompany productionCompany;

    public Broadcast(int id, String name, int seasonNumber, int episodeNumber, String airDate,IProductionCompany productionCompany, int productionCompanyID) {
        this.id = id;
        this.name = name;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.airDate = airDate.split("-");
        this.castRoleMap = CredITSystem.instance.getCastRolesBroadcast(this.id);
        this.productionCompany = productionCompany;
        this.production = CredITSystem.instance.searchProduction(productionCompanyID);
    }

    public Broadcast(String name, int seasonNumber, int episodeNumber, String airDate, IProductionCompany productionCompany) {
        this.name = name;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.airDate = airDate.split("-");
        this.productionCompany = productionCompany;
    }

    @Override
    public boolean save() {
        int idNumber = -1;
        try {
            idNumber = CredITSystem.instance.getPersistenceLayer().createNewBroadcastInDatabase(this);
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
        //Todo call method in persistence to delete the broadcast in database
        return false;
    }

    @Override
    public boolean update(String name, int seasonNumber, int episodeNumber, String airDate) {
        //Todo call method to update in database
        //Todo figure out if we need to make a check for if the production company is in the database.
        return false;
    }

    @Override
    public boolean unassignCast(ICast cast, String role) {
        return true;
    }

    @Override
    public boolean assignCast(ICast cast, String role) {

        return true;
    }

    @Override
    public String toString() {
        return
                this.name + ": " + this.airDate[0] + "-" + this.airDate[1] + "-" + this.airDate[2] + " : " + this.production.getName();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HashMap<ICast, String> getCastMap() {
        return castRoleMap;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    @Override
    public IProduction getProduction() {
        return this.production;
    }

    public String[] getAirDate() {
        return airDate;
    }

    public void setAirDate(String[] airDate) {
        this.airDate = airDate;
    }
}
