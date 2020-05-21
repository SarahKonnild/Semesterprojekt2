package org.openjfx.domain;

import org.openjfx.interfaces.*;
import org.openjfx.persistence.Persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CredITSystem implements ISystem {
    private static CredITSystem instance = null;
    private static IPersistence persistenceLayer;

    /**
     * To use for creating an instance of the System class that can then be used mainly in Presentation.
     */
    private CredITSystem() {
    }

    /*
     * The following search methods are based on 3 different return types. Each of them have 1 overloaded method that just takes another parameter
     * Every search method makes a call to the persistencelayer that then will supply a list based on the result.
     * The search method will then call a private method in the system class, pass the list as a parameter.
     * It will then create objects based on the information in the list and return an arrayList of the objects.
     */

    public static IPersistence getPersistence() {
        if (persistenceLayer == null) {
            getInstance();
        }
        return persistenceLayer;
    }

    public static CredITSystem getInstance() {
        if (instance == null) {
            instance = new CredITSystem();
            persistenceLayer = Persistence.getInstance();
        }
        return instance;
    }

    //region cast methods
    @Override
    public ArrayList<Cast> searchCast(String keyword) {
        return makeCastObjects(persistenceLayer.getCastFromDatabase(keyword));
    }

    @Override
    public ArrayList<Cast> searchCast(int castID) {
        return makeCastObjects(persistenceLayer.getCastFromDatabase(castID));
    }

    /**
     * This method takes the list returned from the search in the database and creates cast objects from that list.
     *
     * @param list
     * @return an arraylist of Cast object
     */
    private ArrayList<Cast> makeCastObjects(List<String> list) {
        ArrayList<Cast> casts = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            //The list is formatted as castID,castRegID,castName
            for (String item : list) {
                String[] items = item.split("\t");
                //Creating a new Cast object for each String item in the list.
                casts.add(new Cast((Integer.parseInt(items[0])), items[1], (items[2])));
            }
        }
        return casts;
    }

    //endregion cast search methods ends here

    //region castRoleMaps methods
    @Override
    public HashMap<Movie, String> getCastRolesMovies(ICast cast) {
        List<String> tempList = persistenceLayer.castMovieRoles(cast);
        return makeCastRolesMovie(tempList);
    }

    @Override
    public HashMap<Broadcast, String> getCastRolesBroadcast(ICast cast) {
        List<String> tempList = persistenceLayer.castBroadcastRoles(cast);
        return makeCastRolesBroadcast(tempList);
    }

    private HashMap<Movie, String> makeCastRolesMovie(List<String> list) {
        HashMap<Movie, String> castMap = new HashMap<>();
        // Gets a list of string formatted as movieID, role
        for (String item : list) {
            //For each item in the list, the string gets split so the ID can be used to search for cast members
            String[] tempArray = item.split("\t");
            int tempID = Integer.parseInt(tempArray[0]);
            String movieRole = tempArray[1];
            Movie tempObj = searchMovie(tempID).get(0);
            castMap.put(tempObj, movieRole);
        }
        return castMap;
    }

    private HashMap<Broadcast, String> makeCastRolesBroadcast(List<String> list) {
        HashMap<Broadcast, String> castMap = new HashMap<>();
        // Gets a list of string formatted as castID, role
        for (String item : list) {
            //For each item in the list, the string gets split so the ID can be used to search for cast members
            String[] tempArray = item.split("\t");
            int tempID = Integer.parseInt(tempArray[0]);
            String tempRole = tempArray[1];
            Broadcast tempObj = searchBroadcast(tempID).get(0);
            castMap.put(tempObj, tempRole);
        }
        return castMap;
    }

    public HashMap<Cast, String> getCastRolesMovies(int movieId) {
        List<String> tempList = persistenceLayer.getCastRolesMoviesFromDatabase(movieId);
        return makeCastRoleMap(tempList);
    }

    public HashMap<Cast, String> getCastRolesBroadcast(int broadcastId) {
        List<String> tempList = persistenceLayer.getCastRolesBroadcastFromDatabase(broadcastId);
        return makeCastRoleMap(tempList);
    }

    private HashMap<Cast, String> makeCastRoleMap(List<String> list) {
        HashMap<Cast, String> castMap = new HashMap<>();
        // Gets a list of string formatted as castID, role
        if (list != null && !list.isEmpty()) {
            for (String item : list) {
                //For each item in the list, the string gets split so the ID can be used to search for cast members
                String[] tempArray = item.split("\t");
                int tempCastID = Integer.parseInt(tempArray[0]);
                String castRole = tempArray[1];
                Cast castObj = searchCast(tempCastID).get(0);
                castMap.put(castObj, castRole);
            }
        }
        return castMap;
    }

    //endregion castRoleMaps ends here

    //region broadcast methods
    @Override
    public ArrayList<Broadcast> searchBroadcast(String keyword) {
        return makeBroadcastObjects(persistenceLayer.getBroadcastFromDatabase(keyword));
    }

    public ArrayList<Broadcast> searchBroadcast(int broadcastID) {
        return makeBroadcastObjects(persistenceLayer.getBroadcastFromDatabase((broadcastID)));
    }

    public ArrayList<Broadcast> searchBroadcasts(int productionID) {
        return makeBroadcastObjects(persistenceLayer.getBroadcastsFromDatabase((productionID)));
    }

    /**
     * This method takes the list returned from the search in the database and creates broadcast objects from that list.
     *
     * @param list A String list over all the broadcast gotten from the search methods.
     * @return an arraylist of broadcast objects
     */
    private ArrayList<Broadcast> makeBroadcastObjects(List<String> list) {
        ArrayList<Broadcast> broadcasts = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            // Each string is formatted as id,name,seasonNumber,episodeNumber,airDate,productionID
            for (String item : list) {
                String[] items = item.split("\t");
                HashMap<Cast, String> tempCastMap = getCastRolesBroadcast(Integer.parseInt(items[0]));
                HashMap<Cast, String> castMap = Objects.requireNonNullElseGet(tempCastMap, HashMap::new);
                broadcasts.add(new Broadcast(
                        Integer.parseInt(items[0]),
                        items[1],
                        Integer.parseInt(items[2]),
                        Integer.parseInt(items[3]),
                        items[4],
                        castMap));
            }
        }
        return broadcasts;
    }

    //endregion Broadcast methods ends here

    //region production methods


    @Override
    public Production searchProductionOnBroadcast(int broadcastId) {
        int productionID = persistenceLayer.getProductionIdOnBroadcast(broadcastId);
        if (productionID != -1) {
            return searchProduction(productionID);
        } else
            return null;

    }

    public Production searchProduction(int productionID) {
        List<String> list = persistenceLayer.getProductionFromDatabase(productionID);
        return makeProductionObjects(list).get(0);
    }

    public ArrayList<Production> searchProductions(int productionCopmpanyID) {
        List<String> list = persistenceLayer.getProductionsFromDatabase(productionCopmpanyID);
        return makeProductionObjects(list);
    }

    @Override
    public ArrayList<Production> searchProduction(String keyword) {
        return makeProductionObjects(persistenceLayer.getProductionFromDatabase(keyword));
    }

    private ArrayList<Production> makeProductionObjects(List<String> list) {
        ArrayList<Production> productions = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            //String is formatted as id,name,year,seasons,episodes
            for (String item : list) {
                String[] items = item.split("\t");
                ArrayList<Broadcast> temp = searchBroadcasts(Integer.parseInt(items[0]));
                ArrayList<Broadcast> broadcasts = Objects.requireNonNullElseGet(temp, ArrayList::new);
                productions.add(new Production(
                        Integer.parseInt(items[0]),
                        items[1],
                        items[2],
                        items[3].equals("null") ? 0 : Integer.parseInt(items[3]),
                        items[4].equals("null") ? 0 : Integer.parseInt(items[4]),
                        broadcasts));
            }
        }
        return productions;
    }
    //endregion production methods ends here

    //region movie methods
    @Override
    public ArrayList<Movie> searchMovie(String keyword) {
        List<String> tempList = persistenceLayer.getMovieFromDatabase(keyword);
        return makeMovieObjects(tempList);
    }

    public ArrayList<Movie> searchMovie(int movieID) {
        List<String> tempList = persistenceLayer.getMovieFromDatabase(movieID);
        return makeMovieObjects(tempList);
    }

    @Override
    public ArrayList<Movie> searchMovies(int productionCompanyID) {
        List<String> tempList = persistenceLayer.getMoviesFromDatabase(productionCompanyID);
        return makeMovieObjects(tempList);
    }

    private ArrayList<Movie> makeMovieObjects(List<String> list) {
        ArrayList<Movie> movies = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            //the string is formatted as id,title,releaseDate
            for (String item : list) {
                String[] items = item.split("\t");
                HashMap<Cast, String> tempCastMap = getCastRolesMovies(Integer.parseInt(items[0]));
                HashMap<Cast, String> castMap = Objects.requireNonNullElseGet(tempCastMap, HashMap::new);
                movies.add(new Movie(
                        Integer.parseInt(items[0]),
                        items[1],
                        items[2],
                        castMap));
            }
        }
        return movies;
    }

    //endregion search movie methods ends here

    //region productionCompany methods
    public ProductionCompany searchProductionCompany(int productionCompanyID) {
        List<String> tempList = persistenceLayer.getProductionCompany(productionCompanyID);
        return makeProductionCompanyObjects(tempList).get(0);
    }

    @Override
    public ArrayList<ProductionCompany> searchProductionCompany(String keyword) {
        List<String> tempList = persistenceLayer.getProductionCompany(keyword);
        return makeProductionCompanyObjects(tempList);
    }

    @Override
    public ProductionCompany searchProductionCompanyOnProduction(int productionId) {
        int tempInt = persistenceLayer.getProductionCompanyIdOnProduction(productionId);
        if (tempInt != -1) {
            return searchProductionCompany(tempInt);
        } else
            return null;
    }

    @Override
    public ProductionCompany searchProductionCompanyOnMovie(int movieId) {
        int tempInt = persistenceLayer.getProductionCompanyIdOnMovie(movieId);
        if (tempInt != -1) {
            return searchProductionCompany(tempInt);
        } else
            return null;
    }

    private ArrayList<ProductionCompany> makeProductionCompanyObjects(List<String> list) {
        ArrayList<ProductionCompany> productionCompanies = new ArrayList<>();
        if (list != null && list.isEmpty()) {
            //String formatted as id,name
            for (String item : list) {
                String[] items = item.split("\t");

                ArrayList<Production> tempProductions = searchProductions(Integer.parseInt(items[0]));
                ArrayList<Production> productions = Objects.requireNonNullElseGet(tempProductions, ArrayList::new);
                ArrayList<Movie> tempMovies = searchMovies(Integer.parseInt(items[0]));
                ArrayList<Movie> movies = Objects.requireNonNullElseGet(tempMovies, ArrayList::new);
                productionCompanies.add(new ProductionCompany(
                        Integer.parseInt(items[0]),
                        items[1],
                        movies,
                        productions));
            }
        }
        return productionCompanies;
    }

    //endregion productionCompany search methods ends here

    @Override
    public Cast addNewCastToDatabase(String name, String regDKID) {
        Cast cast = new Cast(name, regDKID);
        cast.save();
        return cast;
    }

    @Override
    public Broadcast addNewBroadcastToDatabase(String name, int seasonNumber, int episodeNumber, String airDate, int productionID) {
        Broadcast broadcast = new Broadcast(name, seasonNumber, episodeNumber, airDate);
        broadcast.save(productionID);
        return broadcast;
    }

    @Override
    public Production addNewProductionToDatabase(String name, String year, int productionCompanyID) {
        Production production = new Production(name, year);
        production.save(productionCompanyID);
        return production;
    }

    @Override
    public Movie addNewMovieToDatabase(String name, int productionCompanyID, String releasedate) {
        Movie movie = new Movie(name, releasedate);
        movie.save(productionCompanyID);
        return movie;
    }

    @Override
    public ProductionCompany addNewProductionCompanyToDatabase(String name) {
        ProductionCompany productionCompany = new ProductionCompany(name);
        productionCompany.save();
        return productionCompany;
    }

    @Override
    public User createNewUser(String username, String password) {
        return new User(username, password);
    }
}
