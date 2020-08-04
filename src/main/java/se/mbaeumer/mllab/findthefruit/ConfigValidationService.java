package se.mbaeumer.mllab.findthefruit;

public class ConfigValidationService {

    public int validateBoardLength(final String length) throws NumberFormatException{
        int boardLength;
        boardLength = Integer.parseInt(length);
        if (boardLength < 4 || boardLength > 15){
            throw new IllegalArgumentException("The number must be between 4 and 15");
        }
        return boardLength;
    }

    public Position validateFruitPosition(final String coordinates, int boardLength){
        String[] pos = coordinates.split(",");
        int x = Integer.parseInt(pos[0]);
        int y = Integer.parseInt(pos[1]);
        if (x > boardLength || x < 0 || y > boardLength || y < 0){
            throw new IllegalArgumentException("Wrong value");
        }
        return new Position(x, y);
    }
}
