package dao;

import models.Members;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oMembersDao implements MemberDao{
    private final Sql2o sql2o;
    public Sql2oMembersDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void addMember(Members members) {
        String sql = "INSERT INTO members (membername, teamid) VALUES (:membername, :teamid)";
        try(Connection con = sql2o.open()){
            int memberid = (int) con.createQuery(sql)
                    .addParameter("membername", members.getMemberName())
                    .addParameter("teamid", members.getTeamId())
                    .addColumnMapping("MEMBERNAME", "membername")
                    .addColumnMapping("TEAMID", "teamid")
                    .executeUpdate()
                    .getKey();
            members.setMemberId(memberid);
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    @Override
    public List<Members> getAllMembers() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM members")
                    .executeAndFetch(Members.class);
        }
    }
    public List<Members> getAllMembersByTeam(int teamId) {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("SELECT * FROM members WHERE teamId = :teamId")
                    .addParameter("teamId", teamId)
                    .addColumnMapping("TEAMID", "teamId")
                    .addColumnMapping("NAME", "memberName")
                    .executeAndFetch(Members.class);
        }
    }
    @Override
    public Members locateMemberById(int memberid) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM members WHERE memberid = :memberid")
                    .addParameter("memberid", memberid)
                    .executeAndFetchFirst(Members.class);
        }
    }

    @Override
    public void updateMember(String membername, int teamid, int memberid) {
        String sql = "UPDATE members SET (membername, teamid) = (:membername, :teamid) WHERE memberid=:memberid";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("membername", membername)
                    .addParameter("teamid", teamid)
                    .addParameter("memberid", memberid)
                    .executeUpdate();
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteMember(int memberid){
        String sql = "DELETE from members WHERE memberid=:memberid";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("memberid", memberid)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void deleteAllMembers() {
        String sql = "DELETE from members";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

}
