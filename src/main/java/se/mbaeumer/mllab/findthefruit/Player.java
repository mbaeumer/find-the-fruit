package se.mbaeumer.mllab.findthefruit;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class Player {
    private int xPos;
    private int yPos;
    private int energy;
    private String[][] chessboard;
    private List<Action> lessons = new ArrayList();
    private List<PotentialVector> potentialVectors = new ArrayList<>();
    private int historicalActions = 0;

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getEnergy() {
        return energy;
    }

    public void setLessons(List<Action> lessons) {
        this.lessons = lessons;
    }

    public int getHistoricalActions() {
        return historicalActions;
    }

    public void setHistoricalActions(int historicalActions) {
        this.historicalActions = historicalActions;
    }

    public List<Action> getLessons() {
        return this.lessons;
    }

    public Player(int xPos, int yPos, int energy) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.energy = energy;
        this.initPotentialVectors();
    }

    private void initPotentialVectors(){
        potentialVectors.add(new PotentialVector(-1, 0));
        potentialVectors.add(new PotentialVector(0, 1));
        potentialVectors.add(new PotentialVector(0, -1));
        potentialVectors.add(new PotentialVector(1, 0));
    }

    public void explore(final String[][] board, final GameStateEvaluator gameStateEvaluator) throws FileNotFoundException {
        this.chessboard = board;
        Position nextPosition = selectNextPosition();
        move(nextPosition, gameStateEvaluator);
        Action latestAction = lessons.get(lessons.size()-1);
        if (latestAction.getActionResult() == ActionResult.DEAD){
            reset();
        }else if (latestAction.getActionResult() == ActionResult.SUCCESS){
            reset();
        }
        CsvWriter csvWriter = new CsvWriter();
        csvWriter.writeActions(lessons);
    }

    private Position selectNextPosition(){
        Position nextPosition;

        //for each vector
          // previous experience?
          // if exp == dangerous
          // if exp == unknown
          // if exp == not_dangerous
        List<Position> potentialPositions = new ArrayList<>();
        for (final PotentialVector potentialVector : potentialVectors){
            ActionForecast actionForecast = evaluateNextPosition(potentialVector);
            if (actionForecast != ActionForecast.DANGEROUS){
                nextPosition = new Position(xPos + potentialVector.getX(), yPos + potentialVector.getY());
                potentialPositions.add(nextPosition);

            }

        }
        int numberOfOptions = potentialPositions.size();
        int selectedOption = ThreadLocalRandom.current().nextInt(0, potentialPositions.size());
        return potentialPositions.get(selectedOption);
    }

    private ActionForecast evaluateNextPosition(final PotentialVector potentialVector){
        ActionForecast actionForecast = ActionForecast.UNKNOWN;

        Optional<Action> matchingAction = lessons.stream()
                .filter(action -> xPos == action.getOldX() && yPos == action.getOldY()
                && potentialVector.getX() == action.getxDelta()
                        && potentialVector.getY() == action.getyDelta())
                .findFirst();

        if (matchingAction.isPresent()){
            if (matchingAction.get().getActionResult() == ActionResult.DEAD){
                actionForecast = ActionForecast.DANGEROUS;
            }else{
                actionForecast = ActionForecast.NOT_DANGEROUS;
            }
        }
        return actionForecast;
    }

    private void move(final Position nextPosition, final GameStateEvaluator gameStateEvaluator) throws FileNotFoundException {
        // public Action(int oldX, int oldY, int oldEnergy,
        // int xDelta, int yDelta,
        // int newX, int newY, int newEnergy,
        // String nextPositionState, ActionResult actionResult) {
        //gameStateEvaluator.
        Action action = new Action(xPos, yPos, energy,
                nextPosition.getX()-xPos, nextPosition.getY()-yPos,
                nextPosition.getX(), nextPosition.getY(), energy - 1, getNextPositionState(nextPosition),
                gameStateEvaluator.calculateGameState(lessons, nextPosition, energy -1));
        xPos = nextPosition.getX();
        yPos = nextPosition.getY();
        energy--;
        lessons.add(action);

    }

    private String getNextPositionState(final Position nextPosition){
        String state;

        try{
            state = chessboard[nextPosition.getY()][nextPosition.getX()];
        }catch (ArrayIndexOutOfBoundsException ex){
            state = null;
        }

        return state;
    }

    private void reset(){
        this.xPos = 0;
        this.yPos = 0;
        this.energy = 10;
    }
}
