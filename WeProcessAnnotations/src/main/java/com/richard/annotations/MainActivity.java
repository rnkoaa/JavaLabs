package com.richard.annotations;

import com.richard.annotations.domain.CalzonePizza;
import com.richard.annotations.domain.MargheritaPizza;
import com.richard.annotations.domain.Meal;
import com.richard.annotations.domain.Tiramisu;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by rnkoaa on 4/22/15.
 */
public class MainActivity {
    //private MealFactory factory = new MealFactory();
    public Meal order(String mealName) {
        //return factory.create(mealName);
        if (mealName == null) {
            throw new IllegalArgumentException("Name of the meal is null!");
        }

        if ("Margherita".equals(mealName)) {
            return new MargheritaPizza();
        }

        if ("Calzone".equals(mealName)) {
            return new CalzonePizza();
        }

        if ("Tiramisu".equals(mealName)) {
            return new Tiramisu();
        }

        throw new IllegalArgumentException("Unknown meal '" + mealName + "'");
    }


    public static void main(String[] args) {
        new MainActivity().run(args);
    }

    private void run(String[] args) {
        System.out.println("runnining from main ");
        Meal meal = order(usingBufferedReader());
        System.out.println("Bill: $" + meal.getPrice());
    }

    private static String usingConsoleReader() {
        Console console = null;
        String inputString = null;
        try {
            // creates a console object
            console = System.console();
            // if console is not null
            if (console != null) {
                // read line from the user input
                inputString = console.readLine();
                // prints
                System.out.println("Name entered : " + inputString);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inputString;
    }

    private static String usingBufferedReader() {
        System.out.println("Name: ");
        String input = null;
        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            input = bufferRead.readLine();

            System.out.println("Name entered : " + input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return input;
    }

    private static void usingScanner() {
        System.out.println("Name: ");

        Scanner scanIn = new Scanner(System.in);
        String inputString = scanIn.nextLine();

        scanIn.close();
        System.out.println("Name entered : " + inputString);
    }
}
