package org.openjfx.persistence;

import org.openjfx.interfaces.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Persistence implements IPersistence {

    private static Persistence persistence;
    private final String url = "localhost";
    private final int port = 5432;
    private final String databaseName = "credit_db";
    private final String username = "postgres";
    private final String password = Password.PASS;
    private Connection connection = null;

    private Persistence() {
        initializePostgresqlDatabase();
    }

    /**
     * @return the one and only persistence layer object in this program.
     * @author Nichlas & Teis
     * Signleton implementation of persistence layer together with private constructor.
     */

    public static Persistence getInstance() {
        if (persistence == null) {
            persistence = new Persistence();
        }
        return persistence;
    }

    /**
     * @author Nichlas & Teis
     * Initilizes the Database connection with password individual password files.
     */
    private void initializePostgresqlDatabase() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://"+url+":"+port+"/"+databaseName, username, password);
        } catch (SQLException | IllegalArgumentException ex) {
            System.out.println("Sorry... Something is wrong with your password.");
        }
    }

    /**
     * @param broadcast The broadcast object that needs to be saved
     * @return The id created by the database.
     * @author Nichlas & Teis
     * Creates a new broadcast entry in the database.
     */

    @Override
    public int createNewBroadcastInDatabase(IBroadcast broadcast, int productionId) {
        int id;
        // this statement inserts the values of the broadcast in to the Database.
        try {
            connection.setAutoCommit(false);
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

            //queries the database for the id on the entry we just created.
            id = getBroadcastId(broadcast);
            HashMap<ICast, String> map = broadcast.getCastMap();
            // for each cast inserts the cast into the broadcast employs table.
            for (ICast cast : map.keySet()) {
                PreparedStatement stmt2 = connection.prepareStatement(
                        "insert into broadcast_employs(broadcast_id, cast_id, role)" +
                                "values(?,?,?)"
                );
                stmt2.setInt(1, id);
                stmt2.setInt(2, cast.getId());
                stmt2.setString(3, map.get(cast));
                stmt2.execute();
            }

            //links broadcast with the production in database
            PreparedStatement stmt3 = connection.prepareStatement(
                    "insert into contains(broadcast_id, production_id)" +
                            "values(?,?)"
            );
            stmt3.setInt(1, id);
            stmt3.setInt(2, productionId);
            stmt3.execute();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return -1;
        }
        return id;
    }

    /**
     * @param movie the movie object you want to save in the database
     * @return the id of the movie entry we just created. Negative if something went wrong.
     * @author Teis & Nichlas
     */

    @Override
    public int createNewMovieInDatabase(IMovie movie, int productionCompanyId) {
        int id;
        try {
            connection.setAutoCommit(false);
            //inserts the values of the movie object into the database.
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT into movie(name, release_date) " + "values(?,?)"
            );
            stmt.setString(1, movie.getTitle());
            String[] release = movie.getReleaseDate();
            stmt.setDate(2, Date.valueOf(LocalDate.of(Integer.parseInt(release[0]), 1, 1)));
            stmt.execute();
            int prodID = productionCompanyId;
            //Insert the link between the production company and the movie.
            PreparedStatement stmt2 = connection.prepareStatement(
                    "INSERT INTO produces_movie(production_company_id, movie_id)"
                            + "values(?,?)"
            );
            //query the database for the id on the entry we just created.
            id = getMovieId(movie);
            stmt2.setInt(1, prodID);
            stmt2.setInt(2, id);
            stmt2.execute();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return -1;
        }

        return id;
    }

    /**
     * Creates a new production entry with the values of the parameter.
     *
     * @param production The production object that needs to be saved
     * @return the id of the production entry you just created.
     * @author Teis & Nichlas
     */
    @Override
    public int createNewProductionInDatabase(IProduction production, int productionCompanyId) {
        int id;
        try {
            connection.setAutoCommit(false);
            //insert the values of the production object into a new entry in Database.
            PreparedStatement stmt = connection.prepareStatement(
                    "insert into production(name, year)" +
                            "values(?,?)"
            );
            stmt.setString(1, production.getName());
            stmt.setDate(2, Date.valueOf(LocalDate.of(Integer.parseInt(production.getYear()), 1, 1)));
            stmt.execute();

            //Insert the link between the production and the production company.
            int prodID = productionCompanyId;
            PreparedStatement stmt2 = connection.prepareStatement(
                    "INSERT INTO produces(production_company_id, production_id)"
                            + "values(?,?)"
            );

            //query the database for the id on the production we just created.
            id = getProductionId(production);
            stmt2.setInt(1, prodID);
            stmt2.setInt(2, id);
            stmt2.execute();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return -1;
        }
        return id;
    }

    /**
     * creates a new productionCompany entry in the databse.
     *
     * @param productionCompany the object you want to save
     * @return the id of the entry in the database you just created.
     * @author Teis & Nichlas
     */
    @Override
    public int createNewProductionCompanyInDatabase(IProductionCompany productionCompany) {
        //insert the values of the parsed productionCompany
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT into production_company(name)" + "values(?)"
            );
            stmt.setString(1, productionCompany.getName());
            stmt.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
        // query the database for the id on the created entry.
        return getProductionCompanyId(productionCompany);
    }

    @Override
    public boolean removeBroadcastFromDatabase(int id) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement stmt = connection.prepareStatement(
                    "delete from broadcast where broadcast.id = ?");
            stmt.setInt(1, id);
            stmt.execute();

            connection.commit();
            connection.setAutoCommit(false);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * {@inheritDoc}
     * <br>
     * Be aware. This method is extremely destructive.
     * Every broadcast contained in the production and info regarding them will be lost!
     * no backsies!
     */
    @Override
    public boolean removeProductionFromDatabase(int id) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement deleteBroadcastStatement = connection.prepareStatement(
                    "delete from broadcast using contains " +
                            "where broadcast.id = contains.broadcast_id " +
                            "and contains.production_id = ?");
            deleteBroadcastStatement.setInt(1, id);
            deleteBroadcastStatement.execute();

            PreparedStatement deleteProductionStatement = connection.prepareStatement(
                    "delete from production where production.id = ?");
            deleteProductionStatement.setInt(1, id);
            deleteProductionStatement.execute();

            connection.commit();
            connection.setAutoCommit(false);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public boolean removeCastFromDatabase(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "delete from cast_members where id = ?");
            stmt.setInt(1, id);
            stmt.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /**
     * Removes the movie and all references to it in the database.
     *
     * @param id of the movie you want to remove.
     * @return {@code true} if successfully removed the movie. {@code false} if the action didn't complete.
     */
    @Override
    public boolean removeMovieFromDatabase(int id) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement(
                    "delete from movie where id = ?");
            stmt.setInt(1, id);
            stmt.execute();

            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return false;
        }
    }

    @Override
    public boolean removeProductionCompanyFromDatabase(IProductionCompany company) {
        try {
            connection.setAutoCommit(false);
            ArrayList<IMovie> movies = company.getMovieList();
            ArrayList<IProduction> productions = company.getProductionList();
            if (!movies.isEmpty()) {
                for (IMovie movie : movies) {
                    removeMovie(movie.getId());
                }
            }
            if (!productions.isEmpty()) {
                for (IProduction production : productions) {
                    removeProduction(production.getId());
                }
            }
            PreparedStatement removeCompany = connection.prepareStatement("delete from production_company where id = ? ");
            removeCompany.setInt(1, company.getId());
            removeCompany.execute();

            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getBroadcastFromDatabase(String keyword) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM broadcast WHERE LOWER(name) ~ ?");
            stmt.setString(1, keyword.toLowerCase());
            ResultSet resultSet = stmt.executeQuery();

            List<String> resultList = new ArrayList<>();
            while (resultSet.next()) {
                LocalDate date = resultSet.getDate(3).toLocalDate();
                resultList.add((resultSet.getInt(1) + "," +
                        resultSet.getString(2) + "," +
                        resultSet.getInt(5)) + "," +
                        resultSet.getInt(4) + "," +
                        String.valueOf(date.getDayOfMonth()) + "-" +
                        String.valueOf(date.getMonth().getValue()) + "-" +
                        String.valueOf(date.getYear())
                         );
            }
            return resultList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getBroadcastsFromDatabase(int productionId) {
        try {
            //just a long nested SQL query. Looks more complicated than it is.
            PreparedStatement stmt = connection.prepareStatement("" +
                    "SELECT broadcast.id, name, air_date, episode_number, season_number " +
                    "FROM broadcast, contains " +
                    "where broadcast.id = contains.broadcast_id " +
                    "and contains.production_id =  ?");
            stmt.setInt(1, productionId);
            ResultSet resultSet = stmt.executeQuery();

            List<String> resultList = new ArrayList<>();
            while (resultSet.next()) {
                LocalDate date = resultSet.getDate(3).toLocalDate();
                resultList.add((resultSet.getInt(1) + "," +
                        resultSet.getString(2) + "," +
                        resultSet.getInt(5)) + "," +
                        resultSet.getInt(4) + "," +
                        String.valueOf(date.getDayOfMonth()) + "-" +
                        String.valueOf(date.getMonth().getValue()) + "-" +
                        String.valueOf(date.getYear())
                );
            }
            return resultList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Searches the database for the specific broadcastID.
     *
     * @param broadcastID the id of the specific broadcast you want returned
     * @return a {@code List<String>} of the broadcast's parameters. Returns null if no broadcast was found with that broadcastID.
     */
    @Override
    public List<String> getBroadcastFromDatabase(int broadcastID) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM broadcast WHERE id = ?");
            stmt.setInt(1, broadcastID);
            ResultSet resultSet = stmt.executeQuery();

            List<String> resultList = new ArrayList<>();
            while (resultSet.next()) {
                LocalDate date = resultSet.getDate(3).toLocalDate();
                resultList.add((resultSet.getInt(1) + "," +
                        resultSet.getString(2) + "," +
                        resultSet.getInt(5)) + "," +
                        resultSet.getInt(4) + "," +
                        String.valueOf(date.getDayOfMonth()) + "-" +
                        String.valueOf(date.getMonth().getValue()) + "-" +
                        String.valueOf(date.getYear())
                );
            }
            return resultList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Searches the database for movies with the specific title.
     * <br>
     * The search looks for a match and can return multiple results, if they all match.
     *
     * @param keyword the {@code String} the database finds a match of.
     * @return a {@code List<String>} containing the movie's parameters. Returns null if no match was found.
     */
    @Override
    public List<String> getMovieFromDatabase(String keyword) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM movie WHERE LOWER(name) ~ ?");
            stmt.setString(1, keyword.toLowerCase());
            ResultSet resultSet = stmt.executeQuery();

            List<String> resultList = new ArrayList<>();
            while (resultSet.next()) {
                resultList.add((resultSet.getInt(1) + "," +
                        resultSet.getString(2) + "," +
                        resultSet.getDate(3)));
            }
            return resultList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Searches the database for movies with the specific movieID.
     *
     * @param movieID the {@code int} the database finds a match of.
     * @return a {@code List<String>} containing the movie's parameters. Returns null if no match was found.
     */
    @Override
    public List<String> getMovieFromDatabase(int movieID) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM movie WHERE id = ?");
            stmt.setInt(1, movieID);
            ResultSet resultSet = stmt.executeQuery();

            List<String> resultList = new ArrayList<>();
            while (resultSet.next()) {
                resultList.add((resultSet.getInt(1) + "," +
                        resultSet.getString(2) + "," +
                        resultSet.getDate(3)));
            }
            return resultList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Searches the database for movies with at matching productionCompanyID
     *
     * @param productionCompanyID the ID of the production company
     * @return a {@code List<String>} containing the movie's parameters. Returns null if no match was found.
     */
    @Override
    public List<String> getMoviesFromDatabase(int productionCompanyID) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT movie.id, movie.name, movie.release_date " +
                    "FROM movie, produces_movie " +
                    "WHERE movie.id = produces_movie.movie_id " +
                    "and produces_movie.production_company_id = ?");
            stmt.setInt(1, productionCompanyID);
            ResultSet resultSet = stmt.executeQuery();

            List<String> resultList = new ArrayList<>();
            while (resultSet.next()) {
                resultList.add((resultSet.getInt(1) + "," +
                        resultSet.getString(2) + "," +
                        resultSet.getDate(3)));
            }
            return resultList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getCastFromDatabase(String keyword) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM cast_members WHERE LOWER(name) ~ ?");
            stmt.setString(1, keyword.toLowerCase());
            ResultSet resultSet = stmt.executeQuery();

            List<String> resultList = new ArrayList<>();
            while (resultSet.next()) {
                resultList.add((resultSet.getInt(1) + "," +
                        resultSet.getString(2) + "," +
                        resultSet.getString(3)));
            }
            return resultList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getCastFromDatabase(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM cast_members WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            List<String> resultList = new ArrayList<>();
            resultSet.next();
            resultList.add((resultSet.getInt(1) + "," +
                    resultSet.getInt(2) + "," +
                    resultSet.getString(3)));

            return resultList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Searches the database for production companies matching the ID
     *
     * @param id the unique key given to every entry in the database.
     * @return a {@code List<String>} containing the production company's name. Returns null if no match was found.
     */
    @Override
    public List<String> getProductionCompany(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM production_company WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            List<String> resultList = new ArrayList<>();
            while (resultSet.next()) {
                resultList.add((resultSet.getInt(1) + "," + resultSet.getString(2)));
            }
            return resultList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @param movieID the ID of the movie, for which you want the cast list returned
     * @return a {@code List<String>} containing the movie's cast list. The List is formatted as (id, role).
     * Returns null if no match was found or the movie didn't have an cast members.
     */
    @Override
    public List<String> getCastRolesMoviesFromDatabase(int movieID) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM movie_employs WHERE movie_id = ?");
            stmt.setInt(1, movieID);
            ResultSet resultSet = stmt.executeQuery();

            ArrayList<String> roleList = new ArrayList<>();
            while (resultSet.next()) {
                roleList.add((resultSet.getInt(2)) + "," +
                        resultSet.getString(3));
            }
            return roleList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Searches the database for every Cast object associated with a Broadcast, given it's id.
     *
     * @param broadcastID of the Broadcast.
     * @return a {@code List<String>} of all Cast's ids from the database, for the Broadcast.
     */
    @Override
    public List<String> getCastRolesBroadcastFromDatabase(int broadcastID) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM broadcast_employs WHERE broadcast_id = ?");
            stmt.setInt(1, broadcastID);
            ResultSet resultSet = stmt.executeQuery();

            ArrayList<String> roleList = new ArrayList<>();
            while (resultSet.next()) {
                roleList.add((resultSet.getInt(2)) + "," +
                        resultSet.getString(3));
            }
            return roleList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getProductionFromDatabase(String keyword) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM production WHERE LOWER(name) ~ ?");
            stmt.setString(1, keyword.toLowerCase());
            ResultSet resultSet = stmt.executeQuery();

            List<String> productionList = new ArrayList<>();
            while (resultSet.next()) {
                productionList.add(resultSet.getInt(1) + "," +
                        resultSet.getString(2) + "," +
                        resultSet.getDate(3).toLocalDate().getYear() + "," +
                        resultSet.getInt(4) + "," +
                        resultSet.getInt(5));
            }
            return productionList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Searches the database for the id of the production that the broadcast is produced by
     *
     * @param broadcastId the id of the broadcast you want to search based on
     * @return returns the id of the production
     */
    @Override
    public int getProductionIdOnBroadcast(int broadcastId) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT production_id FROM contains WHERE broadcast_id = ?");
            stmt.setInt(1, broadcastId);

            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    /**
     * Searches the database for every entry where the given ID is the owner of a production and returns the ID.
     *
     * @param productionCompanyID the reference ID in the database for the production company
     * @return a list for every production the production company owns. Can be changed to return the name instead.
     */
    @Override
    public List<String> getProductionsFromDatabase(int productionCompanyID) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT production.id, name, year, number_of_seasons, number_of_episodes FROM production, produces WHERE production.id = produces.production_id AND production_company_id = ?");
            stmt.setInt(1, productionCompanyID);
            ResultSet resultSet = stmt.executeQuery();

            List<String> productionList = new ArrayList<>();
            while (resultSet.next()) {
                productionList.add(resultSet.getInt(1) + "," +
                        resultSet.getString(2) + "," +
                        resultSet.getDate(3).toLocalDate().getYear() + "," +
                        resultSet.getInt(4) + "," +
                        resultSet.getInt(5));
            }
            return productionList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Searches the database for a Production and returns it as a {@code List<String>} if successful.
     *
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
            productionList.add(resultSet.getInt(1) + "," +
                    resultSet.getString(2) + "," +
                    resultSet.getDate(3).toLocalDate().getYear() + "," +
                    resultSet.getInt(4) + "," +
                    resultSet.getInt(5));
            return productionList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @param id the ID of the production to search for
     * @return a {@code String} containing the production's name. Returns null if no match was found.
     */
    @Override
    public String getProductionName(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT name FROM production WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return resultSet.getString(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean mergeCastInDatabase(ICast cast1, ICast cast2) {
        if (cast1.getRegDKID().equals(cast2.getRegDKID())) {
            try {
                connection.setAutoCommit(false);
                PreparedStatement updateStatement = connection.prepareStatement(
                        "update broadcast_employs set cast_id = ? where cast_id = ?");
                updateStatement.setInt(1, cast1.getId());
                updateStatement.setInt(2, cast2.getId());
                updateStatement.execute();

                PreparedStatement delStatement = connection.prepareStatement("delete from cast_members where id = ?");
                delStatement.setInt(1, cast2.getId());
                delStatement.execute();

                connection.commit();
                connection.setAutoCommit(true);
                return true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                try {
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean updateCastInDatabase(ICast cast) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE cast_members SET (regdkid, name) = (?,?) WHERE id = ?");
            stmt.setInt(3, cast.getId());
            stmt.setString(1, cast.getRegDKID());
            stmt.setString(2, cast.getName());
            stmt.execute();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateProductionCompanyInDatabase(IProductionCompany productionCompany) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE production_company SET name = ? FROM(SELECT id FROM production_company) AS subquery WHERE production_company.id = ?");
            stmt.setString(1, productionCompany.getName());
            stmt.setInt(2, productionCompany.getId());
            stmt.execute();

            for (IMovie movie : productionCompany.getMovieList()) {
                updateMovieInDatabase(movie);
            }
            for (IProduction production : productionCompany.getProductionList()) {
                updateProduction(production);
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /**
     * Updates a given Movie object in the database to the current attributes of the given parameter.
     *
     * @param movie the current version of the Movie object to be updated.
     * @return {@code true} if the object was updated; {@code false} if the object did not update or if the object could not be found.
     */
    @Override
    public boolean updateMovieInDatabase(IMovie movie) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement updateMovieStatement = connection.prepareStatement(
                    "UPDATE movie SET (name, release_date) = (?,?) WHERE id = ?");
            updateMovieStatement.setInt(3, movie.getId());
            updateMovieStatement.setString(1, movie.getTitle());
            LocalDate tempDate = LocalDate.of(
                    Integer.parseInt(movie.getReleaseDate()[2]),
                    Integer.parseInt(movie.getReleaseDate()[1]),
                    Integer.parseInt(movie.getReleaseDate()[0]));
            updateMovieStatement.setDate(2, Date.valueOf(tempDate));
            updateMovieStatement.execute();

            //removes the current cast members from the object in another table.
            PreparedStatement removeCastStatement = connection.prepareStatement(
                    "DELETE FROM movie_employs WHERE broadcast_id = ?");
            removeCastStatement.setInt(1, movie.getId());
            removeCastStatement.execute();

            int id = movie.getId();
            //foreach loop that runs through the entire map and inserts it all into the table.
            for (Map.Entry<ICast, String> entry : movie.getCastMap().entrySet()) {
                PreparedStatement insertCastStatement = connection.prepareStatement(
                        "INSERT INTO movie_employs (movie_id, cast_id, role) VALUES (?,?,?)");
                insertCastStatement.setInt(1, id);
                insertCastStatement.setInt(2, entry.getKey().getId());
                insertCastStatement.setString(3, entry.getValue());
                insertCastStatement.execute();
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * Updates a given Production object in the database to the current attributes of the given parameter.
     *
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

            for (IBroadcast broadcast : production.getBroadcasts()) {
                updateBroadcastInDatabase(broadcast);
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /**
     * updates a given Broadcast object in the database.
     *
     * @param broadcast the current version of the object that is to overwrite the old version.
     * @return {@code true} if the operation was successful; {@code false} if the operation failed.
     */
    //todo test if this works as intended.
    @Override
    public boolean updateBroadcastInDatabase(IBroadcast broadcast) {
        try {
            connection.setAutoCommit(false);
            //finds the old version of the object and replaces it with the current one.
            PreparedStatement stmt = connection.prepareStatement(
                    "update broadcast set (name, air_date, episode_number, season_number) = (?,?,?,?) where id = ?");
            stmt.setInt(4, broadcast.getId());
            stmt.setString(1, broadcast.getName());
            LocalDate tempDate = LocalDate.of(
                    Integer.parseInt(broadcast.getAirDate()[2]),
                    Integer.parseInt(broadcast.getAirDate()[1]),
                    Integer.parseInt(broadcast.getAirDate()[0]));
            stmt.setDate(2, Date.valueOf(tempDate));
            stmt.setInt(3, broadcast.getEpisodeNumber());
            stmt.setInt(4, broadcast.getSeasonNumber());
            stmt.execute();

            //removes the current cast members from the object in another table.
            PreparedStatement removeCastStatement = connection.prepareStatement(
                    "DELETE FROM broadcast_employs WHERE broadcast_id = ?");
            removeCastStatement.setInt(1, broadcast.getId());
            removeCastStatement.execute();

            //inserts the new version of the cast member Map.
            int id = broadcast.getId();
            //foreach loop that runs through the entire map and inserts it all into the table.
            for (Map.Entry<ICast, String> entry : broadcast.getCastMap().entrySet()) {
                PreparedStatement insertCastStatement = connection.prepareStatement(
                        "INSERT INTO broadcast_employs (broadcast_id, cast_id, role) VALUES (?,?,?)");
                insertCastStatement.setInt(1, id);
                insertCastStatement.setInt(2, entry.getKey().getId());
                insertCastStatement.setString(3, entry.getValue());
                insertCastStatement.execute();
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

    }

    /**
     * Searches the database for a production company and returns it as a {@code List<String>} if successful.
     *
     * @param keyword the name {@code String} for the production compnay that you want returned.
     * @return a {@code List<String>} of the production company's found in the database matching the keyword.
     */
    @Override
    public List<String> getProductionCompany(String keyword) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM production_company WHERE LOWER(production_company.name) ~ ?");
            stmt.setString(1, keyword.toLowerCase());
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<String> resultList = new ArrayList<>();
            while (sqlReturnValues.next()) {
                resultList.add(sqlReturnValues.getInt(1) + "," +
                        (sqlReturnValues.getString(2)));
            }
            return resultList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * Searches the database for the id of the productionCompany that the Production is produced by
     *
     * @param productionId the id of the production you want to search based on
     * @return returns the id of the productionCompany
     */
    @Override
    public int getProductionCompanyIdOnProduction(int productionId) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT production_company_id FROM produces WHERE production_id = ?");
            stmt.setInt(1, productionId);

            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    /**
     * Searches the database for the id of the productionCompany that the movie is produced by
     *
     * @param movieId the id of the movie you want to search based on
     * @return returns the id of the productionCompany
     */
    @Override
    public int getProductionCompanyIdOnMovie(int movieId) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT production_company_id FROM produces_movie WHERE movie_id = ?");
            stmt.setInt(1, movieId);

            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    /**
     * Queries the database to find the movies a cast has been on and his role.
     *
     * @param cast the cast you want to search for.
     * @return list of movie id and the corresponding role.
     * @author Teis & Nichlas & Laust
     */
    public List<String> castMovieRoles(ICast cast) {

        try {
            List<String> returnList = new ArrayList<>();
            PreparedStatement stmt = connection.prepareStatement(
                    "select movie_id, role FROM movie_employs WHERE cast_id = ?"
            );
            stmt.setInt(1, cast.getId());
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                int id = result.getInt(1);
                String role = result.getString(2);
                String text = id + "," + role;
                returnList.add(text);
            }
            return returnList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Queries the database to find the broadcast a cast has been on and his role.
     *
     * @param cast the cast you want to search for.
     * @return list of movie id and the corresponding role.
     * @author Teis & Nichlas & L@WsT
     */

    public List<String> castBroadcastRoles(ICast cast) {
        try {
            List<String> returnList = new ArrayList<>();
            PreparedStatement stmt = connection.prepareStatement(
                    "select broadcast_id, role FROM broadcast_employs WHERE cast_id = ?"
            );
            stmt.setInt(1, cast.getId());
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                int id = result.getInt(1);
                String role = result.getString(2);
                String text = id + "," + role;
                returnList.add(text);
            }
            return returnList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Queries the database for the id of parsed parameter object.
     * This is used internally when creating new entries.
     *
     * @param broadcast the object you want to find the id of in the database
     * @return the id of the entry found.
     * @author Teis & Nichlas
     */

    private int getBroadcastId(IBroadcast broadcast) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "select id FROM broadcast WHERE name = ? and air_date = ? and episode_number = ? and season_number = ?"
            );
            stmt.setString(1, broadcast.getName());
            String[] airdate = broadcast.getAirDate();
            stmt.setDate(2, Date.valueOf(LocalDate.of(
                    Integer.parseInt(airdate[2]),
                    Integer.parseInt(airdate[1]),
                    Integer.parseInt(airdate[0]))));
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

    /**
     * Queries the database for the id of parsed parameter object.
     * This is used internally when creating new entries.
     *
     * @param movie the object you want to find the id of in the database
     * @return the id of the entry found.
     * @author Teis & Nichlas
     */

    private int getMovieId(IMovie movie) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "select id FROM movie WHERE LOWER(name) ~ ? and release_date = ?"
            );
            stmt.setString(1, movie.getTitle().toLowerCase());
            String[] releaseDate = movie.getReleaseDate();
            stmt.setDate(2, Date.valueOf(LocalDate.of(
                    Integer.parseInt(releaseDate[0]), 1, 1)));

            ResultSet result = stmt.executeQuery();
            result.next();
            return result.getInt("id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;

    }

    /**
     * Queries the database for the id of parsed parameter object.
     * This is used internally when creating new entries.
     *
     * @param production the object you want to find the id of in the database
     * @return the id of the entry found.
     * @author Teis & Nichlas
     */
    private int getProductionId(IProduction production) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "select id FROM production WHERE name = ? and production.year = ?"
            );
            stmt.setString(1, production.getName());
            stmt.setDate(2, Date.valueOf(LocalDate.of(Integer.parseInt(production.getYear()), 1, 1)));
            ResultSet result = stmt.executeQuery();
            result.next();
            return result.getInt("id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;

    }

    /**
     * Queries the database for the id of parsed parameter object.
     * This is used internally when creating new entries.
     *
     * @param productionCompany the object you want to find the id of in the database
     * @return the id of the entry found.
     * @author Teis & Nichlas
     */
    private int getProductionCompanyId(IProductionCompany productionCompany) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "select id FROM production_company WHERE name = ?"
            );
            stmt.setString(1, productionCompany.getName());
            ResultSet result = stmt.executeQuery();
            result.next();
            return result.getInt("id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;

    }

    private void removeMovie(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "delete from movie where id = ?");
            stmt.setInt(1, id);
            stmt.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void removeProduction(int id) {
        try {
            PreparedStatement deleteBroadcastStatement = connection.prepareStatement(
                    "delete from broadcast using contains " +
                            "where broadcast.id = contains.broadcast_id " +
                            "and contains.production_id = ?");
            deleteBroadcastStatement.setInt(1, id);
            deleteBroadcastStatement.execute();

            PreparedStatement deleteProductionStatement = connection.prepareStatement(
                    "delete from production where production.id = ?");
            deleteProductionStatement.setInt(1, id);
            deleteProductionStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
