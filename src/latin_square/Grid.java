package latin_square;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Grid
{
    private int N;                              //Grid size
    private Integer[][] grid_array;             //Array of CSP variables (Grid implementation)
    private List<Integer> filled_positions;     //Filled positions
    private List<ConcurrentHashMap<Integer, Integer>> grid_domains;

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
            ConcurrentHashMap<Integer, Integer> domain = new ConcurrentHashMap<>();
            for(int val = 0; val < N; val++) domain.put(val, val);
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
            ConcurrentHashMap<Integer, Integer> domain = new ConcurrentHashMap<>();
            for(int val = 0; val < N; val++) domain.put(val, val);
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
    ConcurrentHashMap<Integer, Integer> getDomainAtPosition(Position _p)
    {
        return _p != null ? grid_domains.get((N * _p.getRow()) + _p.getColumn()) : new ConcurrentHashMap<>();
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

    //--------------------------------------
    // FORWARD-CHECKING VAR DOMAINS CONTROL |-------------------------------------------------------------
    //--------------------------------------

    /**
     * Deletes a value from variable's domain at specified position
     * @param _p Position of variable
     * @param _val value (variable from domain) to delete
     */
    private void deleteValFromDomainAtPosition(Position _p, Integer _val)
    {
        try
        {
            grid_domains.get(_p.getRow() * N + _p.getColumn()).remove(_val);
        }
        catch (IndexOutOfBoundsException e)
        {
            //
        }
    }

    /**
     * Backs a deleted value from domain at specified position
     * @param _p Position of variable
     * @param _val value which will be backed (added again)
     */
    private void backDeletedValFromDomainAtPosition(Position _p, Integer _val)
    {
        try
        {
            grid_domains.get(_p.getRow() * N + _p.getColumn()).put(_val, _val);
        }
        catch (IndexOutOfBoundsException e)
        {
            //
        }
    }

    /**
     * Deletes associated value with variable at position from grid_domains forward (in row and column)
     * @param _p Position of variable
     * @param _val value to delete from domains
     */
    void deleteValsFromDomainsForward(Position _p, Integer _val)
    {
        //From row
        int row = _p.getRow();
        for(int c = 0; c < _p.getColumn(); c++)
        {
            deleteValFromDomainAtPosition(new Position(row, c), _val);
        }
        for(int c = _p.getColumn() + 1; c< N; c++)
        {
            deleteValFromDomainAtPosition(new Position(row, c), _val);
        }

        //From column
        int column = _p.getColumn();
        for(int r = 0; r < _p.getRow(); r++)
        {
            deleteValFromDomainAtPosition(new Position(r, column), _val);
        }
        for(int r = _p.getRow() + 1; r < N; r++)
        {
            deleteValFromDomainAtPosition(new Position(r, column), _val);
        }
    }

    /**
     * Backs a deleted values from domains related with _p variable position
     * @param _p Position of variable
     * @param _val value to back
     */
    void backDeletedValsFromDomains(Position _p, Integer _val)
    {
        //From row
        int row = _p.getRow();
        for(int c = 0; c < _p.getColumn(); c++)
        {
            backDeletedValFromDomainAtPosition(new Position(row, c), _val);
        }
        for(int c = _p.getColumn() + 1; c< N; c++)
        {
            backDeletedValFromDomainAtPosition(new Position(row, c), _val);
        }

        //From column
        int column = _p.getColumn();
        for(int r = 0; r < _p.getRow(); r++)
        {
            backDeletedValFromDomainAtPosition(new Position(r, column), _val);
        }
        for(int r = _p.getRow() + 1; r < N; r++)
        {
            backDeletedValFromDomainAtPosition(new Position(r, column), _val);
        }
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
        for(ConcurrentHashMap<Integer, Integer> posDomain : grid_domains)
        {
            System.out.print("Pole " + i/N + "/" + i%N + " domains = ");
            Iterator it = posDomain.entrySet().iterator();
            while(it.hasNext())
            {
                Map.Entry pair = (Map.Entry) it.next();
                Integer valFromDomain = (Integer) pair.getKey();
                System.out.print(valFromDomain + " | ");
                it.remove();
            }
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
    public List<ConcurrentHashMap<Integer, Integer>> getGrid_domains()
    {
        return grid_domains;
    }

    /**
     * Grid_domains setter
     * @param _grid_domains new Grid_domains (for each CSP variable) to replace with this
     */
    public void setGrid_domains(List<ConcurrentHashMap<Integer, Integer>> _grid_domains)
    {
        this.grid_domains = _grid_domains;
    }
}
