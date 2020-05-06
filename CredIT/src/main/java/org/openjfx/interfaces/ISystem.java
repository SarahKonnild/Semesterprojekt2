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
    public ArrayList<ICast> searchCast(String keyword);

    /**
     * The method makes a call to the persistence layer and gives it a database ID number to be used in the search in the database.
     * Only used by methods to find the casts that belongs to a certain broadcast.
     *
     * @param broadcastId the database ID number of the broadcast
     * @return <code>ArrayList</code> with cast objects
     */
    public ArrayList<ICast> searchCast(int broadcastId);

    /**
     * The method makes a call to the persistence layer and gives it a keyword to be used in the search in the database.
     *
     * @param keyword the word that the user wants to search for in the database
     * @return <code>ArrayList</code> with broadcast objects
     */
    public ArrayList<IBroadcast> searchBroadcast(String keyword);

    /**
     * The method makes a call to the persistence layer and gives it a database ID number to be used in the search in the database.
     * Only used by methods to find the broadcasts that belongs to a certain production.
     *
     * @param productionId the database ID number of the production
     * @return <code>ArrayList</code> with broadcast objects
     */
    public ArrayList<IBroadcast> searchBroadcast(int productionId);

    /**
     * The method makes a call to the persistence layer and gives it a keyword to be used in the search in the database.
     *
     * @param keyword the word that the user wants to search for in the database
     * @return <code>ArrayList</code> with production objects
     */
    public ArrayList<IProduction> searchProduction(String keyword);

    public ArrayList<IMovie> searchMovie(String keyword);

    public ArrayList<IMovie> searchMovies(int productionCompanyID);

    public ArrayList<IProductionCompany> searchProductionCompany(String keyword);

    public IUser createNewUser(String username, String password);

    public IUser getUser();

    public Persistence getPersistenceLayer();
}
