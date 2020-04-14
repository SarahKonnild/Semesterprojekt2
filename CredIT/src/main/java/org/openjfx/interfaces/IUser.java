package org.openjfx.interfaces;

import org.openjfx.domain.Broadcast;
import org.openjfx.domain.Cast;
import org.openjfx.domain.Production;

import java.util.Date;

public interface IUser {
    public Cast addNewCast(String name, int regDKID);
    public boolean checkRole(String role);
    public Production addNewProduction(String name, Date year, String productionCompany);
    public int getId();
    public String getName();
    public String getPassword();
    public String getUsername();
    public Role getRole();
    public Broadcast addNewBroadcast(Production production, String name, int seasonNumber, int episodeNumber, Date airDate);
}
