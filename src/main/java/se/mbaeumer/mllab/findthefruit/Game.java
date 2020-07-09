package se.mbaeumer.mllab.findthefruit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private Player player;
    private String[][] chessboard;

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
        for (int i = 0; i< 50; i++) {
            player.explore(chessboard, new GameStateEvaluator());
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
        chessboard = new String[4][4];

        for (int i = 0; i < 4; i ++){
            for (int j= 0; j < 4; j++){
                chessboard[i][j] = "x";
            }
        }

        chessboard[0][0] = "p";
        chessboard[2][3] = "f";

    }

    private void updateBoard(){

        for (int i = 0; i < 4; i ++){
            for (int j= 0; j < 4; j++){
                chessboard[i][j] = "x";
            }
        }

        try {
            chessboard[player.getyPos()][player.getxPos()] = "p";
        }catch (ArrayIndexOutOfBoundsException ex){
            System.out.println("Invalid player position: " + player.getyPos() + "," + player.getxPos());
        }

        chessboard[2][3] = "f";

    }

    private void initPlayer(){
        this.player = new Player(0, 0, 10);
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
        if (player.getSolution() != null){
            player.getSolution().getPositions().stream().forEach(position -> System.out.println(position.getX() + "," + position.getY()));
        }else{
            System.out.println("No solution found");
        }
    }
}
