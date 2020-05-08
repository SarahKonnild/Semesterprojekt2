package org.openjfx.persistence;

import org.openjfx.domain.*;
import org.openjfx.interfaces.*;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.sql.*;

public class Persistence implements IPersistence {

    private static Persistence persistence;
    private final String url = "localhost";
    private final int port = 5432;
    private final String databaseName = "credIT_db";
    private final String username = "postgres";
    private final String password = Password.PASS;
    private Connection connection = null;

    private Persistence() {
        initializePostgresqlDatabase();
    }

    public static Persistence getInstance() {
        if (persistence == null) {
            persistence = new Persistence();
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
                    "INSERT into movie(name, release_date) " + "values(?,?)"

            );
            stmt.setString(1, "test");
            String[] release = movie.getReleaseDate();
            stmt.setDate(2, Date.valueOf(LocalDate.of(Integer.parseInt(release[2]), Integer.parseInt(release[1]), Integer.parseInt(release[0]))));
            //TODO: Implementer productionCompany
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

            //TODO: Implementer productionCompany
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public int createNewProductionCompanyInDatabase(IProductionCompany production) throws IOException {
        return 0;
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
    public boolean removeProductionCompanyFromDatabase(int id) {
        return false;
    }

    @Override
    public int createNewCastInDatabase(ICast cast) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "insert into cast_members(regdkid, name)" +
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

    /**
     * Searches the database for every Cast object associated with a Broadcast, given it's id.
     * @param id of the Broadcast.
     * @return a {@code List<String>} of all Cast's ids from the database, for the Broadcast.
     */
    @Override
    public List<String> getCastRolesBroadcastFromDatabase(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT broadcast_employs.cast_id FROM broadcast_employs WHERE broadcast_id = ?");
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            ArrayList<String> roleList = new ArrayList<>();
            while (resultSet.next()) {
                roleList.add(resultSet.getString(1));
            }
            return roleList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getProductionFromDatabase(String keyword) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getProductionsFromDatabase(int productionCompanyID) {
        throw new UnsupportedOperationException();
    }

    /**
     * Searches the database for a Production and returns it as a {@code List<String>} if successful.
     * @param id the id {@code int} for the Production that you want returned.
     * @return a {@code List<String>} of the production's attributes from the database.
     */
    @Override
    public List<String> getProductionFromDatabase(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM production WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            List<String> productionList = new ArrayList<>();
            productionList.add(resultSet.getString(1));
            productionList.add(resultSet.getString(2));
            productionList.add(resultSet.getString(3));
            productionList.add(resultSet.getString(4));
            productionList.add(resultSet.getString(5));
            return productionList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public String getProductionName(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean mergeCastInDatabase(ICast cast1, ICast cast2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean updateCastInDatabase(ICast cast) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean updateProductionCompanyInDataBase(IProductionCompany productionCompany) {
        return false;
    }

    /**
     * Updates a given Movie object in the database to the current attributes of the given parameter.
     * @param movie the current version of the Movie object to be updated.
     * @return {@code true} if the object was updated; {@code false} if the object did not update or if the object could not be found.
     */
    @Override
    public boolean updateMovieInDatabase(IMovie movie) {
        try {
            PreparedStatement updateMovieStatement = connection.prepareStatement("UPDATE movie SET (name, release_date) = (?,?) WHERE id = ?");
            updateMovieStatement.setInt(3, movie.getId());
            updateMovieStatement.setString(1, movie.getTitle());
            LocalDate tempDate = LocalDate.of(Integer.parseInt(movie.getReleaseDate()[2]), Integer.parseInt(movie.getReleaseDate()[1]), Integer.parseInt(movie.getReleaseDate()[0]));
            updateMovieStatement.setDate(2, Date.valueOf(tempDate));
            updateMovieStatement.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    /**
     * Updates a given Production object in the database to the current attributes of the given parameter.
     * @param production the current version of the Production object to be updated.
     * @return {@code true} if the object was updated; {@code false} if the object did not update or if the object could not be found.
     */
    @Override
    public boolean updateProduction(IProduction production) {
        try {
            PreparedStatement stmt = connection.prepareStatement("update production set (name,year) = (?,?) where id = ?");
            stmt.setInt(3, production.getId());
            stmt.setString(1, production.getName());
            LocalDate tempDate = LocalDate.of(Integer.parseInt(production.getYear()), 1, 1);
            stmt.setDate(2, Date.valueOf(tempDate));
            stmt.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /**
     * updates a given Broadcast object in the database.
     * @param broadcast the current version of the object that is to overwrite the old version.
     * @return {@code true} if the operation was successful; {@code false} if the operation failed.
     */
    //todo test if this works as intended.
    @Override
    public boolean updateBroadcastInDatabase(IBroadcast broadcast) {
        try {
            //finds the old version of the object and replaces it with the current one.
            PreparedStatement stmt = connection.prepareStatement("update broadcast set (name, air_date, episode_number, season_number) = (?,?,?,?) where id = ?");
            stmt.setInt(4, broadcast.getId());
            stmt.setString(1,broadcast.getName());
            LocalDate tempDate = LocalDate.of(Integer.parseInt(broadcast.getAirDate()[2]), Integer.parseInt(broadcast.getAirDate()[1]), Integer.parseInt(broadcast.getAirDate()[0]));
            stmt.setDate(2, Date.valueOf(tempDate));
            stmt.setInt(3, broadcast.getEpisodeNumber());
            stmt.setInt(4, broadcast.getSeasonNumber());
            stmt.execute();

            //removes the current cast members from the object in another table.
            PreparedStatement removeCastStatement = connection.prepareStatement("DELETE FROM broadcast_employs WHERE broadcast_id = ?");
            removeCastStatement.setInt(1, broadcast.getId());
            removeCastStatement.execute();

            //inserts the new version of the cast member Map.
            int id = broadcast.getId();
            //foreach loop that runs through the entire map and inserts it all into the table.
            //todo add support for the same cast member, having multiple roles.
            for (Map.Entry<ICast, String> entry : broadcast.getCastMap().entrySet()) {
                PreparedStatement insertCastStatement = connection.prepareStatement("INSERT INTO broadcast_employs (broadcast_id, cast_id, role) VALUES (?,?,?)");
                insertCastStatement.setInt(1, id);
                insertCastStatement.setInt(2, entry.getKey().getId());
                insertCastStatement.setString(3, entry.getValue());
                insertCastStatement.execute();
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /**
     * Searches the database for a production company and returns it as a {@code List<String>} if successful.
     * @param keyword the name {@code String} for the production compnay that you want returned.
     * @return a {@code List<String>} of the production company's attributes from the database.
     */
    @Override
    public List<String> getProductionCompany(String keyword) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM production_company WHERE name = ?");
            stmt.setString(1, keyword);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (!sqlReturnValues.next()) {
                return null;
            }
            List<String> resultList = new ArrayList<>();
            resultList.add(String.valueOf(sqlReturnValues.getInt(1)));
            resultList.add(sqlReturnValues.getString(2));
            return resultList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
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
                    "select id FROM movie WHERE name = ? and release_date = ?"
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
        Persistence pt = Persistence.getInstance();
        pt.initializePostgresqlDatabase();
        CredITSystem credITSystem = new CredITSystem();
        IBroadcast iBroadcast = new Broadcast(1,"Cast_Test", 50, 2, "1-2-1990",1);

        ICast iCast = new Cast("Hans", "25j043");
        ICast iCast2 = new Cast("Hanne", "h23sd");
        ICast iCast3 = new Cast("Herold", "h56568");
        ICast iCast4 = new Cast("Harald", "ghdj321");

        HashMap<ICast, String> map = iBroadcast.getCastMap();
        map.put(iCast, "Kam");
        map.put(iCast2, "Kam1");
        map.put(iCast3, "Kam2");
        map.put(iCast4, "Kam3");

        credITSystem.getPersistenceLayer().updateBroadcastInDatabase(iBroadcast);
    }
}
