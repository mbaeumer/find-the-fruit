package se.mbaeumer.mllab.findthefruit;

import javafx.geometry.Pos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private int boardLength;
    private Player player;
    private String[][] chessboard;
    private Position fruitPosition;
    private int iterations;
    private GameStateEvaluator gameStateEvaluator;

    public Game(final int boardLength, final Position fruitPosition, final int iterations) {
        this.boardLength = boardLength;
        this.fruitPosition = fruitPosition;
        this.iterations = iterations;
    }

    public GameStateEvaluator getGameStateEvaluator() {
        return gameStateEvaluator;
    }

    public String[][] getChessboard() {
        return chessboard;
    }

    public Player getPlayer() {
        return player;
    }

    public void initGame(){
        this.initBoard();
        this.initPlayer();
        this.initData();
    }

    public void start() throws FileNotFoundException {
        gameStateEvaluator = new GameStateEvaluator(boardLength, fruitPosition);
        for (int i=0;i<iterations;i++) {
            player.explore(chessboard, gameStateEvaluator);
            updateBoard();
            //displayBoard();
        }
        SummaryService summaryService = new SummaryService();
        summaryService.showSummary(player);
        showSolution();
        // get the players new position
        // calculate the new status
        // update the player´s "experience"
        // update and display the board
    }

    private void initBoard(){
        chessboard = new String[boardLength][boardLength];

        for (int i=0;i<boardLength;i++){
            for (int j=0;j<boardLength;j++){
                chessboard[i][j] = "x";
            }
        }

        chessboard[0][0] = "p";
        chessboard[fruitPosition.getY()][fruitPosition.getX()] = "f";
    }

    private void updateBoard(){

        for (int i=0;i<boardLength;i++){
            for (int j=0;j<boardLength;j++){
                chessboard[i][j] = "x";
            }
        }

        try {
            chessboard[player.getyPos()][player.getxPos()] = "p";
        }catch (ArrayIndexOutOfBoundsException ex){
            System.out.println("Invalid player position: " + player.getyPos() + "," + player.getxPos());
        }

        chessboard[fruitPosition.getY()][fruitPosition.getX()] = "f";

    }

    private void initPlayer(){
        this.player = new Player(0, 0, 15);
    }

    private void initData(){
        CsvReader csvReader = new CsvReader();
        List<Action> previousActions = new ArrayList<>();
        try {
            previousActions = csvReader.readActions("/Users/martinbaumer/Documents/fruit.csv");
        } catch (IOException e) {
            return;
        }

        this.player.setLessons(previousActions);
        this.player.setHistoricalActions(previousActions.size());
    }

    private void displayBoard(){
        System.out.println("New game state");
        for (int i = 0;i<4; i++){
            for (int j = 0;j<4; j++){
                System.out.print(chessboard[i][j]+"\t");
            }
            System.out.println();
        }
    }

    private void showSolution(){
        if (gameStateEvaluator.getSolutions().size() > 0){
            Solution solution = gameStateEvaluator.getSolutions().get(0);
            solution.getPositions().stream().forEach(position -> System.out.println(position.getX() + "," + position.getY()));
        }else{
            System.out.println("No solution found");
        }
    }
}
