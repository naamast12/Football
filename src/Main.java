import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        createTeamNamesFile();
        new LeagueManager();

    }
    public static void createTeamNamesFile(){
        try {
            Path filePath = Path.of(Utils.PATH);
            Files.write(filePath, Utils.TEAM_NAMES, StandardOpenOption.CREATE);
        } catch (IOException e) {
        }
    }

}