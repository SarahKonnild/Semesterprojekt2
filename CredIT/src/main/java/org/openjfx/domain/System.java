package org.openjfx.domain;

import org.openjfx.interfaces.ISystem;
import org.openjfx.interfaces.Role;
import org.openjfx.interfaces.IPersistence;
import org.openjfx.persistence.Persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class System implements ISystem {
    private User user;
    private Persistence persistenceLayer;

    public System(User user){
        this.user = user;
        this.persistenceLayer = Persistence.getInstance();
    }

    @Override
    public boolean addNewCastToDatabase(String name, int regDKID) {
        Cast cast = new Cast(name, regDKID);
        return false;
    }

    @Override
    public boolean addNewBroadcastToDatabase(String name, int seasonNumber, int episodeNumber, String airDate) {
        Broadcast broadcast = new Broadcast(name, seasonNumber, episodeNumber, airDate);
        broadcast.saveBroadcast();
        return true;
    }

             @Override
    public boolean addNewProductionToDatabase(String name, String year, String productionCompany) {
        Production production = new Production(name, year, productionCompany);
        production.saveProduction();
        return true;
    }

    @Override
    public ArrayList<Cast> searchCast(String keyword) {
        ArrayList<Cast> casts = new ArrayList();
        List<String> list = persistenceLayer.getCast(keyword);
        if(list.size() > 0 ) {
            for (int i = 0; i < list.size(); i++){
                String[] items = list.get(i).split(",");
                casts.add(new Cast((Integer.parseInt(items[0])), items[1], (Integer.parseInt(items[2]))));
            }
            return casts;
        }
        return null;
    }

    @Override
    public ArrayList<Broadcast> searchBroadcast(String keyword) {
        ArrayList<Broadcast> broadcasts = new ArrayList();
        List<String> list = persistenceLayer.getBroadcast(keyword);
        if(list.size() > 0 ) {
            for (int i = 0; i < list.size(); i++){
                String[] items = list.get(i).split(",");
                broadcasts.add(new Broadcast((Integer.parseInt(items[0])), items[1], (Integer.parseInt(items[2])), (Integer.parseInt(items[3])), (items[4])));
            }
            return broadcasts;
        }
        return null;
    }

    @Override
    public ArrayList<Production> searchProduction(String keyword) {
        ArrayList<Production> productions = new ArrayList();
        List<String> list = persistenceLayer.getProduction(keyword);
        if(list.size() > 0 ) {
            for (int i = 0; i < list.size(); i++){
                String[] items = list.get(i).split(",");
                productions.add(new Production(Integer.parseInt(items[0]), items[1], items[2],items[3]));
            }
            return productions;
        }
        return null;
    }
    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public Persistence getPersistenceLayer() {
        return this.persistenceLayer;
             }
         }
