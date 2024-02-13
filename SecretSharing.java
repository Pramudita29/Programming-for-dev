/*
    Question:
    You are given an integer n representing the total number of individuals. Each individual is identified by a unique 
    ID from 0 to n-1. The individuals have a unique secret that they can share with others.
    The secret-sharing process begins with person 0, who initially possesses the secret. Person 0 can share the secret 
    with any number of individuals simultaneously during specific time intervals. Each time interval is represented by 
    a tuple (start, end) where start and end are non-negative integers indicating the start and end times of the interval.
    You need to determine the set of individuals who will eventually know the secret after all the possible secret-
    sharing intervals have occurred.
    Example:
    Input: n = 5, intervals = [(0, 2), (1, 3), (2, 4)], firstPerson = 0
    Output: [0, 1, 2, 3, 4]
    Explanation:
    In this scenario, we have 5 individuals labeled from 0 to 4.
    The secret-sharing process starts with person 0, who has the secret at time 0. At time 0, person 0 can share the 
    secret with any other person. Similarly, at time 1, person 0 can also share the secret. At time 2, person 0 shares the 
    secret again, and so on.
    Given the intervals [(0, 2), (1, 3), (2, 4)], we can observe that during these intervals, person 0 shares the secret with 
    every other individual at least once.
    Hence, after all the secret-sharing intervals, individuals 0, 1, 2, 3, and 4 will eventually know the secret.

    Algorithm:
    1. Initialize a set to keep track of individuals who know the secret.
    2. Add the first person (person 0) to the set, as they initially know the secret.
    3. Use a loop to repeatedly check if the secret can be shared further during any interval until no new sharing occurs.
       a. For each interval, check if at least one person within the interval knows the secret.
       b. If so, share the secret with all individuals within that interval.
       c. Update the set of individuals who know the secret.
    4. The loop terminates when a pass through all intervals results in no new individuals learning the secret.
    5. Return the set of individuals who know the secret as the result.
    */
public class SecretSharing {

    public static boolean[] findIndividualsWithSecret(int n, int[][] intervals, int firstPerson) {
        boolean[] knowsSecret = new boolean[n]; // Tracks who knows the secret
        knowsSecret[firstPerson] = true; // Initially, only the first person knows the secret
        
        boolean shared = true;
        while (shared) {
            shared = false;
            for (int[] interval : intervals) {
                boolean intervalSharesSecret = false;
                // Check if anyone in the current interval knows the secret
                for (int i = interval[0]; i <= interval[1] && !intervalSharesSecret; i++) {
                    if (knowsSecret[i]) {
                        intervalSharesSecret = true;
                    }
                }
                // If someone in the interval knows the secret, share it with everyone in the interval
                if (intervalSharesSecret) {
                    for (int i = interval[0]; i <= interval[1]; i++) {
                        // If this person didn't know the secret before, it's a new share
                        if (!knowsSecret[i]) {
                            knowsSecret[i] = true;
                            shared = true;
                        }
                    }
                }
            }
        }
        
        return knowsSecret;
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}};
        int firstPerson = 0;
        
        boolean[] result = findIndividualsWithSecret(n, intervals, firstPerson);
        System.out.print("Individuals who will eventually know the secret: ");
        for (int i = 0; i < n; i++) {
            if (result[i]) {
                System.out.print(i + " ");
            }
        }
    }
}
