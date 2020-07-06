package se.mbaeumer.mllab.findthefruit;

public class Action {


    private int oldX;
    private int oldY;
    private int oldEnergy;
    private int xDelta;
    private int yDelta;
    private int newX;
    private int newY;
    private int newEnergy;
    private String nextPositionState;
    private ActionResult actionResult;

    public Action(){

    }

    public Action(int oldX, int oldY, int oldEnergy, int xDelta, int yDelta, int newX, int newY, int newEnergy, String nextPositionState, ActionResult actionResult) {
        this.oldX = oldX;
        this.oldY = oldY;
        this.oldEnergy = oldEnergy;
        this.xDelta = xDelta;
        this.yDelta = yDelta;
        this.newX = newX;
        this.newY = newY;
        this.newEnergy = newEnergy;
        this.nextPositionState = nextPositionState;
        this.actionResult = actionResult;
    }

    public int getOldX() {
        return oldX;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    public int getOldEnergy() {
        return oldEnergy;
    }

    public void setOldEnergy(int oldEnergy) {
        this.oldEnergy = oldEnergy;
    }

    public int getxDelta() {
        return xDelta;
    }

    public void setxDelta(int xDelta) {
        this.xDelta = xDelta;
    }

    public int getyDelta() {
        return yDelta;
    }

    public void setyDelta(int yDelta) {
        this.yDelta = yDelta;
    }

    public int getNewX() {
        return newX;
    }

    public void setNewX(int newX) {
        this.newX = newX;
    }

    public int getNewY() {
        return newY;
    }

    public void setNewY(int newY) {
        this.newY = newY;
    }

    public int getNewEnergy() {
        return newEnergy;
    }

    public void setNewEnergy(int newEnergy) {
        this.newEnergy = newEnergy;
    }

    public String getNextPositionState() {
        return nextPositionState;
    }

    public void setNextPositionState(String nextPositionState) {
        this.nextPositionState = nextPositionState;
    }

    public ActionResult getActionResult() {
        return actionResult;
    }

    public void setActionResult(ActionResult actionResult) {
        this.actionResult = actionResult;
    }
}
