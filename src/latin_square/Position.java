package latin_square;

public class Position
{
    private int row;
    private int column;

    /**
     * Position constructor
     * @param _row number of row in Grid
     * @param _column number of column in Grid
     */
    Position(int _row, int _column)
    {
        this.row = _row;
        this.column = _column;
    }

    /**
     * Row getter
     * @return number of row in Grid
     */
    int getRow()
    {
        return row;
    }

    /**
     * Row setter
     * @param row new number of row to set
     */
    public void setRow(int row)
    {
        this.row = row;
    }

    /**
     * Column getter
     * @return number of column in Grid
     */
    int getColumn()
    {
        return column;
    }

    /**
     * Column setter
     * @param column new number of column to set
     */
    public void setColumn(int column)
    {
        this.column = column;
    }

    /**
     * toString for a Position
     * @return String print of position with its row and column
     */
    @Override
    public String toString()
    {
        return "row: " + row + " | column: " + column;
    }
}

