/* Algorithm Description:
     1. Sum all the dresses in the sewing machines to find the total number of dresses.
     2. Check if the total number of dresses is divisible by the number of machines to determine
        if equalization is possible. If not divisible, return -1.
     3. Calculate the target number of dresses per machine by dividing the total dresses by the number of machines.
     4. Iterate through the machines, calculating the cumulative difference from the target for each machine.
        This represents the excess or deficit of dresses relative to what is ultimately needed for equalization.
     5. Track the maximum absolute cumulative difference encountered during the iteration. This value represents
        the minimum number of moves required to equalize the dresses across all machines, as it indicates the largest
        single adjustment needed at any point in the process.
     6. Return the maximum absolute cumulative difference as the answer.
    */


public class ClothingFactoryManager {

    public static int minMovesToEqualize(int[] machines) {
        int totalDresses = 0;
        // Calculate the total number of dresses
        for (int dresses : machines) {
            totalDresses += dresses;
        }

        int n = machines.length;
        // Check if equalization is possible
        if (totalDresses % n != 0) {
            return -1;
        }

        int target = totalDresses / n;
        int maxDiff = 0;
        int cumulativeDiff = 0;

        // Calculate the minimum number of moves required
        for (int dresses : machines) {
            int diff = dresses - target; // Difference from the target
            cumulativeDiff += diff; // Cumulative difference
            maxDiff = Math.max(maxDiff, Math.abs(cumulativeDiff)); // Maximum adjustment needed
        }

        return maxDiff;
    }

    public static void main(String[] args) {
        int[] machines = {2, 1, 3, 0, 2};
        System.out.println("Minimum number of moves required: " + minMovesToEqualize(machines));
    }
}
