package se.mbaeumer.mllab.findthefruit;

import java.util.List;
import java.util.stream.Collectors;

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

    public int calculateReward(final List<Action> lessons, final Position position, final int energy){
        int reward = 0;

        if (isInvalidPosition(position) || energy <= 0){
            return -1000;
        }else if (isStepBack(lessons, position)){
            reward = -500;
        }else if (position.getY() == 2 && position.getX() == 3){
            return 1000;
        }

        int numberOfVisits = lessons.stream().filter(action -> action.getNewX() == position.getX() && action.getNewY() == position.getY())
                .collect(Collectors.toList()).size();
        reward = reward - (numberOfVisits * 5);
        return reward;
    }

    private boolean isStepBack(List<Action> lessons, final Position position){
        boolean result = false;
        if (lessons.size() < 2){
            if (position.getX() == 0 && position.getY() == 0){
                return true;
            }
        }
        int index = lessons.size() - 1;
        while (lessons.get(index).getOldX() != 0 && lessons.get(index).getOldY() !=0){
            if (lessons.get(index).getOldX() == position.getX() && lessons.get(index).getOldY() == position.getY()){
                result = true;
            }
            index--;
        }

        return result;
    }

    private boolean isInvalidPosition(final Position position){
        return position.getX() < 0 || position.getX() >= 4 || position.getY() < 0 || position.getY() >= 4;
    }
}
