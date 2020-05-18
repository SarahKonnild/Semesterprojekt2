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
        castList.add("1,daniel1999,Daniel Radcliffe");
        Assert.assertArrayEquals(castList.toArray(), instance.getCastFromDatabase(1).toArray());
    }

    @Test
    public void getProductionFromDatabase() {
        List<String> productionList = new ArrayList<>();
        productionList.add("1,Lucifer,2015,1,2");
        Assert.assertArrayEquals(productionList.toArray(), instance.getProductionFromDatabase(1).toArray());
        Assert.assertArrayEquals(productionList.toArray(), instance.getProductionFromDatabase("lucifer").toArray());
    }

    @Test
    public void getProductionCompanyFromDatabase() {
        List<String> productionCompanyList = new ArrayList<>();
        productionCompanyList.add("1,Warner Bros.");
        Assert.assertArrayEquals(productionCompanyList.toArray(), instance.getProductionCompany(1).toArray());
        Assert.assertArrayEquals(productionCompanyList.toArray(), instance.getProductionCompany("warner").toArray());
    }

    @Test
    public void getCastRolesMoviesFromDatabase() {
        List<String> movieCastList = new ArrayList<>();
        movieCastList.add("1,Harry Potter");
        Assert.assertArrayEquals(movieCastList.toArray(), instance.getCastRolesMoviesFromDatabase(1).toArray());
    }

    @Test
    public void getCastRolesBroadcastFromDatabase() {
        List<String> broadcastCastList = new ArrayList<>();
        broadcastCastList.add("2,Lucifer");
        Assert.assertArrayEquals(broadcastCastList.toArray(), instance.getCastRolesBroadcastFromDatabase(2).toArray());
    }

    @Test
    public void getProductionName() {
        Assert.assertEquals("Lucifer", instance.getProductionName(1));
    }

    @Test
    public void getProductionCompanyIdOnProduction() {
        Assert.assertEquals(1, instance.getProductionCompanyIdOnProduction(1));
    }


}