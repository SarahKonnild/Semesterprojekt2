package org.openjfx.persistence;

import org.openjfx.interfaces.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Persistence implements IPersistence {

    private static Persistence instance;

    private Persistence(){}

    public static Persistence getInstance() {
        //If there is no instance of the Persistence class, make one
        if (instance == null) {
            instance = new Persistence();
        }

        //Return the singleton instance of Persistence
        return instance;
    }

    @Override
    public boolean createNewUserInDatabase(IUser user) throws IOException {
        return false;
    }

    @Override
    public boolean removeUserFromDatabase(int id) {
        return false;
    }

    @Override
    public int createNewBroadcastInDatabase(IBroadcast broadcast) throws IOException {
        return 0;
    }

    @Override
    public int createNewMovieInDatabase(IMovie movie) throws IOException {
        return 0;
    }

    @Override
    public int createNewProductionInDatabase(IProduction production) throws IOException {
        return 0;
    }

    @Override
    public int createNewProductionCompanyInDatabase(IProductionCompany production) throws IOException {
        return 0;
    }

    @Override
    public boolean removeBroadcastFromDatabase(int id) {
        return false;
    }

    @Override
    public boolean removeProductionFromDatabase(int id) {
        return false;
    }

    @Override
    public boolean removeCastFromDatabase(int id) {
        return false;
    }

    @Override
    public boolean removeMovieFromDatabase(int id) {
        return false;
    }

    @Override
    public boolean removeProductionCompanyFromDatabase(int id) {
        return false;
    }

    @Override
    public int createNewCastInDatabase(ICast cast) throws IOException {
        return 0;
    }

    @Override
    public List<String> getBroadcastFromDatabase(String keyword) {
        return null;
    }

    @Override
    public List<String> getBroadcastsFromDatabase(int productionId) {
        return null;
    }

    @Override
    public List<String> getBroadcastFromDatabase(int broadcastID) {
        return null;
    }

    @Override
    public List<String> getMovieFromDatabase(String keyword) {
        return null;
    }

    @Override
    public List<String> getMovieFromDatabase(int movieID) {
        return null;
    }

    @Override
    public List<String> getMoviesFromDatabase(int productionCompanyID) {
        return null;
    }

    @Override
    public List<String> getProductionFromDatabase(String keyword) {
        return null;
    }

    @Override
    public List<String> getProductionsFromDatabase(int productionCompanyID) {
        return null;
    }

    @Override
    public List<String> getProductionFromDatabase(int productionId) {
        return null;
    }

    @Override
    public List<String> getCastFromDatabase(String keyword) {
        return null;
    }

    @Override
    public List<String> getCastFromDatabase(int castID) {
        return null;
    }

    @Override
    public List<String> getProductionCompany(int id) {
        return null;
    }

    @Override
    public List<String> getProductionCompany(String keyword) {
        return null;
    }

    @Override
    public List<String> getCastRolesMoviesFromDatabase(int id) {
        return null;
    }

    @Override
    public List<String> getCastRolesBroadcastFromDatabase(int id) {
        return null;
    }

    @Override
    public String getProductionName(int id) {
        return null;
    }

    @Override
    public boolean mergeCastInDatabase(ICast cast1, ICast cast2) {
        return false;
    }

    @Override
    public boolean updateCastInDatabase(ICast cast) {
        return false;
    }

    @Override
    public boolean updateProductionCompanyInDataBase(IProductionCompany productionCompany) {
        return false;
    }

    @Override
    public boolean updateMovieInDatabase(IMovie movie) {
        return false;
    }

    @Override
    public boolean updateBroadcastInDatabase(IBroadcast broadcast) {
        return false;
    }

    @Override
    public boolean updateProduction(IProduction production) {
        return false;
    }
}
