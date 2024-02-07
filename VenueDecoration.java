/*
QUESTION:
You are a planner working on organizing a series of events in a row of n venues. Each venue can be decorated with 
one of the k available themes. However, adjacent venues should not have the same theme. The cost of decorating 
each venue with a certain theme varies.
The costs of decorating each venue with a specific theme are represented by an n x k cost matrix. For example, 
costs [0][0] represents the cost of decorating venue 0 with theme 0, and costs[1][2] represents the cost of 
decorating venue 1 with theme 2. Your task is to find the minimum cost to decorate all the venues while adhering 
to the adjacency constraint.
For example, given the input costs = [[1, 5, 3], [2, 9, 4]], the minimum cost to decorate all the venues is 5. One 
possible arrangement is decorating venue 0 with theme 0 and venue 1 with theme 2, resulting in a minimum cost of 
1 + 4 = 5. Alternatively, decorating venue 0 with theme 2 and venue 1 with theme 0 also yields a minimum cost of 
3 + 2 = 5.
Write a function that takes the cost matrix as input and returns the minimum cost to decorate all the venues while 
satisfying the adjacency constraint.
Please note that the costs are positive integers.
Example: Input: [[1, 3, 2], [4, 6, 8], [3, 1, 5]] Output: 7 
Explanation: Decorate venue 0 with theme 0, venue 1 with theme 1, and venue 2 with theme 0. Minimum cost: 1 + 
6 + 1 = 7.

Algorithm Description:
1. Define a recursive function to calculate the minimum cost for decorating each venue starting from a specific venue and theme.
2. Use a memoization table to store the results of subproblems to avoid redundant calculations.
3. For the base case, if all venues are considered, return 0 as the cost.
4. For each venue, recursively calculate the cost of decorating it with each theme, excluding the theme used for the previous venue.
5. Update the memoization table with the minimum cost found for decorating the current venue with a specific theme.
6. For the first venue, iterate over all possible themes and use the recursive function to find the minimum cost.
7. Return the minimum cost found for decorating all venues.

This algorithm ensures that adjacent venues do not have the same theme while minimizing the overall decoration cost.
*/

// VenueDecoration class implements the algorithm to find the minimum cost for decorating a series of venues
// with the constraint that adjacent venues must not have the same theme.
public class VenueDecoration {

    // Calculates the minimum cost to decorate all venues with given costs and constraints.
    public static int minCostToDecorate(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) {
            return 0;
        }

        int n = costs.length; // Number of venues
        int k = costs[0].length; // Number of themes

        // Initialize memoization table with -1 to indicate uncomputed states
        int[][] memo = new int[n][k];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                memo[i][j] = -1;
            }
        }

        int minCost = Integer.MAX_VALUE;
        // Start with each theme for the first venue and calculate the minimum cost
        for (int j = 0; j < k; j++) {
            minCost = Math.min(minCost, minCostUtil(costs, n, k, 0, j, memo));
        }

        return minCost;
    }

    // Recursive helper function to calculate the minimum cost for decorating venues.
    private static int minCostUtil(int[][] costs, int n, int k, int venue, int theme, int[][] memo) {
        if (venue == n) {
            return 0; // Base case: all venues are decorated
        }

        if (memo[venue][theme] != -1) {
            return memo[venue][theme]; // Return memoized result if available
        }

        int minCost = Integer.MAX_VALUE;
        // Calculate minimum cost for the current venue with each theme, excluding the previous theme
        for (int nextTheme = 0; nextTheme < k; nextTheme++) {
            if (nextTheme != theme) {
                int cost = costs[venue][theme] + minCostUtil(costs, n, k, venue + 1, nextTheme, memo);
                minCost = Math.min(minCost, cost);
            }
        }

        memo[venue][theme] = minCost; // Memoize the result
        return minCost;
    }

    // Main method to test the function with an example
    public static void main(String[] args) {
        int[][] costs = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
        System.out.println("Minimum cost to decorate all venues: " + minCostToDecorate(costs));
    }
}
