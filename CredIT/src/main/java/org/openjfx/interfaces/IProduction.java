package org.openjfx.interfaces;

import org.openjfx.domain.Broadcast;
import org.openjfx.domain.Production;

import java.util.ArrayList;

public interface IProduction {
    public boolean saveProduction(Production production);
    public int getId();
    public String getName();
    public int getYear();
    public ArrayList<Broadcast> getBroadcasts();
    public String getProductionCompany();
    public int getNumberOfSeasons();
    public int getNumberOfEpisodes();
}
