import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double ans[];
	private int T;
	
	// perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {	  
    	ans = new double[trials];
    	T = trials;
	    if (n<1 || T<1) throw new IllegalArgumentException();   
	    for (int cycle=0; cycle<T; cycle++) {
		    Percolation per = new Percolation(n);
   		    while (per.numberOfOpenSites()<n*n && !per.percolates()) {
   			    int row, col;
   			    do {
   				    row = StdRandom.uniform(1, n+1);
   				    col = StdRandom.uniform(1, n+1);
   			    }while (per.isOpen(row, col));
			    per.open(row, col);
   		    }	
   		    ans[cycle] = per.numberOfOpenSites() / (n*n+0.0);
   	    }	   
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
    	double me = mean();
    	double std = stddev();
    	return (me - 1.96*std / Math.sqrt(T));
	   
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	double me = mean();
    	double std = stddev();
    	return (me + 1.96*std / Math.sqrt(T));
	    
    }
    
    // test client (described below)
    public static void main(String[] args) {
    	int N = StdIn.readInt();
    	int trials = StdIn.readInt();
    	PercolationStats perStat = new PercolationStats(N, trials);
    	StdOut.printf("mean                    = %f\n", perStat.mean());
    	StdOut.printf("stddev                  = %f\n", perStat.stddev());	
    	StdOut.printf("95%% confidence interval = [%f, %f]\n", perStat.confidenceLo(), perStat.confidenceHi());	   
    }
}
