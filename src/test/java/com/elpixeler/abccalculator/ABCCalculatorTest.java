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

    String testBlock2 = " // Declare variables, will be needed" + "  int a, b, c;" + "  double abc;"
            + "  // Get list of available files" + " for (File fileEntry : new File(this.folderPath).listFiles()) {"
            + "     // Check if it is a directory, just bypass it" + "   if (fileEntry.isDirectory())" + "    continue;"
            + "   // Read source text" + "  String source;" + "   try {" + "       source = readContent(fileEntry);"
            + "    } catch (IOException e) {" + "    continue;" + "      }" + "  // Distinguish functions block"
            + "   Map<String, String> funcBlocks = extractFuncBlocks(source);"
            + "    // Now let's calculate the ABC for each method"
            + "   for (Map.Entry<String, String> entry : funcBlocks.entrySet()) {"
            + " a = assignmentsCount(entry.getValue());" + "  b = branchesCount(entry.getValue());"
            + "     c = conditionsCount(entry.getValue());" + "    abc = Math.sqrt(a * a + b * b + c * c);"
            + "     System.out.println(\"ABC score for \" + entry.getKey() + \":\t[A=\" + a + \",B=\" + b + \",C=\" + c + \"]\t\" + abc);\""
            + " }" + "   }";

    /**
     * Test the run method
     */
    @Test
    public void testRunNoSourceFolder() {
        ABCCalculator abcc = new ABCCalculator("no-source-exist");
        try {
            abcc.run();
            fail("NO NullPointerException raised.");
        } catch (Exception e) {
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
     * Test the assignments counter 2
     */
    @Test
    public void testAssignmentsCounter2() {
        ABCCalculator abcc = new ABCCalculator("folder");
        int result = abcc.assignmentsCount(testBlock2);
        assertEquals(6, result);
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
     * Test the branches counter 2
     */
    @Test
    public void testBranchesCounter2() {
        ABCCalculator abcc = new ABCCalculator("folder");
        int result = abcc.branchesCount(testBlock2);
        assertEquals(15, result);
    }

    /**
     * Test the conditions counter
     */
    @Test
    public void testConditionsCounter() {
        ABCCalculator abcc = new ABCCalculator("folder");
        int result = abcc.conditionsCount(testBlock);
        assertEquals(3, result);
    }

    /**
     * Test the conditions counter 2
     */
    @Test
    public void testConditionsCounter2() {
        ABCCalculator abcc = new ABCCalculator("folder");
        int result = abcc.conditionsCount(testBlock2);
        assertEquals(5, result);
    }
}
