import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] ans;
	private final int trials;
	private final double mean;
	private final double stddev;
	private final double confidenceLo;
	private final double confidenceHi;
	
	// perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int t) {
    	if (n < 1 || t < 1) throw new IllegalArgumentException();   
    	ans = new double[t];
    	trials = t;	    
	    for (int cycle =0; cycle < trials; cycle++) {
		    Percolation per = new Percolation(n);
		    boolean[] isEmptySiteLine = new boolean[n+1];
	    	int numOfLine = 0;
		    while (true) {
   			    int row, col;
   			    do {
   				    row = StdRandom.uniform(1, n+1);
   				    col = StdRandom.uniform(1, n+1);
   			    } while (per.isOpen(row, col));
			    per.open(row, col);
			    ans[cycle]++;
			    if (!isEmptySiteLine[row]) {
			    	isEmptySiteLine[row] = true;
			    	numOfLine++;
			    }
			    if (numOfLine == n) {
			    	if (per.percolates()) break;
			    }
			    
   		    }	
   		    ans[cycle] = ans[cycle] / (n*n+0.0);
   	    }
	    mean = StdStats.mean(ans);
	    stddev = StdStats.stddev(ans);
	    confidenceLo = this.confidenceLo();
	    confidenceHi = this.confidenceHi();
    }
    
    // sample mean of percolation threshold
    public double mean() {
    	return mean;	   
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
    	return stddev;
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
    	return (mean - 1.96*stddev / Math.sqrt(trials));
	   
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	return (mean + 1.96*stddev / Math.sqrt(trials));
	    
    }
    
    // test client (described below)
    public static void main(String[] args) {
//    	int N = Integer.parseInt(args[0]);
//    	int trials = Integer.parseInt(args[1]);
    	int n = StdIn.readInt();
    	int trials = StdIn.readInt();
    	PercolationStats perStat = new PercolationStats(n, trials);
    	StdOut.printf("mean                    = %f\n", perStat.mean);
    	StdOut.printf("stddev                  = %f\n", perStat.stddev);	
    	StdOut.printf("95%% confidence interval = [%f, %f]\n", perStat.confidenceLo, perStat.confidenceHi);	   
    }
}
