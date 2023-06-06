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
//        Collections.shuffle(possibleMatches);

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
            Map<String,Integer> points = cycle.pointCalculation();
            Map<String, Integer> sortedByValue = points.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
//                            .thenComparing(Map.Entry.comparingByKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));

            for (Map.Entry<String, Integer> entry : sortedByValue.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                System.out.println("team: " + key + ", score: " + value);
            }
            System.out.println("bla bla");
            Scanner scanner=new Scanner(System.in);
            String choose=scanner.nextLine();
//            cycle.findMatchesByTeam(3);
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
