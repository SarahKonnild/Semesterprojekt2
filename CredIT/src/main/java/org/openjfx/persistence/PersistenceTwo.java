package org.openjfx.persistence;

import org.openjfx.domain.Broadcast;
import org.openjfx.domain.Cast;
import org.openjfx.domain.Production;
import org.openjfx.domain.ProductionCompany;
import org.openjfx.interfaces.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class PersistenceTwo implements IPersistence {

    private static PersistenceTwo persistence;
    private final String url = "localhost";
    private final int port = 5432;
    private final String databaseName = "credit_db";
    private final String username = "postgres";
    private String password = Password.PASS;
    private Connection connection = null;
    private Scanner input;

    private PersistenceTwo() {
        initializePostgresqlDatabase();
    }

    public static PersistenceTwo getInstance() {
        if (persistence == null) {
            persistence = new PersistenceTwo();
        }
        return persistence;
    }

    private void initializePostgresqlDatabase() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://" + url + ":" + port + "/" + databaseName, username, password);
        } catch (SQLException | IllegalArgumentException ex) {
            System.out.println("Sorry... Something is wrong with your password.");
        }
    }

    @Override
    public boolean createNewUserInDatabase(IUser user) {
        return false;
    }

    @Override
    public boolean removeUserFromDatabase(int id) {
        return false;
    }

    @Override
    public int createNewBroadcastInDatabase(IBroadcast broadcast) throws IOException {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT into broadcast(name, air_date,episode_number , season_number  )" +
                            "values(?,?,?,?)"
            );

            stmt.setString(1, broadcast.getName());
            String[] airdate = broadcast.getAirDate();
            stmt.setDate(2, Date.valueOf(LocalDate.of(Integer.parseInt(airdate[2]), Integer.parseInt(airdate[1]), Integer.parseInt(airdate[0]))));
            stmt.setInt(3, broadcast.getEpisodeNumber());
            stmt.setInt(4, broadcast.getSeasonNumber());


            stmt.execute();
            int broadCastId = getBroadcastId(broadcast);
            HashMap<ICast, String> map = broadcast.getCastMap();
            for (ICast cast : map.keySet()) {
                PreparedStatement stmt2 = connection.prepareStatement(
                        "insert into broadcast_employs(broadcast_id, cast_id, role)" +
                                "values(?,?,?)"
                );
                stmt2.setInt(1, broadCastId);
                stmt2.setInt(2, cast.getId());
                stmt2.setString(3, map.get(cast));
                stmt2.execute();
            }
            PreparedStatement stmt3 = connection.prepareStatement(
                    "insert into contains(broadcast_id, production_id)" +
                            "values(?,?)"
            );
            stmt3.setInt(1, broadCastId);
            stmt3.setInt(2, broadcast.getProduction().getId());
            stmt3.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public int createNewMovieInDatabase(IMovie movie) throws IOException {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT into movie(name, realease_date) " + "values(?,?)"

            );
            stmt.setString(1, "test");
            String[] release = movie.getReleaseDate();
            stmt.setDate(2, Date.valueOf(LocalDate.of(Integer.parseInt(release[2]), Integer.parseInt(release[1]), Integer.parseInt(release[0]))));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    @Override
    public int createNewProductionInDatabase(IProduction production) throws IOException {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "insert into production(name, year)" +
                            "values(?,?)"
            );
            stmt.setString(1, production.getName());
            stmt.setDate(2, Date.valueOf(LocalDate.of(Integer.parseInt(production.getYear()), 01, 01)));
            stmt.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public boolean removeBroadcastFromDatabase(int id) {
        return false;
    }

    @Override
    public boolean removeProductionFromDatabase(int id) {
        return false;
    }

    @Override
    public boolean removeCastFromDatabase(int id) {
        return false;
    }

    @Override
    public boolean removeMovieFromDatabase(int id) {
        return false;
    }

    @Override
    public int createNewCastInDatabase(ICast cast) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "insert into \"cast\"(regdkid, name)" +
                            "values(?,?)"
            );

            stmt.setString(1, cast.getRegDKID());
            stmt.setString(2, cast.getName());
            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return 0;
    }

    @Override
    public List<String> getBroadcastFromDatabase(String keyword) {
        return null;
    }

    @Override
    public List<String> getBroadcastsFromDatabase(int productionId) {
        return null;
    }

    @Override
    public List<String> getBroadcastFromDatabase(int productionId) {
        return null;
    }

    @Override
    public List<String> getMovieFromDatabase(String keyword) {
        return null;
    }

    @Override
    public List<String> getMovieFromDatabase(int movieID) {
        return null;
    }

    @Override
    public List<String> getMoviesFromDatabase(int productionCompanyID) {
        return null;
    }

    @Override
    public List<String> getCastFromDatabase(String keyword) {
        return null;
    }

    @Override
    public List<String> getCastFromDatabase(int id) {
        return null;
    }

    @Override
    public List<String> getProductionCompany(int id) {
        return null;
    }

    @Override
    public List<String> getCastRolesMoviesFromDatabase(int id) {
        return null;
    }

    @Override
    public List<String> getCastRolesBroadcastFromDatabase(int id) {
        return null;
    }

    @Override
    public List<String> getProductionFromDatabase(String keyword) {
        return null;
    }

    @Override
    public List<String> getProductionsFromDatabase(int productionCompanyID) {
        return null;
    }

    @Override
    public List<String> getProductionFromDatabase(int id) {
        return null;
    }

    @Override
    public String getProductionName(int id) {
        return null;
    }

    @Override
    public boolean mergeCastInDatabase(ICast cast1, ICast cast2) {
        return false;
    }

    @Override
    public boolean updateCastInDatabase(ICast cast) {
        return false;
    }

    @Override
    public boolean updateMovieInDatabase(IMovie movie) {
        return false;
    }

    @Override
    public boolean updateBroadcastInDatabase(IBroadcast broadcast) {
        return false;
    }

    @Override
    public boolean updateProduction(IProduction production) {
        return false;
    }

    @Override
    public List<String> getProductionCompany(String keyword) {
        return null;
    }

    private int getBroadcastId(IBroadcast broadcast) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "select id FROM broadcast WHERE name = ? and air_date = ? and episode_number = ? and season_number = ?"
            );
            stmt.setString(1, broadcast.getName());
            String[] airdate = broadcast.getAirDate();
            stmt.setDate(2, Date.valueOf(LocalDate.of(Integer.parseInt(airdate[2]), Integer.parseInt(airdate[1]), Integer.parseInt(airdate[0]))));
            stmt.setInt(3, broadcast.getEpisodeNumber());
            stmt.setInt(4, broadcast.getSeasonNumber());

            ResultSet result = stmt.executeQuery();
            result.next();
            return result.getInt("id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;

    }

    private int getMovieId(IMovie movie) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "select id FROM movie WHERE title = ? and release_date = ?"
            );
            stmt.setString(1, movie.getTitle());
            String[] releaseDate = movie.getReleaseDate();
            stmt.setDate(2, Date.valueOf(LocalDate.of(Integer.parseInt(releaseDate[2]), Integer.parseInt(releaseDate[1]), Integer.parseInt(releaseDate[0]))));

            ResultSet result = stmt.executeQuery();
            result.next();
            return result.getInt("id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;

    }

    public static void main(String[] args) {
        PersistenceTwo pt = PersistenceTwo.getInstance();
        pt.initializePostgresqlDatabase();
        IProductionCompany productionCompany = new ProductionCompany(1,"test");
        IProduction production = new Production(3,"Paradise", productionCompany, "2019");
        ICast cast = new Cast(1, "Teis Doe", "9999");
        ICast cast2 = new Cast(2, "Nichlas", "88888");
        pt.createNewCastInDatabase(cast2);
        HashMap<ICast, String> map = new HashMap<>();
        map.put(cast, "fejedreng");
        map.put(cast2, "Director");
        IBroadcast broadcast = new Broadcast("Paradise Hotel", 1, 1, "01-02-2030");
        broadcast.setCastRoleMap(map);
        broadcast.setProduction(production);
        try {
            pt.createNewProductionInDatabase(production);
            pt.createNewBroadcastInDatabase(broadcast);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
