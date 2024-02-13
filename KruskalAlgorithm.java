/*
    Question and Algorithm:
    Implement Kruskal's algorithm to find the minimum spanning tree of a graph. The algorithm includes:
    1. Sorting all edges in non-decreasing order of their weight.
    2. Picking the smallest edge and checking if it forms a cycle with the spanning tree formed so far. If the cycle
       is not formed, include this edge. Otherwise, discard it.
    3. Repeat step 2 until there are (V-1) edges in the spanning tree, where V is the number of vertices.

    This implementation uses a custom MinHeap for edge sorting and Union-Find for cycle detection.
    */

class Edge implements Comparable<Edge> {
    int src, dest, weight; // Source vertex, Destination vertex, and Weight of the edge

    // Constructor for the Edge class
    Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    // Comparator method for sorting edges based on their weight
    public int compareTo(Edge compareEdge) {
        return this.weight - compareEdge.weight;
    }
}

class MinHeap {
    Edge[] heap; // Array to store heap elements
    int size; // Number of elements currently in the heap
    int capacity; // Maximum capacity of the heap

    // Constructor for the MinHeap class
    MinHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new Edge[capacity];
    }

    // Inserts a new edge into the heap
    void insert(Edge edge) {
        if (size == capacity) return; // Heap is full, exit the method
        heap[size] = edge; // Place the new edge at the end of the heap
        int current = size++; // Increment size and keep track of the current index

        // Fix the min heap property if it's violated
        while (heap[current].compareTo(heap[parent(current)]) < 0) {
            swap(current, parent(current)); // Swap with parent until order is restored
            current = parent(current); // Move up to the parent index
        }
    }

    // Extracts and returns the minimum edge (root of the heap)
    Edge extractMin() {
        if (size <= 0) return null; // Heap is empty
        if (size == 1) return heap[--size]; // Only one element in the heap

        Edge root = heap[0]; // Store the root value
        heap[0] = heap[--size]; // Replace root with the last element in the heap
        minHeapify(0); // Call minHeapify to restore the min heap property
        return root; // Return the minimum edge
    }

    // Maintains the min heap property starting from index i
    void minHeapify(int i) {
        int left = leftChild(i); // Index of left child
        int right = rightChild(i); // Index of right child
        int smallest = i; // Initialize smallest as current node

        // Find the smallest among node i, left child, and right child
        if (left < size && heap[left].compareTo(heap[i]) < 0)
            smallest = left;
        if (right < size && heap[right].compareTo(heap[smallest]) < 0)
            smallest = right;

        // If the smallest is not the current node, swap and continue heapifying
        if (smallest != i) {
            swap(i, smallest);
            minHeapify(smallest);
        }
    }

    // Returns the index of the parent node
    int parent(int i) { return (i - 1) / 2; }
    // Returns the index of the left child
    int leftChild(int i) { return (2 * i + 1); }
    // Returns the index of the right child
    int rightChild(int i) { return (2 * i + 2); }
    // Swaps two elements in the heap
    void swap(int x, int y) {
        Edge temp = heap[x];
        heap[x] = heap[y];
        heap[y] = temp;
    }
}

class UnionFind {
    int[] parent, rank; // Parent array and rank array for Union-Find

    // Constructor for the UnionFind class
    UnionFind(int n) {
        parent = new int[n]; // Initialize parent array
        rank = new int[n]; // Initialize rank array
        for (int i = 0; i < n; i++) {
            parent[i] = i; // Each element is initially its own parent (self-loop)
        }
    }

    // Find the representative of the set that contains element i
    int find(int i) {
        if (parent[i] != i) // Path compression heuristic
            parent[i] = find(parent[i]);
        return parent[i];
    }

    // Union of two sets represented by x and y
    void union(int x, int y) {
        int xRoot = find(x), yRoot = find(y); // Find the roots of the sets

        // Union by rank heuristic
        if (rank[xRoot] < rank[yRoot])
            parent[xRoot] = yRoot;
        else if (rank[yRoot] < rank[xRoot])
            parent[yRoot] = xRoot;
        else {
            parent[yRoot] = xRoot;
            rank[xRoot]++;
        }
    }
}

public class KruskalAlgorithm {
    // Main method to execute Kruskal's algorithm
    static void KruskalMST(Edge[] edges, int V) {
        MinHeap minHeap = new MinHeap(edges.length); // Create a MinHeap for edges
        for (Edge edge : edges) {
            minHeap.insert(edge); // Insert all edges into the MinHeap
        }

        UnionFind uf = new UnionFind(V); // Create a UnionFind instance for cycle detection

        int edgeCount = 0; // Number of edges in the MST
        Edge[] result = new Edge[V - 1]; // MST will have V-1 edges

        // Keep extracting the minimum edge and check for cycle
        while (edgeCount < V - 1) {
            Edge edge = minHeap.extractMin(); // Extract the minimum edge
            int x = uf.find(edge.src); // Find root of source vertex
            int y = uf.find(edge.dest); // Find root of destination vertex

            // If including this edge doesn't cause cycle, include it in result
            if (x != y) {
                result[edgeCount++] = edge; // Add edge to MST
                uf.union(x, y); // Union the sets
            }
        }

        // Print the constructed MST
        for (Edge e : result) {
            System.out.println(e.src + " -- " + e.dest + " == " + e.weight);
        }
    }

    // Example main method to test the Kruskal's algorithm
    public static void main(String[] args) {
        // Example usage
        Edge[] edges = {
            new Edge(0, 1, 10),
            new Edge(0, 2, 6),
            new Edge(0, 3, 5),
            new Edge(1, 3, 15),
            new Edge(2, 3, 4)
        };
        int V = 4; // Number of vertices

        KruskalMST(edges, V); // Execute Kruskal's algorithm
    }
}
