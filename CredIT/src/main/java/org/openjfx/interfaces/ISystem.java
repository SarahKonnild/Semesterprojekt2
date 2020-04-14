package org.openjfx.interfaces;

import org.openjfx.domain.Broadcast;
import org.openjfx.domain.Cast;
import org.openjfx.domain.Production;
import org.openjfx.domain.User;
import org.openjfx.persistence.Persistence;

import java.util.ArrayList;

public interface ISystem {
    public ArrayList<Cast> searchCast(String keyword);
    public ArrayList<Cast> searchCast(int broadcastId);
    public ArrayList<Broadcast> searchBroadcast(String keyword);
    public ArrayList<Broadcast> searchBroadcast(int productionId);
    public ArrayList<Production> searchProduction(String keyword);
    public User getUser();
    public Persistence getPersistenceLayer();
}
