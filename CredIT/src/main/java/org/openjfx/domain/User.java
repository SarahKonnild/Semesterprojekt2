package org.openjfx.domain;

import org.openjfx.interfaces.*;

public class User implements IUser {
    private final ISystem system = CredITSystem.getInstance();
    private int id;
    private String name;
    private final String password;
    private final String username;
    private Role role;

    /**
     * Construtor used to create a <code>User</code> account after login
     *
     * @param id       The ID that the user have in the database.
     * @param name     The personal name of the user
     * @param password The password to the user account
     * @param username The name of the user account.
     */
    public User(int id, String name, String password, String username) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.username = username;
        this.role = role;
    }

    /**
     * This constructor makes a <code>User</code> object that should be created when a user login.
     *
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = Role.SYSADMIN;
    }

    @Override
    public ICast addNewCastToDatabase(String name, String regDKID) {
        return system.addNewCastToDatabase(name, regDKID);
    }

    @Override
    public IBroadcast addNewBroadcastToDatabase(String name, int seasonNumber, int episodeNumber, String airDate, int productionID) {
        return system.addNewBroadcastToDatabase(name, seasonNumber, episodeNumber, airDate, productionID);
    }

    @Override
    public IProduction addNewProductionToDatabase(String name, String year, int productionCompanyID) {
        return system.addNewProductionToDatabase(name, year, productionCompanyID);
    }

    @Override
    public IMovie addNewMovieToDatabase(String name, int productionCompanyID, String releasedate) {
        return system.addNewMovieToDatabase(name, productionCompanyID, releasedate);
    }

    @Override
    public IProductionCompany addNewProductionCompanyToDatabase(String name) {
        return system.addNewProductionCompanyToDatabase(name);
    }

    @Override
    public IProductionCompany getProductionCompanyFromDatabase(String name) {
        return CredITSystem.getInstance().searchProductionCompany(name).get(0);
    }
    @Override
    public boolean checkRole(String roleKey) {
        //valueOf checks if the inputted role is equal to the role of this user. Is case senitive.
        return Role.valueOf(roleKey) == this.role;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public Role getRole() {
        return this.role;
    }


}
