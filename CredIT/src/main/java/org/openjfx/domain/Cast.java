package org.openjfx.domain;

import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IPersistence;

import java.io.IOException;

public class Cast implements ICast {
    private final IPersistence persistence = CredITSystem.getPersistence();
    private final CredITSystem system = CredITSystem.getInstance();
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
    public Cast(int id, String regDKID, String name) {
        this.id = id;
        this.name = name;
        this.regDKID = regDKID;
    }

    @Override
    public boolean mergeCastMembers(ICast cast) {
        //Todo gotta figure out how to update this instance with the new data after the merge. Maybe the method should return a list of strings?
        return persistence.mergeCastInDatabase(this, cast);
    }

    @Override
    public boolean update(String name, String regDKID) {
        //Saving the current data in case of a failure to save to database, so the object can revert back.
        String tempName = this.name;
        String tempReg = this.regDKID;
        this.name = name;
        this.regDKID = regDKID;
        if(persistence.updateCastInDatabase(this))
            return true;
        else{
            this.name = tempName;
            this.regDKID = tempReg;
            return false;
        }
    }

    @Override
    public boolean save() {
        int idNumber = persistence.createNewCastInDatabase(this);
        if(idNumber != -1) this.id = idNumber;
        return (idNumber == -1) ? false : true;
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