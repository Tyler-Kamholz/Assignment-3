

public class Assignment3B {

    public static void main(String[] args) {
        int N = 10;
        int[] p = {1, 14, 35, 44, 45, 60, 91, 110, 117, 158}; // Prices for rods of length 0 to 10
        int G = 4; // Gold coating constraint

        // Create a memoization array to store intermediate results
        int[][] memo = new int[N + 1][G + 1];

        for (int i = 0; i <= N; i++) {

            for (int j = 0; j <= G; j++) {

                memo[i][j] = -1; // Initialize memoization array with -1 indicating results are not computed yet

            }

        }

        // Calculate the maximum revenue with gold coating constraint using memoization
        int maxRevenueWithGoldCoating = maxRevenue(N, p, G, memo);

        // Print the result
        System.out.println("Maximum revenue with gold coating constraint: " + maxRevenueWithGoldCoating);
    }


    // Function to calculate the maximum revenue considering gold coating constraint
    public static int maxRevenue(int N, int[] p, int G, int[][] memo) {

        // If the result for the subproblem is already computed, return it from memo
        if (memo[N][G] != -1) {

            return memo[N][G];

        }

        // Base case: If rod length is 0 or gold coating constraint is 0, the revenue is 0
        if (N == 0 || G == 0) {

            memo[N][G] = 0;

        } else {

            int maxRevenue = 0;

            // Iterate through all possible cut lengths from 1 to min(N, G)
            for (int j = 1; j <= Math.min(N, G); j++) {
                // Calculate revenue for the current cut length and recursive subproblem
                maxRevenue = Math.max(maxRevenue, 2 * p[j] + maxRevenue(N - j, p, G - j, memo));
                
            }

            memo[N][G] = maxRevenue;
        }

        // Return the maxRevenue
        return memo[N][G];
    }

}
