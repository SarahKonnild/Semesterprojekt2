package org.openjfx.domain;

import org.openjfx.interfaces.IUser;

import java.util.Date;

public class User implements IUser{
    private int id;
    private String name;
    private String password;
    private String username;
    private Role role;

    private enum Role{
        SYSADMIN, NETWORK, PRODUCER;
    }

    public User(int id, String name, String password, String username, Role role){
        this.id = id;
        this.name = name;
        this.password = password;
        this.username = username;
        this.role = role;
    }
    /*
    @Override
    public Cast addNewCast(String name, int regDKID) {
        return null;
    }

    @Override
    public boolean checkRole(String role) {
        return false;
    }

    @Override
    public Production addNewProduction(String name, Date year, String productionCompany) {
        return null;
    }

    @Override
    public Broadcast addNewBroadcast(Production production, String name, int seasonNumber, int episodeNumber, Date airDate) {
        return null;
    }

 */
}
