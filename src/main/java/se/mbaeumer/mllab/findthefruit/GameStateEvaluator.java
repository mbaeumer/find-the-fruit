package se.mbaeumer.mllab.findthefruit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameStateEvaluator {

    private int boardLength = 0;
    private Position fruitPosition;
    private List<Solution> solutions = new ArrayList<>();

    public GameStateEvaluator(final int boardLength, final Position fruitPosition) {
        this.boardLength = boardLength;
        this.fruitPosition = fruitPosition;
    }

    public List<Solution> getSolutions() {
        return solutions;
    }

    public int calculateReward(final List<Action> lessons, final Position latestPosition, final int energy, final Position playerStartPosition){
        int reward = 0;

        int visits = getVisits(lessons, latestPosition, playerStartPosition);
        if (isInvalidPosition(latestPosition)){
            return -100000;
        }/*else if (energy <= 0){
            return -100000;
        }*/else if (isStepBack(lessons, latestPosition)){
            return -100000;
        }else if ( visits > 0){
            reward = -500 - visits * 50;
        }else if (latestPosition.getY() == fruitPosition.getY() && latestPosition.getX() == fruitPosition.getX()){
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

    private int getVisits(List<Action> lessons, final Position latestPosition, final Position playerStartPosition){
        int visits = 0;

        if (lessons.size() == 0){
            return 0;
        }
        if (lessons.size() <= 2){
            if (latestPosition.getX() == playerStartPosition.getX() && latestPosition.getY() == playerStartPosition.getY()){
                return 1;
            }
        }
        int index = lessons.size() - 1;

        //while (!(lessons.get(index).getOldX() == 0 && lessons.get(index).getOldY() == 0)){
        while (!(lessons.get(index).getOldX() == playerStartPosition.getX() && lessons.get(index).getOldY() == playerStartPosition.getY())){
            if (lessons.get(index).getOldX() == latestPosition.getX() && lessons.get(index).getOldY() == latestPosition.getY()){
                visits++;
            }
            index--;
        }

        if (lessons.get(index).getOldX() == latestPosition.getX() && lessons.get(index).getOldY() == latestPosition.getY()){
            visits++;
        }


        return visits;
    }

    private boolean isStepBack(List<Action> lessons, final Position latestPosition){
        boolean result = false;
        if (lessons.size() <=1){
            return false;
        }

        if (lessons.size() == 2){
            if (latestPosition.getX() == lessons.get(0).getOldX() && latestPosition.getY() == lessons.get(0).getOldY()){
                return true;
            }
        }
        int index = lessons.size() - 2;
        if (lessons.get(index).getOldX() == latestPosition.getX() && lessons.get(index).getOldY() == latestPosition.getY()){
            result = true;
        }

        return result;
    }

    private boolean isInvalidPosition(final Position position){
        return position.getX() < 0 || position.getX() >= boardLength || position.getY() < 0 || position.getY() >= boardLength;
    }

    public void traceSolution(final List<Action> lessons, final Position playerStartPosition){
        Solution solution = new Solution();
        int index = lessons.size()-1;
        while (!(lessons.get(index).getOldX() == playerStartPosition.getX() && lessons.get(index).getOldY() == playerStartPosition.getY())){
            Position position = new Position(lessons.get(index).getNewX(),lessons.get(index).getNewY());
            solution.getPositions().add(0, position);
            index--;
        }

        Position position = new Position(lessons.get(index).getNewX(),lessons.get(index).getNewY());
        solution.getPositions().add(0, position);
        solution.getPositions().add(0,new Position(playerStartPosition.getX(), playerStartPosition.getY()));
        if (isUnique(solution)) {
            this.solutions.add(solution);
        }

    }

    private boolean isUnique(final Solution solution){
        for (Solution solution1:solutions){
            if (solution1.equals(solution)){
                return false;
            }
        }

        return true;
    }
}
