import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class Ex14 {
    public static void main(String[] args) {
        System.out.println("Problem 1: Water Volume");
        int[] heights = new int[]{1, 1, 1, 3, 1, 1, 1, 2};
        int trappedWater = waterVolume(heights);
        printGraph(heights);
        System.out.println("example 1:\nGiven the heights array: " 
            + Arrays.toString(heights));
        System.out.println("The trapped water volume between barriers is: " 
            + trappedWater + " units.");

        int[] heights2 = new int[]{1, 1, 1, 3, 1, 1, 1, 1, 4, 1, 1};
        int trappedWater2 = waterVolume(heights2);
        printGraph(heights2);
        System.out.println("example 2:\nGiven the heights array: "
            + Arrays.toString(heights2));
        System.out.println("The trapped water volume between barriers is: " 
            + trappedWater2 + " units.");


        System.out.println("\n\nProblem 2: Longest Subsequence whith even sum in an Array");
        int longestOddSubsequence = what(heights);
        System.out.println("example 1:\nGiven the array: " 
            + Arrays.toString(heights));
        System.out.println("The length of the longest subsequence with an even sum is: " 
            + longestOddSubsequence);

        System.out.println("example 2:\nGiven the array: " 
            + Arrays.toString(heights2));
        longestOddSubsequence = what(heights2);
        System.out.println("The length of the longest subsequence with an even sum is: " 
            + longestOddSubsequence);



        Scanner scanner = new Scanner(System.in);

        System.out.println("\n\nProblem 3: Find how many solutions for x1+x2+x3=num");
        System.out.print("Enter a number between 3 and 30: ");
        int num = scanner.nextInt();
        int totalSolutions = solutions(num);
        System.out.println("The total number of solutions where three numbers" +
                "(between 1 and 10) sum up to " + num + " is: " + totalSolutions);

        test_mat(scanner);
        scanner.close(); // close the scanner at the very end of the main method
    }

    public static int[][] generateRandomMatrix(int rows, int cols, int minValue, int maxValue) {
        Random rand = new Random();
        int[][] mat = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mat[i][j] = rand.nextInt(maxValue - minValue + 1) + minValue;
            }
        }

        return mat;
    }

    public static void test_mat(Scanner scanner) {
        String choice = "";
        boolean firstTime = true;
        System.out.println("\n\nProblem 4: Find Sum");


        // Initially generate a matrix outside the loop
        int[][] mat = generateRandomMatrix(4, 4, 1, 100);

        while (!choice.equalsIgnoreCase("q")) {
            if (firstTime) {
                System.out.println("Generating mat... Done! The matrix is:");
                prettyPrint(mat);
                firstTime = false;
            }

            System.out.print("Enter the desired sum (or type 'q' to quit): ");
            choice = scanner.next();

            if (choice.equalsIgnoreCase("q")) {
                break;
            }

            int sum;
            try {
                sum = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number or 'q' to quit.");
                continue;
            }
            
            System.out.print("Looking for a path that sums up to " + sum + "... ");

            int[][] path = new int[mat.length][mat[0].length];
            boolean found = findSum(mat, sum, path);
            System.out.println("Done!");
            if (found) {
                System.out.println("The path that sums up to " + sum + " is:");
                prettyPrint(path);
            } else {
                System.out.println("There is no path that sums up to " + sum);
            }

            System.out.println(
                    "\nWould you like to:\n1. Generate a new matrix? (Type 'm')\n2. Enter a new sum? (Press 'a')\n3. Quit? (Type 'q')");
            choice = scanner.next();
            while (!choice.equalsIgnoreCase("m") 
                    && !choice.equalsIgnoreCase("q") 
                    && !choice.equalsIgnoreCase("a")) {
                System.out.println("Invalid input. Please enter a valid choice.");
                choice = scanner.next();
            }

            if (choice.equalsIgnoreCase("m")) {
                mat = generateRandomMatrix(4, 4, 1, 100); // Generate a new matrix only when 'm' is chosen
                firstTime = true;
            } else if (choice.equalsIgnoreCase("q")) {
                break; // This will break out of the loop
            } else if (choice.equalsIgnoreCase("a"))
                continue; // This will loop back to the beginning of the loop
        }
    }
    


    public static void prettyPrint(int[][] mat) {
        for (int[] row : mat) {
            for (int elem : row) {
                System.out.printf("%4d", elem);
            }
            System.out.println();
        }
    }

    public static void printGraph(int[] heights) {
    int max = getMax(heights);
    
    for(int i = max; i >= 1; i--){
        for(int j = 0; j < heights.length; j++){
            if(heights[j] >= i)
                System.out.print("| ");
            else
                System.out.print("  ");
        }
        System.out.println();
    }
    for (int j = 0; j < heights.length; j++) {
        System.out.print("--");
    }
    System.out.println();
    }

    public static int getMax(int[] heights) {
        int max = Integer.MIN_VALUE;
        for (int height : heights) {
            if (height > max)
                max = height;
        }
        return max;
    }
    
    /**
     * A method to calculate the capacity of water a given array can handle.
     * @param heights the given array
     * @return the amount of water it can handle
     */
    public static int waterVolume(int[] heights) {
        int[] leftMaximum = new int[heights.length];
        int[] rightMaximum = new int[heights.length];
        int currentMaxLeft = 0;
        int currentMaxRight = 0;
        int water = 0;
        
        for (int i = 0, j = heights.length - 1; i < heights.length; i++, j--) {
            leftMaximum[i] = currentMaxLeft;
            if (heights[i] > currentMaxLeft) {
                leftMaximum[i] = heights[i];
                currentMaxLeft = heights[i];
            }
            
            rightMaximum[j] = currentMaxRight;
            if (heights[j] > currentMaxRight) {
                rightMaximum[j] = heights[j];
                currentMaxRight = heights[j];
            }
        }
        
        for (int k = 1; k < heights.length - 1; k++) {
            if (leftMaximum[k] >= rightMaximum[k])
                water += (rightMaximum[k] - heights[k]);
            else
                water += (leftMaximum[k] - heights[k]);
        }
        return water;
    } // time - O(n)
      // space - O(1)

    // question 2
    // (a) the method returns the largest number of following numbers (sequence) that their sum is even
    // (b) time - O(n^3), space - O(1)
    // (c) - failed
    // (d) - O(n^2)

    public static int what(int[] a) {
        int temp = 0;
        for (int i = 0; i < a.length; i++) {
            int c = a[i];
            for (int j = i; j < a.length; j++) {
                c += a[j];
                if (c % 2 == 1) {
                    if (j - i + 1 > temp)
                        temp = j - i + 1;
                }
            }
        }
        return temp;
    } // SHAY: Could be done in O(n) (-10)

    // question 3
    public static int findAnswer(int num) {
        if (num == 3) {
            return 1;  // only 1 solution if the answer is 3 (1 + 1 + 1)
        }
        if (num >= 4 && num <= 30) {
            return (findAnswer(num - 1) + (num - 2)); // a recursive equation to find the number of solutions
        }
        return 0;  // answer is not valid
    }

    public static void print(int one, int two, int three, int num) {
        if (one + two + three == num)
            System.out.println(one + " + " + two + " + " + three);
    }

    public static void print1(int one, int two, int three, int num) {
        print(one, two, three, num);
        if (three > 1) {
            print1(one, two, three - 1, num);
        }
    }

    public static void print2(int one, int two, int three, int num) {
        if (two >= 1) {
            print1(one, two, three - 1, num);
            print2(one, two - 1, three, num);
        } else return;
    }

    public static void print3(int one, int two, int three, int num) {
        if (one >= 1) {
            print1(one, two, three - 1, num);
            print2(one, two - 1, three, num);
            print3(one - 1, two, three, num);
        }
    }

    public static int solutions(int num) {
        findAnswer(num);
        print1(10, 10, 10, num);
        print2(10, 10, 10, num);
        print3(10, 10, 10, num);
        return findAnswer(num);
    }

    /**
     * A method that attempts to find a path from any starting position in the
     * matrix.
     * 
     * @para mat the 2D int array to find a path upon
     * @para sum the number of the total path cells
     * @para path an empty array to mark the right path upon
     * @return if there's a valid path
     */
    public static boolean findSum(int[][] mat, int sum, int[][] path) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (findSum(mat, sum, path, i, j)) { // Start from position (i, j)
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A method that tries to find a path from a specific place in the matrix
     * 
     * @para mat the 2D int array to find a path upon
     * @para sum the number of the total path cells
     * @para path an empty array to mark the cells which added to the path
     * @para i the row index
     * @para j the column index
     * @return if there's a valid path
     */
    public static boolean findSum(int[][] mat, int sum, int[][] path, int i, int j) {
        if (i < 0 || i >= mat.length || j < 0 || j >= mat[0].length || path[i][j] == 1 || mat[i][j] > sum) {
            return false;
        }
        if (mat[i][j] == sum) {
            path[i][j] = 1;
            return true;
        }

        path[i][j] = 1; // Mark this cell as part of the current path

        // Try each direction
        if (findSum(mat, sum - mat[i][j], path, i + 1, j) ||
                findSum(mat, sum - mat[i][j], path, i - 1, j) ||
                findSum(mat, sum - mat[i][j], path, i, j + 1) ||
                findSum(mat, sum - mat[i][j], path, i, j - 1)) {
            return true;
        }

        path[i][j] = 0; // If no direction worked out, backtrack
        return false;
    }
}
