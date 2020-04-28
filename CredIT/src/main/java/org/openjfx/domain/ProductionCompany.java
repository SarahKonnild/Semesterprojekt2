package org.openjfx.domain;

import org.openjfx.interfaces.IMovie;
import org.openjfx.interfaces.IProduction;
import org.openjfx.interfaces.IProductionCompany;

import java.io.IOException;
import java.util.ArrayList;

public class ProductionCompany implements IProductionCompany {

    private int id;
    private String name;
    private ArrayList<IProduction> productionList;
    private ArrayList<IMovie> movieList;

    ProductionCompany(String name){
        this.name = name;
    }

    ProductionCompany(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean save() {
        int idNumber = -1;
        try {
            idNumber = CredITSystem.instance.getPersistenceLayer().createNewProductionCompanyInDatabase(this);
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
    public boolean update(String name) {
        return false;
    }

    @Override
    public boolean assignMovie(IMovie movie) {
        return false;
    }

    @Override
    public boolean assignProduction(IProduction production) {
        return false;
    }

    @Override
    public boolean unassignMovie(IMovie movie) {
        return false;
    }

    @Override
    public boolean unassignProduction(IProduction production) {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public ArrayList<IProduction> getProductionList() {
        return null;
    }

    @Override
    public ArrayList<IMovie> getMovieList() {
        return null;
    }
}
