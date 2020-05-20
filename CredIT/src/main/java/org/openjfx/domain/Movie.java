package org.openjfx.domain;

import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IMovie;
import org.openjfx.interfaces.IPersistence;

import java.util.HashMap;

public class Movie implements IMovie {
    private final IPersistence persistence = CredITSystem.getPersistence();
    private String title;
    private String[] releaseDate;
    private int id;
    private HashMap<Cast, String> castRoleMap;

    public Movie(String title, String releaseDate) {
        this.title = title;
        this.releaseDate = new String[3];
        this.releaseDate[0] = "01";
        this.releaseDate[1] = "01";
        this.releaseDate[2] = releaseDate;
        this.castRoleMap = new HashMap<Cast, String>();
    }

    public Movie(int id, String title, String releaseDate, HashMap<Cast, String> castRoleMap) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate.split("-");
        this.castRoleMap = castRoleMap;
    }

    @Override
    public boolean save(int productionCompanyId) {
        int idNumber = persistence.createNewMovieInDatabase(this, productionCompanyId);
        if (idNumber != -1) {
            this.id = idNumber;
        }
        return idNumber != -1;
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
        this.releaseDate[0] = "01";
        this.releaseDate[1] = "01";
        this.releaseDate[2] = releaseYear;
        if (persistence.updateMovieInDatabase(this)) {
            return true;
        } else {
            this.title = tempTitle;
            this.releaseDate = tempYear;
            return false;
        }
    }

    @Override
    public boolean unassignCast(ICast cast, String role) {
        if (castRoleMap.containsKey(cast)) {
            HashMap<Cast, String> tempRoleMap = this.castRoleMap;
            castRoleMap.remove(cast);
            if (persistence.updateMovieInDatabase(this))
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
        HashMap<Cast, String> tempRoleMap = this.castRoleMap;
        Cast realCast = (Cast) cast;
        castRoleMap.put(realCast, role);
        if (persistence.updateMovieInDatabase(this)) {
            return true;
        } else {
            //A mistake in saving to database must have happened. So the data is cleared and the latest data is pulled from database.
            castRoleMap.clear();
            castRoleMap = tempRoleMap;
            return false;
        }
    }

    @Override
    public String toString() {
        return title + " (" + releaseDate[2] + ")";
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
    public HashMap<Cast, String> getCastMap() {
        return this.castRoleMap;
    }

    @Override
    public String[] getReleaseDate() {
        return this.releaseDate;
    }

}
