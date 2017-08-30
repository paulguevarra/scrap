
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Sql2oMembersDao;
import dao.Sql2oTeamsDao;
import models.Members;
import models.Teams;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oMembersDao memberDao = new Sql2oMembersDao(sql2o);
        Sql2oTeamsDao teamDao = new Sql2oTeamsDao(sql2o);
//
        //get: show new team form
        get("/teams/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Teams> teams = teamDao.getAllTeams();
            model.put("teams", teams);
            return new ModelAndView(model, "newteam-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process new team form
        post("/teams/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String teamName = request.queryParams("teamname");
            String teamDescription = request.queryParams("description");
            Teams teams = new Teams(teamName, teamDescription);
            teamDao.addTeam(teams);
            List<Teams>teamsList=teamDao.getAllTeams();
            model.put("teams",teams);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show all teams list
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Teams> teamsList = teamDao.getAllTeams();
            model.put("teams", teamsList);
            List<Members> membersList = memberDao.getAllMembers();
            model.put("members", membersList);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show an individual team
        get("/teams/:teamid", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTeamToFind = Integer.parseInt(request.params("teamid"));
            List<Teams> teamsList = teamDao.getAllTeams();
            Teams foundTeam = teamDao.locateById(idOfTeamToFind);
            model.put("teams", foundTeam);
            List<Members> membersList = teamDao.getAllMembersByTeamId(idOfTeamToFind);
            model.put("members",membersList);
            return new ModelAndView(model, "team-detail.hbs");
        }, new HandlebarsTemplateEngine());
//
//        //get: show a form to update an individual team
//        get("/teams/:id/update", (Request request, Response response) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfTeamToEdit = Integer.parseInt(request.params("teamid"));
//            System.out.println(idOfTeamToEdit);
//            Teams editTeam = teamDao.locateById(idOfTeamToEdit);
//            model.put("editTeam", editTeam);
//            return new ModelAndView(model, "newteam-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //get: delete a member of a specific team
//        get("/teams/:id/members/delete", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfMember = Integer.parseInt(req.params("id"));
//            memberDao.deleteMember(idOfMember);
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //post: process a form to update an individual team
//        post("/teams/:teamid/update", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            String newTeamName = request.queryParams("teamName");
//            String newDescription = request.queryParams("description");
//            String newMemberName = request.queryParams("membername");
//            int idOfTeamToEdit = Integer.parseInt(request.params("teamid"));
//            Teams editTeams = teamDao.locateById(idOfTeamToEdit);
//            teamDao.update(newTeamName,newDescription, teamDao.locateById(idOfTeamToEdit).getId());
//            Members newMember = new Members(newMemberName, idOfTeamToEdit);
//            memberDao.addMember(newMember);
//            model.put("member", newMember);
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //remove a team
//
//        //get: delete a team and its members
//        get("/teams/:id/delete", (Request req, Response res) ->{
//            Map<String, Object> model = new HashMap<>();
//            int id = Integer.parseInt(req.params("id"));
//            List<Members> teamMembers =teamDao.getAllMembersByTeamId(id);
//            teamDao.deleteTeam(id);
//            teamMembers.clear();
//            List<Teams> teams = teamDao.getAllTeams();
//            List<Members> members = memberDao.getAllMembers();
//            model.put("teams", teams);
//            model.put("members", members);
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());
//        //Members:
//        //get: display member form
//        get("/members/new", (req,res)->{
//            Map<String, Object> model = new HashMap<>();
//            List<Teams> teams = teamDao.getAllTeams();
//            List<Members> members = memberDao.getAllMembers();
//            model.put("teams",teams);
//            model.put("members",members);
//            return new ModelAndView(model, "newmember-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //post: process member team form
//        post("/members/new", (req,res)->{
//            Map<String, Object> model = new HashMap<>();
//            String teamChoice= req.queryParams("team");
//            int teamId = Integer.parseInt(teamChoice);
//            Teams foundTeam = teamDao.locateById(teamId);
//            String memberName = req.queryParams("member-name");
//            Members newMember = new Members(memberName, foundTeam.getId());
//            memberDao.addMember(newMember);
//            List<Members> members = memberDao.getAllMembers();
//            List<Teams> teams = teamDao.getAllTeams();
//            model.put("members", members);
//            model.put("teams", teams);
//            return new ModelAndView(model,"success.hbs");
//        }, new HandlebarsTemplateEngine());

    }
}

