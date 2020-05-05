package org.openjfx.interfaces;

import java.util.HashMap;

public interface IMovie {


    public boolean save();

    public boolean delete();

    public boolean update(String title, String releaseYear);

    public boolean assignCast(ICast cast, String role);

    public boolean unassignCast(ICast cast, String role);

    public int getId();

    public String getTitle();

    public HashMap<ICast, String> getCastMap();

    public IProductionCompany getProductionCompany();

    public String[] getReleaseDate();

    public void setReleaseDate(String[] airDate);

    @Override
    public String toString();
}
