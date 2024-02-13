/*
    Question:
    You are developing a student score tracking system that keeps track of scores from different assignments. 
    The ScoreTracker class will be used to calculate the median score from the stream of assignment scores. 
    The class should have the following methods:
    - ScoreTracker() initializes a new ScoreTracker object.
    - void addScore(double score) adds a new assignment score to the data stream.
    - double getMedianScore() returns the median of all the assignment scores in the data stream. If the number 
      of scores is even, the median should be the average of the two middle scores.

    Algorithm for getMedianScore():
    1. Use two priority queues (heaps): a max heap for the lower half of the numbers and a min heap for the upper half.
    2. When adding a score, ensure that the two halves maintain a size difference of no more than 1 to easily calculate the median.
    3. For the median:
       - If the total size is odd, the median is the top of the heap that has one extra element.
       - If the total size is even, the median is the average of the tops of both heaps.
    */

import java.util.PriorityQueue;
import java.util.Collections;

public class ScoreTracker {
       
    private PriorityQueue<Double> lowerHalf; // Max heap
    private PriorityQueue<Double> upperHalf; // Min heap

    // Initializes a new ScoreTracker object.
    public ScoreTracker() {
        lowerHalf = new PriorityQueue<>(Collections.reverseOrder()); // Max heap for lower half
        upperHalf = new PriorityQueue<>(); // Min heap for upper half
    }

    // Adds a new assignment score to the data stream.
    public void addScore(double score) {
        if (lowerHalf.isEmpty() || score <= lowerHalf.peek()) {
            lowerHalf.add(score);
        } else {
            upperHalf.add(score);
        }

        // Rebalance heaps if necessary to ensure their size differs by no more than 1
        if (lowerHalf.size() > upperHalf.size() + 1) {
            upperHalf.add(lowerHalf.poll());
        } else if (upperHalf.size() > lowerHalf.size()) {
            lowerHalf.add(upperHalf.poll());
        }
    }

    // Returns the median of all the assignment scores in the data stream.
    public double getMedianScore() {
        if (lowerHalf.size() == upperHalf.size()) {
            return (lowerHalf.peek() + upperHalf.peek()) / 2.0; // Average of two middle scores if even total
        } else {
            return lowerHalf.peek(); // Top of the larger heap if odd total
        }
    }

    public static void main(String[] args) {
        ScoreTracker scoreTracker = new ScoreTracker();
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        double median1 = scoreTracker.getMedianScore(); // Output: 88.9
        System.out.println("Median score after 4 scores: " + median1);

        scoreTracker.addScore(81.2);
        scoreTracker.addScore(88.7);
        double median2 = scoreTracker.getMedianScore(); // Output: 86.95
        System.out.println("Median score after 6 scores: " + median2);
    }
}
