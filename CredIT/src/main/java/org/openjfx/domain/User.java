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

    public User(int id, String name, String password, String username, Role role){
        this.id = id;
        this.name = name;
        this.password = password;
        this.username = username;
        this.role = role;
    }

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
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public Role getRole() {
        return null;
    }

    @Override
    public Broadcast addNewBroadcast(Production production, String name, int seasonNumber, int episodeNumber, Date airDate) {
        return null;
    }


}
