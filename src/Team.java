import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Team {
    private int id;
    private static int teamIdStatic = 1;

    private String name;
    private List<Player> players;

    public Team(String name, List<Player> players) {
        this.id = teamIdStatic++;
        this.name = name;
        this.players = players;
    }

    public static List<Player> createTeam() {
        Random random = new Random();
        AtomicInteger playerId = new AtomicInteger(1);
        List<Player> playerList = Stream.generate(() -> {
                    String firstName = Utils.PLAYERS_FIRST_NAMES.get(random.nextInt(Utils.PLAYERS_FIRST_NAMES.size()));
                    String lastName = Utils.PLAYERS_LAST_NAMES.get(random.nextInt(Utils.PLAYERS_LAST_NAMES.size()));
                    int currentId = playerId.getAndIncrement();
                    return new Player(currentId, firstName, lastName);
                }).limit(Utils.PLAYERS_AMOUNT_AT_TEAM)
                .collect(Collectors.toList());
        return playerList;
    }

    @Override
    public String toString() {
        return this.name;

    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return this.id;
    }
}
