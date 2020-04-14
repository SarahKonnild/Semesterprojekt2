package org.openjfx.interfaces;
<<<<<<< Updated upstream

public interface IBroadcast {
    //public void addCastMembers(Cast cast);
    // public void removeCastMember(String role, Cast cast);
    // public boolean saveBroadcast(Broadcast broadcast);
    // public void unassignCast(Cast cast, String role);
    // public void assignCast(Cast cast, Broadcast broadcast, String role);
    // public int getId();
    // public String getName();
    // public HashMap<String, ArrayList<Cast>> getCastMap();
    // public int getSeasonNumber();
    // public int getEpisodeNumber();
    // public Date getAirDate();
=======

import org.openjfx.domain.Broadcast;
import org.openjfx.domain.Cast;

public interface IBroadcast {

    public void addCastMembers(Cast cast);
    public void removeCastMember(String role, Cast cast);
    public boolean saveBroadcast(Broadcast broadcast);
    public void unassignCast(Cast cast, Broadcast broadcast, String role);
    public void assignCast(Cast cast, Broadcast broadcast, String role);
>>>>>>> Stashed changes
}

