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

        if (isInvalidPosition(position)){
            return -100000;
        }/*else if (energy <= 0){
            return -100000;
        }*/else if (isStepBack(lessons, position)){
            return -100000;
        }else if (getVisits(lessons, position) > 0){
            reward = -500 - (getVisits(lessons, position) * 50);
        }else if (position.getY() == fruitPosition.getY() && position.getX() == fruitPosition.getX()){
            return 1000;
        }

        /*
        int index = lessons.size() - 1;
        while (!(lessons.get(index).getOldX() == 0 && lessons.get(index).getOldY() == 0)){


            index--;
        }
        */
        //int numberOfVisits = lessons.stream().filter(action -> action.getNewX() == position.getX() && action.getNewY() == position.getY())
          //      .collect(Collectors.toList()).size();
        //reward = reward - (numberOfVisits * 2);
        return reward;
    }

    private int getVisits(List<Action> lessons, final Position position){
        int visits = 0;

        if (lessons.size() == 0){
            return 0;
        }
        if (lessons.size() <= 2){
            if (position.getX() == 0 && position.getY() == 0){
                return 1;
            }
        }
        int index = lessons.size() - 1;
        //while (!(lessons.get(index).getOldX() == 0 && lessons.get(index).getOldY() == 0)){
        while (!(lessons.get(index).getOldX() == 0 && lessons.get(index).getOldY() == 0)){
            if (lessons.get(index).getOldX() == position.getX() && lessons.get(index).getOldY() == position.getY()){
                visits++;
            }
            index--;
        }

        if (lessons.get(index).getOldX() == position.getX() && lessons.get(index).getOldY() == position.getY()){
            visits++;
        }


        return visits;
    }

    private boolean isStepBack(List<Action> lessons, final Position position){
        boolean result = false;
        if (lessons.size() <=1){
            return false;
        }
        if (lessons.size() == 2){
            if (position.getX() == lessons.get(0).getOldX() && position.getY() == lessons.get(0).getOldY()){
                return true;
            }
        }
        int index = lessons.size() - 2;
        if (lessons.get(index).getOldX() == position.getX() && lessons.get(index).getOldY() == position.getY()){
            result = true;
        }

        return result;
    }

    private boolean isInvalidPosition(final Position position){
        return position.getX() < 0 || position.getX() >= boardLength || position.getY() < 0 || position.getY() >= boardLength;
    }
}
