package se.mbaeumer.mllab.findthefruit;

import java.util.ArrayList;
import java.util.List;

public class SummaryService {

    public void showSummary(final Player player){
        int countAlive = 0;
        int countDead = 0;
        int countSuccess = 0;

        for (Action action: player.getLessons()){
            if (action.getActionResult() == ActionResult.DEAD){
                countDead++;
            }else if (action.getActionResult() == ActionResult.SUCCESS){
                countSuccess++;
            }else if (action.getActionResult() == ActionResult.ALIVE){
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


        while (i < player.getLessons().size()-1){
            Action action = player.getLessons().get(i);
            if (action.getActionResult() == ActionResult.DEAD){
                deadActions.add(action);
                countDead++;
            }else if (action.getActionResult() == ActionResult.SUCCESS){
                countSuccess++;
            }else if (action.getActionResult() == ActionResult.ALIVE){
                countAlive++;
            }
            i++;
        }

        System.out.println("Summary (for recent run only");
        System.out.println("Alive:\t" + countAlive);
        System.out.println("Dead:\t" + countDead);
        System.out.println("Success:\t" + countSuccess);

    }
}
