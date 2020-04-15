package org.openjfx.interfaces;

public interface ICast {
    public boolean mergeCastMembers(org.openjfx.domain.ICast cast);
    public org.openjfx.domain.ICast updateCast(String name, int regDKID);
    public boolean saveCast();
    public boolean deleteCast(org.openjfx.domain.ICast cast);
    public int getId();
    public String getName();
    public int getRegDKID();
}
