package graph_coloring;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Grid
{
    private int N;                              //Grid size
    private Integer[][] grid_array;             //Array of CSP variables (Grid implementation)
    private List<Integer> filled_positions;     //Filled positions
    private List<ConcurrentHashMap<Integer, Integer>> grid_domains; //domains for each variable (position) (List of positions implement.)
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
        for(int i = 0; i < N * N; i++) grid_domains.add(new ConcurrentHashMap<>());
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
        for(int i = 0; i < N * N; i++) grid_domains.add(new ConcurrentHashMap<>());
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
     * Counts a uncolored neighbours at specified Position
     * @param _p Position
     * @return number of uncolored neighbours at specified Position
     */
    private int countUncoloredNeighbours(Position _p)
    {
        int result = 0;
        if(getColorAtPositionIfExists(new Position(_p.getRow() + 1, _p.getColumn())) == null) result++;
        if(getColorAtPositionIfExists(new Position(_p.getRow() - 1, _p.getColumn())) == null) result++;
        if(getColorAtPositionIfExists(new Position(_p.getRow(), _p.getColumn() + 1)) == null) result++;
        if(getColorAtPositionIfExists(new Position(_p.getRow(), _p.getColumn() - 1)) == null) result++;
        return result;
    }

    /**
     * Finds a uncolored position with most uncolored neighbours
     * @return Position of uncolored Graph variable with the most uncolored neighbours
     */
    Position getNotFilledPositionMostUncoloredNeighbours()
    {
        int i = 0;
        Position best = null;
        int uncoloredNeighbours = -1;

        while(i < getVarAmount() && uncoloredNeighbours < 4)
        {
            if(!filled_positions.contains(i))
            {
                int positionUncoloredNeihbours = countUncoloredNeighbours(new Position(i/N, i%N));
                if(positionUncoloredNeihbours > uncoloredNeighbours)
                {
                    best = new Position(i/N, i%N);
                    uncoloredNeighbours = positionUncoloredNeihbours;
                }
            }
            i++;
        }
        return best;
    }

    /**
     * Finds a uncolored position with smallest domains
     * @return Position of uncolored Graph variable with the smallest domain
     */
    Position getNotFilledPositionSmallestDomain()
    {
        int i = 0;
        Position best = null;
        int domainSize = colors_amount + 1; //to reduce finding if domainSize will be 0

        while(i < getVarAmount() && domainSize > 0)
        {
            if(!filled_positions.contains(i))
            {
                int positionDomainSize = getDomainAtPosition(new Position(i/N, i%N)).size();
                if(positionDomainSize < domainSize)
                {
                    best = new Position(i/N, i%N);
                    domainSize = positionDomainSize;
                }
            }
            i++;
        }
        return best;
    }

    /**
     * If _p position exists return a color of its
     * @param _p Position which color we want to get
     * @return Color of _p position if exists, null if it's not
     */
    Integer getColorAtPositionIfExists(Position _p)
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
    ConcurrentHashMap<Integer, Integer> getDomainAtPosition(Position _p)
    {
        return _p != null ? grid_domains.get((N * _p.getRow()) + _p.getColumn()) : new ConcurrentHashMap<>();
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
        for(ConcurrentHashMap<Integer, Integer> domain : grid_domains)
        {
            domain.put(colors_amount, colors_amount);
        }
        colors_amount++;

    }

    /**
     * Reduces all vars domains by 1
     */
    void reduceDomains()
    {
        colors_amount--;
        for(ConcurrentHashMap<Integer, Integer> domain : grid_domains)
        {
            domain.remove(colors_amount);
        }
    }

    //-------------
    // CONSTRAINTS |-------------------------------------------------------------
    //-------------

    //CONSTRAINT 1 - Different color with neighbours
    /**
     * Checks if variables at specified positions have different colors (different values)
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

    /**
     * Checks if the variable at specified position has different colors (values) with its neighbours
     * @param _p Position of variable
     * @return True if have different colors, false if it's not
     */
    boolean hasDifferentColorsWithNeighbours(Position _p)
    {
        return (hasDifferentColorsAtPositions(_p, new Position(_p.getRow() - 1, _p.getColumn()))          //UP
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow(), _p.getColumn() + 1))     //RIGHT
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow() + 1, _p.getColumn()))       //DOWN
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow(), _p.getColumn() - 1)));   //LEFT
    }

    //CONSTRAINT 2 - Values of colors with neighbours are different by at least 2
    /**
     * Checks if variables at specified positions have different values of colors (at least by _diff)
     * @param _p1 Position of 1st variable
     * @param _p2 Position of 2nd variable
     * @param _diff Minimal difference between values of colors
     * @return True if have different values of colors at least by _diff, false if it's not
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

    /**
     * Checks if variable at specified position has different values of colors (at least by _diff) with its neighbours
     * @param _p Position of variable
     * @param _diff Minimal difference between values of colors
     * @return True if has different values of colors at least by _diff with neighbours, false if it's not
     */
    boolean hasDifferentColorsWithNeighbours(Position _p, int _diff)
    {
        return (hasDifferentColorsAtPositions(_p, new Position(_p.getRow() - 1, _p.getColumn()), _diff)          //UP
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow(), _p.getColumn() + 1), _diff)     //RIGHT
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow() + 1, _p.getColumn()), _diff)       //DOWN
                && hasDifferentColorsAtPositions(_p, new Position(_p.getRow(), _p.getColumn() - 1), _diff));   //LEFT
    }

    //CONSTRAINT 3 - Different Colors with positions distant by 2

    /**
     * Checks if variable at specified position has a different colors (values) with positions distant by 2
     * @param _p Position of variable
     * @return True if has different values of colors with position distant by 2, false if it's not
     */
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

    //--------------------------------------
    // FORWARD-CHECKING VAR DOMAINS CONTROL |-------------------------------------------------------------
    //--------------------------------------

    /**
     * Deletes a color from variable's domain at specified position
     * @param _p Position of variable
     * @param _color color (variable from domain) to delete
     */
    private Integer deleteColorFromDomainAtPosition(Position _p, Integer _color)
    {
        try
        {
            return grid_domains.get(_p.getRow() * N + _p.getColumn()).remove(_color);
        }
        catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }

    /**
     * Backs a deleted _color from domain at specified position
     * @param _p Position of variable
     * @param _color color which will be backed (added again)
     */
    private void backDeletedColorFromDomainAtPosition(Position _p, Integer _color)
    {
        try
        {
            grid_domains.get(_p.getRow() * N + _p.getColumn()).put(_color, _color);
        }
        catch (IndexOutOfBoundsException e)
        {
            //
        }
    }

    /**
     * Deletes associated _color with variable at position from grid_domains forward
     * @param _p Position of variable
     * @param _color color (value to delete)
     * @return backup list for repair domain after back a colors to in
     */
    List<String> deleteDomainsForward(Position _p, Integer _color)
    {
        //Delete from distant by 1 and color > 1 and < 1
        List<String> backupList = new ArrayList<>();
        //UP color + 1
        if(deleteColorFromDomainAtPosition(new Position(_p.getRow() - 1, _p.getColumn()), _color + 1) != null)
            backupList.add((_p.getRow() - 1) + "," + _p.getColumn() + "," + (_color + 1));
        //UP color - 1
        if(deleteColorFromDomainAtPosition(new Position(_p.getRow() - 1, _p.getColumn()), _color - 1) != null)
            backupList.add((_p.getRow() - 1) + "," + _p.getColumn() + "," + (_color - 1));
        //RIGHT color + 1
        if(deleteColorFromDomainAtPosition(new Position(_p.getRow(), _p.getColumn() + 1), _color + 1) != null)
            backupList.add(_p.getRow() + "," + (_p.getColumn() + 1) + "," + (_color + 1));
        //RIGHT color - 1
        if(deleteColorFromDomainAtPosition(new Position(_p.getRow(), _p.getColumn() + 1), _color - 1) != null)
            backupList.add(_p.getRow() + "," + (_p.getColumn() + 1) + "," + (_color - 1));
        //DOWN color + 1
        if(deleteColorFromDomainAtPosition(new Position(_p.getRow() + 1, _p.getColumn()), _color + 1) != null)
            backupList.add((_p.getRow() + 1) + "," + _p.getColumn() + "," + (_color + 1));
        //DOWN color - 1
        if(deleteColorFromDomainAtPosition(new Position(_p.getRow() + 1, _p.getColumn()), _color - 1) != null)
            backupList.add((_p.getRow() + 1) + "," + _p.getColumn() + "," + (_color - 1));
        //LEFT color + 1
        if(deleteColorFromDomainAtPosition(new Position(_p.getRow(), _p.getColumn() - 1), _color + 1) != null)
            backupList.add(_p.getRow() + "," + (_p.getColumn() - 1) + "," + (_color + 1));
        //LEFT color - 1
        if(deleteColorFromDomainAtPosition(new Position(_p.getRow(), _p.getColumn() - 1), _color - 1) != null)
            backupList.add(_p.getRow() + "," + (_p.getColumn() - 1) + "," + (_color - 1));


        //Delete from up, right, down and left position
        deleteColorFromDomainAtPosition(new Position(_p.getRow() - 1, _p.getColumn()), _color);                      //UP
        deleteColorFromDomainAtPosition(new Position(_p.getRow(), _p.getColumn() + 1), _color);                      //RIGHT
        deleteColorFromDomainAtPosition(new Position(_p.getRow() + 1, _p.getColumn()), _color);                      //DOWN
        deleteColorFromDomainAtPosition(new Position(_p.getRow(), _p.getColumn() - 1), _color);                      //LEFT

        //Delete from distant by 2
        deleteColorFromDomainAtPosition(new Position(_p.getRow() - 2, _p.getColumn()), _color);                     //N
        deleteColorFromDomainAtPosition(new Position(_p.getRow() - 1, _p.getColumn() + 1), _color);         //N-E
        deleteColorFromDomainAtPosition(new Position(_p.getRow(), _p.getColumn() + 2), _color);                   //E
        deleteColorFromDomainAtPosition(new Position(_p.getRow() + 1, _p.getColumn() + 1), _color);          //S-E
        deleteColorFromDomainAtPosition(new Position(_p.getRow() + 2, _p.getColumn()), _color);                     //S
        deleteColorFromDomainAtPosition(new Position(_p.getRow() + 1, _p.getColumn() - 1), _color);         //S-W
        deleteColorFromDomainAtPosition(new Position(_p.getRow(), _p.getColumn() - 2), _color);                  //W
        deleteColorFromDomainAtPosition(new Position(_p.getRow() - 1, _p.getColumn() - 1), _color);         //N-W

        return backupList;
    }

    /**
     * Backs a deleted _color from domains related with _p variable position
     * @param _p Position of variable
     * @param _color color (value) to back
     * @param _backupList List with non regular positions and colors to backup after delete
     */
    void backDeletedColorFromDomains(Position _p, Integer _color, List<String> _backupList)
    {
        //Back to distant by 1 and color > 1 and < 1
        //backupList contains a information in format row,column,color, we need to split them
        //through all position in list
        for (String backElem : _backupList)
        {
            String[] partsOfBackupList = backElem.split(",");
            backDeletedColorFromDomainAtPosition(new Position(Integer.valueOf(partsOfBackupList[0]),
                    Integer.valueOf(partsOfBackupList[1])), Integer.valueOf(partsOfBackupList[2]));
        }

        //Back to up, right, down and left position
        backDeletedColorFromDomainAtPosition(new Position(_p.getRow() - 1, _p.getColumn()), _color);                      //UP
        backDeletedColorFromDomainAtPosition(new Position(_p.getRow(), _p.getColumn() + 1), _color);                      //RIGHT
        backDeletedColorFromDomainAtPosition(new Position(_p.getRow() + 1, _p.getColumn()), _color);                      //DOWN
        backDeletedColorFromDomainAtPosition(new Position(_p.getRow(), _p.getColumn() - 1), _color);                      //LEFT

        //Back to distant by 2
        backDeletedColorFromDomainAtPosition(new Position(_p.getRow() - 2, _p.getColumn()), _color);                     //N
        backDeletedColorFromDomainAtPosition(new Position(_p.getRow() - 1, _p.getColumn() + 1), _color);         //N-E
        backDeletedColorFromDomainAtPosition(new Position(_p.getRow(), _p.getColumn() + 2), _color);                   //E
        backDeletedColorFromDomainAtPosition(new Position(_p.getRow() + 1, _p.getColumn() + 1), _color);          //S-E
        backDeletedColorFromDomainAtPosition(new Position(_p.getRow() + 2, _p.getColumn()), _color);                     //S
        backDeletedColorFromDomainAtPosition(new Position(_p.getRow() + 1, _p.getColumn() - 1), _color);         //S-W
        backDeletedColorFromDomainAtPosition(new Position(_p.getRow(), _p.getColumn() - 2), _color);                  //W
        backDeletedColorFromDomainAtPosition(new Position(_p.getRow() - 1, _p.getColumn() - 1), _color);         //N-W
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
    public void printAllDomains()
    {
        int i = 0;
        for(ConcurrentHashMap<Integer, Integer> posDomain : grid_domains)
        {
            System.out.print("Pole " + i/N + "/" + i%N + " domains = ");
            for (Object o : posDomain.entrySet())
            {
                Map.Entry pair = (Map.Entry) o;
                Integer colorFromDomain = (Integer) pair.getKey();
                System.out.print(colorFromDomain + " | ");
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

