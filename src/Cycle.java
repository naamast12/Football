import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cycle {
    List<Match> matchesAtCycle;
    private int second;
    int id;
    private Map<String,Integer> points;


    public Cycle(int id, List<Match> matchesAtCycle) {
        this.id = id;
        this.matchesAtCycle = matchesAtCycle;

    }

    public List<Match> getMatchesAtCycle() {
        return matchesAtCycle;
    }

    @Override
    public String toString() {
        return "Cycle{" +
                "\n, id cycle=" + id +
                "\n  matchesAtCycle=" + matchesAtCycle +
                '}';
    }

    public void cycleResult() {
        this.matchesAtCycle.stream()
                .forEach(match -> {
                    System.out.println(match.toString());
                    countDown(3);
                    System.out.println(match.matchResult());
                });
    }

    public void countDown(int count) {
        if (count > 0) {
            System.out.println(count);
            Utils.sleep(1000);
            countDown(count - 1);
        }
    }
    public Map<String,Integer> pointCalculation(){
        this.points= new HashMap<>();
        for (Match match:this.matchesAtCycle ) {
            points.put(match.getHomeTeam().getName(), 0);
            points.put(match.getAwayTeam().getName(), 0);
        }
        for (Match match:this.matchesAtCycle ) {
            if (match.getCountHome()>match.getCountAway()) {
                String winnerTeamName = match.getHomeTeam().getName();
                int pointValue = this.points.get(winnerTeamName);
                this.points.put(winnerTeamName, pointValue + Utils.ADD_TO_WINNER);
            }
            else if (match.getCountHome()<match.getCountAway()) {
                String winnerTeamName = match.getAwayTeam().getName();
                int pointValue = this.points.get(winnerTeamName);
                this.points.put(winnerTeamName, pointValue + Utils.ADD_TO_WINNER);
            }
            else {
                String homeName = match.getHomeTeam().getName();
                int pointHomeValue = this.points.get(homeName);
                this.points.put(homeName,pointHomeValue  + Utils.EQUALITY);
                String awayName = match.getAwayTeam().getName();
                int pointAwayValue = this.points.get(awayName);
                this.points.put(awayName, pointAwayValue + Utils.EQUALITY);
            }

        }
        return this.points;
    }

    public List<Match> findMatchesByTeam(int teamId) {
        List<Match> matchByTeam = new ArrayList<>();
        matchByTeam= this.matchesAtCycle.stream()
                .filter(match -> (match.getHomeTeam().getId()==teamId)||match.getAwayTeam().getId()==teamId)
                .toList();
        return matchByTeam;
    }
//    public List<Team> findTopScoringTeams(int n){
//        //מתודה זו אמורה להחזיר את n הקבוצות שהבקיעו את המספר הרב ביותר של השערים.
//        List<Team> team = matchesAtCycle.stream()
//                .max(match -> match.getGoals().size());
//    }
    public List<Player> findPlayersWithAtLeastNGoals(int n){
        //מתודה זו צריכה להחזיר רשימה של שחקנים שכבשו לפחות n  שערים.
            return this.matchesAtCycle.stream()
                    .flatMap(match -> match.getGoals().stream())
                    .collect(Collectors.groupingBy(Goal::getScorer, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() >= n)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }

    }
//    public Team getTeamByPosition(int position){
//        //מתודה זו אמורה להחזיר את הקבוצה שנמצאת במיקום הנתון בטבלת הליגה.
//    }
//    public Map<Integer, Integer> getTopScorers(int n){
//        //מתודה זו מקבלת פרמטר n ואמורה להחזיר את n השחקנים שכבשו את המספר הרב ביותר של שערים, כשהם מאוחסנים במפה: מפתח – מזהה ייחודי של שחקן, ערך – כמות השערים שהבקיע.
//    }




