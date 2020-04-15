package org.openjfx.interfaces;

import java.io.IOException;
import java.util.List;

public interface IPersistence {

    /**
     * Saves a new user to the persistence layer.
     * @param user
     * the user object that needs to be saved
     * @return
     * A boolean that is true if the user was succesfully written to the persistence layer
     * @throws IOException
     */
    public boolean createUser(IUser user) throws IOException;

    /**
     * Deletes a user from the persistence/layer(Database).
     * @param id
     * The ID on the user you want to delete in the persistence layer.
     * @return
     * returns the boolean value of the delete run.
     *
     */
    public boolean deleteUser(int id);

    /**
     * Saves a new broadcast to the persistence layer.
     * @param broadcast
     * The broadcast object that needs to be saved
     * @return
     * A boolean that is true if the broadcast was succesfully written to the persistence layer
     */
    public boolean createBroadcast(IBroadcast broadcast);

    /**
     * Deletes a broadcast from the persistence/layer(Database).
     * @param id
     * The ID on the broadcast you want to delete in the persistence layer.
     * @return
     * returns the boolean value of the delete run.
     *
     */
    public boolean deleteBroadcast(int id);

    /**
     * Saves a new production to the persistence layer.
     * @param production
     * The production object that needs to be saved
     * @return
     * A boolean that is true if the production was succesfully written to the persistence layer
     */
    public boolean createProduction(IProduction production);

    /**
     * Deletes a production from the persistence/layer(Database).
     * @param id
     * The ID on the production you want to delete in the persistence layer.
     * @return
     * returns the boolean value of the delete run.
     *
     */
    public boolean removeProduction(int id);

    /**
     * Saves a new cast to the persistence layer.
     * @param cast
     * The production object that needs to be saved
     * @return
     * A boolean that is true if the cast was succesfully written to the persistence layer
     */
    public boolean createCast(ICast cast);

    /**
     * Deletes a cast from the persistence/layer(Database).
     * @param id
     * The ID on the cast you want to delete in the persistence layer.
     * @return
     * returns the boolean value of the delete run.
     *
     */
    public boolean removeCast(int id);

    /**
     * Finds the broadcasts that matches the keyword and returns them in a list
     * @param keyword
     * The keyword that the broadcasts are selected on
     * @return
     * The list of broadcasts that matched the keyword
     */
    public List<String> getBroadcast(String keyword);

    /**
     * Finds the casts that matches the keyword and returns them in a list
     * @param keyword
     * The keyword that the casts are selected on
     * @return
     * The list of casts that matched the keyword
     */
    public List<String> getCast(String keyword);

    /**
     * Finds the production that matches the keyword and returns them in a list
     * @param keyword
     * The keyword that the production are selected on
     * @return
     * The list of production that matched the keyword
     */
    public List<String> getProduction(String keyword);

    /**
     * Merges the two casts in the persistence layer, going through and finding all the references and making sure that the merge is completed correctly
     * @param cast1
     * The first cast member that needs merging
     * @param cast2
     * The second cast member that needs merging
     * @return
     */
    public boolean mergeCast(ICast cast1, ICast cast2);

    /**
     *
     * @param id
     * @param name
     * @param regDKID
     * @return
     */
    public boolean update(int id, String name, int regDKID);


}
