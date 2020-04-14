package org.openjfx.domain;
import org.openjfx.interfaces.IBroadcast;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Broadcast implements IBroadcast {
    private int id;
    private String name;
    private HashMap<String, ArrayList<Cast>> castMap;
    private int seasonNumber;
    private int episodeNumber;
    private String airDate;

    public Broadcast(String name, int seasonNumber, int episodeNumber, String airDate) {
        this.name = name;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.airDate = airDate;
    }

    public Broadcast(int id, String name, int seasonNumber, int episodeNumber, String airDate) {
        this.id = id;
        this.name = name;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.airDate = airDate;
    }

    @Override
    public void removeCastMember(String role, Cast cast) {

    }

    @Override
    public boolean saveBroadcast() {
        return false;
    }

    @Override
    public void unassignCast(Cast cast, Broadcast broadcast, String role) {

    }

    @Override
    public void assignCast(Cast cast, Broadcast broadcast, String role) {

    }

    @Override
    public void addCastMembers(Cast cast) {

    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, ArrayList<Cast>> getCastMap() {
        return castMap;
    }

    public void setCastMap(HashMap<String, ArrayList<Cast>> castMap) {
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
