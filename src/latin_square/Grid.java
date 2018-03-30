package latin_square;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Grid
{
    private int N;                              //Grid size
    private Integer[][] grid_array;             //Array of CSP variables (Grid implementation)
    private List<Integer> filled_positions;     //Filled positions
    private List<HashSet<Integer>> grid_domains; //domains for each variable (position) (List of positions implement.)

    /**
     * Grid constructor - creates a Grid instance with randomized N size
     * For Latin square CSP problem
     */
    Grid()
    {
        //Randomized N (from 1 to 10)
        Random rand = new Random();
        N = rand.nextInt(10) + 1;
        grid_array = new Integer[N][N];
        filled_positions = new ArrayList<>();

        //Initializing a HashSet for each CSP variable in array
        //Each CSP variable (in latin square) have a N-1 numbers to fill Grid
        grid_domains = new ArrayList<>();
        for(int i = 0; i < N * N; i++)
        {
            HashSet<Integer> domain = new HashSet<>();
            for(int val = 0; val < N; val++) domain.add(val);
            grid_domains.add(domain);
        }
    }

    /**
     * Grid constructor - creates a Grid instance with N size passed by param
     * For Latin square CSP problem
     * @param _N Grid size
     */
    Grid(int _N)
    {
        this.N = _N;
        grid_array = new Integer[N][N];
        filled_positions = new ArrayList<>();

        //Initializing a HashSet for each CSP variable in array
        //Each CSP variable (in latin square) have a N-1 numbers to fill Grid
        grid_domains = new ArrayList<>();
        for(int i = 0; i < N * N; i++)
        {
            HashSet<Integer> domain = new HashSet<>();
            for(int val = 0; val < N; val++) domain.add(val);
            grid_domains.add(domain);
        }
    }

    //----------
    // METHODS |-----------------------------------------------------------
    //----------

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
     * Filled_positions list getter
     * @return Filled_positions
     */
    public List<Integer> getFilled_positions()
    {
        return filled_positions;
    }

    /**
     * Filled_positions list setter
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
    public List<HashSet<Integer>> getGrid_domains()
    {
        return grid_domains;
    }

    /**
     * Grid_domains setter
     * @param _grid_domains new Grid_domains (for each CSP variable) to replace with this
     */
    public void setGrid_domains(List<HashSet<Integer>> _grid_domains)
    {
        this.grid_domains = _grid_domains;
    }
}
