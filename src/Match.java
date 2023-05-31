import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Match {
    private int id;
    private Team homeTeam;
    private Team awayTeam;
    private List<Goal> goals;

    public Match(List<Team> teams) {
        this.id++;
        Random random = new Random();
        Team homeTeam = teams.get(random.nextInt(teams.size()));
        Team awayTeam = teams.get(random.nextInt(teams.size()));
        //TODO
        while (homeTeam.equals(awayTeam))
            awayTeam = teams.get(random.nextInt(teams.size()));
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        int goalsAmountHomeTeam = random.nextInt(Utils.MAX_SCORE_TEAM);
        int goalsAmountAwayTeam = random.nextInt(Utils.MAX_SCORE_TEAM);
        List<Goal> homeGoals = Stream.generate(() -> new Goal(this.homeTeam.getPlayers()))
                .limit(goalsAmountHomeTeam)
                .collect(Collectors.toList());
        List<Goal> awayGoals = Stream.generate(() -> new Goal(this.awayTeam.getPlayers()))
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

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", goals=" + goals +
                '}';
    }
}
