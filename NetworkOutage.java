/*
    Question:
    You are tasked with creating an application for an ISP to manage network devices. Given a 2D array representing
    network connections between devices, write an algorithm to return a list of impacted network devices if there's
    a power outage on a certain device. This helps notify linked consumers about the outage.
    
    Input: edges= {{0,1},{0,2},{1,3},{1,6},{2,4},{4,6},{4,5},{5,7}}, Target Device: 4
    Output: Impacted Device List = {5,7}
    
    Algorithm:
    1. Build a graph representation from the given list of edges.
    2. Perform a Depth-First Search (DFS) starting from the target device to find all reachable devices.
    3. Collect all devices reached during the DFS as they are impacted by the outage.
    4. Return the list of impacted devices.
    */

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    
    public class NetworkOutage {
        /*
        Method to add an edge to the graph. This ensures the graph is undirected.
        - src: Source node of the edge
        - dest: Destination node of the edge
        */
        private static void addEdge(Map<Integer, List<Integer>> graph, int src, int dest) {
            // Ensure the adjacency list for src exists, then add dest to src's list
            graph.computeIfAbsent(src, k -> new ArrayList<>()).add(dest);
            // Ensure the adjacency list for dest exists, then add src to dest's list, ensuring an undirected edge
            graph.computeIfAbsent(dest, k -> new ArrayList<>()).add(src);
        }
        
        /*
        DFS method to explore all impacted devices starting from the target device.
        - graph: The graph represented as an adjacency list
        - node: The current node being explored
        - visited: Array to keep track of visited nodes
        - impacted: List to collect all impacted nodes
        */
        private static void dfs(Map<Integer, List<Integer>> graph, int node, boolean[] visited, List<Integer> impacted) {
            // Mark the current node as visited
            visited[node] = true;
            // Iterate through all neighbors of the current node
            for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
                // If the neighbor hasn't been visited yet
                if (!visited[neighbor]) {
                    // Add the neighbor to the list of impacted devices
                    impacted.add(neighbor);
                    // Recursively visit the neighbor
                    dfs(graph, neighbor, visited, impacted);
                }
            }
        }
        
        /*
        Method to find impacted devices given the edges and the target device.
        - edges: Array of edges representing network connections
        - targetDevice: The device with a power outage
        Returns a list of impacted devices.
        */
        public static List<Integer> findImpactedDevices(int[][] edges, int targetDevice) {
            // Create a graph as an adjacency list
            Map<Integer, List<Integer>> graph = new HashMap<>();
            // Fill the graph with edges
            for (int[] edge : edges) {
                addEdge(graph, edge[0], edge[1]);
            }
            
            // List to store impacted devices
            List<Integer> impacted = new ArrayList<>();
            // Array to keep track of visited nodes, assuming device IDs are 0-indexed and continuous
            boolean[] visited = new boolean[graph.size() + 1];
            // Perform DFS from the target device
            dfs(graph, targetDevice, visited, impacted);
            
            // Return the list of impacted devices
            return impacted;
        }
        
        public static void main(String[] args) {
            // Network connections represented as edges
            int[][] edges = {{0,1},{0,2},{1,3},{1,6},{2,4},{4,6},{4,5},{5,7}};
            // Device with power outage
            int targetDevice = 4;
            // Find and print the list of impacted devices
            List<Integer> impactedDevices = findImpactedDevices(edges, targetDevice);
            System.out.println("Impacted Device List = " + impactedDevices);
        }
    }