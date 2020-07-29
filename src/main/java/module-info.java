module find.the.fruit {
    requires javafx.controls;
    opens se.mbaeumer.mllab.findthefruit to javafx.graphics;
    exports se.mbaeumer.mllab.findthefruit;
    //opens se.mbaeumer.mllab.findthefruit to javafx.base;
}

