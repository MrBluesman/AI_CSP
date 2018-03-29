package graph_coloring;

import java.lang.reflect.Array;
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
    private int colors_amount;                  //Amount of color (domain size)

    /**
     * Grid constructor - creates a Grid instance with randomized N size
     */
    Grid()
    {
        //Randomized N (from 1 to 10)
        Random rand = new Random();
        N = rand.nextInt(10) + 1;
        grid_array = new Integer[N][N];
        filled_positions = new ArrayList<>();
        colors_amount = 0;

        //Initializing a HashSet for each CSP variable in array
        grid_domains = new ArrayList<>();
        for(int i = 0; i < N * N; i++) grid_domains.add(new HashSet<>());
    }

    /**
     * Grid constructor - creates a Grid instance with N size passed by param
     * @param _N Grid size
     */
    Grid(int _N)
    {
        this.N = _N;
        grid_array = new Integer[N][N];
        filled_positions = new ArrayList<>();
        colors_amount = 0;

        //Initializing a HashSet for each CSP variable in array
        grid_domains = new ArrayList<>();
        for(int i = 0; i < N * N; i++) grid_domains.add(new HashSet<>());
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
     * If _p position exists return a color of its
     * @param _p Position which color we want to get
     * @return Color of _p position if exists, null if it's not
     */
    private Integer getColorAtPositionIfExists(Position _p)
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
        return getColorAtPositionIfExists(_p) != null;
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
     * Sets a color at specified position (sets a value of CSP variable at position)
     * @param _c Color which we want to set
     * @param _p Position of CSP variable which color we want to set
     */
    void setColorAtPosition(Integer _c, Position _p)
    {
        grid_array[_p.getRow()][_p.getColumn()] = _c;
    }

    /**
     * Unsets a color at specified position (unsets a value of CSP variable at position)
     * @param _p Position of CSP variable which color we want to unset
     */
    void unsetColorAtPosition(Position _p)
    {
        grid_array[_p.getRow()][_p.getColumn()] = null;
    }

    /**
     * Gets a color at specified position (gets a value of CSP variable at position)
     * @param _p Position of CSP variable which color we want to get
     * @return color at specified position (value of CSP variable at position)
     */
    private Integer getColorAtPosition(Position _p)
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

    /**
     * Expands all vars domains by 1 if we don't have enough enough colors
     */
    void expandDomains()
    {
        for(HashSet<Integer> domain : grid_domains)
        {
            domain.add(colors_amount);
        }
        colors_amount++;
    }

    /**
     * Reduces all vars domains by 1
     */
    void reduceDomains()
    {
        colors_amount--;
        for(HashSet<Integer> domain : grid_domains)
        {
            domain.remove(colors_amount);
        }
    }

    //CONSTRAINT 1 - Different color with neighbours
    /**
     * Checks if variables at specified positions has different colors (different values)
     * @param _p1 Position of 1st variable
     * @param _p2 Position of 2nd variable
     * @return True if has different colors, false if it's not
     */
    private boolean hasDifferentColorsAtPositions(Position _p1, Position _p2)
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

    boolean hasDifferentColorsWithNeighbours(Position _p)
    {
        return (hasDifferentColorsAtPositions(_p, new Position(_p.getRow() - 1, _p.getColumn()))          //UP
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow(), _p.getColumn() + 1))     //RIGHT
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow() + 1, _p.getColumn()))       //DOWN
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow(), _p.getColumn() - 1)));   //LEFT
    }

    //CONSTRAINT 2 - Values of colors with neighbours are different by at least 2
    /**
     * Checks if variables at specified positions has different values of colors (at least by _diff)
     * @param _p1 Position of 1st variable
     * @param _p2 Position of 2nd variable
     * @param _diff Minimal difference between values of colors
     * @return True if has different values of colors at least by _diff, false if it's not
     */
    private boolean hasDifferentColorsAtPositions(Position _p1, Position _p2, int _diff)
    {
        try
        {
            Integer color1 = getColorAtPosition(_p1);
            Integer color2 = getColorAtPosition(_p2);
            return Math.abs(color1 - color2) >= _diff;
        }
        catch (ArrayIndexOutOfBoundsException | NullPointerException e)
        {
            return true;
        }
    }

    boolean hasDifferentColorsWithNeighbours(Position _p, int _diff)
    {
        return (hasDifferentColorsAtPositions(_p, new Position(_p.getRow() - 1, _p.getColumn()), _diff)          //UP
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow(), _p.getColumn() + 1), _diff)     //RIGHT
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow() + 1, _p.getColumn()), _diff)       //DOWN
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow(), _p.getColumn() - 1), _diff));   //LEFT
    }

    //CONSTRAINT 3 - Different Colors with positions distant by 2
    boolean hasDifferentColorsWithPositionsDistantBy2(Position _p)
    {
        return (hasDifferentColorsAtPositions(_p, new Position(_p.getRow() - 2, _p.getColumn()))                    //N
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow() - 1, _p.getColumn() + 1))     //N-E
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow(), _p.getColumn() + 2))               //E
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow() + 1, _p.getColumn() + 1))     //S-E
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow() + 2, _p.getColumn()))                 //S
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow() + 1, _p.getColumn() - 1))     //S-W
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow(), _p.getColumn() - 2))               //W
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow() - 1, _p.getColumn() - 1))     //N-W
        );
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
                System.out.print("\u001B[3" + anAGrid_array + "m" + "[" + anAGrid_array + "]");
            System.out.println();
        }
    }

    /**
     * Prints a domain of each Grid position in array (each CSP variable)
     */
    void printAllDomains()
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

    /**
     * Colors_amount getter
     * @return value of colors_amount (domain size)
     */
    public int getColors_amount()
    {
        return colors_amount;
    }

    /**
     * Colors_amount setter
     * @param _colors_amount new colors_amount to replace with this
     */
    public void setColors_amount(int _colors_amount)
    {
        this.colors_amount = _colors_amount;
    }
    //--------------------------------------------------------------------------------
}

