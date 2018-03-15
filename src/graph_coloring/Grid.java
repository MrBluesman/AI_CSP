package graph_coloring;

public class Grid
{
    private int N;  //Grid size
    private Integer[][] grid_array;

    /**
     * Grid constructor - creates a Grid instance with randomized N size
     */
    public Grid()
    {
        //to_do - randomized
        N = 5;
        grid_array = new Integer[N][N];
    }

    /**
     * Grid consturctor - creates a Grid instance with N size passed by param
     * @param _N Grid size
     */
    public Grid(int _N)
    {
        this.N = _N;
        grid_array = new Integer[N][N];
    }

    //----------
    // METHODS |-----------------------------------------------------------
    //----------


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
     * @param _grid_array new grid_array to replace with previous
     */
    public void setGrid_array(Integer[][] _grid_array)
    {
        this.grid_array = _grid_array;
    }

    //--------------------------------------------------------------------------------
}

