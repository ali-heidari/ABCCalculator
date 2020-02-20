package com.elpixeler.abccalculator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

/**
 * Unit test for ABCCalculator.
 */
public class ABCCalculatorTest {
    /**
     * Test the run method
     */
    @Test 
    public void testRunNoSourceFolder() {
        ABCCalculator abcc = new ABCCalculator("no-source-exist");
        try {
            abcc.run();
            fail("NO NullPointerException raised.");
        } catch (NullPointerException e) {
        } 
    }
}
