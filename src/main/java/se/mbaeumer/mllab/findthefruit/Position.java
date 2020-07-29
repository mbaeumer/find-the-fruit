package se.mbaeumer.mllab.findthefruit;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Position {

    private IntegerProperty x = new SimpleIntegerProperty(this, "x");
    public IntegerProperty xProperty(){return this.x;}
    public int getX(){return this.x.get();}
    public void setX(int x){this.x.set(x);}

    private IntegerProperty y = new SimpleIntegerProperty(this, "y");
    public IntegerProperty yProperty(){return this.y;}
    public int getY(){return this.y.get();}
    public void setY(int y){this.y.set(y);}

    public Position(int x, int y) {
        this.x.set(x);
        this.y.set(y);
    }

    /*
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    */
}
