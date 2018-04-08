package latin_square;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CSPLatinSquare
{
    private Grid grid;      //Grid of Latin square to solve by CSPGrid
    private boolean endB;   //End of backtracking flag
    private boolean endFC;  //End of forward-checking flag

    /**
     * CSPGrid constructor
     * @param _gSize size of Grid to solve by CSPGrid
     */
    public CSPLatinSquare(int _gSize)
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

        int amountOfSteps = 0;
        if(endB) return amountOfSteps;

        //Grid CSP completed!
        if(grid.hasFilledNodes())
        {
            grid.printGrid();
            System.out.println();
            endB = true;
            return amountOfSteps;
        }

        //Get a position and its domain
        Position pos = grid.getNotFilledPosition();                 //Founded position
//        Position pos = grid.getNotFilledPositionMostUnsetNeighbours();
        ConcurrentHashMap<Integer, Integer> posDomain = grid.getDomainAtPosition(pos); //domain of this position

//        amountOfSteps++;
        for (Object o : posDomain.entrySet())
        {
            amountOfSteps++;
            Map.Entry pair = (Map.Entry) o;
            Integer val = (Integer) pair.getKey();
            //Setting a first color from domain
            grid.setValAtPosition(val, pos);
            boolean ok = true;
//
            //Checking constraints
            if (grid.hasUniqueRow(pos) && grid.hasUniqueColumn(pos))
            {
                grid.setPositionAsFilled(pos);
            }
            else
            {
                grid.unsetValAtPosition(pos);
                ok = false;
            }

            if (ok)
            {
                amountOfSteps += Backtracking(level + 1);
                //Cleaning after backing from recursion
                grid.unsetPositionAsFilled(pos);
                grid.unsetValAtPosition(pos);
            }
        }
        return amountOfSteps;
    }

    public int ForwardChecking(int level)
    {

        int amountOfSteps = 0;
        if(endFC) return amountOfSteps;

        //Grid CSP completed!
        if(grid.hasFilledNodes())
        {
            grid.printGrid();
            System.out.println();
            endFC = true;
            return amountOfSteps;
        }

        //Get a position and its domain
        Position pos = grid.getNotFilledPosition();                 //Founded position
//        Position pos = grid.getNotFilledPositionMostUnsetNeighbours();
//        Position pos = grid.getNotFilledPositionSmallestDomain();
        ConcurrentHashMap<Integer, Integer> posDomain = grid.getDomainAtPosition(pos); //domain of this position

//        amountOfSteps++;
        for (Object o : posDomain.entrySet())
        {
            amountOfSteps++;
            Map.Entry pair = (Map.Entry) o;
            Integer val = (Integer) pair.getKey();
            //Setting a first color from domain
            grid.setValAtPosition(val, pos);
            boolean ok = true;
//
            //Checking constraints
            if (grid.hasUniqueRow(pos) && grid.hasUniqueColumn(pos))
            {
                grid.setPositionAsFilled(pos);
                grid.deleteValsFromDomainsForward(pos, val);
            }
            else
            {
                grid.unsetValAtPosition(pos);
                ok = false;
            }

            if (ok)
            {
                amountOfSteps += ForwardChecking(level + 1);
                //Cleaning after backing from recursion
                grid.backDeletedValsFromDomains(pos, val);
                grid.unsetPositionAsFilled(pos);
                grid.unsetValAtPosition(pos);
            }
        }
        return amountOfSteps;
    }

    //--------------------
    // SETTERS & GETTERS |-------------------------------------------------------------
    //--------------------

    /**
     * Grid getter
     * @return Grid solving by CSPGLatinSquare
     */
    public Grid getGrid()
    {
        return grid;
    }

    /**
     * Grid setter
     * @param grid New Grid to replace with previous and solve by CSPLatinSquare
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
