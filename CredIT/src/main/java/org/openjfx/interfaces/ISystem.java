package org.openjfx.interfaces;


import org.openjfx.domain.*;

import java.util.ArrayList;
import java.util.HashMap;

public interface ISystem {

    HashMap<Movie, String> getCastRolesMovies(ICast cast);

    HashMap<Broadcast, String> getCastRolesBroadcast(ICast cast);

    /**
     * The method makes a call to the persistence layer and gives it a keyword to be used in the search in the database.
     *
     * @param keyword the word that the user wants to search for in the database
     * @return <code>ArrayList</code> with cast objects
     */
    ArrayList<Cast> searchCast(String keyword);

    /**
     * The method makes a call to the persistence layer and gives it a database ID number to be used in the search in the database.
     * Only used by methods to find the casts that belongs to a certain broadcast.
     *
     * @param broadcastId the database ID number of the broadcast
     * @return <code>ArrayList</code> with cast objects
     */
    ArrayList<Cast> searchCast(int broadcastId);

    /**
     * The method makes a call to the persistence layer and gives it a keyword to be used in the search in the database.
     *
     * @param keyword the word that the user wants to search for in the database
     * @return <code>ArrayList</code> with broadcast objects
     */
    ArrayList<Broadcast> searchBroadcast(String keyword);

    /**
     * The method makes a call to the persistence layer and gives it a database ID number to be used in the search in the database.
     * Only used by methods to find the broadcasts that belongs to a certain production.
     *
     * @param productionId the database ID number of the production
     * @return <code>ArrayList</code> with broadcast objects
     */
    ArrayList<Broadcast> searchBroadcast(int productionId);

    /**
     * The method makes a call to the persistence layer and gives it a keyword to be used in the search in the database.
     *
     * @param keyword the word that the user wants to search for in the database
     * @return <code>ArrayList</code> with production objects
     */
    ArrayList<Production> searchProduction(String keyword);

    /**
     * The method returns the production that a broadcast is connected to
     *
     * @param broadcastId The id of the Production that is searched for
     * @return The Production that have the broadcast in it.
     */
    Production searchProductionOnBroadcast(int broadcastId);

    ArrayList<Movie> searchMovie(String keyword);

    ArrayList<Movie> searchMovies(int productionCompanyID);

    ArrayList<ProductionCompany> searchProductionCompany(String keyword);

    /**
     * Searches the database for a ProductionCompany that produced the Production
     *
     * @param productionId the id of the Production you want the productionCompany of
     * @return returns the productionComapany object.
     */
    ProductionCompany searchProductionCompanyOnProduction(int productionId);

    /**
     * Searches the database for a ProductionCompany that produced the movie
     *
     * @param movieId the id of the movie you want the productionComapany of
     * @return returns the productionCompany object
     */
    ProductionCompany searchProductionCompanyOnMovie(int movieId);

    Broadcast addNewBroadcastToDatabase(String name, int seasonNumber, int episodeNumber, String airDate, int productionID);

    ProductionCompany addNewProductionCompanyToDatabase(String name);

    Cast addNewCastToDatabase(String name, String regDKID);

    Movie addNewMovieToDatabase(String name, int productionCompanyID, String releasedate);

    Production addNewProductionToDatabase(String name, String year, int productionCompanyID);

    User createNewUser(String username, String password);
}
