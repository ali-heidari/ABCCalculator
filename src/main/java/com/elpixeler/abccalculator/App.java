package com.elpixeler.abccalculator;

/**
 * The entry point of app
 *
 */
public class App {
    public static void main(String[] args) {
        ABCCalculator abcc = new ABCCalculator("source");
        try {
            abcc.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
