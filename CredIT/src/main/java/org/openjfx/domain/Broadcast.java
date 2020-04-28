package org.openjfx.domain;

import org.openjfx.interfaces.IBroadcast;
import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IPersistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Broadcast implements IBroadcast {
    private final IPersistence persistence = CredITSystem.getPersistence();
    private int id;
    private String name;
    private HashMap<ICast, String> castRoleMap;
    private String produtionName;
    private int seasonNumber;
    private int episodeNumber;
    private String[] airDate;

    public Broadcast(int id, String name, int seasonNumber, int episodeNumber, String airDate, String productionName) {
        this.id = id;
        this.name = name;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.airDate = airDate.split("-");
        this.produtionName = productionName;
        this.castRoleMap = CredITSystem.instance.getCastRoles(this.id);
    }

    public Broadcast(String name, int seasonNumber, int episodeNumber, String airDate) {
        this.name = name;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.airDate = airDate.split("-");
    }

    @Override
    public boolean save() {
        int idNumber = -1;
        try {
            idNumber = persistence.createNewBroadcastInDatabase(this);
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
                this.name + ": " + this.airDate[0] + "-" + this.airDate[1] + "-" + this.airDate[2] + " : " + this.produtionName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HashMap<ICast, String> getCastMap() {
        return castMap;
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
    public String getProductionName() {
        return this.produtionName;
    }

    public String[] getAirDate() {
        return airDate;
    }

    public void setAirDate(String[] airDate) {
        this.airDate = airDate;
    }
}
