import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * coursera course algorithm I 1.1 union-find coding assignment.
 * @author surface_handsome
 *
 */
public class Percolation {
  // the number of rows (= the number of columns)
  private final int rowColNum;
  // false is blocked, true is open
  private final boolean[][] grid;
  private final WeightedQuickUnionUF uf;
  private final int virtualTop;
  private final int virtualBottom; 
  private int numberOfOpenSites;
  
  /**
   * init an (n+1)*(n+1) grid.
   * @param n the number of rows(= the number of columns) in the grid.
   *     one row and one column are useless for better index refering.
   *
   */
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("n should > 0");
    }
    // sites without virtual sites
    numberOfOpenSites = 0;
    int totalSites = n * n;
    grid = new boolean[n + 1][n + 1];
    rowColNum = n;
    // 2 more virtual sites at n^2 and n^2+1 in uf's id array.
    uf = new WeightedQuickUnionUF(totalSites + 2);
    virtualTop = rowColNum * rowColNum;
    virtualBottom = virtualTop + 1;
  }
  
  /**
   * open a new site.
   * do fallowing:
   * 1.validate 
   * 2.set grid[row][col] to 1 
   * 3.connect this new opened site to adjacent already opened sites
   * 
   * @param row the row of the site to be opened
   * @param col the col of the site to be opened
   */
  public void open(int row, int col) {
    if (validate(row,col) && grid[row][col] == false) {
      grid[row][col] = true;
      numberOfOpenSites++;
      // connect this new opened site to adjacent already opened sites
      if (isExist(row - 1, col) && grid[row - 1][col] == true) {
        uf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
      }
      if (isExist(row + 1, col) && grid[row + 1][col] == true) {
        uf.union(xyTo1D(row, col), xyTo1D(row + 1, col));
      }
      if (isExist(row, col - 1) && grid[row][col - 1] == true) {
        uf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
      } 
      if (isExist(row - 1, col + 1) && grid[row][col + 1] == true) {
        uf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
      }
      // if this site is in first or last row, we connect it to virtual site.
      if (row == 1) {
        uf.union(xyTo1D(row, col), virtualTop);
      }
      if (row == rowColNum) {
        uf.union(xyTo1D(row, col), virtualBottom);
      }
    } 
  }
  
  /** returns if a site(row,col) is valid. */
  private boolean validate(int row, int col) {
    if (row >= 1 && row <= rowColNum && col >= 1 && col <= rowColNum)  {
      return true;
    } else {
      throw new IllegalArgumentException("illegal row or col input");
    }
  }
  
  // check if a index pair exists, use when inputting invaid row & col is inevitable.
  private boolean isExist(int row, int col) {
    if (row >= 1 && row <= rowColNum && col >= 1 && col <= rowColNum) {
      return true;
    } else {
      return false;
    }
  }
   
  private int xyTo1D(int row, int col) {
    return (row - 1) * rowColNum + (col - 1);

  }
  
  
  /** check if this site is open. */
  public boolean isOpen(int row, int col) {
    if (validate(row,col) && grid[row][col] == true) {
      return true;
    } else {
      return false;
    }
   
  }
  
  /** check if the site is full(not blocked). */
  public boolean isFull(int row, int col) {
    if (isOpen(row, col) && uf.connected(virtualTop, xyTo1D(row, col))) {
      return true;
    } else {
      return false;
    }
  }
  
  public int numberOfOpenSites() {
    return numberOfOpenSites;
  }
  
  /** determine if the system percolates. */
  public boolean percolates() {
    return uf.connected(virtualTop,virtualBottom);
  }
  
  /** test main. */
  public static void main(String[] args) {
    Percolation p = new Percolation(2);
    p.open(1, 2);
    p.open(2, 2);
    System.out.println(p.percolates());
    
  }
}
