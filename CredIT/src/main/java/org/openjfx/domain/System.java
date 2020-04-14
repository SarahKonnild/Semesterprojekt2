package org.openjfx.domain;

import org.openjfx.interfaces.ISystem;

public class System //implements ISystem
         {
    private User user;
    private int persistenceLayer;

    public System(User user, int persistenceLayer){
        this.user = user;
        this.persistenceLayer = persistenceLayer;
    }

/*
    @Override
    public User createUser(int id, String name, String password, String username, String role) {
        return null;
    }

    @Override
    public ArrayList<Cast> searchCast(String keyword) {
        return null;
    }

    @Override
    public ArrayList<Broadcast> searchBroadcast(String keyword) {
        return null;
    }

    @Override
    public ArrayList<Production> searchProduction(String keyword) {
        return null;
    }

 */
}
