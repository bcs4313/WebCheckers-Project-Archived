package com.webcheckers.ui.boardview;

import com.webcheckers.model.GameBoard;

/**
 * This class represents each space on the checker board 
 */
public class Space {
    private int cellIdx;
    private Piece currentPiece;

    /**
     * Constructor
     * 
     * @param cellIDx
     *  the id of the specific cell
     * @param pieceType
     *  the piece that is on the space (can be empty)
     * @throws Exception
     */

    public Space(int cellIDx, GameBoard.cells pieceType) throws Exception {
        this.cellIdx=cellIDx;
        if(cellIDx>7){
            throw new Exception("Invalid index size: Must be 7 or lower.");
        }

        if(pieceType != GameBoard.cells.E && pieceType != GameBoard.cells.X)
        {
            currentPiece = new Piece(pieceType);
        }
    }

    public int getCellIdx(){
        return cellIdx;
    }

    //todo: needs to check if piece is a dark square and has no other piece on it.
    public boolean isValid(){
        return false;
    }


    public Piece getPiece(){
        return currentPiece;
    }
}
