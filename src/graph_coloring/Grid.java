package graph_coloring;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Grid
{
    private int N;  //Grid size
    private Integer[][] grid_array;             //Array of CSP variables (Grid implementation)
    private List<Integer> filled_positions;     //Filled positions
    private HashSet[][] grid_domains;  //domains of each variable

    /**
     * Grid constructor - creates a Grid instance with randomized N size
     */
    public Grid()
    {
        //to_do - randomized
        N = 5;
        grid_array = new Integer[N][N];
        filled_positions = new ArrayList<>();

        //Initializing a HashSet for each CSP variable in array
        grid_domains = new HashSet[N][N];
        for (int i = 0; i < grid_domains.length; i++)
            for (int j = 0; j < grid_domains[i].length; j++)
            {
                grid_domains[i][j] = new HashSet<>();
            }
    }

    /**
     * Grid constructor - creates a Grid instance with N size passed by param
     * @param _N Grid size
     */
    public Grid(int _N)
    {
        this.N = _N;
        grid_array = new Integer[N][N];
        filled_positions = new ArrayList<>();

        //Initializing a HashSet for each CSP variable in array
        grid_domains = new HashSet[N][N];
        for (int i = 0; i < grid_domains.length; i++)
            for (int j = 0; j < grid_domains[i].length; j++)
            {
                grid_domains[i][j] = new HashSet<>();
            }
    }

    //----------
    // METHODS |-----------------------------------------------------------
    //----------

    /**
     * Checks if the graph coloring is completed
     * @return true if the graph coloring is completed, false if it's not
     */
    public boolean hasFilledNodes()
    {
        return filled_positions.size() == this.getVarAmount();
    }

    /**
     * Amount of Graph variables getter
     * @return Amount of Graph variables to fill
     */
    private int getVarAmount()
    {
        return N * N;
    }

    /**
     * Finds a uncolored position
     * @return position of uncolored Graph variable
     */
    public Position getNotFilledPosition()
    {
        int i = 0;
        while(i < getVarAmount())
        {
            if(!filled_positions.contains(i)) return new Position(i/N, i%N);
        }
        return null;
    }

    /**
     * If _p position exists return a color of its
     * @param _p Position which color we want to get
     * @return Color of _p position if exists, null if it's not
     */
    public Integer getColorAtPositionIfExists(Position _p)
    {
        try
        {
            return grid_array[_p.getRow()][_p.getColumn()];
        }
        catch (ArrayIndexOutOfBoundsException | NullPointerException r)
        {
            return null;
        }
    }

    /**
     * Checks if _p Position is filled
     * @param _p Position which we checking
     * @return True if is filled, false if its not
     */
    public boolean isFilledPosition(Position _p)
    {
        return getColorAtPositionIfExists(_p) != null;
    }

    /**
     * Gets a domain of variable at position
     * @param _p Position of variable which domain we want
     * @return Domain of variable at _p position
     */
    public HashSet getDomainAtPosition(Position _p)
    {
        return grid_domains[_p.getRow()][_p.getColumn()];
    }

    /**
     * Sets a color at specified position (sets a value of CSP variable at position)
     * @param _c Color which we want to set
     * @param _p Position of CSP variable which color we want to set
     */
    public void setColorAtPosition(Integer _c, Position _p)
    {
        grid_array[_p.getRow()][_p.getColumn()] = _c;
    }

    /**
     * Unsets a color at specified position (unsets a value of CSP variable at position)
     * @param _p Position of CSP variable which color we want to unset
     */
    public void unsetColorAtPosition(Position _p)
    {
        grid_array[_p.getRow()][_p.getColumn()] = null;
    }

    /**
     * Gets a color at specified position (gets a value of CSP variable at position)
     * @param _p Position of CSP variable which color we want to get
     * @return color at specified position (value of CSP variable at position)
     */
    public Integer getColorAtPosition(Position _p)
    {
        return grid_array[_p.getRow()][_p.getColumn()];
    }

    //CONSTRAINT 1 - Different color with neighbours

    /**
     * Checks if variables at specified positions has different colors (different values)
     * @param _p1 Position of 1st variable
     * @param _p2 Position of 2nd variable
     * @return True if has different colors, false if it's not
     */
    public boolean hasDifferentColorsAtPositions(Position _p1, Position _p2)
    {
        try
        {
            Integer color1 = getColorAtPosition(_p1);
            Integer color2 = getColorAtPosition(_p2);

            return !color1.equals(color2);
        }
        catch (ArrayIndexOutOfBoundsException | NullPointerException e)
        {
            return true;
        }
    }

    public boolean hasDifferentColorsWithNeighbors(Position _p)
    {
        return (hasDifferentColorsAtPositions(_p, new Position(_p.getRow() - 1, _p.getColumn()))          //LEFT
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow(), _p.getColumn() + 1))     //RIGHT
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow() + 1, _p.getColumn()))       //UP
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow(), _p.getColumn() - 1)));   //DOWN
    }

    //-----------
    // PRINTERS |-------------------------------------------------------------
    //-----------

    /**
     * Prints Grid
     */
    public void printGrid()
    {
        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
            {
                System.out.print(grid_array[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Prints a domain of each Grid position in array (each CSP variable)
     */
    public void printAllDomains()
    {
        for (int i = 0; i < grid_domains.length; i++)
            for (int j = 0; j < grid_domains[i].length; j++)
            {
                System.out.print("Pole "+i+"/"+j+" domains = ");

                for (Object c : grid_domains[i][j])
                {
                    System.out.print(c + " | ");
                }

                System.out.println();
            }
    }

    //--------------------
    // SETTERS & GETTERS |-------------------------------------------------------------
    //--------------------

    /**
     * N getter
     * @return value of N variable
     */
    public int getN()
    {
        return N;
    }

    /**
     * N setter
     * @param _N new Grid size
     */
    public void setN(int _N)
    {
        this.N = _N;
    }

    /**
     * Grid_array getter
     * @return Grid_array
     */
    public Integer[][] getGrid_array()
    {
        return grid_array;
    }

    /**
     * Grid_array setter
     * @param _grid_array new grid_array to replace with this
     */
    public void setGrid_array(Integer[][] _grid_array)
    {
        this.grid_array = _grid_array;
    }

    /**
     * Filled_positions getter
     * @return Filled_positions
     */
    public List<Integer> getFilled_positions()
    {
        return filled_positions;
    }

    /**
     * Filled_positions setter
     * @param _filled_positions new filled_position list to replace with this
     */
    public void setFilled_positions(List<Integer> _filled_positions)
    {
        this.filled_positions = _filled_positions;
    }

    /**
     * Grid_domains getter
     * @return Grid_domains array - Array of domains of each CSP variable
     */
    public HashSet[][] getGrid_domains()
    {
        return grid_domains;
    }

    /**
     * Grid_domains setter
     * @param grid_domains new Grid_domains (for each CSP variable) to replace with this
     */
    public void setGrid_domains(HashSet[][] grid_domains)
    {
        this.grid_domains = grid_domains;
    }

    //--------------------------------------------------------------------------------
}

