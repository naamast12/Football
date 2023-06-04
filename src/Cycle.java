import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Cycle extends Thread{
    List<Match> matchesAtCycle;
    private int second;
    int id;

    public Cycle(int id , List<Match> matchesAtCycle){
        this.id = id;
        this.matchesAtCycle = matchesAtCycle;
        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(this.second);
            this.second--;
        }).start();

    }

    @Override
    public String toString() {
        return "Cycle{" +
                "\n, id cycle=" + id +
                "\n  matchesAtCycle=" + matchesAtCycle +
                '}';
    }
    public void cycleResult (){

    }

    @Override
    public void run() {
        while (true){

        }
    }

    //       public List<Match> findMatchesByTeam(int teamId){
////        //מתודה זו אמורה להחזיר רשימה של משחקים שקבוצה מסוימת שיחקה.
//           List<Match> matchByTeam = new ArrayList<>();
//           matchByTeam = this.
//    }
//    public List<Team> findTopScoringTeams(int n){
//        //מתודה זו אמורה להחזיר את n הקבוצות שהבקיעו את המספר הרב ביותר של השערים.
//    }
//    public List<Player> findPlayersWithAtLeastNGoals(int n){
//        //מתודה זו צריכה להחזיר רשימה של שחקנים שכבשו לפחות n  שערים.
//    }
//    public Team getTeamByPosition(int position){
//        //מתודה זו אמורה להחזיר את הקבוצה שנמצאת במיקום הנתון בטבלת הליגה.
//    }
//    public Map<Integer, Integer> getTopScorers(int n){
//        //מתודה זו מקבלת פרמטר n ואמורה להחזיר את n השחקנים שכבשו את המספר הרב ביותר של שערים, כשהם מאוחסנים במפה: מפתח – מזהה ייחודי של שחקן, ערך – כמות השערים שהבקיע.
//    }

}

