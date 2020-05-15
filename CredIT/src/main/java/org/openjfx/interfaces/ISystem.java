package org.openjfx.interfaces;


import org.openjfx.persistence.Persistence;

import java.util.ArrayList;

public interface ISystem {
    /**
     * The method makes a call to the persistence layer and gives it a keyword to be used in the search in the database.
     *
     * @param keyword the word that the user wants to search for in the database
     * @return <code>ArrayList</code> with cast objects
     */
    ArrayList<ICast> searchCast(String keyword);

    /**
     * The method makes a call to the persistence layer and gives it a database ID number to be used in the search in the database.
     * Only used by methods to find the casts that belongs to a certain broadcast.
     *
     * @param broadcastId the database ID number of the broadcast
     * @return <code>ArrayList</code> with cast objects
     */
    ArrayList<ICast> searchCast(int broadcastId);

    /**
     * The method makes a call to the persistence layer and gives it a keyword to be used in the search in the database.
     *
     * @param keyword the word that the user wants to search for in the database
     * @return <code>ArrayList</code> with broadcast objects
     */
    ArrayList<IBroadcast> searchBroadcast(String keyword);

    /**
     * The method makes a call to the persistence layer and gives it a database ID number to be used in the search in the database.
     * Only used by methods to find the broadcasts that belongs to a certain production.
     *
     * @param productionId the database ID number of the production
     * @return <code>ArrayList</code> with broadcast objects
     */
    ArrayList<IBroadcast> searchBroadcast(int productionId);

    /**
     * The method makes a call to the persistence layer and gives it a keyword to be used in the search in the database.
     *
     * @param keyword the word that the user wants to search for in the database
     * @return <code>ArrayList</code> with production objects
     */
    ArrayList<IProduction> searchProduction(String keyword);

    /**
     * The method returns the production that a broadcast is connected to
     * @param broadcastId The id of the Production that is searched for
     * @return The Production that have the broadcast in it.
     */
    IProduction searchProduction(int broadcastId);

    ArrayList<IMovie> searchMovie(String keyword);

    ArrayList<IMovie> searchMovies(int productionCompanyID);

    ArrayList<IProductionCompany> searchProductionCompany(String keyword);

    /**
     * Searches the database for a ProductionCompany that produced the Production
     * @param productionId the id of the Production you want the productionCompany of
     * @return returns the productionComapany object.
     */
    IProductionCompany searchProductionCompanyOnProduction(int productionId);

    /**
     * Searches the database for a ProductionCompany that produced the movie
     * @param movieId the id of the movie you want the productionComapany of
     * @return returns the productionCompany object
     */
    IProductionCompany searchProductionCompanyOnMovie(int movieId);

    IUser createNewUser(String username, String password);

    IUser getUser();

    Persistence getPersistenceLayer();
}
