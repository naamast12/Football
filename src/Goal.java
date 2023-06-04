import java.util.List;
import java.util.Random;

public class Goal {
    private int id;
    private int minute;
    private Player scorer;


    public Goal(int id, Team homeTeam, Team awayTeam) {
        this.id=id;

        Random random = new Random();
        this.minute = random.nextInt(Utils.START_GAME,Utils.END_GAME);
        this.scorer = players.get(random.nextInt(players.size()));
        this.id++;

    }

    public Player getScorer() {
        return scorer;
    }

    @Override
    public String toString() {
        return "\n           Goal{" +
                "id="+this.id+
                ", minute=" + minute +
                ", scorer=" + scorer +
                '}';
    }
}
