//import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean[][] site;
	private final int row, col;
	private final int top, bottom;
	private final WeightedQuickUnionUF siteUnion;
	private final WeightedQuickUnionUF backUnion;
	
	// create n-by-n grid, with all sites blocked
	public Percolation(int n) {  
		if (n < 1) {
			throw new IllegalArgumentException();
		}
		row = n;
		col = n;
		top = 0;
		bottom = n*n+1; 
		site = new boolean[row+1][col+1];
		siteUnion = new WeightedQuickUnionUF(n*n+2);
		backUnion = new WeightedQuickUnionUF(n*n+1);
	}
	// change index from 2D to 1D
	public int indexTo1D(int i, int j) {
		return (i-1)*row+j;
	}
	// open site (row, col) if it is not open already
	public void open(int i, int j) {
		if (i < 1 || i > row || j < 1 || j > col) throw new IllegalArgumentException();
		int index = indexTo1D(i, j);
		if (!site[i][j]) {
			site[i][j] = true;
			// top
			if (i == 1) {
				siteUnion.union(index, top);
				backUnion.union(index, top);
			}
			// bottom
			if (i == row) {
				siteUnion.union(index, bottom);
			}
			// up
			if (i > 1 && isOpen(i-1, j)) {
				siteUnion.union(index, index-col);
				backUnion.union(index, index-col);
			}
			// down
			if (i < row && isOpen(i+1, j)) {
				siteUnion.union(index, index+col);
				backUnion.union(index, index+col);
			}
			// left
			if (j > 1 && isOpen(i, j-1)) {
				siteUnion.union(index, index-1);
				backUnion.union(index, index-1);
			}
			// right
			if (j < col && isOpen(i, j+1)) {
				siteUnion.union(index, index+1);
				backUnion.union(index, index+1);
			}			
		}		
		
	}
	
	// is site (row, col) open?
	public boolean isOpen(int i, int j) {
		if (i < 1 || i > row || j < 1 || j > col) throw new IllegalArgumentException();
		return site[i][j];
	} 
	
	// is site (row, col) full?
    public boolean isFull(int i, int j) {
		if (i < 1 || i > row || j < 1 || j > col) throw new IllegalArgumentException();
		int index = indexTo1D(i, j);
		return backUnion.connected(index, top);
    } 
    
    // number of open sites
    public int numberOfOpenSites() {
    	int cnt = 0;
    	for (int i=1; i<row+1; i++) {
    		for (int j=1; j<col+1; j++) {
    			if (isOpen(i, j)) {cnt++;}
    		}
    	}
    	return cnt;
    }  
    // does the system percolate?
    public boolean percolates() {
    	return siteUnion.connected(top, bottom);
    }             

    public static void main(String[] args) {    // test client (optional)
//    	int T = 10000;
//    	double prob = 0.0;
//    	double sum = 0.0;
//    	System.out.println(T);
//    	for (int cycle=0; cycle<T; cycle++) {
//    		Percolation per = new Percolation(2);
////    		System.out.println(cycle);
//    		while (per.numberOfOpenSites()<per.row*per.col && !per.percolates()) {
////    			System.out.println("Begin!");
//    			int row, col;
//    			   do {
//    				   row = StdRandom.uniform(1, per.row+1);
//    				   col = StdRandom.uniform(1, per.col+1);
//    			   }while (per.isOpen(row, col));
//				
//				per.open(row, col);
////				System.out.println("cycle-->"+cycle+" row:"+row+" col:"+col+" num:"+per.numberOfOpenSites());
//    		}
//    		
//    		prob = per.numberOfOpenSites()/(per.row*per.col+0.1);
////    		System.out.println(prob+"    "+per.percolates());
//    		sum += prob;
//    	}
//    	System.out.println("Finish!");
//    	System.out.println(sum/T);	
	}
}



