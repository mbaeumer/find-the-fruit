package se.mbaeumer.mllab.findthefruit;

public class PotentialPosition {
    private Position position;
    private int reward;

    public PotentialPosition(Position position, int reward) {
        this.position = position;
        this.reward = reward;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }
}
