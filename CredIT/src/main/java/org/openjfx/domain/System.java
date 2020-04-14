package org.openjfx.domain;

import org.openjfx.interfaces.ISystem;
import org.openjfx.interfaces.Role;

import java.util.ArrayList;

public class System implements ISystem
         {
    private User user;
    private persistenceLayer;

    public System(User user, int persistenceLayer){
        this.user = user;
        this.persistenceLayer = persistenceLayer;
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

             @Override
             public User getUser() {
                 return null;
             }

             @Override
             public int getPersistenceLayer() {
                 return 0;
             }
         }
