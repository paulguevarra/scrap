package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TeamsTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void newTeamsObjectGetsCreatedCorrectly_true() throws Exception {
        Teams teams = new Teams("TeamFusion");
        assertEquals(true, teams instanceof Teams);
    }

    @After
    public void tearDown() throws Exception {
    }

}