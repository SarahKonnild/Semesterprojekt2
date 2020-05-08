package org.openjfx.domain;

import org.openjfx.interfaces.*;

import java.io.IOException;
import java.util.HashMap;

public class Broadcast implements IBroadcast {
    private final IPersistence persistence = CredITSystem.getPersistence();
    private final CredITSystem system = CredITSystem.getInstance();
    private int id;
    private String name;
    private HashMap<ICast, String> castRoleMap;
    private int seasonNumber;
    private int episodeNumber;
    private String[] airDate;
    private IProduction production;

    public Broadcast(int id, String name, int seasonNumber, int episodeNumber, String airDate, int productionID) {
        this.id = id;
        this.name = name;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.airDate = airDate.split("-");
        this.castRoleMap = system.getCastRolesBroadcast(this.id);
        this.production = system.searchProduction(productionID);
    }

    public Broadcast(String name, int seasonNumber, int episodeNumber, String airDate, IProduction production) {
        this.name = name;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.airDate = airDate.split("-");
        this.castRoleMap = null;
        //todo What to do about Production objects?
    }

    @Override
    public boolean save() {
        int idNumber = persistence.createNewBroadcastInDatabase(this);
        if(idNumber != -1) this.id = idNumber;
        return (idNumber == -1) ? false : true;
    }

    @Override
    public boolean delete() {
        return persistence.removeBroadcastFromDatabase(this.id);
    }

    @Override
    public boolean update(String name, int seasonNumber, int episodeNumber, String airDate) {
        String tempName = this.name;
        String[] tempDate = this.airDate;
        int tempSeason = this.seasonNumber;
        int tempEpisode = this.episodeNumber;

        this.name = name;
        this.airDate = airDate.split("-");
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;

        if(persistence.updateBroadcastInDatabase(this))
            return true;
        else{
            this.name = tempName;
            this.airDate = tempDate;
            this.seasonNumber = tempSeason;
            this.episodeNumber = tempEpisode;
            return false;
        }
    }

    @Override
    public boolean unassignCast(ICast cast, String role) {
        if (castRoleMap.containsKey(cast)) {
            HashMap<ICast, String> tempRoleMap = this.castRoleMap;
            castRoleMap.remove(cast);
            if(persistence.updateBroadcastInDatabase(this))
                return true;
            else {
                castRoleMap.clear();
                castRoleMap = tempRoleMap;
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean assignCast(ICast cast, String role) {
        HashMap<ICast, String> tempRoleMap = this.castRoleMap;
        castRoleMap.put(cast, role);
        if(persistence.updateBroadcastInDatabase(this))
        {
            return true;
        }else
        {
            //A mistake in saving to database must have happened. So the data is cleared and the latest data is pulled from database.
            castRoleMap.clear();
            castRoleMap = tempRoleMap;
            return false;
        }
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

    @Override
    public IProduction getProduction() {
        return this.production;
    }

    public void setProduction(){
        //Todo decide if it will search for the production itself or if it gets a production object
    }

    public String[] getAirDate() {
        return airDate;
    }

    public void setAirDate(String[] airDate) {
        this.airDate = airDate;
    }

    public void setCastRoleMap(HashMap<ICast, String> castRoleMap) {
        this.castRoleMap = castRoleMap;
    }

    public void setProduction(IProduction production) {
        this.production = production;
    }
}
