package org.openjfx.interfaces;

import java.util.HashMap;

public interface IMovie {


    boolean save();

    boolean delete();

    boolean update(String title, String releaseYear);

    boolean assignCast(ICast cast, String role);

    boolean unassignCast(ICast cast, String role);

    int getId();

    String getTitle();

    HashMap<ICast, String> getCastMap();

    IProductionCompany getProductionCompany();

    String[] getReleaseDate();

    void setReleaseDate(String[] airDate);

    @Override
    String toString();
}
