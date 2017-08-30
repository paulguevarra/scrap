package dao;

import models.Members;
import models.Teams;

import java.util.List;

public interface TeamDao {
    //create
    void addteam (Teams teams);

    //read
    List<Teams> getAllteams();
    List<Members>getAllmembersbyteamid(int teamid);
    Teams locatebyid(int teamid);
    //update
    void update(String teamname, String description, int teamid);
    //delete
    void deleteteam(int teamid);
    void deleteallteams();

}
