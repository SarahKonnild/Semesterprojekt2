package org.openjfx.interfaces;

import java.io.IOException;
import java.util.List;

public interface IPersistence {

    /**
     * Saves a new user to the persistence layer.
     *
     * @param user the user object that needs to be saved
     * @return A boolean that is true if the user was succesfully written to the persistence layer
     * @throws IOException
     */
    public boolean createNewUserInDatabase(IUser user) throws IOException;

    /**
     * Deletes a user from the persistence/layer(Database).
     *
     * @param id The ID on the user you want to delete in the persistence layer.
     * @return returns the boolean value of the delete run.
     */
    public boolean removeUserFromDatabase(int id);

    /**
     * Saves a new broadcast to the persistence layer.
     *
     * @param broadcast The broadcast object that needs to be saved
     * @return A boolean that is true if the broadcast was succesfully written to the persistence layer
     */
    public int createNewBroadcastInDatabase(IBroadcast broadcast) throws IOException;

    /**
     * Deletes a broadcast from the persistence/layer(Database).
     *
     * @param id The ID on the broadcast you want to delete in the persistence layer.
     * @return returns the boolean value of the delete run.
     */
    public boolean removeBroadcastFromDatabase(int id);

    /**
     * Saves a new production to the persistence layer.
     *
     * @param production The production object that needs to be saved
     * @return A boolean that is true if the production was succesfully written to the persistence layer
     */
    public int createNewProductionInDatabase(IProduction production) throws IOException;

    /**
     * Deletes a production from the persistence/layer(Database).
     *
     * @param id The ID on the production you want to delete in the persistence layer.
     * @return returns the boolean value of the delete run.
     */
    public boolean removeProductionFromDatabase(int id);

    /**
     * Saves a new cast to the persistence layer.
     *
     * @param cast The production object that needs to be saved
     * @return A boolean that is true if the cast was succesfully written to the persistence layer
     */
    public int createNewCastInDatabase(ICast cast) throws IOException;

    /**
     * Deletes a cast from the persistence/layer(Database).
     *
     * @param id The ID on the cast you want to delete in the persistence layer.
     * @return returns the boolean value of the delete run.
     */
    public boolean removeCastFromDatabase(int id);

    /**
     * Finds the broadcasts that matches the keyword and returns them in a list
     *
     * @param keyword The keyword that the broadcasts are selected on
     * @return The list of broadcasts that matched the keyword
     */
    public List<String> getBroadcastFromDatabase(String keyword);

    /**
     * Finds the broadcasts that matches the keyword and returns them in a list
     *
     * @param id The keyword that the broadcasts are selected on
     * @return The list of broadcasts that matched the keyword
     */
    public List<String> getBroadcastFromDatabase(int id);

    /**
     * Finds the casts that matches the keyword and returns them in a list
     *
     * @param keyword The keyword that the casts are selected on
     * @return The list of casts that matched the keyword
     */
    public List<String> getCastFromDatabase(String keyword);

    /**
     * Finds the casts that matches the keyword and returns them in a list
     *
     * @param id The keyword that the casts are selected on
     * @return The list of casts that matched the keyword
     */
    public List<String> getCastFromDatabase(int id);


    /**
     * Finds the production that matches the keyword and returns them in a list
     *
     * @param keyword The keyword that the production are selected on
     * @return The list of production that matched the keyword
     */
    public List<String> getProductionFromDatabase(String keyword);


    /**
     * Finds the production that matches the keyword and returns them in a list
     *
     * @param id The keyword that the production are selected on
     * @return The list of production that matched the keyword
     */
    public List<String> getProductionFromDatabase(int id);

    /**
     * Merges the two casts in the persistence layer, going through and finding all the references and making sure that the merge is completed correctly
     *
     * @param cast1 The first cast member that needs merging
     * @param cast2 The second cast member that needs merging
     * @return The boolean value of the merge
     */
    public boolean mergeCastInDatabase(ICast cast1, ICast cast2);

    /**
     * Update a cast members values to new name and regDKID
     *
     * @param id      The id of the cast member
     * @param name    The new name of the cast member
     * @param regDKID The new regDKID of the cast member
     * @return
     */
    public boolean updateCastInDatabase(int id, String name, String regDKID);


}
