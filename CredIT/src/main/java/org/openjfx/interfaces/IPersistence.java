package org.openjfx.interfaces;

import java.util.List;

public interface IPersistence {

    /**
     * Saves a new broadcast to the persistence layer.
     *
     * @param broadcast The broadcast object that needs to be saved
     * @return A boolean that is true if the broadcast was succesfully written to the persistence layer
     */
    int createNewBroadcastInDatabase(IBroadcast broadcast, int productionId);

    int createNewMovieInDatabase(IMovie movie, int productionCompanyId);

    /**
     * Saves a new production to the persistence layer.
     *
     * @param production The production object that needs to be saved
     * @return A boolean that is true if the production was succesfully written to the persistence layer
     */
    int createNewProductionInDatabase(IProduction production, int productionCompanyId);

    int createNewProductionCompanyInDatabase(IProductionCompany production);

    /**
     * Deletes a broadcast from the persistence/layer(Database).
     *
     * @param id The ID on the broadcast you want to delete in the persistence layer.
     * @return returns the boolean value of the delete run.
     */
    boolean removeBroadcastFromDatabase(int id);

    /**
     * Deletes a production from the persistence/layer(Database).
     *
     * @param id The ID on the production you want to delete in the persistence layer.
     * @return returns the boolean value of the delete run.
     */
    boolean removeProductionFromDatabase(int id);

    boolean removeCastFromDatabase(int id);

    boolean removeMovieFromDatabase(int id);

    boolean removeProductionCompanyFromDatabase(IProductionCompany company);

    /**
     * Saves a new cast to the persistence layer.
     *
     * @param cast The production object that needs to be saved
     * @return A boolean that is true if the cast was succesfully written to the persistence layer
     */
    int createNewCastInDatabase(ICast cast);

    /**
     * Finds the broadcasts that matches the keyword and returns them in a list
     *
     * @param keyword The keyword that the broadcasts are selected on
     * @return The list of broadcasts that matched the keyword
     */
    List<String> getBroadcastFromDatabase(String keyword);

    /**
     * Finds broadcast that have the id as their production, return a list of those broadcasts.
     *
     * @param productionId The keyword that the broadcasts are selected on
     * @return The list of broadcasts that matched the keyword
     */
    List<String> getBroadcastsFromDatabase(int productionId);

    List<String> getBroadcastFromDatabase(int broadcastID);

    List<String> getMovieFromDatabase(String keyword);

    List<String> getMovieFromDatabase(int movieID);

    List<String> getMoviesFromDatabase(int productionCompanyID);

    /**
     * Finds the production that matches the keyword and returns them in a list
     *
     * @param keyword The keyword that the production are selected on
     * @return The list of production that matched the keyword
     */
    List<String> getProductionFromDatabase(String keyword);

    /**
     * Searches the database for the id of the production that the broadcast is produced by
     *
     * @param broadcastId the id of the broadcast you want to search based on
     * @return returns the id of the production
     */
    int getProductionIdOnBroadcast(int broadcastId);

    List<String> getProductionsFromDatabase(int productionCompanyID);

    List<String> getProductionFromDatabase(int productionId);

    /**
     * Finds the casts that matches the keyword and returns them in a list
     *
     * @param keyword The keyword that the casts are selected on
     * @return The list of casts that matched the keyword
     */
    List<String> getCastFromDatabase(String keyword);

    /**
     * Finds the casts that matches the keyword and returns them in a list
     *
     * @param castID The keyword that the casts are selected on
     * @return The list of casts that matched the keyword
     */
    List<String> getCastFromDatabase(int castID);

    List<String> getProductionCompany(int id);

    List<String> getProductionCompany(String keyword);

    /**
     * Searches the database for the id of the productionCompany that the Production is produced by
     *
     * @param productionId the id of the production you want to search based on
     * @return returns the id of the productionCompany
     */
    int getProductionCompanyIdOnProduction(int productionId);

    /**
     * Searches the database for the id of the productionCompany that the movie is produced by
     *
     * @param movieId the id of the movie you want to search based on
     * @return returns the id of the productionCompany
     */
    int getProductionCompanyIdOnMovie(int movieId);

    /**
     * Search for the id of movie or broadcast, returns a list of castID and a string with their role in this format CastID, role
     *
     * @param id
     * @return
     */
    List<String> getCastRolesMoviesFromDatabase(int id);

    List<String> getCastRolesBroadcastFromDatabase(int id);

    /**
     * Merges the two casts in the persistence layer, going through and finding all the references and making sure that the merge is completed correctly
     *
     * @param cast1 The first cast member that needs merging
     * @param cast2 The second cast member that needs merging
     * @return The boolean value of the merge
     */
    boolean mergeCastInDatabase(ICast cast1, ICast cast2);

    List<String> castMovieRoles(ICast cast);

    List<String> castBroadcastRoles(ICast cast);

    /**
     * Update a cast members values to new name and regDKID
     */
    boolean updateCastInDatabase(ICast cast);

    boolean updateProductionCompanyInDatabase(IProductionCompany productionCompany);

    boolean updateMovieInDatabase(IMovie movie);

    boolean updateBroadcastInDatabase(IBroadcast broadcast);

    boolean updateProduction(IProduction production);


}
