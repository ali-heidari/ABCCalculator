package com.elpixeler.abccalculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

/**
 * Unit test for ABCCalculator.
 */
public class ABCCalculatorTest {
    String testBlock = "   // Check if bytes initiated before" + "if (bytes == null)" + "return;" + "   // Init configs"
            + "   fillConfigs();" + "  // Fill the points" + "    initPoints();"
            + "    // We start with angle 0 and go against clock's direction" + "   double angle = 0;"
            + "   // Calculates every points and iterate along increasing angle"
            + "   for (int i = 0; i < _BarCount; i++, angle += _AngleStep) {" + "     // Convert to radians"
            + "     double radianAngle = Math.toRadians(angle);" + "    this.fillStartingPoints(i, radianAngle);"
            + "   // Calculates points for current round" + "   calcRound(i, radianAngle);" + " }"
            + " if (getConfig(\"needsInit\") == 0)" + "     canvas.drawLines(points, paint);" + "super.onDraw(canvas);"
            + "// Resets configurations variable for next calling of onDraw" + "this.resetConfigs();";

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

    /**
     * Test the assignments counter
     */
    @Test
    public void testAssignmentsCounter() {
        ABCCalculator abcc = new ABCCalculator("folder");
        int result = abcc.assignmentsCount(testBlock);
        assertEquals(5, result);
    }
    /**
     * Test the branches counter
     */
    @Test
    public void testBranchesCounter() {
        ABCCalculator abcc = new ABCCalculator("folder");
        int result = abcc.branchesCount(testBlock);
        assertEquals(9, result);
    }
    /**
     * Test the conditions counter
     */
    @Test
    public void testConditionsCounter() {
        ABCCalculator abcc = new ABCCalculator("folder");
        int result = abcc.conditionsCount(testBlock);
        assertEquals(5, result);
    }
}
