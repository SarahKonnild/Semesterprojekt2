package org.openjfx.domain;

import org.openjfx.interfaces.*;
import org.openjfx.persistence.Persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CredITSystem implements ISystem {
    static CredITSystem instance = null;
    private static Persistence persistenceLayer;
    private User user;

    /**
     * To use for creating an instance of the System class that can then be used mainly in Presentation.
     */
    public CredITSystem() {
        this.persistenceLayer = Persistence.getInstance();
        this.instance = this;
    }

    //FIXME I do not think we need this constructor.
    public CredITSystem(User user) {
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

    public static IPersistence getPersistence() {
        return persistenceLayer;
    }

    //region cast database seach metods here
    @Override
    public ArrayList<ICast> searchCast(String keyword) {
        return makeCastObjects(persistenceLayer.getCastFromDatabase(keyword));
    }

    public HashMap<ICast, String> getCastRolesMovies(int id){
        return getCastRoles(persistenceLayer.getCastRolesMoviesFromDatabase(id));
    }

    public HashMap<ICast, String> getCastRolesBroadcast(int id){
        return getCastRoles(persistenceLayer.getCastRolesBroadcastFromDatabase(id));
    }

    private HashMap<ICast, String> getCastRoles(List<String> list){
        HashMap<ICast, String> castMap = new HashMap<>();
        for(String item : list ){
            String[] temp = item.split(",");
            ICast castObj = searchCast(temp[0]).get(1);
            castMap.put(castObj, temp[1]);
        };
        return castMap;
    };

    @Override
    public ArrayList<ICast> searchCast(int castID) {
        return makeCastObjects(persistenceLayer.getCastFromDatabase(castID));
    }

    //endregion

    /**
     * This method takes the list returned from the search in the database and creates cast objects from that list.
     *
     * @param cast
     * @return an arraylist of Cast object
     */
    private ArrayList<ICast> makeCastObjects(List<String> cast) {
        if (cast.size() > 0) {
            ArrayList<ICast> casts = new ArrayList();
            for (int i = 0; i < cast.size(); i++) {
                String[] items = cast.get(i).split(",");
                casts.add(new Cast((Integer.parseInt(items[0])), items[1], ((items[2]))));
            }
            return casts;
        }
        return null;
    }

    //region broadcast database search methods here
    @Override
    public ArrayList<IBroadcast> searchBroadcast(String keyword) {
        return makeBroadcastObjects(persistenceLayer.getBroadcastFromDatabase(keyword));
    }

    @Override
    public ArrayList<IBroadcast> searchBroadcast(int productionID) {
        return makeBroadcastObjects(persistenceLayer.getBroadcastFromDatabase((productionID)));
    }

    //endregion

    /**
     * This method takes the list returned from the search in the database and creates broadcast objects from that list.
     *
     * @param broadcast A String list over all the broadcast gotten from the search methods.
     * @return an arraylist of broadcast objects
     */
    private ArrayList<IBroadcast> makeBroadcastObjects(List<String> broadcast) {
        ArrayList<IBroadcast> broadcasts = new ArrayList<>();
        ArrayList<ICast> castObjects = new ArrayList<>();
        HashMap<String, ArrayList<ICast>> castRolesMap = new HashMap<>();
        //TODO Take a decision on whatever we use a hashmap with the ICast as the key and a single string with all the roles for that cast member.

        List<String> list = broadcast;
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                //Runs though all the elements in the received list and splits to a String Array where each string represent a broadcast
                String[] items = list.get(i).split(",");
                if (items[2].length() > 0) {
                    //Takes the 3rd element in the string array and splits it to its different key. 3rd element represent the hashmap over roles and cast members
                    //The string Items looks like this = key;value:value:value_key;value:value:value_key;value:value:value_
                    String[] key = items[2].split("_");
                }
                //Need to update this to take the hashmap instead
                broadcasts.add(new Broadcast(
                        Integer.parseInt(items[0]),
                        items[1],
                        Integer.parseInt(items[3]),
                        Integer.parseInt(items[4]),
                        items[5],
                        Integer.parseInt(items[6])));
            }
            return broadcasts;
        }
        return null;
    }

    //endregion

    //endregion

    //region production search methods here

    public IProduction searchProduction(int id){
        List<String> list = persistenceLayer.getProductionFromDatabase(id);
        IProduction production = searchProduction(list).get(1);
        return production;
    };
    @Override
    public ArrayList<IProduction> searchProduction(String keyword){
        return searchProduction(persistenceLayer.getProductionFromDatabase(keyword));
    }

    @Override
    public ArrayList<IMovie> searchMovie(String keyword) {
        return null;
    }

    ;

    private ArrayList<IProduction> searchProduction(List<String> list) {
        ArrayList<IProduction> productions = new ArrayList<>();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String[] items = list.get(i).split(",");
                productions.add(new Production(Integer.parseInt(items[0]), items[1], Integer.parseInt(items[3]), items[4]));
            }
            return productions;
        }
        return null;
    }

    public IProductionCompany searchProductionCompany(int id){return null;};

    @Override
    public IProductionCompany searchProductionCompany(String keyword){return null;};

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
