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
}
