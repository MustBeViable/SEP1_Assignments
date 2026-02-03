import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureConverterTest {
    static private TemperatureConverter tempConv;
    static private double positive = 120.5;
    static private double negative = -120.5;
    static private double zero = 0.0;
    static private double inRange = 20.0;

    @BeforeAll
    public static void method() {
        tempConv = new TemperatureConverter();
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Fahrenheit -> Celsius conversion works for positives, negatives and edge cases")
    void fahrenheitToCelsius() {
        double celsiusAssertionPositive = (positive - 32.0) * (5.0 / 9.0);
        double celsiusAssertionNegative = (negative - 32.0) * (5.0 / 9.0);
        double celsiusAssertionZero = (zero - 32.0) * (5.0 / 9.0);

        assertEquals(celsiusAssertionPositive, tempConv.fahrenheitToCelsius(positive),  "Positive equals test failed");
        assertEquals(celsiusAssertionNegative, tempConv.fahrenheitToCelsius(negative),  "negative equals test failed");
        assertNotEquals(celsiusAssertionNegative, tempConv.fahrenheitToCelsius(positive),  "Should not pass");
        assertEquals(celsiusAssertionZero, tempConv.fahrenheitToCelsius(0.0), 0.000001);
        assertNotEquals(null, tempConv.fahrenheitToCelsius(positive), "Should not return null");

        //tests for edge cases
        assertEquals(0.0, tempConv.fahrenheitToCelsius(32.0));
        assertEquals(100.0, tempConv.fahrenheitToCelsius(212));
        assertEquals(-40.0, tempConv.fahrenheitToCelsius(-40.0));

        //test for conversion doesn't change
        assertEquals(32.0, tempConv.fahrenheitToCelsius(tempConv.celsiusToFahrenheit(32.0)), 0.0000000000000001);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Celsius -> Fahrenheit conversion works for positives, negatives and edge cases")
    void celsiusToFahrenheit() {
        double fahrenheitAssertionPositive = ((positive * (9.0 / 5.0)) + 32.0);
        double fahrenheitAssertionNegative = ((negative * (9.0 / 5.0)) + 32.0);
        double fahrenheitAssertionZero = ((zero * (9.0 / 5.0)) + 32.0);

        assertEquals(fahrenheitAssertionPositive, tempConv.celsiusToFahrenheit(positive),  "Positive equals test failed");
        assertEquals(fahrenheitAssertionNegative, tempConv.celsiusToFahrenheit(negative),  "negative equals test failed");
        assertNotEquals(fahrenheitAssertionNegative, tempConv.celsiusToFahrenheit(positive),  "Should not pass");
        assertEquals(fahrenheitAssertionZero, tempConv.celsiusToFahrenheit(0.0), 0.001);
        assertNotEquals(null, tempConv.celsiusToFahrenheit(positive), "Should not return null");

        //tests for edge cases
        assertEquals(32.0, tempConv.celsiusToFahrenheit(0.0));
        assertEquals(212.0, tempConv.celsiusToFahrenheit(100.0));
        assertEquals(-40.0, tempConv.celsiusToFahrenheit(-40.0));

        //test for conversion doesn't change
        assertEquals(32.0, tempConv.celsiusToFahrenheit(tempConv.fahrenheitToCelsius(32.0)), 0.0000000000000001);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test for isExtremeTemperature method passes with edge cases")
    void isExtremeTemperature() {
        assertTrue(tempConv.isExtremeTemperature(positive));
        assertTrue(tempConv.isExtremeTemperature(negative));
        assertFalse(tempConv.isExtremeTemperature(0.0));
        assertFalse(tempConv.isExtremeTemperature(inRange));

        //edge cases:
        assertFalse(tempConv.isExtremeTemperature(50.0));
        assertFalse(tempConv.isExtremeTemperature(-40));
        assertTrue(tempConv.isExtremeTemperature(-40.00001));
        assertTrue(tempConv.isExtremeTemperature(50.000000001));
    }
}