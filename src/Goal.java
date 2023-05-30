import java.util.List;
import java.util.Random;

public class Goal {
    private static int id;
    private int minute;
    private Player scorer;

    public Goal(List<Player> players) {
        this.id++;
        Random random = new Random();
        this.minute = random.nextInt(Utils.START_GAME,Utils.END_GAME);
        this.scorer = players.get(random.nextInt(players.size()));
    }

    @Override
    public String toString() {
        return "Goal{" +
                "id="+this.id+
                "minute=" + minute +
                ", scorer=" + scorer +
                '}';
    }
}
