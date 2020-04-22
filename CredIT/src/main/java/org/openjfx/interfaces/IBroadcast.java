package org.openjfx.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface that the Broadcast class should implement. Used to expose methods and data to other layers
 */
public interface IBroadcast {

    /**
     * Saves the broadcast to the persistence.
     *
     * @return
     */
    public boolean save();

    /**
     * Assigns a {@code Cast} object to a specified role value in the HashMap of the broadcast.
     *
     * @param cast the {@code Cast} object that is to be assigned.
     * @param role the role of the given cast member, as a {@code String}.
     */
    public boolean assignCast(ICast cast, String role);

    /**
     * Removes a {@code Cast} object from the Broadcast object's HashMap.
     *
     * @param cast the {@code Cast} object that is to be removed.
     * @param role the role of the given cast member, as a {@code String}.
     */
    public boolean unassignCast(ICast cast, String role);

    /**
     * Returns the ID of the broadcast. This ID is given by the persistence layer.
     *
     * @return the ID of the broadcast.
     */
    public int getId();

    /**
     * Returns the name of the broadcast.
     *
     * @return the name of the broadcast.
     */
    public String getName();

    /**
     * Returns a map over the roles and their associated cast members on this broadcast.
     *
     * @return a map over the roles and their associated cast members on this broadcast.
     */
    public HashMap<String, ArrayList<ICast>> getCastMap();

    /**
     * Returns the season number of this broadcast.
     *
     * @return the season number of this broadcast.
     */
    public int getSeasonNumber();

    /**
     * Returns the episode number of this broadcast.
     *
     * @return the episode number of this broadcast.
     */
    public int getEpisodeNumber();

    public String getProductionName();

    /**
     * Returns the air date of this broadcast.
     *
     * @return the air date of this broadcast.
     */
    public String[] getAirDate();

    public void setAirDate(String[] airDate);

    @Override
    public String toString();
}

