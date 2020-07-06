package se.mbaeumer.mllab.findthefruit;

import java.util.List;

public class GameStateEvaluator {

    public ActionResult calculateGameState(List<Action> lessons, final Position position, final int energy){
        ActionResult actionResult = ActionResult.ALIVE;
        if (isInvalidPosition(position) || energy <= 0 || isStepBack(lessons,position)){
            // GAME OVER
            actionResult = ActionResult.DEAD;
        }else if (position.getY() == 2 && position.getX() == 3){
            System.out.println("THE PLAYER FOUND THE FRUIT!!!");
            actionResult = ActionResult.SUCCESS;
        }

        return actionResult;

    }

    private boolean isStepBack(List<Action> lessons, final Position position){
        boolean result;
        if (lessons.size() < 2){
            result = false;
        }else {
            Action secondLatestAction = lessons.get(lessons.size() - 2);
            result = secondLatestAction.getOldX() == position.getX() && secondLatestAction.getOldY()==position.getY();
        }
        return result;
    }

    private boolean isInvalidPosition(final Position position){
        return position.getX() < 0 || position.getX() >= 4 || position.getY() < 0 || position.getY() >= 4;
    }
}
