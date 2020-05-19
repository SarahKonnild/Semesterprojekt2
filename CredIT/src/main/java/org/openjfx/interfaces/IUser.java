package org.openjfx.interfaces;

import java.util.ArrayList;

public interface IUser {
    /**
     * Gets parameters from the presentation layer to then create a new cast member in the database.
     * Creates a new object of the class cast and calls the <code>saveCast()</code>saveCast method on the object
     *
     * @param name    the name of the person
     * @param regDKID the persons registerings Danmark ID number
     * @return if it succeed creating and saving a new cast returns <code>true</code> else <code>false</code>
     */
    ICast addNewCastToDatabase(String name, String regDKID);
    /**
     * Gets parameters from the presentation layer to then create a new broadcast in the database.
     * Creates a new object of the <code>Broadcast</code> class and calls <code>saveBroadcast()</code> on the object
     *
     * @param name          name of the broadcast
     * @param seasonNumber  the number of the season the broadcast belongs to
     * @param episodeNumber the episode number the broadcast has in the season
     * @param airDate       the date of which the broadcast aired
     * @return if it succeed creating and saving a new broadcast returns <code>true</code> else <code>false</code>
     */
    IBroadcast addNewBroadcastToDatabase(String name, int seasonNumber, int episodeNumber, String airDate, int productionID);
    /**
     * Gets parameters from the presentation layer to then create a new cast member in the database.
     * Creates a new object of the <code>Cast</code> class and calls <code>saveCast()</code> on the object
     *
     * @param name              name of the production
     * @param year              the year the production was created/first aired
     * @param productionCompany the name of the company that produces this production
     * @return if it succeed creating and saving a new cast returns <code>true</code> else <code>false</code>
     */
    IProduction addNewProductionToDatabase(String name, String year, int productionCompanyID);

    /**
     * The method is used to see if the user that is currently logged in has the ability to do certain things.
     *
     * @return Returns <code>true</code> if the string match the role of this user object else <code>false</code>
     */
    IMovie addNewMovieToDatabase(String name, int productionCompanyID, String releasedate);

    boolean checkRole(String role);

    IProductionCompany addNewProductionCompanyToDatabase(String name);

    IProductionCompany getProductionCompanyFromDatabase(String name);

    int getId();

    String getName();

    String getPassword();

    String getUsername();

    Role getRole();
}
