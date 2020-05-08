package org.openjfx.interfaces;

import java.util.ArrayList;

public interface IProductionCompany {

    boolean save();

    boolean delete();

    boolean update(String name);

    boolean assignMovie(IMovie movie);

    boolean assignProduction(IProduction production);

    boolean unassignMovie(IMovie movie);

    boolean unassignProduction(IProduction production);

    String getName();

    ArrayList<IProduction> getProductionList();

    ArrayList<IMovie> getMovieList();
}
