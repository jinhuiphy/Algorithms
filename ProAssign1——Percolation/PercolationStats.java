//import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private final double ans[];
	private final int T;
	private final double mean;
	private final double stddev;
	private final double confidenceLo;
	private final double confidenceHi;
	
	// perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
    	if (n < 1 || trials < 1) throw new IllegalArgumentException();   
    	ans = new double[trials];
    	T = trials;	    
	    for (int cycle=0; cycle<T; cycle++) {
		    Percolation per = new Percolation(n);
   		    while (!per.percolates()) {
   			    int row, col;
   			    do {
   				    row = StdRandom.uniform(1, n+1);
   				    col = StdRandom.uniform(1, n+1);
   			    }while (per.isOpen(row, col));
			    per.open(row, col);
   		    }	
   		    ans[cycle] = per.numberOfOpenSites() / (n*n+0.0);
   	    }
	    mean = this.mean();
	    stddev = this.stddev();
	    confidenceLo = this.confidenceLo();
	    confidenceHi = this.confidenceHi();
    }
    
    // sample mean of percolation threshold
    public double mean() {
    	return StdStats.mean(ans);	   
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
    	return StdStats.stddev(ans);
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
    	return (mean - 1.96*stddev / Math.sqrt(T));
	   
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	return (mean + 1.96*stddev / Math.sqrt(T));
	    
    }
    
    // test client (described below)
    public static void main(String[] args) {
    	int N = Integer.parseInt(args[0]);
    	int trials = Integer.parseInt(args[1]);
//    	int N = StdIn.readInt();
//    	int trials = StdIn.readInt();
    	PercolationStats perStat = new PercolationStats(N, trials);
    	StdOut.printf("mean                    = %f\n", perStat.mean);
    	StdOut.printf("stddev                  = %f\n", perStat.stddev);	
    	StdOut.printf("95%% confidence interval = [%f, %f]\n", perStat.confidenceLo, perStat.confidenceHi);	   
    }
}
