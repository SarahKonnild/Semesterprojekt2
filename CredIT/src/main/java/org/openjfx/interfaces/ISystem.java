package org.openjfx.interfaces;

import org.openjfx.domain.Broadcast;
import org.openjfx.domain.Cast;
import org.openjfx.domain.Production;
import org.openjfx.domain.User;

import java.util.ArrayList;

public interface ISystem {
    public ArrayList<Cast> searchCast(String keyword);
    public ArrayList<Broadcast> searchBroadcast(String keyword);
    public ArrayList<Production> searchProduction(String keyword);
    public User getUser();
    public int getPersistenceLayer();
}
