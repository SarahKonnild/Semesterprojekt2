package org.openjfx.domain;

import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IPersistence;
import org.openjfx.persistence.Persistence;

public class Cast implements ICast {
    private final IPersistence persistence = Persistence.getInstance();
    private int id;
    private String name;
    private int regDKID;

    public Cast(int id, String name, int regDKID) {
            this.id = id;
            this.name = name;
            this.regDKID = regDKID;
    }

    @Override
    public boolean mergeCastMembers(ICast cast) {
        persistence.mergeCastInDatabase(this, cast);
        return persistence.mergeCastInDatabase(this, cast);
    }

    @Override
    public ICast updateCast(String name, int regDKID) {
        this.name = name;
        this.regDKID = regDKID;
        persistence.updateCastInDatabase(this.id, name, regDKID);
        return this;
    }

    @Override
    public boolean saveCast(ICast cast) {
        persistence.createNewCastInDatabase(cast);
        return persistence.createNewCastInDatabase(cast);
    }

    @Override
    public boolean deleteCast(ICast cast) {
        persistence.removeCastFromDatabase(cast.getId());
        return persistence.removeCastFromDatabase(cast.getId());
    }
    public int getId() {
        return id; }

    public void setId(int id) {
        this.id = id; }

    public String getName() {
        return name; }

    public void setName(String name) {
        this.name = name; }

    public int getRegDKID() {
        return regDKID; }

    public void setRegDKID(int regDKID) {
        this.regDKID = regDKID; }

}