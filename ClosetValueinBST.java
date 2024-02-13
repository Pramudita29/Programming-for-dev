/*
    Question:
    You are provided with a balanced binary search tree (BST) and a target value k.
    Your task is to return x number of values that are closest to the given target k.
    Note: You have only one set of unique values x in the binary search tree that are closest to the target.
    Input: K = 3.8, x = 2
    Output: [4, 3] (or any two closest values)
    
    Logic:
    1. Perform an in-order traversal of the BST to generate a sorted list of values.
    2. Use a two-pointer technique on the sorted list to find the window of x closest values to the target k.
    3. This approach leverages the property of BST that in-order traversal yields values in sorted order.
    4. Adjust the window to ensure we find the closest x values to k by comparing the distances of the edges of the window to k.
    5. Finally, extract and return these x closest values.
    */


import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class ClosetValueinBST {
    
    // Method to find x closest values to k in a BST
    public static int[] findClosestValues(TreeNode root, double k, int x) {
        List<Integer> sortedValues = new ArrayList<>();
        inOrderTraversal(root, sortedValues); // Step 1: In-order traversal
        
        int start = 0, end = sortedValues.size() - 1; // Two pointers
        
        // Step 2: Narrow down to the window of size x closest to k
        while (end - start >= x) {
            if (Math.abs(sortedValues.get(start) - k) > Math.abs(sortedValues.get(end) - k)) {
                start++;
            } else {
                end--;
            }
        }
        
        // Extract the x closest values
        int[] result = new int[x];
        for (int i = 0; i < x; i++) {
            result[i] = sortedValues.get(start + i);
        }
        
        return result;
    }
    
    // Helper method for in-order traversal
    private static void inOrderTraversal(TreeNode node, List<Integer> sortedValues) {
        if (node == null) return; // Base case
        inOrderTraversal(node.left, sortedValues); // Visit left subtree
        sortedValues.add(node.val); // Visit node
        inOrderTraversal(node.right, sortedValues); // Visit right subtree
    }
    
    public static void main(String[] args) {
        // Example usage
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        
        double k = 3.8;
        int x = 2;
        int[] closestValues = findClosestValues(root, k, x); // Find x closest values to k
        
        System.out.println("Closest values to " + k + ": " + Arrays.toString(closestValues)); // Output the result
    }
}
