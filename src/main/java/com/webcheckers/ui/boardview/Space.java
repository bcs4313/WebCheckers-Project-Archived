package com.webcheckers.ui.boardview;

import com.webcheckers.model.GameBoard;

/**
 * This class represents each space on the checker board
 * @author Carlos Hargrove
 */
public class Space {
    private int cellIdx; // cell id num
    private Piece currentPiece; // piece this space contains
    private int row;

    /**
     * Constructor
     *
     * @param cellIDx
     *  the id of the specific cell
     * @param pieceType
     *  the piece that is on the space (can be empty)
     * @throws Exception invalid index exception
     */
    public Space(int cellIDx, GameBoard.cells pieceType, int row) throws Exception {
        this.row=row;
        this.cellIdx=cellIDx;
        if(cellIDx>7){
            throw new Exception("Invalid index size: Must be 7 or lower.");
        }

        if(pieceType != GameBoard.cells.E && pieceType != GameBoard.cells.X)
        {
            currentPiece = new Piece(pieceType);
        }
    }

    // get a cell's identification number
    public int getCellIdx(){
        return cellIdx;
    }

    //todo: needs to check if piece is a dark square and has no other piece on it.
    public boolean isValid(){
        System.out.println(row +"/"+cellIdx);
        if(row % 2==1){ //if black space
            if(cellIdx%2==1){
                return false;
            } else{
                if(currentPiece==null) {
                    return true;
                }
                return false;
            }
        } else {
            if (cellIdx % 2 == 1) {
                if(currentPiece==null) {
                    return true;
                }
                return false;
            } else{
                return false;
            }
        }
    }


    // return the piece this space contains
    public Piece getPiece(){
        return currentPiece;
    }
}
