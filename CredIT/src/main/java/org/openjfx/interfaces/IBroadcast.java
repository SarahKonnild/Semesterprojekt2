package org.openjfx.interfaces;

import org.openjfx.domain.Broadcast;
import org.openjfx.domain.Cast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public interface IBroadcast {
    public void addCastMembers(Cast cast);
    public void removeCastMember(String role, Cast cast);
    public boolean saveBroadcast(Broadcast broadcast);
    public void assignCast(Cast cast, Broadcast broadcast, String role);
    public void unassignCast(Cast cast, Broadcast broadcast, String role);
    public int getId();
    public String getName();
    public HashMap<String, ArrayList<ICast>> getCastMap();
    public int getSeasonNumber();
    public int getEpisodeNumber();
    public String getAirDate();
}

