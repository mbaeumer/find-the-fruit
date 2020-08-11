package se.mbaeumer.mllab.findthefruit;

import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Solution {
    private List<Position> positions = new ArrayList<>();

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Solution solution = (Solution) o;
        if (positions.size() != solution.getPositions().size()) return false;
        int i = 0;
        for (Position position : positions){
            if (position.getX() != solution.getPositions().get(i).getX()
                    || position.getY() != solution.getPositions().get(i).getY()){
                return false;
            }
            i++;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(positions);
    }
}
