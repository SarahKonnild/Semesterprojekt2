package org.openjfx.interfaces;

import java.util.List;

public interface IPersistence {

    public boolean createUser(IUser user);

    public boolean deleteUser(int id);

    public boolean createBroadcast(IBroadcast broadcast);

    public boolean deleteBroadcast(int id);

    public boolean createProduction(IProduction production);

    public boolean removeProduction(int id);

    public boolean createCast(ICast cast);

    public boolean removeCast(int id);

    public List<String> getBroadcast(String keyword);

    public List<String> getBroadcast(int productionId);

    public List<String> getCast(String keyword);

    public List<String> getCast(int broadcastId);

    public List<String> getProduction(String keyword);

    public boolean mergeCast(ICast cast1, ICast cast2);

    public boolean update(int id, String name, int regDKID);


}
