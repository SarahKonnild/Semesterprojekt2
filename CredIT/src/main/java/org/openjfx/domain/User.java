package org.openjfx.domain;

import org.openjfx.interfaces.*;
import org.openjfx.interfaces.Role;

public class User implements IUser{
    private int id;
    private String name;
    private String password;
    private String username;
    private Role role;

    /**
     * Construtor used to create a <code>User</code> account after login
     * @param id The ID that the user have in the database.
     * @param name The personal name of the user
     * @param password The password to the user account
     * @param username The name of the user account.
     */
    public User(int id, String name, String password, String username){
        this.id = id;
        this.name = name;
        this.password = password;
        this.username = username;
        this.role = role;
    }

    /**
     * This constructor makes a <code>User</code> object that should be created when a user login.
     * @param username
     * @param password
     */
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.role = Role.SYSADMIN;
    }

    @Override
    public ICast addNewCastToDatabase(String name, int regDKID) {
        ICast cast = new Cast(name, regDKID);
        cast.saveCast();
        return cast;
    }
    @Override
    public IBroadcast addNewBroadcastToDatabase(String name, int seasonNumber, int episodeNumber, String airDate) {
        IBroadcast broadcast = new Broadcast(name, seasonNumber, episodeNumber, airDate);
        broadcast.saveBroadcast();
        return broadcast;
    }
    @Override
    public IProduction addNewProductionToDatabase(String name, String year, String productionCompany) {
        IProduction production = new Production(name, year, productionCompany);
        production.saveProduction();
        return production;
    }

    @Override
    public boolean checkRole(String roleKey)
    {
        //valueOf checks if the inputted role is equal to the role of this user. Is case senitive.
        if(Role.valueOf(roleKey) == this.role){
            return true;
        } else return false;
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
