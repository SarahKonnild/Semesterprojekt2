package org.openjfx.interfaces;

import org.openjfx.domain.Movie;
import org.openjfx.domain.Production;

import java.util.ArrayList;

public interface IProductionCompany {

    /**
     * Saves a new prodution company to the database
     *
     * @return True if it saved and false if not
     */
    boolean save();

    /**
     * Deletes the production company in the database
     *
     * @return True if deleted and false if not
     */
    boolean delete();

    /**
     * Updates the name of the production company in domain and calls methods to the update in the database
     *
     * @param name
     * @return
     */
    boolean update(String name);

    String getName();

    int getId();

    ArrayList<Production> getProductionList();

    ArrayList<Movie> getMovieList();
}
