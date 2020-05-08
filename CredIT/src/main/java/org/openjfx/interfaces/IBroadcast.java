package org.openjfx.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface that the Broadcast class should implement. Used to expose methods and data to other layers
 */
public interface IBroadcast {

    /**
     * Saves the broadcast to the database.
     *
     * @return
     */
    boolean save();

    /**
     * Deletes the broadcast data in the database.
     * @return
     */
    boolean delete();


    /**
     * Takes the new values of the variables as arguments and sets the variables on the object to those,
     * afterwards it calls save methods in persistens
     * @param name
     * @param seasonNumber
     * @param episodeNumber
     * @param airDate
     * @return
     */
    boolean update(String name, int seasonNumber, int episodeNumber, String airDate);

    /**
     * Assigns a {@code Cast} object to a specified role value in the HashMap of the broadcast.
     *
     * @param cast the {@code Cast} object that is to be assigned.
     * @param role the role of the given cast member, as a {@code String}.
     */
    boolean assignCast(ICast cast, String role);

    /**
     * Removes a {@code Cast} object from the Broadcast object's HashMap.
     *
     * @param cast the {@code Cast} object that is to be removed.
     * @param role the role of the given cast member, as a {@code String}.
     */
    boolean unassignCast(ICast cast, String role);

    /**
     * Returns the ID of the broadcast. This ID is given by the persistence layer.
     *
     * @return the ID of the broadcast.
     */
    int getId();

    /**
     * Returns the name of the broadcast.
     *
     * @return the name of the broadcast.
     */
    String getName();

    /**
     * Returns a map over the roles and their associated cast members on this broadcast.
     *
     * @return a map over the roles and their associated cast members on this broadcast.
     */
    HashMap<ICast, String> getCastMap();

    /**
     * Returns the season number of this broadcast.
     *
     * @return the season number of this broadcast.
     */
    int getSeasonNumber();

    /**
     * Returns the episode number of this broadcast.
     *
     * @return the episode number of this broadcast.
     */
    int getEpisodeNumber();

    IProduction getProduction();

    /**
     * Returns the air date of this broadcast.
     *
     * @return the air date of this broadcast.
     */
    String[] getAirDate();

    @Override
    String toString();
}

