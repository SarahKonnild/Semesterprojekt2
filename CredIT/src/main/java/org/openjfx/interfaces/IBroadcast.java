package org.openjfx.interfaces;

import org.openjfx.domain.Broadcast;
import org.openjfx.domain.ICast;

import java.util.ArrayList;
import java.util.HashMap;

public interface IBroadcast {
    public void addCastMembers(ICast cast);
    public void removeCastMember(String role, ICast cast);
    public boolean saveBroadcast();
    public void assignCast(ICast cast, Broadcast broadcast, String role);
    public void unassignCast(ICast cast, Broadcast broadcast, String role);
    public int getId();
    public String getName();
    public HashMap<String, ArrayList<ICast>> getCastMap();
    public int getSeasonNumber();
    public int getEpisodeNumber();
    public String getAirDate();
}

