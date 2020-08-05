package se.mbaeumer.mllab.findthefruit;

import java.util.List;
import java.util.stream.Collectors;

public class GameStateEvaluator {

    private int boardLength = 0;
    private Position fruitPosition;

    public GameStateEvaluator(final int boardLength, final Position fruitPosition) {
        this.boardLength = boardLength;
        this.fruitPosition = fruitPosition;
    }

    public ActionResult calculateGameState(List<Action> lessons, final Position position, final int energy){
        ActionResult actionResult = ActionResult.ALIVE;
        if (isInvalidPosition(position) || energy <= 0 || isStepBack(lessons,position)){
            // GAME OVER
            actionResult = ActionResult.DEAD;
        }else if (position.getY() == fruitPosition.getY() && position.getX() == fruitPosition.getX()){
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
        }else if (position.getY() == fruitPosition.getY() && position.getX() == fruitPosition.getX()){
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
        return position.getX() < 0 || position.getX() >= boardLength || position.getY() < 0 || position.getY() >= boardLength;
    }
}
