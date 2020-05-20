package org.openjfx.interfaces;

import org.openjfx.domain.Cast;

import java.util.HashMap;

public interface IMovie {


    /**
     * Saves the object to the database
     * @return True if its saved to the database and false if not
     */
    boolean save(int productionCompanyId);

    /**
     * Deletes the object, or data acosited with it in the database
     * @return True if deleted from database and false if not
     */
    boolean delete();

    /**
     * Updates the title and releaseYear on the object and afterwards it calls methods to save it to the database
     * @param title
     * @param releaseYear
     * @return true if its saved to the database and false if not
     */
    boolean update(String title, String releaseYear);

    /**
     * Takes a Cast object and a role. Assign it to the hashmap of castRoles and saves this connection to the database
     * @param cast
     * @param role
     * @return True if succed save in database and false if it fails
     */
    boolean assignCast(Cast cast, String role);

    /**
     * Removes the cast and their role from the castRole map if they are in the map. Also calls the method
     * in persistence to remove the connection in the database
     * @param cast
     * @param role
     * @return True if it succeded to remove the connection in the database and false if not.
     */
    boolean unassignCast(Cast cast, String role);

    int getId();

    String getTitle();

    HashMap<Cast, String> getCastMap();

    String[] getReleaseDate();

    @Override
    String toString();
}
