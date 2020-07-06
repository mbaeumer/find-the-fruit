package se.mbaeumer.mllab.findthefruit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class CsvWriter {

    public void writeActions(List<Action> actionList) throws FileNotFoundException {

        File output = new File("/Users/martinbaumer/Documents/fruit.csv");

        try (PrintWriter pw = new PrintWriter(output)) {
            actionList.stream().map(this::toCsv)
                    .forEach(pw::println);

        }


    }

    private String toCsv(final Action action){
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(action.getOldX()).append(",")
                .append(action.getOldY()).append(",").append(action.getOldEnergy()).append(",")
                .append(action.getxDelta()).append(",").append(action.getyDelta()).append(",")
                .append(action.getNewX()).append(",").append(action.getNewY()).append(",")
                .append(action.getNewEnergy()).append(",").append(action.getNextPositionState()).append(",")
                .append(action.getActionResult()).toString();

    }
}
