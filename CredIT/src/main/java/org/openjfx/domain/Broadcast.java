package org.openjfx.domain;

import org.openjfx.interfaces.IBroadcast;
import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IPersistence;

import java.util.HashMap;

public class Broadcast implements IBroadcast {
    private final IPersistence persistence = CredITSystem.getPersistence();
    private int id;
    private String name;
    private HashMap<Cast, String> castRoleMap;
    private int seasonNumber;
    private int episodeNumber;
    private String[] airDate;

    public Broadcast(int id, String name, int seasonNumber, int episodeNumber, String airDate, HashMap<Cast, String> castRoleMap) {
        this.id = id;
        this.name = name;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.airDate = airDate.split("-");
        this.castRoleMap = castRoleMap;
    }

    public Broadcast(String name, int seasonNumber, int episodeNumber, String airDate) {
        this.name = name;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.airDate = airDate.split("-");
        this.castRoleMap = new HashMap<Cast, String>();
    }

    @Override
    public boolean save(int productionId) {
        int idNumber = persistence.createNewBroadcastInDatabase(this, productionId);
        if (idNumber != -1) this.id = idNumber;
        return idNumber != -1;
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

        if (persistence.updateBroadcastInDatabase(this))
            return true;
        else {
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
            HashMap<Cast, String> tempRoleMap = this.castRoleMap;
            castRoleMap.remove(cast);
            if (persistence.updateBroadcastInDatabase(this))
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
        HashMap<Cast, String> tempRoleMap = this.castRoleMap;
        castRoleMap.put((Cast) cast, role);
        if (persistence.updateBroadcastInDatabase(this)) {
            return true;
        } else {
            //A mistake in saving to database must have happened. So the data is cleared and the latest data is pulled from database.
            castRoleMap.clear();
            castRoleMap = tempRoleMap;
            return false;
        }
    }

    @Override
    public String toString() {
        return
                this.name + ": " + this.airDate[0] + "-" + this.airDate[1] + "-" + this.airDate[2];
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HashMap<Cast, String> getCastMap() {
        return castRoleMap;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public String[] getAirDate() {
        return airDate;
    }

}
