package org.openjfx.interfaces;

import java.util.ArrayList;

public interface IProduction {
    /**
     * Method to be used to save the object to the database.
     *
     * @return <code>true</code> if it manged to save the production to the database else <code>false</code>
     */
    boolean save();

    /**
     * Method calls methods in persitence to delete the data in the database
     * @return
     */
    boolean delete();

    /**
     * Takes the new values as arguemnts and updates it on the object, call methods to save the update to the database
     * @param name
     * @param year
     * @return
     */
    boolean update(String name, String year);

    boolean assignBroadcast(IBroadcast broadcast);

    boolean unassignBroadcast(IBroadcast broadcast);

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
