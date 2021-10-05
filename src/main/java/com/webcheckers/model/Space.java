package com.webcheckers.model;

import java.util.ArrayList;

/**
 * Space on the game board
 * @author Carlos Hargrove
 */
public class Space {

    /**
     * Enums that detail the color of the board spaces.
     */
    private enum Color {
        WHITE,
        BLACK
    }

    private int cellIDx;
    private Piece currentPiece;
    private Color color;

    /**
     * Object for a Space on the board.
     * @param cellIDx - The cell of this row on the board.
     * @param row - the row this space is located.
     * @throws Exception - Thrown if the board is too big. This shouldn't happen.
     */
    public Space(int cellIDx, int row) throws Exception {
        this.cellIDx=cellIDx;
        if(cellIDx>7){
            throw new Exception("Invalid index size: Must be 7 or lower.");
        }

        /*
        * Calculates the checkerboard pattern of a board.
        * Rows 1 3 5 7 are Black/White  ex. XOXOXOX
        * Rows 2 4 6 are White/Black        OXOXOXO
        * where X=black and O=White
        */
        if(row%2==0){
            if(cellIDx%2==0){
                color=Color.WHITE;
            } else{
                color=Color.BLACK;
            }
        } else{
            if(cellIDx%2==0){
                color=Color.BLACK;
            } else{
                color=Color.WHITE;
            }
        }
    }

    /**
     * @return - the cell index of this given space within a row.
     */
    public int getCellIDx(){
        return cellIDx;
    }

    /**
     * Determines whether or not a space is a valid movement option.
     * @return - True if the space is black and has no piece there. Otherwise false.
     */
    public boolean isValid(){
        if(color.equals(Color.BLACK)&&currentPiece.equals(null)){
            return true;
        }
        return false;
    }

    /**
     * @return - the piece at this given space. Null if there is none.
     */
    public Piece getPiece(){
        return currentPiece;
    }
}
