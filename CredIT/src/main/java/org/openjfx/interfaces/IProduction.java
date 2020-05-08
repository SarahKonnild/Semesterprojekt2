package org.openjfx.interfaces;

import java.util.ArrayList;

public interface IProduction {
    /**
     * Method to be used to save the object to the database.
     *
     * @return <code>true</code> if it manged to save the production to the database else <code>false</code>
     */
    boolean save();

    boolean delete();

    boolean update(String name, String year);

    int getId();

    String getName();

    String getYear();

    ArrayList<IBroadcast> getBroadcasts();

    IProductionCompany getProductionCompany();

    int getNumberOfSeasons();

    int getNumberOfEpisodes();

    @Override
    String toString();
}
