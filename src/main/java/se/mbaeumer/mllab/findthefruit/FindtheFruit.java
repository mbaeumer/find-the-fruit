package se.mbaeumer.mllab.findthefruit;

import java.io.FileNotFoundException;

public class FindtheFruit {
    public static void main(String[] args) throws FileNotFoundException {

        Game game = new Game(4);
        game.initGame();
        game.start();

    }
}
