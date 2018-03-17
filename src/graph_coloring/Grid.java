package graph_coloring;

import java.util.ArrayList;
import java.util.List;

public class Grid
{
    private int N;  //Grid size
    private Integer[][] grid_array;
    private List<Integer> filled_positions;

    /**
     * Grid constructor - creates a Grid instance with randomized N size
     */
    public Grid()
    {
        //to_do - randomized
        N = 5;
        grid_array = new Integer[N][N];
        filled_positions = new ArrayList<>();
    }

    /**
     * Grid consturctor - creates a Grid instance with N size passed by param
     * @param _N Grid size
     */
    public Grid(int _N)
    {
        this.N = _N;
        grid_array = new Integer[N][N];
        filled_positions = new ArrayList<>();
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

    //--------------------------------------------------------------------------------
}

