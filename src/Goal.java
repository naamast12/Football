import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Goal {
    private int id;
    private int minute;
    private Player scorer;


    public Goal(int id, List<Player> playersAtMatch) {
        this.id=id;
        Random random = new Random();
        this.minute = random.nextInt(Utils.START_GAME,Utils.END_GAME);
        this.scorer = playersAtMatch.get(random.nextInt(playersAtMatch.size()));

    }

    public Player getScorer() {
        return scorer;
    }

}
