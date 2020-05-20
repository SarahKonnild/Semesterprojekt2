package org.openjfx.interfaces;

import org.openjfx.domain.Movie;
import org.openjfx.domain.Production;

import java.util.ArrayList;

public interface IProductionCompany {

    /**
     * Saves a new prodution company to the database
     * @return True if it saved and false if not
     */
    boolean save();

    /**
     * Deletes the production company in the database
     * @return True if deleted and false if not
     */
    boolean delete();

    /**
     * Updates the name of the production company in domain and calls methods to the update in the database
     * @param name
     * @return
     */
    boolean update(String name);

    /**
     * Add a connection between the movie and this production company in domain and database
     * @param movie
     * @return true or false
     */
    boolean assignMovie(Movie movie);

    /**
     * Add a connection between the production and this production company in domain and database
     * @param production
     * @return true or false
     */
    boolean assignProduction(Production production);

    /**
     * Removes the connection between production and productioncompany in domain and the database
     * @param movie
     * @return true or false
     */
    boolean unassignMovie(Movie movie);

    /**
     * Removes the connection between production and productioncompany in domain and the database
     * @param production
     * @return true or false
     */
    boolean unassignProduction(Production production);

    String getName();

    int getId();

    ArrayList<Production> getProductionList();

    ArrayList<Movie> getMovieList();
}
