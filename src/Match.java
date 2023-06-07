import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Match {
    private int id;
    private Team homeTeam;
    private Team awayTeam;
    private List<Goal> goals;
    private long countHome;
    private long countAway;


    public Match(int id, Team homeTeam, Team awayTeam) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        Random random = new Random();
        List<Player> playersAtMatch = new ArrayList<>();
        playersAtMatch.addAll(homeTeam.getPlayers());
        playersAtMatch.addAll(awayTeam.getPlayers());
        AtomicInteger playerId = new AtomicInteger(1);
        this.goals = Stream.generate(()->{
            int currentId = playerId.getAndIncrement();
            return new Goal(currentId, playersAtMatch);
        })
                .limit(random.nextInt(7))
                .collect(Collectors.toList());



    }
    public String matchResult() {
        countHome = this.goals.stream()
                .map(Goal::getScorer)
                .filter(this.homeTeam.getPlayers()::contains)
                .count();

        countAway = this.goals.stream()
                .map(Goal::getScorer)
                .filter(this.awayTeam.getPlayers()::contains)
                .count();

        return countHome + " to:" + this.homeTeam.getName() + "\n" + countAway + " to:" + this.awayTeam.getName();
    }
public Map<Integer, Integer> goalAtPlayer() {
    return goals.stream()
            .collect(Collectors.groupingBy(
                    goal -> goal.getScorer().getId(),
                    Collectors.summingInt(goal -> 1)
            ));
}
    public long getCountHome() {
        return countHome;
    }

    public long getCountAway() {
        return countAway;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    @Override
    public String toString() {
        return "\n" + homeTeam.getName() +
                " vs " + awayTeam.getName() ;
    }

    public List<Goal> getGoals() {
        return goals;
    }

}
