package org.openjfx.domain;

import org.openjfx.interfaces.*;
import org.openjfx.persistence.Persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class System implements ISystem {
    private User user;
    private Persistence persistenceLayer;
    static System instance = null;

    public System(){
        this.persistenceLayer = Persistence.getInstance();
        this.instance = this;
    }

    public System(User user){
        this.user = user;
        this.persistenceLayer = Persistence.getInstance();
        this.instance = this;
    }

    /*
     * The following search methods are based on 3 different return types. Each of them have 1 overloaded method that just takes another parameter
     * Every search method makes a call to the persistencelayer that then will supply a list based on the result.
     * The search method will then call a private method in the system class, pass the list as a parameter.
     * It will then create objects based on the information in the list and return an arrayList of the objects.
     */

    //region search methods goes here


    //region cast database seach metods here
    @Override
    public ArrayList<ICast> searchCast(String keyword) {
        IUser user1 = new User(10, "name","password", "username");
        user1.addNewBroadcastToDatabase("name",10,10,"yes");

        return makeCastObjects(persistenceLayer.getCastFromDatabase(keyword));
    }

    @Override
    public ArrayList<ICast> searchCast(int broadcastId) {
        return makeCastObjects(persistenceLayer.getCastFromDatabase(broadcastId));
    }

    /**
     * This method takes the list returned from the search in the database and creates cast objects from that list.
     * @param cast
     * @return an arraylist of Cast object
     */
    private ArrayList<ICast> makeCastObjects(List<String> cast) {
        if(cast.size() > 0 ) {
            ArrayList<ICast> casts = new ArrayList();
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
    public ArrayList<IBroadcast> searchBroadcast(String keyword) {
        return makeBroadcastObjects(persistenceLayer.getBroadcastFromDatabase(keyword));
    }
    @Override
    public ArrayList<IBroadcast> searchBroadcast(int broadcastID) {
        return makeBroadcastObjects(persistenceLayer.getBroadcastFromDatabase(broadcastID));
    }

    /**
     * This method takes the list returned from the search in the database and creates broadcast objects from that list.
     * @param broadcast
     * @return an arraylist of broadcast objects
     */
    private ArrayList<IBroadcast> makeBroadcastObjects(List<String> broadcast) {
        ArrayList<IBroadcast> broadcasts = new ArrayList<>();
        ArrayList<ICast> castObjects = new ArrayList<>();
        HashMap<String, ArrayList<ICast>> castRolesMap = new HashMap<>();

        List<String> list = broadcast;
        if(list.size() > 0 ) {
            for (int i = 0; i < list.size(); i++){
                //Runs though all the elements in the recived list and splits to a String Array where each string represent a broadcast
                String[] items = list.get(i).split(",");
                if(items[2].length() > 0){
                    //Takes the 3rd element in the string array and splits it to its diffrent key. 3rd element represent the hashmap over roles and cast members
                    //The string Items looks like this = key;value:value:value_key;value:value:value_key;value:value:value_
                    String[] key = items[2].split("_");
                    for(int j = 0; j < key.length;j++){
                        //Splits each string of keys out to the values aka the role and the following
                        //a string in the key array looks like this = key;value:value:value
                        String[] pair = key[j].split(";");
                        //The pair array has 2 elements. First is key and the 2nd is value:value:value
                        String[] values = pair[1].split(":");
                        for(int k = 0; k < values.length;k++){
                            //Calls the search method for casts where is gives the cast ID.
                            castObjects = searchCast(Integer.parseInt(values[k]));
                        }
                        castRolesMap.put(pair[0],castObjects);
                    }
                }
                //Need to update this to take the hashmap instead
                broadcasts.add(new Broadcast((Integer.parseInt(items[0])), items[1], (Integer.parseInt(items[2])), (Integer.parseInt(items[3])), (items[4])));
            }
            return broadcasts;
        }
        return null;
    }

    //endregion

    //region production seach methods here
    @Override
    public ArrayList<IProduction> searchProduction(String keyword) {
        ArrayList<IProduction> productions = new ArrayList<>();
        ArrayList<IBroadcast> broadcastObjects = new ArrayList<>();

        List<String> list = persistenceLayer.getProductionFromDatabase(keyword);
        if(list.size() > 0 ) {
            for (int i = 0; i < list.size(); i++){
                String[] items = list.get(i).split(",");
                if(items[2].length() > 0){
                    String[] broadcastIds = items[2].split(";");
                    for(int j = 0; j < broadcastIds.length; j++ ){
                        broadcastObjects = searchBroadcast(Integer.parseInt(broadcastIds[j]));
                    }
                }
                productions.add(new Production(Integer.parseInt(items[0]), items[1], broadcastObjects,items[3], items[4]));
            }
            return productions;
        }
        return null;
    }

    //endregion

    //endregion

    @Override
    public IUser createNewUser(String username, String password) {
        IUser userAccount = new User(username, password);
        return userAccount;
    }

    @Override
    public IUser getUser() {

        return this.user;
    }

    @Override
    public Persistence getPersistenceLayer() {
        return this.persistenceLayer;
    }
}
