/*
    Question:
    You are given a 2D grid representing a maze in a virtual game world. The grid consists of different types of cells:
    - 'P' represents an empty path where you can move freely.
    - 'W' represents a wall that you cannot pass through.
    - 'S' represents the starting point.
    - Lowercase letters ('a' to 'f') represent hidden keys.
    - Uppercase letters ('A' to 'F') represent locked doors that can be opened with the corresponding keys.
    - 'E' represents the exit point.
    You start at 'S' and can move in any of the four cardinal directions to adjacent cells, but you cannot walk through walls ('W').
    Your task is to find the minimum number of moves required to collect all the keys and reach the exit point 'E'. If it is impossible, return -1.

    Algorithm:
    1. Perform a Breadth-First Search (BFS) starting from 'S', considering all possible moves in the four cardinal directions.
    2. Use a bitmask to represent keys collected at any point. This allows tracking which doors can be opened.
    3. For each step, check and update the state based on the type of cell encountered (empty path, key, door, wall, or exit).
    4. Keep track of visited states to avoid revisiting the same cell with the same keys collected.
    5. If the exit 'E' is reached and all keys have been collected, return the number of moves made.
    6. If all possible states are explored and the exit cannot be reached with all keys, return -1.
    */

    
    import java.util.LinkedList;
    import java.util.Queue;
    
    public class MazeSolver {
        // Define a state class to encapsulate the current position, collected keys, and move count
        static class State {
            int x, y; // Current position in the maze
            int keys; // Bitmask representing keys collected so far
            int moves; // Number of moves taken to reach this state
            State(int x, int y, int keys, int moves) {
                this.x = x; // Initialize current X position
                this.y = y; // Initialize current Y position
                this.keys = keys; // Initialize keys collected
                this.moves = moves; // Initialize moves made
            }
        }
    
        // Directions array to represent movement in four cardinal directions: up, down, left, right
        private static final int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
        public static int minMovesToCollectAllKeys(String[][] grid) {
            int m = grid.length; // Number of rows in the grid
            int n = grid[0].length; // Number of columns in the grid
            int startX = 0, startY = 0, totalKeys = 0; // Variables to store starting position and total key count
            boolean[][][] visited = new boolean[m][n][1 << 6]; // Visited states: position and keys collected
            Queue<State> queue = new LinkedList<>(); // Queue for BFS
    
            // Scan the grid to find the starting point 'S' and count total keys
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    char cell = grid[i][j].charAt(0); // Get the cell character
                    if (cell == 'S') { // Starting point found
                        startX = i; // Set starting X position
                        startY = j; // Set starting Y position
                    } else if (cell >= 'a' && cell <= 'f') { // Key found
                        totalKeys++; // Increment total key count
                    }
                }
            }
    
            // Add initial state to the queue with starting position, 0 keys, and 0 moves
            queue.add(new State(startX, startY, 0, 0));
            // Mark the starting state as visited
            visited[startX][startY][0] = true;
    
            // BFS to explore all possible states in the maze
            while (!queue.isEmpty()) {
                State current = queue.poll(); // Get and remove the head of the queue
    
                // Check if all keys have been collected
                if (current.keys == (1 << totalKeys) - 1) { 
                    return current.moves; // Return the number of moves to collect all keys
                }
    
                // Try moving in all four directions from the current state
                for (int[] dir : directions) {
                    int newX = current.x + dir[0], newY = current.y + dir[1]; // Calculate new position
                    int newKeys = current.keys; // Copy keys collected so far
                    // Ensure the new position is within bounds and not visited with the same keys
                    if (newX >= 0 && newX < m && newY >= 0 && newY < n && !visited[newX][newY][newKeys]) {
                        char nextCell = grid[newX][newY].charAt(0); // Cell in the new position
                        // Check cell type and act accordingly
                        if (nextCell == 'W') continue; // Wall, cannot move
                        // Door, can move only if we have the key
                        if (nextCell >= 'A' && nextCell <= 'F' && (newKeys & (1 << (nextCell - 'A'))) == 0) continue;
                        // Collect key if it's a key cell
                        if (nextCell >= 'a' && nextCell <= 'f') newKeys |= (1 << (nextCell - 'a')); 
    
                        // Mark the new state as visited
                        visited[newX][newY][newKeys] = true;
                        // Add the new state to the queue
                        queue.add(new State(newX, newY, newKeys, current.moves + 1));
                    }
                }
            }
    
            return -1; // Return -1 if it's impossible to collect all keys
        }
    
        public static void main(String[] args) {
            // Example grid input
            String[][] grid = {
                {"S","P","q","P","P"},
                {"W","W","W","P","W"},
                {"r","P","Q","P","R"}
            };
            // Calculate and print the minimum moves to collect all keys
            System.out.println("Minimum moves to collect all keys: " + minMovesToCollectAllKeys(grid));
        }
    }
    