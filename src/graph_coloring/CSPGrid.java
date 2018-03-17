package graph_coloring;

public class CSPGrid
{
    private Grid grid;      //Grid to solve by CSPGrid
    private boolean endB;   //End of backtracking flag

    /**
     * CSPGrid constructor
     * @param _gSize size of Grid to solve by CSPGrid
     */
    public CSPGrid(int _gSize)
    {
        grid = new Grid(_gSize);
        endB = false;
    }

    //--------------------
    // SETTERS & GETTERS |-------------------------------------------------------------
    //--------------------


    /**
     * Grid getter
     * @return Grid solving by CSPGrid
     */
    public Grid getGrid()
    {
        return grid;
    }

    /**
     * Grid setter
     * @param grid New Grid to replace with previous and solve by CSPGrid
     */
    public void setGrid(Grid grid)
    {
        this.grid = grid;
    }

    /**
     * EndB (End of backtracking flag) getter
     * @return End of backtracking flag
     */
    public boolean isEndB()
    {
        return endB;
    }

    /**
     * EndB (End of backtracking flag) setter
     * @param endB New backtracking flag value to replace with this
     */
    public void setEndB(boolean endB)
    {
        this.endB = endB;
    }
}
