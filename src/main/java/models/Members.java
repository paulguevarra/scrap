package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Members {

    private int teamid;
    private String membername;
    private int memberid;

    public Members(String membername, int teamid){
        this.membername = membername;
        this.teamid = teamid;
    }
    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }
    public void setMembername(String membername){ this.membername=membername;}
    public void setTeamid(int teamid){ this.teamid=teamid;}

    public String getMembername() {
        return membername;
    }
    public int getTeamid() {
        return teamid;
    }

    public int getMemberid() {
        return memberid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Members members = (Members) o;

        if (teamid != members.teamid) return false;
        return membername.equals(members.membername);
    }

    @Override
    public int hashCode() {
        int result = teamid;
        result = 31 * result + membername.hashCode();
        return result;
    }
}
