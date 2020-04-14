package org.openjfx.domain;

import org.openjfx.interfaces.ICast;
public class Cast implements ICast {
    private int id;
    private String name;
    private int regDKID;

    public Cast(int id, String name, int regDKID) {
            this.id = id;
            this.name = name;
            this.regDKID = regDKID;
    }

    @Override
    public boolean mergeCastMember(Cast cast) {
            throw new UnsupportedOperationException();
    }

    @Override
    public Cast updateCast(String name, int regDKID) {
            this.name = name;
            this.regDKID = regDKID;
            return this;
    }

    @Override
    public boolean saveCast(Cast cast) {
            throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteCast(Cast cast) {
            throw new UnsupportedOperationException();
    }
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getRegDKID() { return regDKID; }

    public void setRegDKID(int regDKID) { this.regDKID = regDKID; }

}