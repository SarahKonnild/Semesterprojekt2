package org.openjfx.domain;

import org.openjfx.interfaces.IUser;
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

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.role = Role.SYSADMIN;
    }

    @Override
    public boolean addNewCastToDatabase(String name, int regDKID) {
        Cast cast = new Cast(name, regDKID);
        cast.saveCast();
        return true;
    }
    @Override
    public boolean addNewBroadcastToDatabase(String name, int seasonNumber, int episodeNumber, String airDate) {
        Broadcast broadcast = new Broadcast(name, seasonNumber, episodeNumber, airDate);

        broadcast.saveBroadcast();
        return true;
    }
    @Override
    public boolean addNewProductionToDatabase(String name, String year, String productionCompany) {
        Production production = new Production(name, year, productionCompany);
        production.saveProduction();
        return true;
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
