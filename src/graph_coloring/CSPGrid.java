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
//        System.out.println(levell);
        if(endB) return 0;
        //Grid CSP completed!
        if(grid.hasFilledNodes())
        {
//            System.out.println("BAKDJKSHFDJKSDF");
            grid.printGrid();
            System.out.println();
            endB = true;
            return 0;
        }

        //Basic information about founded position
        Position pos = grid.getNotFilledPosition();                 //Founded position
//        System.out.println("psiapsia");
//        System.out.println(pos);
        //int row = pos.getRow();                                     //row of this position
        //int column = pos.getColumn();                               //column of this positon
        HashSet posDomain = grid.getDomainAtPosition(pos); //domain of this position
//        System.out.println(posDomain);

        //Choosing a color from domain at position (Constraints CHECKING)
        for(int i = 0; i < posDomain.size(); i++)
        {
            //Setting a first color from domain
            grid.setColorAtPosition(i, pos);
            boolean ok = true;

            //Checking constraints
            if(grid.hasDifferentColorsWithNeighbours(pos, 2)
                    && grid.hasDifferentColorsWithPositionsDistantBy2(pos))
            {
                grid.setPositionAsFilled(pos);
            }
            else
            {
                grid.unsetColorAtPosition(pos);
                ok = false;
            }

            if(ok)
            {
//                grid.printGrid();
                Backtracking(levell + 1);
                //Cleaning after backing from recursion
                grid.unsetPositionAsFilled(pos);
                grid.unsetColorAtPosition(pos);
            }
        }

        //if we are on the first Backtracking levell our colors amount is not enough
        //We need to expand domains and run Backtracking again
        if(levell == 0 && !endB)
        {
            grid.expandDomains();
            Backtracking(0);
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
