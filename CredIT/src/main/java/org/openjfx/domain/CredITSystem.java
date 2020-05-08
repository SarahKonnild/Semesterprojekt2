package org.openjfx.domain;

import org.openjfx.interfaces.*;
import org.openjfx.persistence.PersistenceTwo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CredITSystem implements ISystem {
    static CredITSystem instance = null;
    private static PersistenceTwo persistenceLayer;
    private static CredITSystem instance = null;
    private static Persistence persistenceLayer;
    private User user;

    /**
     * To use for creating an instance of the System class that can then be used mainly in Presentation.
     */
    public CredITSystem() {
        this.persistenceLayer = PersistenceTwo.getInstance();
        this.instance = this;
        persistenceLayer = Persistence.getInstance();
        instance = this;
    }

    //FIXME I do not think we need this constructor.
    public CredITSystem(User user) {
        this.user = user;
        this.persistenceLayer = PersistenceTwo.getInstance();
        this.instance = this;
        persistenceLayer = Persistence.getInstance();
        instance = this;
    }

    /*
     * The following search methods are based on 3 different return types. Each of them have 1 overloaded method that just takes another parameter
     * Every search method makes a call to the persistencelayer that then will supply a list based on the result.
     * The search method will then call a private method in the system class, pass the list as a parameter.
     * It will then create objects based on the information in the list and return an arrayList of the objects.
     */


    public static IPersistence getPersistence() {
        return persistenceLayer;
    }

    public static CredITSystem getInstance() {
        return instance;
    }

    //region cast metods
    @Override
    public ArrayList<ICast> searchCast(String keyword) {
        return makeCastObjects(persistenceLayer.getCastFromDatabase(keyword));
    }

    @Override
    public ArrayList<ICast> searchCast(int castID) {
        return makeCastObjects(persistenceLayer.getCastFromDatabase(castID));
    }

    /**
     * This method takes the list returned from the search in the database and creates cast objects from that list.
     *
     * @param list
     * @return an arraylist of Cast object
     */
    private ArrayList<ICast> makeCastObjects(List<String> list) {
        if (list.size() > 0) {
            ArrayList<ICast> casts = new ArrayList();
            //The list is formatted as castID, castName, castRegID
            for (String item : list) {
                String[] items = item.split(",");
                //Creating a new Cast object for each String item in the list.
                casts.add(new Cast((Integer.parseInt(items[0])), items[1], ((items[2]))));
            }
            return casts;
        } else
            return null;
    }



    //endregion cast search methods ends here

    //region castRoleMaps methods
    public HashMap<ICast, String> getCastRolesMovies(int id) {
        List<String> tempList = persistenceLayer.getCastRolesMoviesFromDatabase(id);
        return makeCastRoleMap(tempList);
    }

    public HashMap<ICast, String> getCastRolesBroadcast(int id) {
        List<String> tempList = persistenceLayer.getCastRolesBroadcastFromDatabase(id);
        return makeCastRoleMap(tempList);
    }
    private HashMap<ICast, String> makeCastRoleMap(List<String> list) {
        HashMap<ICast, String> castMap = new HashMap<>();
        // Gets a list of string formatted as castID, role
        for (String item : list) {
            //For each item in the list, the string gets split so the ID can be used to search for cast members
            String[] tempArray = item.split(",");
            int tempCastID = Integer.parseInt(tempArray[0]);
            String castRole = tempArray[1];

            ICast castObj = searchCast(tempCastID).get(0);

            castMap.put(castObj, castRole);
        }
        return castMap;
    }

    //endregion castRoleMaps ends here

    //region broadcast methods
    @Override
    public ArrayList<IBroadcast> searchBroadcast(String keyword) {
        return makeBroadcastObjects(persistenceLayer.getBroadcastFromDatabase(keyword));
    }

    @Override
    public ArrayList<IBroadcast> searchBroadcast(int productionID) {
        return makeBroadcastObjects(persistenceLayer.getBroadcastFromDatabase((productionID)));
    }

    /**
     * This method takes the list returned from the search in the database and creates broadcast objects from that list.
     *
     * @param list A String list over all the broadcast gotten from the search methods.
     * @return an arraylist of broadcast objects
     */
    private ArrayList<IBroadcast> makeBroadcastObjects(List<String> list) {
        ArrayList<IBroadcast> broadcasts = new ArrayList<>();
        if (list.size() > 0) {
            // Each string is formatted as id,name,seasonNumber,episodeNumber,airDate,productionID
            for (String item : list) {
                String[] items = item.split(",");

                broadcasts.add(new Broadcast(
                        Integer.parseInt(items[0]),
                        items[1],
                        Integer.parseInt(items[2]),
                        Integer.parseInt(items[3]),
                        items[4],
                        Integer.parseInt(items[5])));
            }
            return broadcasts;
        } else
            return null;
    }

    //endregion Broadcast methods ends here

    //region production methods

    public IProduction searchProduction(int productionID) {
        List<String> list = persistenceLayer.getProductionFromDatabase(productionID);
        return makeProductionObjects(list).get(0);
    }

    public ArrayList<IProduction> searchProductions(int productionCopmpanyID) {
        List<String> list = persistenceLayer.getProductionFromDatabase(productionCopmpanyID);
        return makeProductionObjects(list);
    }

    @Override
    public ArrayList<IProduction> searchProduction(String keyword) {
        return makeProductionObjects(persistenceLayer.getProductionFromDatabase(keyword));
    }

    private ArrayList<IProduction> makeProductionObjects(List<String> list) {
        ArrayList<IProduction> productions = new ArrayList<>();
        if (list.size() > 0) {
            //String is formatted as id,name,productionCompanyID,year
            for(String item : list){
                String[] items = item.split(",");
                productions.add(new Production(
                        Integer.parseInt(items[0]),
                        items[1],
                        Integer.parseInt(items[3]),
                        items[4]));
            }
            return productions;
        }else
            return null;
    }
    //endregion production methods ends here

    //region movie methods
    @Override
    public ArrayList<IMovie> searchMovie(String keyword) {
        List<String> tempList = persistenceLayer.getMovieFromDatabase(keyword);
        return makeMovieObjects(tempList);
    }

    @Override
    public ArrayList<IMovie> searchMovies(int productionCompanyID) {
        List<String> tempList = persistenceLayer.getMoviesFromDatabase(productionCompanyID);
        return makeMovieObjects(tempList);
    }

    private ArrayList<IMovie> makeMovieObjects(List<String> list){
        if(list.size()>0){
            ArrayList<IMovie> movies = null;
            //the string is formatted as id,title,releaseDate,productionCompanyID
            for(String item : list){
                String[] items = item.split(",");
                movies.add(new Movie(
                        Integer.parseInt(items[0]),
                        items[1],
                        items[2],
                        Integer.parseInt(items[3])));
            }
            return movies;
        }else
            return null;
    }

    //endregion search movie methods ends here

    //region productionCompnay methods
    public IProductionCompany searchProductionCompany(int productionCompanyID) {
        List<String> tempList = persistenceLayer.getProductionCompany(productionCompanyID);
        return makeProductionCompany(tempList).get(0);
    }

    @Override
    public ArrayList<IProductionCompany> searchProductionCompany(String keyword) {
        List<String> tempList = persistenceLayer.getProductionCompany(keyword);
        return makeProductionCompany(tempList);
    }

    private ArrayList<IProductionCompany> makeProductionCompany(List<String> list){
        if(list.size() > 0){
            ArrayList<IProductionCompany> productionCompanies = null;
            for(String item : list){
                String[] items = item.split(",");
                productionCompanies.add(new ProductionCompany(Integer.parseInt(items[0]), items[1]));
            }
            return productionCompanies;
        }else
            return null;
    }

    //endregion prodcutionCompany search methods ends here

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
        return persistenceLayer;
    }
}
