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
  public void run() throws NullPointerException {
    // Declare variables, will be needed
    int a, b, c;
    double abc;
    // Get list of available files
    for (File fileEntry : new File(this.folderPath).listFiles()) {
      // Check if it is a directory, just bypass it
      if (fileEntry.isDirectory())
        continue;
      // Read source text
      String source;
      try {
        source = readContent(fileEntry);
      } catch (IOException e) {
        continue;
      }
      // Distinguish functions block
      Map<String, String> funcBlocks = extractFuncBlocks(source);
      // Now let's calculate the ABC for each method
      for (Map.Entry<String, String> entry : funcBlocks.entrySet()) {
        a = assignmentsCount(entry.getValue());
        b = branchesCount(entry.getValue());
        c = conditionsCount(entry.getValue());
        abc = Math.sqrt(a * a + b * b + c * c);
        System.out.println("ABC score for " + entry.getKey() + ":\t[A=" + a + ",B=" + b + ",C=" + c + "]\t" + abc);
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
    Pattern pattern = Pattern.compile(
        "((?:(?:public|private|protected|static|final|native|synchronized|abstract|transient+\\s+)+[$_\\w<>\\[\\]\\s]*\\s+))([\\$_\\w]+\\([^\\)]*\\)?\\s*)(\\{?[^\\}]*\\}?)");
    Matcher matcher = pattern.matcher(source);
    Map<String, String> blocks = new HashMap<>();
    while (matcher.find()) {
      blocks.put(matcher.group(2), matcher.group(3));
    }
    return blocks;
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
    Matcher matcher = Pattern.compile("(=|\\+\\+|\\-\\-)").matcher(text);
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
    Matcher matcher = Pattern.compile("(==|<|>|!|\\?)\\B|(if.\\(|else\\sif|else|case|default|try|catch)\\b").matcher(text);
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
