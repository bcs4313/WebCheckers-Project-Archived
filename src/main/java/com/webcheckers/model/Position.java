package com.webcheckers.model;

/**
 * Simple representation of a position on a checker board
 * @author Cody Smith (bcs4313@rit.edu)
 */
public class Position {
    private int row; // 0 to 7
    private int cell; // 0 to 7

    public Position(int row, int cell)
    {
        this.row = row;
        this.cell = cell;
    }

    public int getRow()
    {
        return row;
    }

    public int getCell()
    {
        return cell;
    }
}
