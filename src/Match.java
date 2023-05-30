import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Match {
    private static int id = 1;
    private Team homeTeam;
    private Team awayTeam;
    private List<Goal> goals;

    public Match(List<Team> teams) {
        this.id++;
        Random random = new Random();
        Team homeTeam = teams.get(random.nextInt(teams.size()));
        Team awayTeam = teams.get(random.nextInt(teams.size()));
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
        for (Goal goal:goals){
            System.out.println(goal.toString());
        }
//        for (int i = 0; i < goalsAmountHomeTeam; i++) {
//            goals.add(new Goal(this.homeTeam.getPlayers()));
//        }
//        for (int i = 0; i < goalsAmountAwayTeam; i++) {
//            goals.add(new Goal(this.awayTeam.getPlayers()));
//        }
    }


}
