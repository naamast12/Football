import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LeagueManager {
    private int teamId;
    private List<Team> teamList;
    private Map <Integer, Match> matchesAtCycles;
    private List<List<Match>> leagueCycles;

    public LeagueManager(){
        this.createTeams();
//        List<Match> possibleMatches = new ArrayList<>();
//        for (int homeTeam = 0; homeTeam<this.teamList.size(); homeTeam++){
//            for (int awayTeam = 1; awayTeam <this.teamList.size() ; awayTeam++) {
//                if (homeTeam!=awayTeam&&homeTeam<awayTeam)
//                    possibleMatches.add(new Match(teamList.get(homeTeam),teamList.get(awayTeam)));
//            }
//        }
        Random random = new Random();
        AtomicInteger id = new AtomicInteger(1);
        List<Match> possibleMatches = IntStream.range(0, teamList.size())
                .boxed()
                .flatMap(homeTeam -> IntStream.range(homeTeam + 1, teamList.size())
                        .mapToObj(awayTeam -> {
                            int currentId = id.getAndIncrement();
                            return new Match(currentId, teamList.get(homeTeam), teamList.get(awayTeam));
                        }))
                .collect(Collectors.toList());
        Collections.shuffle(possibleMatches);

        this.leagueCycles = Stream.generate(() -> {
            List<Match> cycle = List.of(possibleMatches.get(0),possibleMatches.get(1),possibleMatches.get(2)
                    ,possibleMatches.get(3),possibleMatches.get(4));
            if (Utils.MATCH_TIMES_AT_CYCLE <= possibleMatches.size()) {
                possibleMatches.subList(0, Utils.MATCH_TIMES_AT_CYCLE).clear();
            }
            return cycle;
        }).limit(Utils.CYCLES_AMOUNT).collect(Collectors.toList());
        System.out.println(leagueCycles.toString());
//        this.leagueCycles = Stream.generate(() -> {
//                    AtomicInteger playerId = new AtomicInteger(1);
//                    this.matchesAtCycles = new HashMap<>();
//                    this.matchesAtCycles=teamList.stream()
//                            .flatMap()
//                    List<Match> matchesAtCycle= Stream.generate(()->{
//                                int currentId = playerId.getAndIncrement();
//                                return new Match(currentId,this.teamList);
//                            })
//                            .limit(Utils.MATCH_TIMES_AT_CYCLE).distinct()
//                            .toList();
//                    return matchesAtCycle;
//                })
//                .limit(Utils.CYCLES_AMOUNT).distinct()
//                .collect(Collectors.toList());
//        System.out.println(this.leagueCycles.toString());

    }

    private void createTeams() {
        List<String> teamsNames = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(Utils.PATH))) {
            teamsNames=lines.collect(Collectors.toList());
        } catch (IOException e) {
        }
        this.teamList = teamsNames.stream()
                .map(name -> new Team(name, Team.createTeam())).toList();
        for (Team team : this.teamList) {
//            System.out.println(team.toString());
        }
    }

    @Override
    public String toString() {
        return "LeagueManager{" +
                "teamId=" + teamId +
                ", teamList=" + teamList +
                ", leagueCycles=" + leagueCycles +
                '}';
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
