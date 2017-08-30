package dao;

import models.Members;
import models.Teams;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;


import java.util.List;

public class Sql2oTeamsDao implements TeamDao {
    private final Sql2o sql2o;


    public Sql2oTeamsDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void addTeam(Teams teams) {
        String sql = "INSERT INTO teams (teamname,description) VALUES (:teamname, :description)";
        try(Connection con = sql2o.open()){
            int teamid = (int) con.createQuery(sql)
                    .addParameter("teamname", teams.getTeamname())
                    .addParameter("description", teams.getDescription())
                    .addColumnMapping("TEAMNAME", "teamname")
                    .addColumnMapping("DESCRIPTION", "description")

                    .executeUpdate()
                    .getKey();
            teams.setId(teamid);
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    @Override
    public List<Teams> getAllTeams() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM teams")
                    .executeAndFetch(Teams.class); //fetch a list
        }
    }

    @Override
    public Teams locateById(int number) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM teams WHERE teamid = :teamid")
                    .addParameter("teamid", number)
                    .executeAndFetchFirst(Teams.class);
        }
    }
    @Override
    public void update(String newName, String newDescription, int teamid){
        String sql = "UPDATE teams SET (teamname,description) = (:teamname, :description) WHERE teamid=:teamid";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("teamname", newName)
                    .addParameter("description", newDescription)
                    .addParameter("teamid",teamid )
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Members> getAllMembersByTeamId(int teamid) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM members WHERE teamid = :teamid")
                    .addParameter("teamid", teamid)
                    .executeAndFetch(Members.class);
        }
    }
    @Override
    public void deleteTeam(int teamid) {
        String sql = "DELETE FROM teams WHERE teamid=:teamid"; //raw sql
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("teamid", teamid)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
    @Override
    public void deleteAllTeams() {
        String sql = "DELETE FROM teams"; //raw sql
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

}
