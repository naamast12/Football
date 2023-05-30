import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LeagueManager {
    private List<Team> teamList;
    private List<List<Match>> leagueCycles;

    public LeagueManager(){
        this.teamList=createTeams();
        this.teamList = this.createTeams();
        List<Match> matchesAtCycle = new ArrayList<>();
//        matchesAtCycle.stream().collect(Match::new(this.teamList))
//        for (int i = 0; i < Utils.MATCH_TIMES_AT_CYCLE; i++) {
//            Match match = new Match(this.teamList);
//            matchesAtCycle.add(match);
//        }
        new Match(teamList);
//        this.leagueCycles.add(matchesAtCycle);

    }

    private List<Team> createTeams(){
        int teamId = 1;
        List<String> teamsNames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(Utils.PATH))) {
            String line = reader.readLine();
            teamsNames.stream()
                    .filter(line -> line != null)
                    .collect(Collectors.toList());
            while ((line = reader.readLine()) != null) {
                teamsNames.add(line);
            }
        } catch (IOException e) {

        }
        List<Team>teams = new ArrayList<>();
        for (String name : teamsNames) {
            Team team = new Team(teamId,name, Team.createTeam());
            teams.add(team);
            System.out.println(team.toString());
            teamId++;
        }
        return teams;

    }

    public List<Team> getTeamList() {
        return teamList;
    }
    //    public List<Match> findMatchesByTeam(int teamId){
//        //מתודה זו אמורה להחזיר רשימה של משחקים שקבוצה מסוימת שיחקה.
//    }
//    public List<Team> findTopScoringTeams(int n){
//        //מתודה זו אמורה להחזיר את n הקבוצות שהבקיעו את המספר הרב ביותר של השערים.
//    }
//    public List<Player> findPlayersWithAtLeastNGoals(int n){
//        //מתודה זו צריכה להחזיר רשימה של שחקנים שכבשו לפחות n  שערים.
//    }
//    public Team getTeamByPosition(int position){
//        //מתודה זו אמורה להחזיר את הקבוצה שנמצאת במיקום הנתון בטבלת הליגה.
//    }
//    public Map<Integer, Integer> getTopScorers(int n){
//        //מתודה זו מקבלת פרמטר n ואמורה להחזיר את n השחקנים שכבשו את המספר הרב ביותר של שערים, כשהם מאוחסנים במפה: מפתח – מזהה ייחודי של שחקן, ערך – כמות השערים שהבקיע.
//    }


}
