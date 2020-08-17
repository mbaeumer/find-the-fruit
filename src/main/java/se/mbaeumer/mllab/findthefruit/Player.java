package se.mbaeumer.mllab.findthefruit;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Player {
    private int xStart;
    private int yStart;
    private int xPos;
    private int yPos;
    private int energy;
    private String[][] chessboard;
    private List<Action> lessons = new ArrayList<>();
    private List<PotentialVector> potentialVectors = new ArrayList<>();
    private int historicalActions = 0;

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getxStart() {
        return xStart;
    }

    public int getyStart() {
        return yStart;
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
        this.xStart = xPos;
        this.yStart = yPos;
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
        if (latestAction.getReward() == -100000){
            reset();
        }else if (latestAction.getReward() == 1000){
            traceSolution(gameStateEvaluator);
            reset();
        }
        CsvWriter csvWriter = new CsvWriter();
        csvWriter.writeActions(lessons);
    }

    private void traceSolution(final GameStateEvaluator gameStateEvaluator){
        gameStateEvaluator.traceSolution(lessons, new Position(xStart, yStart));
    }

    private Position selectNextPosition(){
        List<PotentialPosition> potentialPositions = new ArrayList<>();
        for (final PotentialVector potentialVector : potentialVectors){
            potentialPositions.add(evaluateNextPosition(potentialVector));
        }

        printAlternatives(potentialPositions);

        int i = 0;
        boolean allTheSame = true;
        for (int j=1; j < potentialPositions.size(); j++){
            if (potentialPositions.get(i).getReward() != potentialPositions.get(j).getReward()){
                allTheSame = false;
            }
        }
        PotentialPosition bestPosition;
        if (!allTheSame){
            bestPosition = potentialPositions.stream().max(Comparator.comparing(PotentialPosition::getReward)).get();
        }else{
            Random random = new Random();
            int rand = ThreadLocalRandom.current().nextInt(0,potentialPositions.size());
            bestPosition = potentialPositions.get(rand);
        }

        return bestPosition.getPosition();
    }



    private void printAlternatives(List<PotentialPosition> potentialPositions){
        String s = "";
        for (PotentialPosition potentialPosition : potentialPositions){
            s = s + "[" + potentialPosition.getPosition().getX() + "," + potentialPosition.getPosition().getY() + "]," + potentialPosition.getReward() + "-";
        }
        System.out.println(s);

    }

    private PotentialPosition evaluateNextPosition(final PotentialVector potentialVector){
        PotentialPosition potentialPosition;

        List<Action> matchingActions = lessons.stream()
                .filter(action -> action.getOldX() == xPos && action.getOldY() == yPos
                        && action.getNewX() == xPos + potentialVector.getX()
                        && action.getNewY() == yPos + potentialVector.getY())
                .collect(Collectors.toList());

       Action action = matchingActions.stream().reduce((first, second) -> second).orElse(null);

       if (action != null){
           Position position = new Position(action.getNewX(), action.getNewY());
           potentialPosition = new PotentialPosition(position, action.getReward());
       }else{
           Position position = new Position(xPos + potentialVector.getX(), yPos + potentialVector.getY());
           potentialPosition = new PotentialPosition(position, 5);
       }

        return potentialPosition;
    }

    private void move(final Position nextPosition, final GameStateEvaluator gameStateEvaluator) throws FileNotFoundException {
        Action action = new Action(xPos, yPos, energy,
                nextPosition.getX()-xPos, nextPosition.getY()-yPos,
                nextPosition.getX(), nextPosition.getY(), energy - 1, getNextPositionState(nextPosition),
                gameStateEvaluator.calculateReward(lessons, nextPosition, energy -1, new Position(xStart, yStart)));
        xPos = nextPosition.getX();
        yPos = nextPosition.getY();
        energy--;
        System.out.println("Moved " + action.getNewX() + "," + action.getNewY() + "," + action.getReward());
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
        this.xPos = this.xStart;
        this.yPos = this.yStart;
        this.energy = 15;
    }
}
