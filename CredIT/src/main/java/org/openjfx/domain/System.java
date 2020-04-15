package org.openjfx.domain;

import org.openjfx.interfaces.*;
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

    /*
     * The following search methods are based on 3 different return types. Each of them have 1 overloaded method that just takes another parameter
     * Every seach method makes a call to the persistencelayer that then will supply a list based on the result.
     * The search method will then call a private method in the system class, pass the list as a parameter.
     * It will then create objects based on the information in the list and return an arrayList of the objects.
     */

    //region search methods goes here


    //region cast database seach metods here
    @Override
    public ArrayList<Cast> searchCast(String keyword) {
        IUser user1 = new User(10, "name","password", "username");
        user1.addNewBroadcastToDatabase("name",10,10,"yes");

        return makeCastObjects(persistenceLayer.getCast(keyword));
    }

    @Override
    public ArrayList<Cast> searchCast(int broadcastId) {
        return makeCastObjects(persistenceLayer.getCast(broadcastId));
    }

    /**
     * This method takes the list returned from the search in the database and creates cast objects from that list.
     * @param cast
     * @return an arraylist of Cast object
     */
    private ArrayList<Cast> makeCastObjects(List<String> cast) {
        if(cast.size() > 0 ) {
            ArrayList<Cast> casts = new ArrayList();
            for (int i = 0; i < cast.size(); i++){
                String[] items = cast.get(i).split(",");
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

    /**
     * This method takes the list returned from the search in the database and creates broadcast objects from that list.
     * @param broadcast
     * @return an arraylist of broadcast objects
     */
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
