package se.mbaeumer.mllab.findthefruit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public List<Action> readActions(final String filename) throws IOException {
        List<Action> actions = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Action action = new Action();
                action.setOldX(Integer.parseInt(data[0]));
                action.setOldY(Integer.parseInt(data[1]));
                action.setOldEnergy(Integer.parseInt(data[2]));
                action.setxDelta(Integer.parseInt(data[3]));
                action.setyDelta(Integer.parseInt(data[4]));
                action.setNewX(Integer.parseInt(data[5]));
                action.setNewY(Integer.parseInt(data[6]));
                action.setNewEnergy(Integer.parseInt(data[7]));
                action.setNextPositionState(data[8]);
                action.setActionResult(ActionResult.valueOf(data[9]));

                actions.add(action);

            }
        }

        return actions;
    }
}
