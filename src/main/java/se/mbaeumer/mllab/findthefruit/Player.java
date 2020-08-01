package se.mbaeumer.mllab.findthefruit;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private int xPos;
    private int yPos;
    private int energy;
    private String[][] chessboard;
    private List<Action> lessons = new ArrayList<>();
    private List<PotentialVector> potentialVectors = new ArrayList<>();
    private int historicalActions = 0;

    private List<Solution> solutions = new ArrayList<>();

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

    public List<Solution> getSolutions(){
        return solutions;
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
        if (latestAction.getReward() == -1000){
            reset();
        }else if (latestAction.getReward() == 1000){
            traceSolution();
            reset();
        }
        CsvWriter csvWriter = new CsvWriter();
        csvWriter.writeActions(lessons);
    }

    private void traceSolution(){
        Solution solution = new Solution();
        int index = lessons.size()-1;
        while (!(lessons.get(index).getOldX() == 0 && lessons.get(index).getOldY() == 0)){
            Position position = new Position(lessons.get(index).getNewX(),lessons.get(index).getNewY());
            solution.getPositions().add(0, position);
            index--;
        }

        Position position = new Position(lessons.get(index).getNewX(),lessons.get(index).getNewY());
        solution.getPositions().add(0, position);
        solution.getPositions().add(0,new Position(0, 0));
        this.solutions.add(solution);

    }

    private Position selectNextPosition(){
        List<PotentialPosition> potentialPositions = new ArrayList<>();
        for (final PotentialVector potentialVector : potentialVectors){
            potentialPositions.add(evaluateNextPosition(potentialVector));
        }

        printAlternatives(potentialPositions);

        PotentialPosition bestPosition = potentialPositions.stream().max(Comparator.comparing(PotentialPosition::getReward)).get();
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
                .filter(action -> action.getNewX() == xPos + potentialVector.getX()
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
                gameStateEvaluator.calculateReward(lessons, nextPosition, energy -1));
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
        this.xPos = 0;
        this.yPos = 0;
        this.energy = 10;
    }
}
