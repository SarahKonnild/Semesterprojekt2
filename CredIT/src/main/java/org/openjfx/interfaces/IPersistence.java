package org.openjfx.interfaces;

import java.util.List;

public interface IPersistence {

    public boolean createNewUserInDatabase(IUser user);

    public boolean removeUserFromDatabase(int id);

    public boolean createNewBroadcastInDatabase(IBroadcast broadcast);

    public boolean removeBroadcastFromDatabase(int id);

    public boolean createNewProductionInDatabase(IProduction production);

    public boolean removeProductionFromDatabase(int id);

    public boolean createNewCastInDatabase(ICast cast);

    public boolean removeCastFromDatabase(int id);

    public List<String> getBroadcastFromDatabase(String keyword);

    public List<String> getBroadcastFromDatabase(int productionId);

    public List<String> getCastFromDatabase(String keyword);

    public List<String> getCastFromDatabase(int broadcastId);

    public List<String> getProductionFromDatabase(String keyword);

    public boolean mergeCastInDatabase(ICast cast1, ICast cast2);

    public boolean updateCastInDatabase(int id, String name, int regDKID);


}
