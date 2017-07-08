import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF algo;
    private WeightedQuickUnionUF algo2;
    private int topId;
    private int bottomId;
    private int dimension;
    private boolean[][] isOpen;
    private int opens;


    public Percolation(int n) {               // create n-by-n grid, with all sites blocked
        if (n <= 0) {
            throw  new IllegalArgumentException();
        }
        algo = new WeightedQuickUnionUF(n*n+2);
        algo2 = new WeightedQuickUnionUF(n*n+2);
        dimension = n;
        topId = 0;
        bottomId = dimension * dimension +1;
        isOpen = new boolean[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                isOpen[i][j] = false;
            }
        }
    }

    private int getId(int row, int col) {
        return (row-1)*dimension+col;
    }
    private void union(int id1, int row, int col) {
        if (!isOpen(row, col)) {
            return;
        }
        algo.union(id1, getId(row, col));
        algo2.union(id1, getId(row, col));
    }

    public    void open(int row, int col) {    // open site (row, col) if it is not open already
        if (isOpen(row, col)) {
            return;
        }
        opens++;
        isOpen[row-1][col-1] = true;

        int openId = getId(row, col);
        if (row > 1) {
            union(openId, row - 1, col);
        } else {
            algo.union(openId, topId);
            algo2.union(openId, topId);
        }
        if (row < dimension) {
            union(openId, row + 1, col);
        } else {
            algo2.union(openId, bottomId);
        }
        if (col > 1) {
            union(openId, row, col - 1);
        }
        if (col < dimension) {
            union(openId, row, col + 1);
        }
    }

    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        if (row <= 0 || row > dimension || col <= 0 || col > dimension) {
            throw new IndexOutOfBoundsException();
        }
        return isOpen[row - 1] [col -1];
    }

    public boolean isFull(int row, int col) { // is site (row, col) full?
        if (row <= 0 || row > dimension || col <= 0 || col > dimension) {
            throw new IndexOutOfBoundsException();
        }
        return algo.connected(getId(row, col), topId);
    }

    public     int numberOfOpenSites() {       // number of open sites
        return opens;
    }

    public boolean percolates() {              // does the system percolate?
        return algo2.connected(topId, bottomId);
    }

    public static void main(String[] args) {   // test client (optional)
    }
}
