package org.openjfx.interfaces;
<<<<<<< Updated upstream

public interface ICast {
    //public boolean mergeCastMembers(Cast cast);
    // public Cast updateCast(String name, int regDKID);
    // public boolean saveCast(Cast cast);
    // public boolean deleteCast(Cast cast);
    public int getId();
    public String getName();
    public int getRegDKID();
=======

import org.openjfx.domain.Cast;

public interface ICast {
    boolean mergeCastMember(Cast cast);
    Cast updateCast(String name, int regDKID);
    boolean saveCast(Cast cast);
    boolean deleteCast(Cast cast);
>>>>>>> Stashed changes
}
