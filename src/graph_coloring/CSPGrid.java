package graph_coloring;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CSPGrid
{
    private Grid grid;      //Grid to solve by CSPGrid
    private boolean endB;   //End of backtracking flag
    private boolean endFC;  //End of forward-checking flag

    /**
     * CSPGrid constructor
     * @param _gSize size of Grid to solve by CSPGrid
     */
    public CSPGrid(int _gSize)
    {
        grid = new Grid(_gSize);
        endB = false;
        endFC = false;
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

        //Get a position and its domain
        Position pos = grid.getNotFilledPosition();                 //Founded position
        ConcurrentHashMap<Integer, Integer> posDomain = grid.getDomainAtPosition(pos); //domain of this position

        //Choosing a color from domain at position (Constraints CHECKING)
        for(Object o : posDomain.entrySet())
        {
            Map.Entry pair = (Map.Entry) o;
            Integer color = (Integer) pair.getKey();
            //Setting a first color from domain
            grid.setColorAtPosition(color, pos);
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

        //if we are on the first Backtracking level our colors amount is not enough
        //We need to expand domains and run Backtracking again
        if(levell == 0 && !endB)
        {
            grid.expandDomains();
            Backtracking(0);
        }

        return 0;
    }

    public int ForwardChecking(int level)
    {
        int levell = level;
        //int amountOfResults = 0;
        if(endFC) return 0;
        //Grid CSP completed!
        if(grid.hasFilledNodes())
        {
            grid.printGrid();
            System.out.println();
            endFC = true;
            return 0;
        }

        //Get a position and its domain
//        Position pos = grid.getNotFilledPosition();                 //Founded position
        Position pos = grid.getNotFilledPositionSmallestDomain();
        ConcurrentHashMap<Integer, Integer> posDomain = grid.getDomainAtPosition(pos); //domain of this position

        //Choosing a color from domain at position (Constraints CHECKING)
        for(Object o : posDomain.entrySet())
        {
            Map.Entry pair = (Map.Entry) o;
            Integer color = (Integer) pair.getKey();
            //Setting a first color from domain
            grid.setColorAtPosition(color, pos);
            boolean ok = true;

            List<String> backupColorsList = new ArrayList<>();
            //Checking constraints
            if(grid.hasDifferentColorsWithNeighbours(pos, 2)
                    && grid.hasDifferentColorsWithPositionsDistantBy2(pos))
            {
                grid.setPositionAsFilled(pos);
                backupColorsList = grid.deleteDomainsForward(pos, color);
            }
            else
            {
                grid.unsetColorAtPosition(pos);
                ok = false;
            }

            if(ok)
            {
                ForwardChecking(levell + 1);
                //Cleaning after backing from recursion
                grid.backDeletedColorFromDomains(pos, color, backupColorsList);
                grid.unsetPositionAsFilled(pos);
                grid.unsetColorAtPosition(pos);
            }
        }

        //if we are on the first Backtracking level our colors amount is not enough
        //We need to expand domains and run Backtracking again
        if(levell == 0 && !endFC)
        {
            grid.expandDomains();
            ForwardChecking(0);
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

    /**
     * EndFC (End of forward-checking flag) getter
     * @return End of forward-checking flag
     */
    public boolean isEndFC()
    {
        return endFC;
    }

    /**
     * EndFC (End of forward-checking flag) setter
     * @param endFC New forward-checking flag value to replace with this
     */
    public void setEndFC(boolean endFC)
    {
        this.endFC = endFC;
    }
}
