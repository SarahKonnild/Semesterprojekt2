package org.openjfx.domain;

import org.openjfx.interfaces.IUser;
import org.openjfx.interfaces.Role;

import java.util.Date;

public class User implements IUser{
    private int id;
    private String name;
    private String password;
    private String username;
    private Role role;

    public User(int id, String name, String password, String username){
        this.id = id;
        this.name = name;
        this.password = password;
        this.username = username;
        this.role = role;
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
