package se.mbaeumer.mllab.findthefruit;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void testShouldGetEquals(){
        Solution firstSolution = new Solution();
        firstSolution.getPositions().add(new Position(0,0));
        firstSolution.getPositions().add(new Position(0,1));
        firstSolution.getPositions().add(new Position(0,2));
        firstSolution.getPositions().add(new Position(0,3));
        firstSolution.getPositions().add(new Position(0,4));

        Solution solution = new Solution();
        solution.getPositions().add(new Position(0,0));
        solution.getPositions().add(new Position(0,1));
        solution.getPositions().add(new Position(0,2));
        solution.getPositions().add(new Position(0,3));
        solution.getPositions().add(new Position(0,4));
        Assert.assertTrue(solution.equals(firstSolution));
    }

    @Test
    public void testShouldNotGetEquals(){
        Solution firstSolution = new Solution();
        firstSolution.getPositions().add(new Position(0,0));
        firstSolution.getPositions().add(new Position(0,1));
        firstSolution.getPositions().add(new Position(0,2));
        firstSolution.getPositions().add(new Position(0,3));

        Solution solution = new Solution();
        solution.getPositions().add(new Position(0,0));
        solution.getPositions().add(new Position(0,1));
        solution.getPositions().add(new Position(0,2));
        solution.getPositions().add(new Position(0,3));
        solution.getPositions().add(new Position(0,4));
        Assert.assertFalse(solution.equals(firstSolution));
    }

    @Test
    public void testShouldNotGetEquals2(){
        Solution firstSolution = new Solution();
        firstSolution.getPositions().add(new Position(0,0));
        firstSolution.getPositions().add(new Position(0,1));
        firstSolution.getPositions().add(new Position(0,2));
        firstSolution.getPositions().add(new Position(0,3));
        firstSolution.getPositions().add(new Position(0,4));

        Solution solution = new Solution();
        solution.getPositions().add(new Position(0,0));
        solution.getPositions().add(new Position(0,1));
        solution.getPositions().add(new Position(0,2));
        solution.getPositions().add(new Position(0,3));
        solution.getPositions().add(new Position(0,5));
        Assert.assertFalse(solution.equals(firstSolution));
    }

}