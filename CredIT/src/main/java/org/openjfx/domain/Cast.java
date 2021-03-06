package org.openjfx.domain;

import org.openjfx.interfaces.ICast;
import org.openjfx.interfaces.IPersistence;

import java.util.ArrayList;

public class Cast implements ICast {
    private final IPersistence persistence = CredITSystem.getPersistence();
    private final CredITSystem system = CredITSystem.getInstance();
    private int id;
    private String name;
    private String regDKID;

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
        if (persistence.mergeCastInDatabase(this, cast)) {
            ArrayList<Cast> listCast = system.searchCast(this.id);
            if (listCast != null && !listCast.isEmpty()) {
                Cast newCast = listCast.get(0);
                this.name = newCast.getName();
                this.regDKID = newCast.getRegDKID();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean update(String name, String regDKID) {
        //Saving the current data in case of a failure to save to database, so the object can revert back.
        String tempName = this.name;
        String tempReg = this.regDKID;
        //assigning the new values
        this.name = name;
        this.regDKID = regDKID;
        if(persistence.updateCastInDatabase(this)){
            return true;
        }else{
            //reverting back to the original value because it didnt save in the database
            this.regDKID = tempReg;
            this.name = tempName;
            return false;
        }
    }

    @Override
    public boolean save() {
        int idNumber = persistence.createNewCastInDatabase(this);
        if (idNumber != -1) {
            this.id = idNumber;
        }
        return idNumber != -1;
    }

    @Override
    public boolean delete() {
        return persistence.removeCastFromDatabase(this.id);
    }

    @Override
    public String toString() {
        return name;
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