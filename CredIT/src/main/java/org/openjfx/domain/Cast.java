package org.openjfx.domain;

import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IPersistence;
import org.openjfx.persistence.Persistence;

import java.io.IOException;

public class Cast implements ICast {
    private final IPersistence persistence = Persistence.getInstance();
    private int id;
    private String name;
    private int regDKID;
    private String role;

    public Cast(String name, int regDKID) {
        this.name = name;
        this.regDKID = regDKID;
    }

    public Cast(int id, String name, int regDKID) {
            this.id = id;
            this.name = name;
            this.regDKID = regDKID;
    }


    @Override
    public boolean mergeCastMembers(ICast cast) {
        ICast tempCast = this;
        return persistence.mergeCastInDatabase(tempCast, cast);
    }

    @Override
    public boolean updateCast(String name, int regDKID) {
        this.name = name;
        this.regDKID = regDKID;
        persistence.updateCastInDatabase(this.id, name, regDKID);
        return true;
    }

    @Override
    public boolean saveCast() {
        try {
            return persistence.createNewCastInDatabase(this);
        } catch(IOException ex){
            ex.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean deleteCast() {
            return persistence.removeCastFromDatabase(this.id);
    }

    public void setRole(String role){
        this.role = role;
    }
    public String getRole(){
        return this.role;
    }

    public int getId() {
        return id; }

    public String getName() {
        return name; }

    public int getRegDKID() {
        return regDKID; }


}