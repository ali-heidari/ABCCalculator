package com.elpixeler.abccalculator;

import java.io.IOException;

/**
 * The entry point of app
 *
 */
public class App {
    public static void main(String[] args) {
        ABCCalculator abcc = new ABCCalculator("source");
        try {
            abcc.run();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
