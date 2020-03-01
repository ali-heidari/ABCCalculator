package com.elpixeler.abccalculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The calculator
 *
 */
public class ABCCalculator {
  private String folderPath;

  /*
   * Always needs a folder where containing all files to check
   */
  public ABCCalculator(String folderPath) {
    this.folderPath = folderPath;
  }

  /*
   * Getter for folderPath
   */
  public String getFolderPath() {
    return this.folderPath;
  }

  /*
   * Setter for folderPath
   */
  public void getFolderPath(String folderPath) {
    this.folderPath = folderPath;
  }

  /*
   * Runs the calculator
   */
  public void run() throws NullPointerException, IOException {
    // Declare variables, will be needed
    int a, b, c;
    double abc;
    // Get list of available files
    for (File fileEntry : new File(this.folderPath).listFiles()) {
      // Check if it is a directory, just bypass it
      if (fileEntry.isDirectory())
        continue;

      // Read source text
      String source = readContent(fileEntry);
      // Distinguish functions block
      Map<String, String> funcBlocks = extractFuncBlocks(source);
      // Now let's calculate the ABC for each method
      for (Map.Entry<String, String> entry : funcBlocks.entrySet()) {
        a = assignmentsCount(entry.getValue());
        b = branchesCount(entry.getValue());
        c = conditionsCount(entry.getValue());
        abc = Math.sqrt(a * a + b * b + c * c);
        System.out.println(fileEntry.getName() + "= ABC score for " + entry.getKey() + ":\t[A=" + a + ",B=" + b + ",C="
            + c + "]\t" + abc);
      }
    }
  }

  /*
   * Finds function blocks
   * 
   * @return Map<String,String> which contains function name and the block
   */
  private Map<String, String> extractFuncBlocks(String source) {
    // Create a regex pattern to extract methods bodies
    // This regex has 3 capturing group; method keywords, method name and the block
    // NOTE: In java there is no support for recursive REGEX at this time, therefore
    // i defined a simple rule for end of methods which need to be followed.
    // void foo() { if(SOME_CONDITION){ } }//ENDOFMETHOD
    // As you see each method should end with }//ENDOFMETHOD
    // Now we can extract body of method with REGEX
    Pattern pattern = Pattern.compile(
        "((?:(?:public|private|protected|static|final|native|synchronized|abstract|transient+\\s+)+[$_\\w<>\\[\\]\\s]*\\s+))([\\$_\\w]+\\([^\\)]*\\)?\\s*)(\\{?[\\s\\S]*?\\}?\\/\\/ENDOFMETHOD)");
    Matcher matcher = pattern.matcher(source);
    Map<String, String> blocks = new HashMap<>();
    while (matcher.find()) {
      blocks.put(matcher.group(2), extractValidLines(matcher.group(3)));
    }
    return blocks;
  }

  /**
   * Finds the valid code lines and returns no comments
   * 
   * @param block The raw body of method
   * @return Only code lines
   */
  public String extractValidLines(String block) {
    Pattern pattern = Pattern.compile("(\\w+.+(;|\\)|\\{)$)", Pattern.MULTILINE | Pattern.DOTALL);
    Matcher matcher = pattern.matcher(block);
    String codeLines="";
    while (matcher.find()) {
      codeLines+= matcher.group()+ "\n";
    }
    return codeLines;
  }

  /**
   * Calculates the A factor for the text
   * 
   * @param text Text to calculates assignments count
   * @return The number of assignments
   */
  public int assignmentsCount(String text) {
    int count = 0;
    // Regex against every combination of =, ++ and --
    Matcher matcher = Pattern.compile("((\\s=\\s)|(\\+=)|(\\-=)|(\\*=)|(\\/=)|\\+\\+|\\-\\-)").matcher(text);
    while (matcher.find())
      count++;
    return count;
  }

  /**
   * Calculates the B factor for the text
   * 
   * @param text Text to calculates branches count
   * @return The number of branches
   */
  public int branchesCount(String text) {
    int count = 0;
    // Regex against every combination of =, ++ and --
    Matcher matcher = Pattern.compile("(\\w+\\()").matcher(text);
    while (matcher.find())
      count++;
    return count;
  }

  /**
   * Calculates the C factor for the text
   * 
   * @param text Text to calculates conditions count
   * @return The number of conditions
   */
  public int conditionsCount(String text) {
    int count = 0;
    // Regex against every combination of =, ++ and --
    Matcher matcher = Pattern.compile(
        "((?:((if\\s*\\(+)|;)[^(\\\\n)]*)(==|<|>|!|\\?))|((if\\s*\\()|(else\\s*if)|else|case|default|(try\\s*\\{)|catch)")
        .matcher(text);
    while (matcher.find())
      count++;
    return count;
  }

  /*
   * Read the content of provided file
   */
  private String readContent(File file) throws IOException {
    // Ensure reader will be closed at the end.
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      StringBuilder sb = new StringBuilder();
      String line = br.readLine();
      // Read all content line by line and join them into an string builder instance
      while (line != null) {
        sb.append(line);
        // Dealing with different platforms, unix and non-unix disagreement
        sb.append(System.lineSeparator());
        line = br.readLine();
      }
      return sb.toString();
    }
  }
}
