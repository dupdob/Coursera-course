import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] threshold;
    private int trials;
    public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid
        this.trials = trials;
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n");
        }
        threshold = new double[trials];
        for (int i = 0; i < trials; i++) {
            int count = 0;
            Percolation perco = new Percolation(n);
            while (!perco.percolates()) {
                final int row = StdRandom.uniform(n)+1;
                final int col = StdRandom.uniform(n)+1;
                if (perco.isOpen(row, col)) {
                    continue;
                }
                perco.open(row, col);
                count++;
            }
            threshold[i] = count/ (double) (n * n);
        }
    }
    public double mean() {                         // sample mean of percolation threshold
        return StdStats.mean(threshold);
    }
    public double stddev() {                        // sample standard deviation of percolation threshold
        return StdStats.stddev(threshold);
    }

    public double confidenceLo() {                 // low  endpoint of 95% confidence interval
        return mean() - 1.96 * stddev()/Math.sqrt(trials);
    }
    public double confidenceHi() {                // high endpoint of 95% confidence interval
        return mean() + 1.96 * stddev()/Math.sqrt(trials);
    }

    public static void main(String[] args) {        // test client (described below)
    }
}