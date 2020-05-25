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
    public String toString() {
        return this.name;
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
