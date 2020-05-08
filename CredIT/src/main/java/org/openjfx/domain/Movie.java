package org.openjfx.domain;

import org.openjfx.interfaces.*;

import java.io.IOException;
import java.util.HashMap;

public class Movie implements IMovie {
    private final IPersistence persistence = CredITSystem.getPersistence();
    private final CredITSystem system = CredITSystem.getInstance();
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
        this.castRoleMap = system.getCastRolesMovies(this.id);
        this.productionCompany = system.searchProductionCompany(productionCompanyID);
    }

    @Override
    public boolean save() {
        int idNumber = persistence.createNewMovieInDatabase(this);
        if(idNumber != -1) this.id = idNumber;
        return (idNumber == -1) ? false : true; 
    }

    @Override
    public boolean delete() {
        return persistence.removeMovieFromDatabase(this.id);
    }

    @Override
    public boolean update(String title, String releaseYear) {
        String tempTitle = this.title;
        String[] tempYear = this.releaseDate;
        this.title = title;
        this.releaseDate = releaseYear.split("-");
        if (persistence.updateMovieInDatabase(this))
            return true;
        else {
            this.title = tempTitle;
            this.releaseDate = tempYear;
            return false;
        }
    }

    @Override
    public boolean unassignCast(ICast cast, String role) {
        if (castRoleMap.containsKey(cast)) {
            HashMap<ICast, String> tempRoleMap = this.castRoleMap;
            castRoleMap.remove(cast);
            if(persistence.updateMovieInDatabase(this))
                return true;
            else {
                castRoleMap.clear();
                castRoleMap = tempRoleMap;
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean assignCast(ICast cast, String role) {
        HashMap<ICast, String> tempRoleMap = this.castRoleMap;
        castRoleMap.put(cast, role);
        if(persistence.updateMovieInDatabase(this))
        {
            return true;
        }else
        {
            //A mistake in saving to database must have happened. So the data is cleared and the latest data is pulled from database.
            castRoleMap.clear();
            castRoleMap = tempRoleMap;
            return false;
        }
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

}
