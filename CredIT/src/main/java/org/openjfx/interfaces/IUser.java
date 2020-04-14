package org.openjfx.interfaces;

import org.openjfx.domain.Broadcast;
import org.openjfx.domain.Cast;
import org.openjfx.domain.Production;

import java.util.Date;

public interface IUser {
    public boolean addNewCastToDatabase(String name, int regDKID);
    public boolean addNewBroadcastToDatabase(String name, int seasonNumber, int episodeNumber, String airDate);
    public boolean addNewProductionToDatabase(String name, String year, String productionCompany);
    public boolean checkRole(String role);
    public int getId();
    public String getName();
    public String getPassword();
    public String getUsername();
    public Role getRole();
}
