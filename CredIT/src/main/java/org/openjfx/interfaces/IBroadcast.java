package org.openjfx.interfaces;

import org.openjfx.domain.Broadcast;
import org.openjfx.domain.Cast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public interface IBroadcast {
    public boolean saveBroadcast(Broadcast broadcast);
    public void assignCast(Cast cast,String role);
    public void unassignCast(Cast cast, String role);
    public int getId();
    public String getName();
    public HashMap<String, ArrayList<Cast>> getCastMap();
    public int getSeasonNumber();
    public int getEpisodeNumber();
    public Date getAirDate();
}

