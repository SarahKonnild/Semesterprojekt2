package persistence;

import org.junit.Assert;
import org.junit.Test;
import org.openjfx.interfaces.IPersistence;
import org.openjfx.persistence.Persistence;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PersistenceTest {
    private IPersistence instance;
    private Object SortComparator;

    @org.junit.Before
    public void setUp() throws Exception {
        instance = Persistence.getInstance();
    }

    @org.junit.Test
    public void getCastFromDatabase() {
        List<String> castList = new ArrayList<>();
        castList.add("1\tdaniel1999\tDaniel Radcliffe");
        Assert.assertArrayEquals(castList.toArray(), instance.getCastFromDatabase(1).toArray());
        Assert.assertArrayEquals(castList.toArray(), instance.getCastFromDatabase("daniel radcliffe").toArray());
    }

    @Test
    public void getProductionFromDatabase() {
        List<String> productionList = new ArrayList<>();
        productionList.add("1\tLucifer\t2015\t1\t2");
        Assert.assertArrayEquals(productionList.toArray(), instance.getProductionFromDatabase(1).toArray());
        Assert.assertArrayEquals(productionList.toArray(), instance.getProductionFromDatabase("lucifer").toArray());
    }

    @Test
    public void getProductionCompanyFromDatabase() {
        List<String> productionCompanyList = new ArrayList<>();
        productionCompanyList.add("1\tWarner Bros.");
        Assert.assertArrayEquals(productionCompanyList.toArray(), instance.getProductionCompany(1).toArray());
        Assert.assertArrayEquals(productionCompanyList.toArray(), instance.getProductionCompany("warner").toArray());
    }

    @Test
    public void getCastRolesMoviesFromDatabase() {
        List<String> movieCastList = new ArrayList<>();
        movieCastList.add("1\tHarry Potter");
        Assert.assertArrayEquals(movieCastList.toArray(), instance.getCastRolesMoviesFromDatabase(1).toArray());
    }

    @Test
    public void getCastRolesBroadcastFromDatabase() {
        List<String> broadcastCastList = new ArrayList<>();
        broadcastCastList.add("2\tLucifer");
        Assert.assertArrayEquals(broadcastCastList.toArray(), instance.getCastRolesBroadcastFromDatabase(2).toArray());
    }

    @Test
    public void getProductionCompanyIdOnProduction() {
        Assert.assertEquals(1, instance.getProductionCompanyIdOnProduction(1));
    }

    @Test
    public void getBroadcast() {
        LinkedList<String> broadcastList = new LinkedList<>();
        broadcastList.add("1\tPilot\t1\t1\t25-1-2016");
        Assert.assertArrayEquals(broadcastList.toArray(), instance.getBroadcastFromDatabase(1).toArray());
        Assert.assertArrayEquals(broadcastList.toArray(), instance.getBroadcastFromDatabase("PILOT").toArray());
        broadcastList.addFirst("2\tLucifer, Stay. Good Devil\t1\t2\t1-2-2016");
        Assert.assertArrayEquals(broadcastList.toArray(), instance.getBroadcastsFromDatabase(1).toArray());
    }

    @Test
    public void getMovie() {
        List<String> movieList = new ArrayList<>();
        movieList.add("1\tHarry Potter and the Sorcerer's Stone\t1-1-2001");
        Assert.assertArrayEquals(movieList.toArray(), instance.getMovieFromDatabase(1).toArray());
        Assert.assertArrayEquals(movieList.toArray(), instance.getMovieFromDatabase("Harry Potter and the sorcerer's stone").toArray());
        Assert.assertArrayEquals(movieList.toArray(), instance.getMoviesFromDatabase(1).toArray());
    }
}