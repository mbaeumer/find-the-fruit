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
}