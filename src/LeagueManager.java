import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LeagueManager {
    private int teamId;
    private List<Team> teamList;
    private List<Cycle> leagueCycles;

    public LeagueManager(){
        this.createTeams();
        Random random = new Random();
        AtomicInteger idMatches = new AtomicInteger(1);
        List<Match> possibleMatches = IntStream.range(0, teamList.size())
                .boxed()
                .flatMap(homeTeam -> IntStream.range(homeTeam + 1, teamList.size())
                        .mapToObj(awayTeam -> {
                            int currentId = idMatches.getAndIncrement();
                            return new Match(currentId, teamList.get(homeTeam), teamList.get(awayTeam));
                        }))
                .collect(Collectors.toList());


        AtomicInteger idCycle = new AtomicInteger(1);
        this.leagueCycles = Stream.generate(() -> {
            List<Match> cycleList = List.of(possibleMatches.get(0),possibleMatches.get(1),possibleMatches.get(2)
                    ,possibleMatches.get(3),possibleMatches.get(4));
            if (Utils.MATCH_TIMES_AT_CYCLE <= possibleMatches.size()) {
                possibleMatches.subList(0, Utils.MATCH_TIMES_AT_CYCLE).clear();
            }
            int currentId = idCycle.getAndIncrement();
            Cycle cycle = new Cycle(currentId,cycleList);
            cycle.cycleResult();
            Map<Team,Integer> points = cycle.pointCalculation();
            Map<Team, Integer> sortPoints = points.entrySet().stream()
                    .sorted(Map.Entry.<Team, Integer>comparingByValue().reversed()
                            .thenComparing((e1, e2) -> {
                                int goalDifference1 = cycle.goalDifference().get(e1.getKey());
                                int goalDifference2 = cycle.goalDifference().get(e2.getKey());
                                return Integer.compare(goalDifference1, goalDifference2);
                            }) .thenComparing(Map.Entry.comparingByKey(
                                    Comparator.comparing(Team::getName))))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));


            sortPoints.forEach((team, score) ->
                    System.out.println("team: " + team.getName() + ", score: " + score));

            System.out.println( "enter your choose:" +
                                "\n 1.find matches by team"+
                                "\n 2.find players with at least N goals"+
                                "\n 3.find top scoring teams"+
                                "\n 4.get team by position"+
                                "\n 5.get top scorers");
            Scanner scanner=new Scanner(System.in);
            int choose = 0;
            try {
                choose = scanner.nextInt();
            } catch (Exception e) {
            }
            switch (choose){
                case 1:
                    System.out.println("choose id");
                    int idChoose= 0;
                    try {
                        idChoose = scanner.nextInt();
                    } catch (Exception e) {

                    }
                    List<Match> matchByTeam=cycle.findMatchesByTeam(idChoose);
                    if(matchByTeam.size()==0)
                        System.out.println("this team didnt play at this cycle" );
                    else
                        matchByTeam.forEach(match -> System.out.println(match.toString()));
                    break;
                case 2:
                    System.out.println("choose goals min");
                    int goalMinChoose= 0;
                    try {
                        goalMinChoose = scanner.nextInt();
                    } catch (Exception e) {

                    }
                    List<Player> minGoalsPlayers=cycle.findPlayersWithAtLeastNGoals(goalMinChoose);
                    if(minGoalsPlayers.size()==0)
                        System.out.println("No player has scored at least that number of goals");
                    else
                        minGoalsPlayers.forEach(player -> System.out.println(player.toString()));
                    break;
                case 3:
                    System.out.println("how many teams?");
                    int teamAmount= 0;
                    try {
                        teamAmount = scanner.nextInt();
                    } catch (Exception e) {

                    }
                    List<Map.Entry<Team,Long>> maxGoalTeam= cycle.findTopScoringTeams(teamAmount);
                    maxGoalTeam.stream()
                            .map(entry -> entry.getKey())
                            .forEach(team -> System.out.println(team.toString()));

                    break;
                case 4:
                    System.out.println("which position?");
                    int position = 0;
                    try {
                        position = scanner.nextInt();
                    } catch (Exception e) {

                    }
                    Team team =cycle.getTeamByPosition(position);
                    if (team!=null)
                        System.out.println(team.toString());
                    else
                        System.out.println("There is no group at this location");

                    break;
                case 5:
                    System.out.println("how many players");
                    int playersAmount = 0;
                    try {
                        playersAmount = scanner.nextInt();
                    } catch (Exception e) {
                    }
                    Map<Integer,Integer> players =cycle.getTopScorers(playersAmount);
                    players.forEach((playerId, goals) -> System.out.println
                            ("Player ID: " + playerId + ", Goals: " + goals));
                    if (players.size()<playersAmount)
                        System.out.println("this is the max players");
                    break;
                default:
                    System.out.println("not option");
                    break;
            }
            return cycle;
        }).limit(Utils.CYCLES_AMOUNT).collect(Collectors.toList());
    }


    private void createTeams() {
        List<String> teamsNames = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(Utils.PATH))) {
            teamsNames=lines.collect(Collectors.toList());
        } catch (IOException e) {
        }
        this.teamList = teamsNames.stream()
                .map(name -> new Team(name, Team.createTeam())).toList();
    }
    @Override
    public String toString() {
        return "LeagueManager{" +
                "teamId=" + teamId +
                ", teamList=" + teamList +
                ", leagueCycles=" + leagueCycles +
                '}';
    }



}
