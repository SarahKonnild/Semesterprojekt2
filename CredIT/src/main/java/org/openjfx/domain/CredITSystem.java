package org.openjfx.domain;

import org.openjfx.interfaces.*;
import org.openjfx.persistence.Persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CredITSystem implements ISystem {
    private static CredITSystem instance = null;
    private static Persistence persistenceLayer;
    private User user;

    /**
     * To use for creating an instance of the System class that can then be used mainly in Presentation.
     */
    public CredITSystem() {
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

    //region cast methods
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
        ArrayList<ICast> casts = new ArrayList();
        if (list != null && !list.isEmpty()) {
            //The list is formatted as castID,castRegID,castName
            for (String item : list) {
                String[] items = item.split("\t");
                //Creating a new Cast object for each String item in the list.
                casts.add(new Cast((Integer.parseInt(items[0].strip())), items[1], ((items[2]))));
            }
        }
        return casts;
    }

    //endregion cast search methods ends here

    //region castRoleMaps methods
    @Override
    public HashMap<IMovie, String> getCastRolesMovies(ICast cast) {
        List<String> tempList = persistenceLayer.castMovieRoles(cast);
        return makeCastRolesMovie(tempList);
    }

    @Override
    public HashMap<IBroadcast, String> getCastRolesBroadcast(ICast cast) {
        List<String> tempList = persistenceLayer.castBroadcastRoles(cast);
        return makeCastRolesBroadcast(tempList);
    }

    private HashMap<IMovie, String> makeCastRolesMovie(List<String> list) {
        HashMap<IMovie, String> castMap = new HashMap<>();
        // Gets a list of string formatted as movieID, role
        for (String item : list) {
            //For each item in the list, the string gets split so the ID can be used to search for cast members
            String[] tempArray = item.split("\t");
            int tempID = Integer.parseInt(tempArray[0].strip());
            String movieRole = tempArray[1];
            IMovie tempObj = searchMovie(tempID).get(0);
            castMap.put(tempObj, movieRole);
        }
        return castMap;
    }

    private HashMap<IBroadcast, String> makeCastRolesBroadcast(List<String> list) {
        HashMap<IBroadcast, String> castMap = new HashMap<>();
        // Gets a list of string formatted as castID, role
        for (String item : list) {
            //For each item in the list, the string gets split so the ID can be used to search for cast members
            String[] tempArray = item.split("\t");
            int tempID = Integer.parseInt(tempArray[0].strip());
            String tempRole = tempArray[1];
            IBroadcast tempObj = searchBroadcast(tempID).get(0);
            castMap.put(tempObj, tempRole);
        }
        return castMap;
    }

    public HashMap<ICast, String> getCastRolesMovies(int movieId) {
        List<String> tempList = persistenceLayer.getCastRolesMoviesFromDatabase(movieId);
        return makeCastRoleMap(tempList);
    }

    public HashMap<ICast, String> getCastRolesBroadcast(int broadcastId) {
        List<String> tempList = persistenceLayer.getCastRolesBroadcastFromDatabase(broadcastId);
        return makeCastRoleMap(tempList);
    }

    private HashMap<ICast, String> makeCastRoleMap(List<String> list) {
        HashMap<ICast, String> castMap = new HashMap<>();
        // Gets a list of string formatted as castID, role
        if (list != null && !list.isEmpty()) {
            for (String item : list) {
                //For each item in the list, the string gets split so the ID can be used to search for cast members
                String[] tempArray = item.split("\t");
                int tempCastID = Integer.parseInt(tempArray[0].strip());
                String castRole = tempArray[1];
                ICast castObj = searchCast(tempCastID).get(0);
                castMap.put(castObj, castRole);
            }
        }
        return castMap;
    }

    //endregion castRoleMaps ends here

    //region broadcast methods
    @Override
    public ArrayList<IBroadcast> searchBroadcast(String keyword) {
        return makeBroadcastObjects(persistenceLayer.getBroadcastFromDatabase(keyword));
    }

    public ArrayList<IBroadcast> searchBroadcast(int broadcastID) {
        return makeBroadcastObjects(persistenceLayer.getBroadcastFromDatabase((broadcastID)));
    }

    public ArrayList<IBroadcast> searchBroadcasts(int productionID) {
        return makeBroadcastObjects(persistenceLayer.getBroadcastsFromDatabase((productionID)));
    }

    /**
     * This method takes the list returned from the search in the database and creates broadcast objects from that list.
     *
     * @param list A String list over all the broadcast gotten from the search methods.
     * @return an arraylist of broadcast objects
     */
    private ArrayList<IBroadcast> makeBroadcastObjects(List<String> list) {
        ArrayList<IBroadcast> broadcasts = new ArrayList<>();
        if (list != null && list.size() > 0) {
            // Each string is formatted as id,name,seasonNumber,episodeNumber,airDate,productionID
            for (String item : list) {
                String[] items = item.split("\t");

                broadcasts.add(new Broadcast(
                        Integer.parseInt(items[0].strip()),
                        items[1],
                        Integer.parseInt(items[2].strip()),
                        Integer.parseInt(items[3].strip()),
                        items[4]));
            }
        }
        return broadcasts;
    }

    //endregion Broadcast methods ends here

    //region production methods


    @Override
    public IProduction searchProductionOnBroadcast(int broadcastId) {
        int productionID = persistenceLayer.getProductionIdOnBroadcast(broadcastId);
        if (productionID != -1) {
            return searchProduction(productionID);
        } else
            return null;

    }

    public IProduction searchProduction(int productionID) {
        List<String> list = persistenceLayer.getProductionFromDatabase(productionID);
        return makeProductionObjects(list).get(0);
    }

    public ArrayList<IProduction> searchProductions(int productionCopmpanyID) {
        List<String> list = persistenceLayer.getProductionsFromDatabase(productionCopmpanyID);
        return makeProductionObjects(list);
    }

    @Override
    public ArrayList<IProduction> searchProduction(String keyword) {
        return makeProductionObjects(persistenceLayer.getProductionFromDatabase(keyword));
    }

    private ArrayList<IProduction> makeProductionObjects(List<String> list) {
        ArrayList<IProduction> productions = new ArrayList<>();
        if (list != null && list.size() > 0) {
            //String is formatted as id,name,year,seasons,episodes
            for (String item : list) {
                String[] items = item.split("\t");
                productions.add(new Production(
                        Integer.parseInt(items[0].strip()),
                        items[1],
                        items[2],
                        items[3].equals("null") ? 0 : Integer.parseInt(items[3].strip()),
                        items[4].equals("null") ? 0 : Integer.parseInt(items[4].strip())));
            }
        }
        return productions;
    }
    //endregion production methods ends here

    //region movie methods
    @Override
    public ArrayList<IMovie> searchMovie(String keyword) {
        List<String> tempList = persistenceLayer.getMovieFromDatabase(keyword);
        return makeMovieObjects(tempList);
    }

    public ArrayList<IMovie> searchMovie(int movieID) {
        List<String> tempList = persistenceLayer.getMovieFromDatabase(movieID);
        return makeMovieObjects(tempList);
    }

    @Override
    public ArrayList<IMovie> searchMovies(int productionCompanyID) {
        List<String> tempList = persistenceLayer.getMoviesFromDatabase(productionCompanyID);
        return makeMovieObjects(tempList);
    }

    private ArrayList<IMovie> makeMovieObjects(List<String> list) {
        ArrayList<IMovie> movies = new ArrayList<>();
        if (list != null && list.size() > 0) {
            //the string is formatted as id,title,releaseDate
            for (String item : list) {
                String[] items = item.split("\t");
                movies.add(new Movie(
                        Integer.parseInt(items[0].strip()),
                        items[1],
                        items[2]));
            }
        }
        return movies;
    }

    //endregion search movie methods ends here

    //region productionCompany methods
    public IProductionCompany searchProductionCompany(int productionCompanyID) {
        List<String> tempList = persistenceLayer.getProductionCompany(productionCompanyID);
        return makeProductionCompanyObjects(tempList).get(0);
    }

    @Override
    public ArrayList<IProductionCompany> searchProductionCompany(String keyword) {
        List<String> tempList = persistenceLayer.getProductionCompany(keyword);
        return makeProductionCompanyObjects(tempList);
    }

    @Override
    public IProductionCompany searchProductionCompanyOnProduction(int productionId) {
        int tempInt = persistenceLayer.getProductionCompanyIdOnProduction(productionId);
        if (tempInt != -1) {
            return searchProductionCompany(tempInt);
        } else
            return null;
    }

    @Override
    public IProductionCompany searchProductionCompanyOnMovie(int movieId) {
        int tempInt = persistenceLayer.getProductionCompanyIdOnMovie(movieId);
        if (tempInt != -1) {
            return searchProductionCompany(tempInt);
        } else
            return null;
    }

    private ArrayList<IProductionCompany> makeProductionCompanyObjects(List<String> list) {
        ArrayList<IProductionCompany> productionCompanies = new ArrayList<>();
        if (list != null && list.size() > 0) {
            //String formatted as id,name
            for (String item : list) {
                String[] items = item.split("\t");
                productionCompanies.add(new ProductionCompany(Integer.parseInt(items[0].strip()), items[1].strip()));
            }
        }
        return productionCompanies;
    }

    //endregion productionCompany search methods ends here

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
