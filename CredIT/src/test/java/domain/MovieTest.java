package domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openjfx.domain.Cast;
import org.openjfx.domain.Movie;
import org.openjfx.interfaces.ICast;

import java.util.HashMap;

public class MovieTest {
    Movie movie;

    @Before
    public void setUp() throws Exception {
        HashMap<Cast, String> castMap = new HashMap<>();
        movie = new Movie(1, "Teis På Is", "11-09-2020", castMap);
    }

    @Test
    public void string() {
        Assert.assertEquals("Teis På Is (2020)", movie.toString());
    }

    @Test
    public void getId() {
        Assert.assertSame(1, movie.getId());
    }

    @Test
    public void getTitle() {
        Assert.assertSame("Teis På Is", movie.getTitle());
    }

    @Test
    public void getReleaseDate() {
        String[] dateArray = new String[]{"11", "09", "2020"};
        Assert.assertArrayEquals(dateArray, movie.getReleaseDate());
    }
}
