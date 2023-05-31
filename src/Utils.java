import java.util.Arrays;
import java.util.List;

public class Utils {
    public static final String PATH = "teams.csv";
    public static final List<String> TEAM_NAMES = Arrays.asList("Maccabi TLV", "Maccabi Haifa", "Maccabi Ashdod", "Barcelona", "Madrid", "France", "Morocco","Hapoel TLV","Hapoel Haifa","Beitar");

    public static final List<String> PLAYERS_FIRST_NAMES = Arrays.asList("Avia", "Naama", "Hadas", "Michal", "Reut", "Shira", "Gefen", "Ofra", "Renana", "Moshe", "Yaakov", "Noam", "Shahar", "Banya", "Oz", "Purity", "Or", "Uriah", "Tzofia", "Uriel", "Daniel", "Yosef", "Yogev", "Lior", "Ram", "Michael", "Naor", "Ayelet", "Avichai", "Shai" );
    public static final List<String> PLAYERS_LAST_NAMES = Arrays.asList("Ohayon", "David", "Cohen", "Givati", "Straussman", "Levy", "Hassan", "Dahan", "Assis", "Peleg", "Weiss", "Moyal", "Yehezkel", "Mehtsari", "Aharoni", "Gelman", "Eldar", "Neria", "Amsalem", "Peretz", "Khoury", "Vaknin", "Asraf", "Fitoussi", "Sharabi", "Mazuz", "Buchnik", "Buchris", "Malka", "Asayag"  );

    public static final int PLAYERS_AMOUNT_AT_TEAM = 15;
    public static final int MATCH_TIMES_AT_CYCLE = 5;
    public static final int START_GAME = 1;
    public static final int END_GAME = 90;
    public static final int MAX_SCORE_TEAM = 6;
}
