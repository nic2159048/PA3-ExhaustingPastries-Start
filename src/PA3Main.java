import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/**
 * PA3Main.java, PA3-ExhaustingPastries assignment
 * 
 */
public class PA3Main {
    public static void main(String[] args) {
        // Initializes scanner
        Scanner in = null;
        try {
            in = new Scanner(new File(args[0]));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        int cusPrice = Integer.valueOf(in.nextLine());
        HashMap<String, Integer> foods = new HashMap<String, Integer>();
        while (in.hasNextLine()) {
            String[] food = in.nextLine().split(" ");
            foods.put(food[0].substring(0, food[0].length() - 1),
                    maxAmount(food));
        }
        ArrayList<String> foodsSorted = new ArrayList<String>(foods.keySet());
        Collections.sort(foodsSorted);
        for (String name : foodsSorted) {
            System.out.println(name + " " + foods.get(name));
        }
        int[] holdArray = new int[foodsSorted.size()];
        int unique = 0;
        cusEnumerate(foodsSorted.size(), holdArray, 0, cusPrice, foods,
                foodsSorted, unique);
    }

    public static int maxAmount(String[] food) {
        Integer[] intFood = new Integer[food.length - 1];
        Integer[] maxInt = new Integer[food.length - 1];
        for (int i = 0; i < food.length - 1; i++) {
            intFood[i] = Integer.parseInt(food[i + 1]);
            maxInt[i] = 0;
        }
        int max=0;
        int total = 0;
        Integer[] foodCopy = intFood.clone();
        Integer ending = enumerate(intFood.length, intFood, max, total,
                foodCopy);
        // System.out.println(ending);
        return ending;
    }

    public static int enumerate(int N, Integer[] barray, int I,
            int max, Integer[] foodCopy) {
        if (N == I) {
            if (overSize(barray, N)) {
                I = 0;
                return max;
            }
            int money=0;
            for (int i = 0; i <= N - 1; i++) {

                money = money + (foodCopy[i] * barray[i]);
            }
            if (money > max) {
                max = money;
                I = 0;
                return max;
            }
            I = 0;
            return max;
        }
        for (int i = 0; i <= N; i++) {
            barray[I] = i;
            max = enumerate(N, barray, I + 1, max, foodCopy);
        }
        return max;
    }

    public static boolean overSize(Integer[] barray, int N) {
        int over=0;
        for (int i = 0; i <= N - 1; i++) {
            over= over+ barray[i]*(i+1);
    }
        return over > barray.length;
    }

    public static void cusEnumerate(int N, int[] barray, int I, int cusPrice,
            HashMap<String, Integer> foods, ArrayList<String> foodsSorted,
            int unique) {
        if (N == I) {
            int total=0;
            int iter = 0;
            for (String name : foodsSorted) {
                total = total + (foods.get(name) * barray[iter]);
                iter = iter + 1;
            }
            if (total > cusPrice) {
                I = 0;
                return;
            }
            int potential = 0;
            for (int j : barray)
                if (j > 0) {
                    potential = potential + 1;
                }
            if (potential > unique) {
                unique = potential;
                I = 0;
                return;
            }
            for (int j : barray)
            System.out.print(j);
        System.out.println();

        I = 0;
        return;
    }
        for (int i = 0; i <= 9; i++) {
        barray[I] = i;
            cusEnumerate(N, barray, I + 1, cusPrice, foods, foodsSorted,
                    unique);
    }
    }
}