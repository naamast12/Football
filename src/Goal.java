import java.util.List;
import java.util.Random;

public class Goal {
    private int id;
    private int minute;
    private Player scorer;

    public Goal(int id, List<Player> players) {
        this.id=id;
        Random random = new Random();
        this.minute = random.nextInt(Utils.START_GAME,Utils.END_GAME);
        this.scorer = players.get(random.nextInt(players.size()));
        this.id++;

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
