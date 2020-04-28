package org.openjfx.domain;

import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IMovie;

import java.util.HashMap;

public class Movie implements IMovie {
    private String title;
    private String productionCompany;
    private String[] releaseDate;
    private int id;
    private HashMap<ICast, String> castRoleMap;

    public Movie(String title, String productionCompany, String releaseDate){
        this.title = title;
        this.productionCompany = productionCompany;
        this.releaseDate = releaseDate.split("-");
    }

    public Movie(int id, String title, String productionCompany, String releaseDate){
        this.id = id;
        this.title = title;
        this.productionCompany = productionCompany;
        this.releaseDate = releaseDate.split("-");
    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public boolean update(String title, String releaseYear, String ProductionCompany) {
        return false;
    }

    @Override
    public boolean assignCast(ICast cast, String role) {
        return false;
    }

    @Override
    public boolean unassignCast(ICast cast, String role) {
        return false;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public HashMap<ICast, String> getCastMap() {
        return this.castRoleMap;
    }

    @Override
    public String getProductionCompany() {
        return this.productionCompany;
    }

    @Override
    public String[] getReleaseDate() {
        return this.releaseDate;
    }
    
    @Override
    public void setReleaseDate(String[] airDate) {

    }
}
