package org.openjfx.interfaces;

import java.util.HashMap;

public interface IMovie {


    public boolean save();

    public boolean assignCast(ICast cast, String role);

    public boolean unassignCast(ICast cast, String role);

    public int getId();

    public String getName();

    public HashMap<ICast, String> getCastMap();

    public int getSeasonNumber();

    public int getEpisodeNumber();

    public String getProductionName();

    public String[] getAirDate();

    public void setAirDate(String[] airDate);

    @Override
    public String toString();
}
