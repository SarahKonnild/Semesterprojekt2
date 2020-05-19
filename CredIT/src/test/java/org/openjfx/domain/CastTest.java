package org.openjfx.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CastTest {
    Cast testCast;

    @Before
    public void setUp() throws Exception {
        testCast = new Cast(1, "Teisaa2020", "Teis Aalbæk-Nielsen");
    }

    @Test
    public void testToString() {
        Assert.assertSame("Teis Aalbæk-Nielsen",testCast.toString());
    }

    @Test
    public void getId() {
        Assert.assertSame(1, testCast.getId());
    }

    @Test
    public void getName() {
        Assert.assertSame("Teis Aalbæk-Nielsen", testCast.getName());
    }

    @Test
    public void getRegDKID() {
        Assert.assertSame("Teisaa2020", testCast.getRegDKID());
    }
}