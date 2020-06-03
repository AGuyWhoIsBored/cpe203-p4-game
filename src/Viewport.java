/* Quarter-Long Virtual World Project - Viewport Class
 * Name: Duncan Applegarth
 * Instructor: Kirsten Mork
 * Section: CPE203-03
 */

public final class Viewport
{
    // instance variables
    private int row;
    private int col;
    private int numRows;
    private int numCols;

    // constructor
    public Viewport(int numRows, int numCols) 
    {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    // accessor methods
    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getNumRows() { return numRows; }
    public int getNumCols() { return numCols; }

    // public functional methods
    public Point viewportToWorld(int col, int row) { return new Point(col + this.col, row + this.row); }
    public Point worldToViewport(int col, int row) { return new Point(col - this.col, row - this.row); }

    public void shift(int col, int row) 
    {
        this.col = col;
        this.row = row;
    }

    public boolean contains(Point p) 
    {
        return p.getY() >= row 
            && p.getY() < row + numRows
            && p.getX() >= col 
            && p.getX() < col + numCols;
    }
}
