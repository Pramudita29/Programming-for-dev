/*
    Question:
    Implement the Ant Colony Optimization (ACO) algorithm to solve the Traveling Salesman Problem (TSP).
    In TSP, a salesman must visit all cities exactly once, returning to the starting city, with the goal of minimizing the total travel distance.
    ACO simulates the behavior of ants finding paths between their colony and food sources to find the shortest path for TSP.

    Algorithm:
    1. Initialize pheromones on all edges with a small positive value.
    2. Place ants on random cities.
    3. Each ant constructs a tour by repeatedly choosing the next city according to probabilities influenced by pheromone levels and distance.
    4. After all ants complete their tours, update pheromones: increase on used edges, decrease on all due to evaporation.
    5. Repeat the process for a set number of iterations or until convergence.
    */

import java.util.Arrays;
import java.util.Random;

class AntColonyOptimization {

    private double[][] distance; // Distance between cities
    private double[][] pheromones; // Pheromone on path between cities
    private int numberOfCities;
    private int numberOfAnts;
    private double decayFactor = 0.5; // Pheromone decay factor
    private double alpha = 1.0; // Pheromone importance
    private double beta = 2.0; // Distance importance
    private Random random = new Random();

    public AntColonyOptimization(double[][] distance) {
        this.distance = distance;
        this.numberOfCities = distance.length;
        this.numberOfAnts = numberOfCities;
        this.pheromones = new double[numberOfCities][numberOfCities];

        // Initialize pheromones
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                pheromones[i][j] = 0.1; // Small positive value
            }
        }
    }

    public void solve() {
        for (int iteration = 0; iteration < 10; iteration++) { // Number of iterations
            int[][] tours = new int[numberOfAnts][numberOfCities];
            double[] lengths = new double[numberOfAnts];

            // Construct tours for each ant
            for (int ant = 0; ant < numberOfAnts; ant++) {
                tours[ant] = constructTour(ant);
                lengths[ant] = calculateTourLength(tours[ant]);
            }

            // Update pheromones
            updatePheromones(tours, lengths);

            // Optionally, print the best tour length of this iteration
            System.out.println("Iteration " + iteration + ": Best tour length = " + Arrays.stream(lengths).min().getAsDouble());
        }
    }

    private int[] constructTour(int ant) {
        int[] tour = new int[numberOfCities];
        boolean[] visited = new boolean[numberOfCities];
        int currentCity = ant % numberOfCities; // Start city
        tour[0] = currentCity;
        visited[currentCity] = true;

        for (int i = 1; i < numberOfCities; i++) {
            currentCity = selectNextCity(currentCity, visited);
            tour[i] = currentCity;
            visited[currentCity] = true;
        }
        return tour;
    }

    private int selectNextCity(int currentCity, boolean[] visited) {
        double[] probs = calculateProbs(currentCity, visited);
        double randProb = random.nextDouble();
        double total = 0;
        for (int i = 0; i < numberOfCities; i++) {
            total += probs[i];
            if (total >= randProb) {
                return i;
            }
        }
        return -1; // Should not happen
    }

    private double[] calculateProbs(int currentCity, boolean[] visited) {
        double[] probs = new double[numberOfCities];
        double sum = 0;
        for (int i = 0; i < numberOfCities; i++) {
            if (!visited[i]) {
                probs[i] = Math.pow(pheromones[currentCity][i], alpha) * Math.pow(1.0 / distance[currentCity][i], beta);
                sum += probs[i];
            }
        }
        for (int i = 0; i < numberOfCities; i++) {
            if (!visited[i]) {
                probs[i] /= sum;
            }
        }
        return probs;
    }

    private double calculateTourLength(int[] tour) {
        double length = 0;
        for (int i = 0; i < numberOfCities - 1; i++) {
            length += distance[tour[i]][tour[i + 1]];
        }
        length += distance[tour[numberOfCities - 1]][tour[0]]; // Return to start
        return length;
    }

    private void updatePheromones(int[][] tours, double[] lengths) {
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                pheromones[i][j] *= (1 - decayFactor); // Evaporation
            }
        }
        for (int ant = 0; ant < numberOfAnts; ant++) {
            double contribution = 1.0 / lengths[ant];
            for (int i = 0; i < numberOfCities - 1; i++) {
                pheromones[tours[ant][i]][tours[ant][i + 1]] += contribution;
            }
            pheromones[tours[ant][numberOfCities - 1]][tours[ant][0]] += contribution; // Return to start contribution
        }
    }

    public static void main(String[] args) {
        // Example usage with a dummy distance matrix for demonstration purposes
        double[][] distance = {
                {0, 2, 3, 4, 5},
                {2, 0, 4, 5, 6},
                {3, 4, 0, 6, 7},
                {4, 5, 6, 0, 8},
                {5, 6, 7, 8, 0}
        };
        AntColonyOptimization aco = new AntColonyOptimization(distance);
        aco.solve();
    }
}
