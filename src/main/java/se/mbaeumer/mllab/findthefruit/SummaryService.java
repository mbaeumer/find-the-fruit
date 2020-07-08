package se.mbaeumer.mllab.findthefruit;

import java.util.ArrayList;
import java.util.List;

public class SummaryService {

    public void showSummary(final Player player){
        int countAlive = 0;
        int countDead = 0;
        int countSuccess = 0;

        for (Action action: player.getLessons()){
            if (action.getReward() == -1000){
                countDead++;
            }else if (action.getReward() == 1000){
                countSuccess++;
            }else{
                countAlive++;
            }

        }

        System.out.println("Summary");
        System.out.println("Alive:\t" + countAlive);
        System.out.println("Dead:\t" + countDead);
        System.out.println("Success:\t" + countSuccess);


        countAlive = 0;
        countDead = 0;
        countSuccess = 0;

        int i=player.getHistoricalActions();

        List<Action> deadActions = new ArrayList<>();


        while (i < player.getLessons().size()){
            Action action = player.getLessons().get(i);
            if (action.getReward() == -1000){
                deadActions.add(action);
                countDead++;
            }else if (action.getReward() == 1000){
                countSuccess++;
            }else{
                countAlive++;
            }
            i++;
        }

        System.out.println("Summary (for most recent run only)");
        System.out.println("Alive:\t" + countAlive);
        System.out.println("Dead:\t" + countDead);
        System.out.println("Success:\t" + countSuccess);

    }
}
