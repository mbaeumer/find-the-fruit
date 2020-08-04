package se.mbaeumer.mllab.findthefruit;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigValidationServiceTest {

    @Test
    public void validateBoardLength() {
        ConfigValidationService service = new ConfigValidationService();
        Assert.assertTrue(5 == service.validateBoardLength("5"));
    }

    @Test(expected = NumberFormatException.class)
    public void shouldGetNumberFormatException() {
        ConfigValidationService service = new ConfigValidationService();
        service.validateBoardLength("test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldGetIllegalArgumentException() {
        ConfigValidationService service = new ConfigValidationService();
        service.validateBoardLength("16");
    }

    @Test
    public void validateFruitPosition() {
        ConfigValidationService configValidationService = new ConfigValidationService();
        Position position = configValidationService.validateFruitPosition("4,4", 8);
        Assert.assertTrue(position.getX() == 4 && position.getY() == 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldGetWrongValue() {
        ConfigValidationService configValidationService = new ConfigValidationService();
        Position position = configValidationService.validateFruitPosition("7,4", 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldGetWrongValue2() {
        ConfigValidationService configValidationService = new ConfigValidationService();
        Position position = configValidationService.validateFruitPosition("2,-4", 5);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void shouldGetException() {
        ConfigValidationService configValidationService = new ConfigValidationService();
        Position position = configValidationService.validateFruitPosition("44", 5);
        Assert.assertTrue(position.getX() == 4 && position.getY() == 4);
    }
}