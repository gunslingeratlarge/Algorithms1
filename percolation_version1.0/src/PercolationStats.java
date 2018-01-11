
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private final double[] threshold;
  private double mean;
  private double stddev;
  private final int trials;
  private static final double CONFIDENCE_95 = 1.96;
  
  /**
   * run percolation experiments.
   * @param n n*n grid
   * @param trials experiment times
   */
  public PercolationStats(int n, int trials) {
    if (trials <= 0) {
      throw new IllegalArgumentException();
    }
    // run trials times
    this.trials = trials;
    threshold = new double[trials];
    for (int i = 0; i < trials; i++) {
      int count = 0;
      Percolation perc = new Percolation(n);
      while (!perc.percolates() && count <= n * n - 1) {
        int row = StdRandom.uniform(1, n + 1);
        int col = StdRandom.uniform(1, n + 1);
        if (!perc.isOpen(row,col)) {
          perc.open(row,col);
          count++;
        }
      }
      threshold[i] = (1.0 * count) / (n * n);
    }
  } 
  
  public double mean() {
    mean = StdStats.mean(threshold);
    return mean;
  }
  
  public double confidenceLo() {
    double confidenceLo = mean() - CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    return confidenceLo;
  }
  
  public double confidenceHi() {
    double confidenceHi = mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    return confidenceHi;
  }
  
  /**
   * Returns the sample standard deviation of percolation threshold.
   * @return the sample standard deviation of percolation threshold, 
   *     returns Double.NaN if length == 1
   */
  public double stddev() {
    if (threshold.length == 1) {
      stddev = Double.NaN;
    } else {
      stddev = StdStats.stddev(threshold);
    }
    return stddev;
  }
  
  /** test main. */
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);
    PercolationStats percStats = new PercolationStats(n,trials);
    System.out.println("mean\t\t\t= " + percStats.mean());
    System.out.println("stddev\t\t\t= " + percStats.stddev());
    System.out.println("95% confidence interval\t= " + "[" + percStats.confidenceLo() + ", "
        + percStats.confidenceHi() + "]");

  }
}
