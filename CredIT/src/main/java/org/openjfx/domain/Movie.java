package org.openjfx.domain;

import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IMovie;
import org.openjfx.interfaces.IProduction;
import org.openjfx.interfaces.IProductionCompany;

import java.io.IOException;
import java.util.HashMap;

public class Movie implements IMovie {
    private String title;
    private String[] releaseDate;
    private int id;
    private HashMap<ICast, String> castRoleMap;
    private IProductionCompany productionCompany;

    public Movie(String title, IProductionCompany productionCompany, String releaseDate){
        this.title = title;
        this.releaseDate = releaseDate.split("-");
        this.productionCompany = productionCompany;
    }

    public Movie(int id, String title, String releaseDate, int productionCompanyID){
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate.split("-");
        this.castRoleMap = CredITSystem.instance.getCastRolesMovies(this.id);
        //this.productionCompany = CredITSystem.instance.searchProductionCompany(productionCompanyID);
    }

    @Override
    public boolean save() {
        int idNumber = -1;
        try {
            idNumber = CredITSystem.instance.getPersistenceLayer().createNewMovieInDatabase(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (idNumber != -1) {
            this.id = idNumber;
            return true;
        } else
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
    public IProductionCompany getProductionCompany() {
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
