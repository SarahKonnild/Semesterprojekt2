package org.openjfx.domain;

import org.openjfx.interfaces.IMovie;
import org.openjfx.interfaces.IPersistence;
import org.openjfx.interfaces.IProduction;
import org.openjfx.interfaces.IProductionCompany;

import java.util.ArrayList;

public class ProductionCompany implements IProductionCompany {
    private final IPersistence persistence = CredITSystem.getPersistence();
    private final CredITSystem system = CredITSystem.getInstance();
    private int id;
    private String name;
    private ArrayList<IProduction> productionList;
    private ArrayList<IMovie> movieList;

    ProductionCompany(String name) {
        this.name = name;
        this.movieList = new ArrayList<>();
        this.productionList = new ArrayList<>();
    }

    ProductionCompany(int id, String name) {
        this.id = id;
        this.name = name;
        loadProductionList();
        loadMovieList();
    }

    private void loadProductionList() {
        ArrayList<IProduction> temp = system.searchProductions(this.id);
        if(!temp.isEmpty()) {
            this.productionList = temp;
        }
    }

    private void loadMovieList() {
        ArrayList<IMovie> temp = system.searchMovies(this.id);
        if(!temp.isEmpty()) {
            this.movieList = temp;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean save() {
        int idNumber = persistence.createNewProductionCompanyInDatabase(this);
        if (idNumber != -1) this.id = idNumber;
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
    public boolean assignMovie(IMovie movie) {
        if (!movieList.contains(movie)) {
            ArrayList<IMovie> tempList = this.movieList;
            this.movieList.add(movie);
            if (persistence.updateProductionCompanyInDatabase(this))
                return true;
            else {
                this.movieList = tempList;
                return false;
            }
        } else
            return true;
    }

    @Override
    public boolean assignProduction(IProduction production) {
        if (!productionList.contains(production)) {
            ArrayList<IProduction> tempList = this.productionList;
            this.productionList.add(production);
            if (persistence.updateProductionCompanyInDatabase(this))
                return true;
            else {
                this.productionList = tempList;
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public boolean unassignMovie(IMovie movie) {
        if (this.movieList.contains(movie)) {
            ArrayList<IMovie> tempList = this.movieList;
            this.movieList.remove(movie);
            if (persistence.updateProductionCompanyInDatabase(this))
                return true;
            else {
                this.movieList = tempList;
                return false;
            }

        } else
            return false;
    }

    @Override
    public boolean unassignProduction(IProduction production) {
        if (this.productionList.contains(production)) {
            //Saves the list so it can revert back if anything goes wrong.
            ArrayList<IProduction> tempList = this.productionList;
            this.movieList.remove(production);
            if (persistence.updateProductionCompanyInDatabase(this))
                return true;
            else {
                //Reverts the changes because something went wrong
                this.productionList = tempList;
                return false;
            }

        } else
            return false;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public ArrayList<IProduction> getProductionList() {
        return this.productionList;
    }

    @Override
    public ArrayList<IMovie> getMovieList() {
        return this.movieList;
    }
}
