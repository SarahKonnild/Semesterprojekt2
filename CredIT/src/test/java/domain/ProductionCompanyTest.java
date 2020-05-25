package domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openjfx.domain.Movie;
import org.openjfx.domain.Production;
import org.openjfx.domain.ProductionCompany;
import org.openjfx.interfaces.IMovie;
import org.openjfx.interfaces.IProduction;

import java.util.ArrayList;

public class ProductionCompanyTest {
    ProductionCompany productionCompany;

    @Before
    public void setUp() {
        ArrayList<Movie> movieArraylist = new ArrayList<>();
        ArrayList<Production> productionArrayList = new ArrayList<>();
        productionCompany = new ProductionCompany(1, "Teis Productions", movieArraylist, productionArrayList);
    }

    @Test
    public void string() {
        Assert.assertEquals("Teis Productions", productionCompany.toString());
    }

    @Test
    public void getName() {
        Assert.assertEquals("Teis Productions", productionCompany.getName());
    }

    @Test
    public void getId() {
        Assert.assertSame(1, productionCompany.getId());
    }
}
