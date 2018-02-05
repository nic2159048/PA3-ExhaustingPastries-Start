import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * PA3Main.java, PA3-ExhaustingPastries assignment
 * 
 * This program may take upwards of a minute to complete, be patient as it needs
 * this time to check all possibilities
 * 
 * INSTRUCTIONS This program calculates the highest profit possible for a
 * slicing of pastries and how many of them a customer can buy within a budget
 * 
 * Input format should be <customer budget integer> <string>: <integer cost of
 * 1in slice> <integer cost of 2in slice> <integer cost of 3in slice> ...
 * <string>: <integer cost of 1in slice> <integer cost of 2in slice> <integer
 * cost of 3in slice> ... ... ... ...
 * 
 * The first integer is the budget customers have for buying pastries. This is
 * followed by a list of pastries, starting with a pastry name, then followed by
 * the price for length of 1 unit, price per length of 2 units, etc. The file
 * could have any number of lines and each pastry line could have any number of
 * length costs. If a pastry has X costs after it, then it is X inches long.
 * 
 * 
 * Written By Nicholas Hernandez
 */
public class PA3Main {
    public static int[] holdArray;

    // Method calls and file handling for the program
    public static void main(String[] args) {
        // Initializes scanner
        Scanner in = null;
        // Error handling for empty file resulting in program being exited
        try {
            in = new Scanner(new File(args[0]));
        } catch (Exception ex) {
            System.out.println("ERROR: File not found");
            System.exit(1);
        }
        // Gets the customers budget unless one is not given correctly in which
        // case the program is exited
        int cusPrice = 0;
        try {
            cusPrice = Integer.valueOf(in.nextLine());
        } catch (Exception ex) {
            System.out.println("ERROR: Incorrect budget input");
            System.exit(1);
        }
        // Initializes a hash map and populates it with each slice of pastry
        HashMap<String, Integer> foods = new HashMap<String, Integer>();
        while (in.hasNextLine()) {
            String[] food = in.nextLine().split(":");
            String[] numbers = null;
            numbers = food[1].split(" ");
            foods.put(food[0],
                    maxAmount(numbers));
        }
        // Sorts the foods alphabetically
        ArrayList<String> foodsSorted = new ArrayList<String>(foods.keySet());
        Collections.sort(foodsSorted);
        // Calculates the max amount of unique pastries an individual can buy
        // within their budget
        int[] holdArray1 = new int[foodsSorted.size()];
        int unique = 0;
        cusEnumerate(foodsSorted.size(), holdArray1, 0,
                cusPrice, foods,
                foodsSorted, unique);
        // Prints the final output
        finalPrint(foods, foodsSorted, cusPrice);
    }

    // Returns an integer of the max price the baker can sell their slices for
    public static int maxAmount(String[] food) {
        // error handling for foods that were not provided with a price
        try {
            String test = food[0];
                }catch(Exception ex) {
                    return 0;
                }
        // Creates an integer array of the input file for a food
        Integer[] intFood = new Integer[food.length - 1];
        Integer[] maxInt = new Integer[food.length - 1];
        for (int i = 0; i < food.length - 1; i++) {
            // Error handling incorrect slice prices, assigns -1 as an
            // identifier for the printing purpose
            try {
            intFood[i] = Integer.parseInt(food[i + 1]);
            maxInt[i] = 0;
            } catch (Exception ex) {
                return -1;
            }
        }
        // Creates enumerations to find the max price that can be charged and
        // returns it
        int max=0;
        int total = 0;
        Integer[] foodCopy = intFood.clone();
        Integer ending = enumerate(intFood.length, intFood, max, total,
                foodCopy);

        return ending;
    }

    // Creates enumerations for a given int[] that returns the max pricing per
    // length
    public static int enumerate(int N, Integer[] barray, int I,
            int max, Integer[] foodCopy) {
        // If the length of the array is equal to the potential length of the
        // pastry it processes the array
        if (N == I) {
            // If the length of the sizes is greater than possible the program
            // backtracks
            if (overSize(barray, N)) {
                I = 0;
                return max;
            }
            // Calculates the money a set of slicing is worth
            int money=0;
            for (int i = 0; i <= N - 1; i++) {
                money = money + (foodCopy[i] * barray[i]);
            }
            // If money is the new max it is returned
            if (money > max) {
                max = money;
                I = 0;

                return max;
            }
            I = 0;
            return max;
        }
        // loops through all outcomes for a certain length
        for (int i = 0; i <= N; i++) {
            barray[I] = i;
            max = enumerate(N, barray, I + 1, max, foodCopy);
        }
        return max;
    }

    // Returns true if a slicing is over size and impossible, else false
    public static boolean overSize(Integer[] barray, int N) {
        // Calculates the length of the slicing
        int over=0;
        for (int i = 0; i <= N - 1; i++) {
            over= over+ barray[i]*(i+1);
    }
        return over != barray.length;
    }

    // Determines the highest amount of unique pastries a person can buy within
    // their budget
    public static void cusEnumerate(int N, int[] barray, int I, int cusPrice,
            HashMap<String, Integer> foods, ArrayList<String> foodsSorted,
            int unique) {
        if (N == I) {
            // Calculates the total cost of an array[]
            int total=0;
            int iter = 0;
            for (String name : foodsSorted) {
                total = total + (foods.get(name) * barray[iter]);
                iter = iter + 1;
            }
            // If the customer can't afford a pastry you backtrack
            if (total > cusPrice) {
                I = 0;
                return;
            }
            // Calculates the potential unique combos of pricing
            int potential = 0;
            for (int j : barray)
                if (j > 0) {
                    potential = potential + 1;
                }
            // if potential is higher than previous unique new unique array is
            // saved to holdArray
            if (potential >= unique) {
                unique = potential;
                holdArray = barray.clone();
                I = 0;
                return;
            }
        I = 0;
            return;
    }
        // Loops through the many combinations recursively
        for (int i = 0; i <= 1; i++) {
        barray[I] = i;
            cusEnumerate(N, barray, I + 1, cusPrice, foods, foodsSorted,
                    unique);
    }
        return;
    }

    // Prints the final output
    public static void finalPrint(HashMap<String, Integer> foods,
            ArrayList<String> foodsSorted, int cusPrice) {
        // if an error has occurred in handling error is printed first
        for (String name : foodsSorted) {
            if (foods.get(name) == -1) {
                System.out.println("ERROR: Incorrect price input");
            }
        }
        // Prints the cost of each food and skips errors
        for (String name : foodsSorted) {
            if (foods.get(name) == -1) {
                continue;
            }
            System.out.println(name + " costs " + " $" + foods.get(name));
        }
        System.out.println();
        // Initialization of array list for printing
        int i = 0;
        int potential = 0;
        ArrayList<Integer> valuesList = new ArrayList<Integer>(foods.values());
        // Deletes duplicates for prices
        Set<Integer> temp = new HashSet<>();
        temp.addAll(valuesList);
        valuesList.clear();
        valuesList.addAll(temp);
        Collections.sort(valuesList);
        // Prints what foods can be bought in ascending money order and
        // alphabetical order second based on uniques
        System.out.println(Arrays.toString(holdArray));
            for (String name : foodsSorted) {
            System.out.println(name);
            if (holdArray[i] >= 1) {
                if (foods.get(name) < 0) {
                    continue;
                }
                System.out.println(
                        "Can buy " + name + " for $" + foods.get(name));
                potential = potential + 1;
            }
            i = i + 1;
            }

        System.out.println();
        System.out.println("The max number of unique pastries that can be bought with $" + cusPrice + " is: "+ potential);
    }
}