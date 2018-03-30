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
        //Randomized N (from 2 to 10)
        Random rand = new Random();
        N = rand.nextInt(10) + 2;
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

    /**
     * Checks if the graph coloring is completed
     * @return true if the graph coloring is completed, false if it's not
     */
    boolean hasFilledNodes()
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
    Position getNotFilledPosition()
    {
        int i = 0;
        while(i < getVarAmount())
        {
            if(!filled_positions.contains(i)) return new Position(i/N, i%N);
            i++;
        }
        return null;
    }

    /**
     * If _p position exists return a assigned value of its
     * @param _p Position which value we want to get
     * @return Color of _p position if exists, null if it's not
     */
    private Integer getValAtPositionIfExists(Position _p)
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
    boolean isFilledPosition(Position _p)
    {
        return getValAtPositionIfExists(_p) != null;
    }

    /**
     * Gets a domain of variable at position
     * @param _p Position of variable which domain we want
     * @return Domain of variable at _p position
     */
    HashSet<Integer> getDomainAtPosition(Position _p)
    {
        return _p != null ? grid_domains.get((N * _p.getRow()) + _p.getColumn()) : new HashSet<>();
    }

    /**
     * Sets a value at specified position (sets a value of CSP variable at position)
     * @param _v Value which we want to set
     * @param _p Position of CSP variable which value we want to set
     */
    void setValAtPosition(Integer _v, Position _p)
    {
        grid_array[_p.getRow()][_p.getColumn()] = _v;
    }

    /**
     * Unsets a value at specified position (unsets a value of CSP variable at position)
     * @param _p Position of CSP variable which value we want to unset
     */
    void unsetValAtPosition(Position _p)
    {
        grid_array[_p.getRow()][_p.getColumn()] = null;
    }

    /**
     * Gets a value at specified position (gets a value of CSP variable at position)
     * @param _p Position of CSP variable which value we want to get
     * @return value at specified position (value of CSP variable at position)
     */
    private Integer getValAtPosition(Position _p)
    {
        return grid_array[_p.getRow()][_p.getColumn()];
    }

    /**
     * Sets specified Position as filled - this position has a color assigned
     * @param _p Position which we want to set
     */
    void setPositionAsFilled(Position _p)
    {
        filled_positions.add((_p.getRow() * N) + _p.getColumn());
    }

    /**
     * Sets specified Position as not filled - this position hasn't a color assigned
     * @param _p Position which we want to unset
     */
    void unsetPositionAsFilled(Position _p)
    {
        filled_positions.remove(Integer.valueOf((_p.getRow() * N) + _p.getColumn()));
    }

    //-------------
    // CONSTRAINTS |-------------------------------------------------------------
    //-------------

    //CONSTRAINT 1 - UNIQUE ROW
    /**
     * Checks if the row of position is unique (Constraint 1 of Latin square)
     * @param _p Position which row we want to check
     * @return true if it's unique, false if it's not
     */
    boolean hasUniqueRow(Position _p)
    {
        int row = _p.getRow();
        HashSet<Integer> rowVals = new HashSet<>();
        boolean isRowUnique = true;
        int i = 0;
        while(i < N && isRowUnique)
        {
            if(grid_array[row][i] != null)
            {
                if (!rowVals.contains(grid_array[row][i]))
                {
                    rowVals.add(grid_array[row][i]);
                }
                else isRowUnique = false;
            }
            i++;
        }
        return isRowUnique;
    }

    //CONSTRAINT 2 - UNIQUE COLUMN
    /**
     * Checks if the column of position is unique (Constraint 2 of Latin square)
     * @param _p Position which column we want to check
     * @return true if it's unique, false if it's not
     */
    boolean hasUniqueColumn(Position _p)
    {
        int column = _p.getColumn();
        HashSet<Integer> columnVals = new HashSet<>();
        boolean isColumnUnique = true;
        int i = 0;
        while(i < N && isColumnUnique)
        {
            if(grid_array[i][column] != null)
            {
                if (!columnVals.contains(grid_array[i][column]))
                {
                    columnVals.add(grid_array[i][column]);
                }
                else isColumnUnique = false;
            }
            i++;
        }
        return isColumnUnique;
    }

    //-----------
    // PRINTERS |-------------------------------------------------------------
    //-----------

    /**
     * Prints Grid
     */
    void printGrid()
    {
        for (Integer[] aGrid_array : grid_array)
        {
            for (Integer anAGrid_array : aGrid_array)
                System.out.print("[" + anAGrid_array + "]");
            System.out.println();
        }
    }

    /**
     * Prints a domain of each Grid position in array (each CSP variable)
     */
    public void printAllDomains()
    {
        int i = 0;
        for(HashSet<Integer> posDomain : grid_domains)
        {
            System.out.print("Pole " + i/N + "/" + i%N + " domains = ");
            for(Integer valFromDomain : posDomain) System.out.print(valFromDomain + " | ");
            System.out.println();
            i++;
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
