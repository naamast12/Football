import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cycle {
    List<Match> matchesAtCycle;
    int id;
    private Map<Team, Integer> points;


    public Cycle(int id, List<Match> matchesAtCycle) {
        this.id = id;
        this.matchesAtCycle = matchesAtCycle;

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
                    countDown(1);
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

    public Map<Team, Integer> pointCalculation() {
        this.points = new HashMap<>();
//        for (Match match : this.matchesAtCycle) {
//            points.put(match.getHomeTeam(), 0);
//            points.put(match.getAwayTeam(), 0);
//        }
        this.matchesAtCycle.stream()
                .flatMap(match -> Stream.of(match.getHomeTeam(), match.getAwayTeam()))
                .distinct()
                .forEach(team -> points.put(team, 0));

        for (Match match : this.matchesAtCycle) {
            if (match.getCountHome() > match.getCountAway()) {
                Team winnerTeam = match.getHomeTeam();
                int pointValue = this.points.get(winnerTeam);
                this.points.put(winnerTeam, pointValue + Utils.ADD_TO_WINNER);
            } else if (match.getCountHome() < match.getCountAway()) {
                Team winnerTeam = match.getAwayTeam();
                int pointValue = this.points.get(winnerTeam);
                this.points.put(winnerTeam, pointValue + Utils.ADD_TO_WINNER);
            } else {
                Team home = match.getHomeTeam();
                int pointHomeValue = this.points.get(home);
                this.points.put(home, pointHomeValue + Utils.EQUALITY);
                Team away = match.getAwayTeam();
                int pointAwayValue = this.points.get(away);
                this.points.put(away, pointAwayValue + Utils.EQUALITY);
            }
        }

        return this.points;
    }

    public Map<Team,Integer> goalDifference () {
        int goalDifferenceHome = 0;
        int goalDifferenceAway = 0;
        Map<Team, Integer> goalDifferenceMap = new HashMap<>();
        for (Match match : this.matchesAtCycle) {
            goalDifferenceMap.put(match.getHomeTeam(), 0);
            goalDifferenceMap.put(match.getAwayTeam(), 0);
        }
        for (Match difference : this.matchesAtCycle) {
                goalDifferenceHome += difference.getCountHome() - difference.getCountAway();
                goalDifferenceAway += difference.getCountAway() - difference.getCountHome();
                Team nameHome = difference.getHomeTeam();
                Team nameAway = difference.getAwayTeam();
                int valueHome = goalDifferenceMap.get(nameHome);
                int valueAway = goalDifferenceMap.get(nameAway);
                goalDifferenceMap.put(nameHome, valueHome + goalDifferenceHome);
                goalDifferenceMap.put(nameAway, valueAway + goalDifferenceAway);
        }
        return goalDifferenceMap;
    }

    public List<Match> findMatchesByTeam(int teamId) {
        List<Match> matchByTeam = new ArrayList<>();
        matchByTeam = this.matchesAtCycle.stream()
                .filter(match -> (match.getHomeTeam().getId() == teamId) || match.getAwayTeam().getId() == teamId)
                .toList();
        return matchByTeam;
    }

    public List<Map.Entry<Team, Long>> findTopScoringTeams(int n) {
        //מתודה זו אמורה להחזיר את n הקבוצות שהבקיעו את המספר הרב ביותר של השערים.
        Map<Team, Long> goalTeam = new HashMap<>();
        for (Match match : this.matchesAtCycle) {
            goalTeam.put(match.getHomeTeam(), match.getCountHome());
            goalTeam.put(match.getAwayTeam(), match.getCountAway());
        }
        List<Map.Entry<Team, Long>> topN = goalTeam.entrySet()
                .stream()
                .sorted(Map.Entry.<Team, Long>comparingByValue().reversed())
                .limit(n)
                .collect(Collectors.toList());
        return topN;

    }

    public List<Player> findPlayersWithAtLeastNGoals(int n) {
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


    public Team getTeamByPosition(int position) {
//        מתודה זו אמורה להחזיר את הקבוצה שנמצאת במיקום הנתון בטבלת הליגה
        Optional<Map.Entry<Team, Integer>> positionTeam = points.entrySet()
                .stream()
                .skip(position - 1)
                .findFirst();
        Team team;
        if (positionTeam.isPresent()) {
            Map.Entry<Team, Integer> entry = positionTeam.get();
            Team key = entry.getKey();
            team = key;
        } else {
            team = null;
        }
        return team;
    }

    public Map<Integer, Integer> getTopScorers(int n) {
//        //מתודה זו מקבלת פרמטר n ואמורה להחזיר את n השחקנים שכבשו את המספר הרב ביותר של שערים,
//        כשהם מאוחסנים במפה: מפתח – מזהה ייחודי של שחקן, ערך – כמות השערים שהבקיע.
        Map<Integer, Integer> goalsByPlayer = matchesAtCycle.stream()
                .flatMap(match -> match.goalAtPlayer().entrySet().stream())
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.summingInt(Map.Entry::getValue)
                ));
        Map<Integer, Integer> sortByPlayers =goalsByPlayer.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                    .limit(n)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));

        return  sortByPlayers;
    }

    public int compareToByDifference(Map.Entry<String, Integer> entry1, Map.Entry<String , Integer> entry2) {
        Map<Team,Integer>  goalsDifferenceTeam = goalDifference();
        return goalsDifferenceTeam.get(entry1)-goalsDifferenceTeam.get(entry2);
    }
//    public int compareToByName(Map.Entry<String, Integer> entry1, Map.Entry<String , Integer> entry2) {
//
//
//        return name1.compareToIgnoreCase(name2);
//    }

}


