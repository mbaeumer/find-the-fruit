package se.mbaeumer.mllab.findthefruit;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Action {
    private IntegerProperty oldX = new SimpleIntegerProperty(this, "oldX");
    public IntegerProperty oldXProperty(){return this.oldX;}
    public int getOldX(){return this.oldX.get();}
    public void setOldX(int x){this.oldX.set(x);}

    private IntegerProperty oldY = new SimpleIntegerProperty(this, "oldY");
    public IntegerProperty oldYProperty(){return this.oldY;}
    public int getOldY(){return this.oldY.get();}
    public void setOldY(int oldY){this.oldY.set(oldY);}

    private IntegerProperty oldEnergy = new SimpleIntegerProperty(this, "oldEnergy");
    public IntegerProperty oldEnergyProperty(){return this.oldEnergy;}
    public int getOldEnergy(){return this.oldEnergy.get();}
    public void setOldEnergy(int oldY){this.oldEnergy.set(oldY);}

    private IntegerProperty xDelta = new SimpleIntegerProperty(this, "xDelta");
    public IntegerProperty xDeltaProperty(){return this.xDelta;}
    public int getXDelta(){return this.xDelta.get();}
    public void setXDelta(int oldY){this.xDelta.set(oldY);}

    private IntegerProperty yDelta = new SimpleIntegerProperty(this, "yDelta");
    public IntegerProperty yDeltaProperty(){return this.yDelta;}
    public int getYDelta(){return this.yDelta.get();}
    public void setYDelta(int oldY){this.yDelta.set(oldY);}

    private IntegerProperty newX = new SimpleIntegerProperty(this, "newX");
    public IntegerProperty newXProperty(){return this.newX;}
    public int getNewX(){return this.newX.get();}
    public void setNewX(int x){this.newX.set(x);}

    private IntegerProperty newY = new SimpleIntegerProperty(this, "newY");
    public IntegerProperty newYProperty(){return this.newY;}
    public int getNewY(){return this.newY.get();}
    public void setNewY(int x){this.newY.set(x);}

    private IntegerProperty newEnergy = new SimpleIntegerProperty(this, "newEnergy");
    public IntegerProperty newEnergyProperty(){return this.newEnergy;}
    public int getNewEnergy(){return this.newEnergy.get();}
    public void setNewEnergy(int oldY){this.newEnergy.set(oldY);}

    private StringProperty nextPositionState = new SimpleStringProperty(this, "nextPositionState");
    public StringProperty nextPositionStateProperty(){return this.nextPositionState;}
    public String getNextPositionState(){return this.nextPositionState.get();}
    public void setNextPositionState(String oldY){this.nextPositionState.set(oldY);}

    private IntegerProperty reward = new SimpleIntegerProperty(this, "reward");
    public IntegerProperty rewardProperty(){return this.reward;}
    public int getReward(){return this.reward.get();}
    public void setReward(int oldY){this.reward.set(oldY);}

    public Action(){

    }

    public Action(int oldX, int oldY, int oldEnergy, int xDelta, int yDelta, int newX, int newY, int newEnergy, String nextPositionState, int reward) {
        this.oldX.set(oldX);
        this.oldY.set(oldY);
        this.oldEnergy.set(oldEnergy);
        this.xDelta.set(xDelta);
        this.yDelta.set(yDelta);
        this.newX.set(newX);
        this.newY.set(newY);
        this.newEnergy.set(newEnergy);
        this.nextPositionState.set(nextPositionState);
        this.reward.set(reward);
    }

}
