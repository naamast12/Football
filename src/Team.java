import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Team {
    private int id;
    private String name;
    private List<Player> players;

    public Team(int id, String name, List<Player> players) {
        this.id = id;
        this.name = name;
        this.players = players;
    }

    public static List<Player> createTeam(){
        Random random = new Random();
        List<Player> playerList = new ArrayList<>();
        int playerId = 1;
        for (int i = 0; i < 15; i++) {
            String firstName = Utils.PLAYERS_FIRST_NAMES.get(random.nextInt(0,30));
            String lastName = Utils.PLAYERS_LAST_NAMES.get(random.nextInt(0,30));
            Player player = new Player(playerId,firstName,lastName);
            playerList.add(player);
            playerId++;
        }
        return playerList;
    }

    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", players=" + players +
                '}';
    }
}
