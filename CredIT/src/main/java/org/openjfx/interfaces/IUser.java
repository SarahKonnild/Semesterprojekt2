package org.openjfx.interfaces;

public interface IUser {
    /**
     * Gets parameters from the presentation layer to then create a new cast member in the database.
     * Creates a new object of the class cast and calls the <code>saveCast()</code>saveCast method on the object
     * @param name the name of the person
     * @param regDKID the persons registerings Danmark ID number
     * @return if it succeed creating and saving a new cast returns <code>true</code> else <code>false</code>
     */
    public boolean addNewCastToDatabase(String name, int regDKID);
    /**
     * Gets parameters from the presentation layer to then create a new broadcast in the database.
     * Creates a new object of the <code>Broadcast</code> class and calls <code>saveBroadcast()</code> on the object
     * @param name name of the broadcast
     * @param seasonNumber the number of the season the broadcast belongs to
     * @param episodeNumber the episode number the broadcast has in the season
     * @param airDate the date of which the broadcast aired
     * @return if it succeed creating and saving a new broadcast returns <code>true</code> else <code>false</code>
     */
    public boolean addNewBroadcastToDatabase(String name, int seasonNumber, int episodeNumber, String airDate);
    /**
     * Gets parameters from the presentation layer to then create a new cast member in the database.
     * Creates a new object of the <code>Cast</code> class and calls <code>saveCast()</code> on the object
     * @param name name of the production
     * @param year the year the production was created/first aired
     * @param productionCompany the name of the company that produces this production
     * @return if it succeed creating and saving a new cast returns <code>true</code> else <code>false</code>
     */
    public boolean addNewProductionToDatabase(String name, String year, String productionCompany);
    /**
     * The method is used to see if the user that is currently logged in has the ability to do certain things.
     * @param roleKey the role of the user thats logged in.
     * @return Returns <code>true</code> if the straing match the role of this user object else <code>false</code>
     */
    public boolean checkRole(String role);
    public int getId();
    public String getName();
    public String getPassword();
    public String getUsername();
    public Role getRole();
}
