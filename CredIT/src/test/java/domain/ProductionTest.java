package domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openjfx.domain.Production;
import org.openjfx.interfaces.IBroadcast;

import java.util.ArrayList;

public class ProductionTest {
    Production production;

    @Before
    public void setUp() throws Exception {
        ArrayList<IBroadcast> broadcastList = new ArrayList<>();
        production = new Production(1, "Teis til lands, til vands og i luften", "2020", 2, 3, broadcastList);
    }

    @Test
    public void string() {
        Assert.assertEquals("Teis til lands, til vands og i luften: 2020", production.toString());
    }

    @Test
    public void getId() {
        Assert.assertSame(1, production.getId());
    }

    @Test
    public void getName() {
        Assert.assertSame("Teis til lands, til vands og i luften", production.getName());
    }

    @Test
    public void getYear() {
        Assert.assertSame("2020", production.getYear());
    }

    @Test
    public void getNumberOfSeasons() {
        Assert.assertSame(2, production.getNumberOfSeasons());
    }

    @Test
    public void getNumberOfEpisodes() {
        Assert.assertSame(3, production.getNumberOfEpisodes());
    }
}

