package graph_coloring;

import java.util.HashSet;

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

    //---------------------------------
    // BACKTRACKING & FORWARD-CHECKING |-----------------------------------------------------------
    //---------------------------------

    public int Backtracking(int level)
    {
        int levell = level;
        //int amountOfResults = 0;

        if(endB) return 0;

        //Grid CSP completed!
        if(grid.hasFilledNodes())
        {
            grid.printGrid();
            System.out.println();
            endB = true;
            return 0;
        }

        //Basic information about founded position
        Position pos = grid.getNotFilledPosition();                 //Founded position
        //int row = pos.getRow();                                     //row of this position
        //int column = pos.getColumn();                               //column of this positon
        HashSet posDomain = grid.getDomainAtPosition(pos); //domain of this position

        //Choosing a color from domain at position (Constraints CHECKING)
        for(int i = 0; i < posDomain.size(); i++)
        {
            //Setting a first color from domain
            grid.setColorAtPosition(i, pos);
            boolean ok = true;

            //Checking constraints
        }

        return 0;
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
