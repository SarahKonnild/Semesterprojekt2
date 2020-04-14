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
    static System instance = null;

    public System(User user){
        this.user = user;
        this.persistenceLayer = Persistence.getInstance();
        this.instance = this;
    }

    //region search methods goes here

    //region cast database seach metods here
    @Override
    public ArrayList<Cast> searchCast(String keyword) {
        return makeCastObjects(persistenceLayer.getCast(keyword));
    }

    @Override
    public ArrayList<Cast> searchCast(int broadcastId) {
        return makeCastObjects(persistenceLayer.getCast(broadcastId));
    }

    private ArrayList<Cast> makeCastObjects(List<String> cast) {
        ArrayList<Cast> casts = new ArrayList();
        List<String> list = cast;
        if(list.size() > 0 ) {
            for (int i = 0; i < list.size(); i++){
                String[] items = list.get(i).split(",");
                casts.add(new Cast((Integer.parseInt(items[0])), items[1], (Integer.parseInt(items[2]))));
            }
            return casts;
        }
        return null;
    }

    //endregion

    //region broadcast database search methods here
    @Override
    public ArrayList<Broadcast> searchBroadcast(String keyword) {
        return makeBroadcastObjects(persistenceLayer.getBroadcast(keyword));
    }
    @Override
    public ArrayList<Broadcast> searchBroadcast(int productionID) {
        return makeBroadcastObjects(persistenceLayer.getBroadcast(productionID));
    }
    private ArrayList<Broadcast> makeBroadcastObjects(List<String> broadcast) {
        ArrayList<Broadcast> broadcasts = new ArrayList();
        List<String> list = broadcast;
        if(list.size() > 0 ) {
            for (int i = 0; i < list.size(); i++){
                String[] items = list.get(i).split(",");
                broadcasts.add(new Broadcast((Integer.parseInt(items[0])), items[1], (Integer.parseInt(items[2])), (Integer.parseInt(items[3])), (items[4])));
            }
            return broadcasts;
        }
        return null;
    }

    //endregion

    //region production seach methods here
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

    //endregion

    //endregion

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public Persistence getPersistenceLayer() {
        return this.persistenceLayer;
             }
         }
