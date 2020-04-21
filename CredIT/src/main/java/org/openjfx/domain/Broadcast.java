package org.openjfx.domain;
import org.openjfx.interfaces.IBroadcast;
import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IPersistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Broadcast implements IBroadcast {
    private final IPersistence persistence = CredITSystem.getPersistence();
    private int id;
    private String name;
    private HashMap<String, ArrayList<ICast>> castMap;
    private String produtionName;
    private int seasonNumber;
    private int episodeNumber;
    private String[] airDate;

    public Broadcast(int id, String name, HashMap<String, ArrayList<ICast>> castMap, int seasonNumber, int episodeNumber, String airDate, String productionName) {
        this.id = id;
        this.name = name;
        this.castMap = castMap;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.airDate = airDate.split("-");
        this.produtionName = productionName;
    }

    public Broadcast(String name, int seasonNumber, int episodeNumber, String airDate){
        this.name = name;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.airDate = airDate.split("-");
    }

//    private void loadCast(){
//
//    }

    @Override
    public boolean saveBroadcast() {
        try {
            return persistence.createNewBroadcastInDatabase(this);
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean unassignCast(ICast cast, String role) {
        if (castMap.containsKey(role) && castMap.get(role).contains(cast)){
            castMap.get(role).remove(cast);
            if (castMap.get(role).isEmpty()){
                castMap.remove(role);
            }
        }
        return true;
    }

    @Override
    public boolean assignCast(ICast cast, String role) {
        if (castMap.containsKey(role)) {
            castMap.get(role).add(cast);
        }
        if (!castMap.containsKey(role)) {
            castMap.put(role, new ArrayList<ICast>());
            castMap.get(role).add(cast);
        }
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

    public HashMap<String, ArrayList<ICast>> getCastMap() {
        return castMap;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    @Override
    public String getProductionName() {
        return this.produtionName;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String[] getAirDate() {
        return airDate;
    }

    public void setAirDate(String[] airDate) {
        this.airDate = airDate;
    }
}
