package org.openjfx.persistence;

import org.openjfx.interfaces.*;

import java.io.IOException;
import java.util.List;
import java.sql.*;
import java.util.Scanner;

public class PersistenceTwo implements IPersistence {

    private static PersistenceTwo persistence;
    private String url = "localhost";
    private int port = 5432;
    private String databaseName = "creditdb";
    private String username = "postgres";
    private String password;
    private Connection connection = null;
    private Scanner input;

    public static PersistenceTwo getInstance(){
        if(persistence == null){
            persistence = new PersistenceTwo();
        }
        return persistence;
    };


    private PersistenceTwo(){
        getPassword();
        initializePostgresqlDatabase();
    }

    private void initializePostgresqlDatabase() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://" + url + ":" + port + "/" + databaseName, username, password);
        } catch (SQLException | IllegalArgumentException ex) {
            System.out.println("Sorry... Something is wrong with your password.");
        } finally {
            if (connection == null) {
                getPassword();
                initializePostgresqlDatabase();
            }

        }
    }
    private void getPassword(){
        input = new Scanner(System.in);
        System.out.println("Please enter your PostgresDB password");
        password = input.nextLine();
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
    public List<String> getBroadcastFromDatabase(int productionId) {
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
    public List<String> getCastFromDatabase(String keyword) {
        return null;
    }

    @Override
    public List<String> getCastFromDatabase(int id) {
        return null;
    }

    @Override
    public List<String> getProductionCompany(int id) {
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
    public List<String> getCastRolesFromDatabase(int id) {
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
    public List<String> getProductionFromDatabase(int id) {
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
