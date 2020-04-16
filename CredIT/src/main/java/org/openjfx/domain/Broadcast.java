package org.openjfx.domain;
import org.openjfx.interfaces.IBroadcast;
import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IPersistence;
import org.openjfx.persistence.Persistence;

import java.util.ArrayList;
import java.util.HashMap;

public class Broadcast implements IBroadcast {
    private final IPersistence persistence = Persistence.getInstance();
    private int id;
    private String name;
    private HashMap<String, ArrayList<ICast>> castMap;
    private int seasonNumber;
    private int episodeNumber;
    private String airDate;

    public Broadcast(int id, String name, HashMap<String, ArrayList<ICast>> castMap, int seasonNumber, int episodeNumber, String airDate) {
        this.id = id;
        this.name = name;
        this.castMap = castMap;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.airDate = airDate;
    }

    @Override
    public boolean saveBroadcast(IBroadcast broadcast) {
        persistence.createBroadcast(broadcast);
        return persistence.createBroadcast(broadcast);
    }

    @Override
    public void unassignCast(ICast cast, String role) {
        if (castMap.containsKey(role) && castMap.get(role).contains(cast)){
            castMap.get(role).remove(cast);
            if (castMap.get(role).isEmpty()){
                castMap.remove(role);
            }
        }
    }

    @Override
    public void assignCast(ICast cast, String role) {
        if (castMap.containsKey(role)) {
            castMap.get(role).add(cast);
        }
        if (!castMap.containsKey(role)) {
            castMap.put(role, new ArrayList<ICast>());
            castMap.get(role).add(cast);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, ArrayList<ICast>> getCastMap() {
        return castMap;
    }

    public void setCastMap(HashMap<String, ArrayList<ICast>> castMap) {
        this.castMap = castMap;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }
}
