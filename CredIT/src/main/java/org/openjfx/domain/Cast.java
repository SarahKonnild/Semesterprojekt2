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
        ICast tempCast = this;
        return persistence.mergeCastInDatabase(tempCast, cast);
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
        return persistence.createNewCastInDatabase(cast);
    }

    @Override
    public boolean deleteCast(ICast cast) {
        return persistence.removeCastFromDatabase(cast.getId());
    }

    public int getId() {
        return id; }

    public String getName() {
        return name; }

    public int getRegDKID() {
        return regDKID; }


}