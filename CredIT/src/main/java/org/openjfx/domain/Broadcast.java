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
    private Date airDate;

    public Broadcast(int id, String name, HashMap<String, ArrayList<Cast>> castMap, int seasonNumber, int episodeNumber, Date airDate) {
        this.id = id;
        this.name = name;
        this.castMap = castMap;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.airDate = airDate;
    }

    @Override
    public boolean saveBroadcast(Broadcast broadcast) {
        return false;
    }


    /**
     * Removes a Cast object from the Broadcast object's HashMap.
     * @param cast the Cast object that is to be removed.
     * @param role the role of the given cast member.
     */
    @Override
    public void unassignCast(Cast cast, String role) {
        if (castMap.containsKey(role) && castMap.get(role).contains(cast)){
            castMap.get(role).remove(cast);
        }
    }

    /**
     * Assigns a Cast object to a specified role value in the HashMap of the broadcast.
     * @param cast the Cast object that is to be assigned.
     * @param role the role of the given cast member.
     */
    @Override
    public void assignCast(Cast cast, String role) {
        if (castMap.containsKey(role)) {
            castMap.get(role).add(cast);
        }
        if (!castMap.containsKey(role)) {
            castMap.put(role, new ArrayList<Cast>());
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

    public Date getAirDate() {
        return airDate;
    }

    public void setAirDate(Date airDate) {
        this.airDate = airDate;
    }
}
