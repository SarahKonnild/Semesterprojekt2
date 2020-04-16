package org.openjfx.interfaces;

import org.openjfx.domain.Cast;

public interface ICast {
    public boolean mergeCastMembers(ICast cast);
    public Cast updateCast(String name, int regDKID);
    public boolean saveCast();
    public boolean deleteCast();
    public int getId();
    public String getName();
    public int getRegDKID();
}
