package org.openjfx.domain;

import jdk.jfr.Timespan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openjfx.interfaces.ICast;

import java.util.Arrays;
import java.util.HashMap;

public class BroadcastTest {
    Broadcast broadcastTest;

    @Before
    public void setUp() throws Exception {
        HashMap<ICast, String> castMap = new HashMap<>();
        broadcastTest = new Broadcast(1, "Teis's Verden", 2, 3, "2020-19-05", castMap);
    }

    @Test
    public void getId() {
        Assert.assertSame(1, broadcastTest.getId());
    }

    @Test
    public void getName() {
        Assert.assertSame("Teis's Verden", broadcastTest.getName());
    }

    @Test
    public void getSeasonNumber() {
        Assert.assertSame(2, broadcastTest.getSeasonNumber());
    }

    @Test
    public void getEpisodeNumber() {
        Assert.assertSame(3, broadcastTest.getEpisodeNumber());
    }

    @Test
    public void getAirDate() {
        String[] dateArray = new String[]{"2020", "19", "05"};
        Assert.assertArrayEquals(dateArray, broadcastTest.getAirDate());
    }
}
