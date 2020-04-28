package org.openjfx.domain;

import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IPersistence;

import java.io.IOException;

public class Cast implements ICast {
    private final IPersistence persistence = CredITSystem.getPersistence();
    private int id;
    private String name;
    private String regDKID;
    private String role;

    /**
     * Constructor used for first time creation of a new cast member that needs to be saved to the database
     *
     * @param name    The name of the cast member
     * @param regDKID The Registerings Danmark ID that the cast member has
     */
    public Cast(String name, String regDKID) {
        this.name = name;
        this.regDKID = regDKID;
    }

    /**
     * Constructor used when creating instances of <code>Cast</code> based on data from the persistence layer
     *
     * @param id      The ID that the cast member has in the database
     * @param name    The name of the cast member
     * @param regDKID The Registerings Danmark ID that the cast member has
     */
    public Cast(int id, String name, String regDKID) {
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
    public boolean update(String name, String regDKID) {
        this.name = name;
        this.regDKID = regDKID;
        persistence.updateCastInDatabase(this);
        return true;
    }

    @Override
    public boolean save() {
        int idNumber = -1;
        try {
            idNumber = CredITSystem.instance.getPersistenceLayer().createNewCastInDatabase(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (idNumber != -1) {
            this.id = idNumber;
            return true;
        } else
            return false;
    }

    @Override
    public boolean delete() {
        return persistence.removeCastFromDatabase(this.id);
    }

    @Override
    public String toString() {

        return name;
    }

    @Override
    public String getRole() {
        return this.role;
    }

    @Override
    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRegDKID() {
        return regDKID;
    }


}