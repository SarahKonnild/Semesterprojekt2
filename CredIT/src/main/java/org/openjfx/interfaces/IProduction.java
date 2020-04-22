package org.openjfx.interfaces;

import java.util.ArrayList;

public interface IProduction {
    /**
     * Method to be used to save the object to the database.
     *
     * @return <code>true</code> if it manged to save the production to the database else <code>false</code>
     */
    public boolean save();

    public int getId();

    public String getName();

    public String getYear();

    public ArrayList<IBroadcast> getBroadcasts();

    public String getProductionCompany();

    public int getNumberOfSeasons();

    public int getNumberOfEpisodes();

    @Override
    public String toString();
}
