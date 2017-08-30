package dao;

import models.Teams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oTeamsDaoTest {
    private Sql2oTeamsDao teamDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        final String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        teamDao = new Sql2oTeamsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }
    //helper
    public Teams setupTeam (){
        return new Teams("Team Fusion", "taste our kung fu");
    }
    public Teams setupTeam2 () {
        return new Teams("Golden Dragons", "the one and only");
    }

    @Test
    public void addingTeamSetsId() throws Exception {
        Teams teams = setupTeam();
        int originalTeamId = teams.getId();
        teamDao.addTeam(teams);
        assertNotEquals(originalTeamId, teams.getId());
    }
    @Test
    public void existingTeamsCanBeFoundById() throws Exception {
        Teams teams = setupTeam();
        teamDao.addTeam(teams);
        Teams foundTeam = teamDao.locateById(teams.getId());
        assertEquals(teams, foundTeam);
    }

    @Test
    public void addedTeamsAreReturnedFromgetAll() throws Exception {
        Teams teams = setupTeam();
        teamDao.addTeam(teams);
        assertEquals(1, teamDao.getAllTeams().size());
    }

    @Test
    public void noTeamsReturnsEmptyList() throws Exception {
        assertEquals(0, teamDao.getAllTeams().size());
    }

    @Test
    public void locateTeamById() throws Exception {
        Teams teams = setupTeam();
        Teams anotherTeam = setupTeam2();
        teamDao.addTeam(teams);
        teamDao.addTeam(anotherTeam);
        assertEquals(anotherTeam, teamDao.locateById(anotherTeam.getId()));
    }
    @Test
    public void removeTeam() throws Exception {
        Teams test = setupTeam();
        teamDao.addTeam(test);

        Teams test2 = setupTeam2();
        teamDao.addTeam(test2);
        teamDao.deleteTeam(test2.getId());
        assertEquals(1, teamDao.getAllTeams().size());
    }


}