package test.presentation;

import org.junit.Assert;
import org.junit.Test;
import org.openjfx.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class PersistenceTest {
    private Persistence instance;
    private List<String> castList;

    @org.junit.Before
    public void setUp() throws Exception {
        instance = Persistence.getInstance();
    }

    @org.junit.Test
    public void getCastFromDatabase() {
        castList = new ArrayList<>();
        castList.add("2,1234,Greta Tunfisk");
        Assert.assertArrayEquals(castList.toArray(), instance.getCastFromDatabase(2).toArray());
    }

    @Test
    public void getProductionFromDatabase() {
        List<String> productionList = new ArrayList<>();
        productionList.add("3,Lost,2009,2,6");
        Assert.assertArrayEquals(productionList.toArray(), instance.getProductionFromDatabase(3).toArray());
        Assert.assertArrayEquals(productionList.toArray(), instance.getProductionFromDatabase("lost").toArray());
    }

    @Test
    public void getProductionCompanyFromDatabase() {
        List<String> productionCompanyList = new ArrayList<>();
        productionCompanyList.add("2,Disney");
        Assert.assertArrayEquals(productionCompanyList.toArray(), instance.getProductionCompany(2).toArray());
        Assert.assertArrayEquals(productionCompanyList.toArray(), instance.getProductionCompany("disney").toArray());
    }

    @Test
    public void getCastRolesMoviesFromDatabase() {
        List<String> movieCastList = new ArrayList<>();
        movieCastList.add("4,The Bee");
        Assert.assertArrayEquals(movieCastList.toArray(), instance.getCastRolesMoviesFromDatabase(1).toArray());
    }

    @Test
    public void getCastRolesBroadcastFromDatabase() {
        List<String> broadcastCastList = new ArrayList<>();
        broadcastCastList.add("2,Actor");
        Assert.assertArrayEquals(broadcastCastList.toArray(), instance.getCastRolesBroadcastFromDatabase(6).toArray());
    }

    @Test
    public void getProductionName() {
        Assert.assertEquals("Lost", instance.getProductionName(3));
    }

    @Test
    public void getProductionCompanyIdOnProduction() {
        Assert.assertEquals(2, instance.getProductionCompanyIdOnProduction(3));
    }
}