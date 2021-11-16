package com.webcheckers.model;

/**
 * Simple representation of a position on a checker board
 * @author Cody Smith (bcs4313@rit.edu)
 */
public class Position {
    private final int row; // 0 to 7
    private final int cell; // 0 to 7

    /**
     * Position Object Constructor
     * @param row which row are we defining in a checker board?
     * @param cell which cell are we defining in a checker board?
     */
    public Position(int row, int cell)
    {
        this.row = row;
        this.cell = cell;
    }

    // retrieve row pos
    public int getRow()
    {
        return row;
    }

    // retrieve cell pos
    public int getCell()
    {
        return cell;
    }
}
