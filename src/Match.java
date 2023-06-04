import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Match {
    private int id;
    private Team homeTeam;
    private Team awayTeam;
    private List<Goal> goals;

    public Match(int id,List<Team> teams) {
        this.id=id;
        Random random = new Random();
        Team homeTeam = teams.get(random.nextInt(teams.size()));
        Team awayTeam = teams.get(random.nextInt(teams.size()));
        while (homeTeam.equals(awayTeam))
            awayTeam = teams.get(random.nextInt(teams.size()));
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        int goalsAmountHomeTeam = random.nextInt(Utils.MAX_SCORE_TEAM);
        int goalsAmountAwayTeam = random.nextInt(Utils.MAX_SCORE_TEAM);
        AtomicInteger playerId = new AtomicInteger(0);
        List<Goal> homeGoals = Stream.generate(() -> {
                    int currentId = playerId.getAndIncrement();
                    return new Goal(currentId, this.homeTeam.getPlayers());
                })
                .limit(goalsAmountHomeTeam)
                .collect(Collectors.toList());
        List<Goal> awayGoals = Stream.generate(() -> {
                    int currentId = playerId.getAndIncrement();
                    return new Goal(currentId, this.awayTeam.getPlayers());
                })
                .limit(goalsAmountAwayTeam)
                .collect(Collectors.toList());
        this.goals=new ArrayList<>();
        this.goals.addAll(homeGoals);
        this.goals.addAll(awayGoals);
//        System.out.println(this.homeTeam.toString());
//        System.out.println(this.awayTeam.toString());
//        for (Goal goal:goals){
//            System.out.println(goal.toString());
//        }
    }

    public Match(int id, Team homeTeam, Team awayTeam) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

//    public String toString() {
//        return "\nMatch{" +
//                "id=" + id +
//                "\n       homeTeam=" + homeTeam +
//                "\n       awayTeam=" + awayTeam +
//                ", goals=" + goals +
//                '}';
//    }


    @Override
    public String toString() {
        return "Match{" +
                "\n   id=" + id +
                "\n         , homeTeam=" + homeTeam +
                "\n         , awayTeam=" + awayTeam +
                '}';
    }
}
