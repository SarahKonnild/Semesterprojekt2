package org.openjfx.interfaces;

import java.util.ArrayList;

public interface IProductionCompany {

    public boolean save();

    public boolean delete();

    public boolean update(String name);

    public boolean assignMovie(IMovie movie);

    public boolean assignProduction(IProduction production);

    public boolean unassignMovie(IMovie movie);

    public boolean unassignProduction(IProduction production);

    public String getName();

    public ArrayList<IProduction> getProductionList();

    public ArrayList<IMovie> getMovieList();
}
