package org.openjfx.domain;

import org.openjfx.interfaces.IPersistence;
import org.openjfx.interfaces.IProductionCompany;

import java.util.ArrayList;

public class ProductionCompany implements IProductionCompany {
    private final IPersistence persistence = CredITSystem.getPersistence();
    private int id;
    private String name;
    private ArrayList<Production> productionList;
    private ArrayList<Movie> movieList;

    public ProductionCompany(String name) {
        this.name = name;
        this.movieList = new ArrayList<>();
        this.productionList = new ArrayList<>();
    }

    public ProductionCompany(int id, String name, ArrayList<Movie> movies, ArrayList<Production> productions) {
        this.id = id;
        this.name = name;
        this.productionList = productions;
        this.movieList = movies;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean save() {
        int idNumber = persistence.createNewProductionCompanyInDatabase(this);
        if (idNumber != -1) {
            this.id = idNumber;
        }
        return idNumber != -1;
    }

    @Override
    public boolean delete() {
        return persistence.removeProductionCompanyFromDatabase(this);
    }

    @Override
    public boolean update(String name) {
        String tempName = this.name;
        this.name = name;
        if (persistence.updateProductionCompanyInDatabase(this)) {
            return true;
        } else {
            this.name = tempName;
            return false;
        }
    }

    @Override
    public boolean assignMovie(Movie movie) {
        if (!movieList.contains(movie)) {
            ArrayList<Movie> tempList = this.movieList;
            this.movieList.add(movie);
            if (persistence.updateProductionCompanyInDatabase(this)) {
                return true;
            } else {
                this.movieList = tempList;
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public boolean assignProduction(Production production) {
        if (!productionList.contains(production)) {
            ArrayList<Production> tempList = this.productionList;
            this.productionList.add(production);
            if (persistence.updateProductionCompanyInDatabase(this)) {
                return true;
            } else {
                this.productionList = tempList;
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public boolean unassignMovie(Movie movie) {
        if (this.movieList.contains(movie)) {
            ArrayList<Movie> tempList = this.movieList;
            this.movieList.remove(movie);
            if (persistence.updateProductionCompanyInDatabase(this)) {
                return true;
            } else {
                this.movieList = tempList;
                return false;
            }

        } else {
            return false;
        }
    }

    @Override
    public boolean unassignProduction(Production production) {
        if (this.productionList.contains(production)) {
            //Saves the list so it can revert back if anything goes wrong.
            ArrayList<Production> tempList = this.productionList;
            this.productionList.remove(production);
            if (persistence.updateProductionCompanyInDatabase(this)) {
                return true;
            } else {
                //Reverts the changes because something went wrong
                this.productionList = tempList;
                return false;
            }

        } else {
            return false;
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public ArrayList<Production> getProductionList() {
        return this.productionList;
    }

    @Override
    public ArrayList<Movie> getMovieList() {
        return this.movieList;
    }
}
