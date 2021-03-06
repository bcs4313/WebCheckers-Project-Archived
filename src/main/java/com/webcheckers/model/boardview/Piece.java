package com.webcheckers.model.boardview;

import com.webcheckers.model.GameBoard;

/**
 * This class represents a checker piece on the game board
 * @author Carlos Hargrove
 */

public class Piece {

    // checker piece types
    public enum Type {
        KING,
        SINGLE
    }

    // checker team colors
    public enum Color{
        RED,
        WHITE
    }

    private Type type; // king or single?
    private Color color; // white of red?

    /**
     * Constructor
     * 
     * @param pieceType enum value that defines what piece
     * this space actually contains
     */
    public Piece(GameBoard.cells pieceType){

        if(pieceType != GameBoard.cells.WK && pieceType != GameBoard.cells.RK)
        {
            this.type = Type.SINGLE;
        }
        else
        {
            this.type = Type.KING;
        }

        if(pieceType == GameBoard.cells.WK || pieceType == GameBoard.cells.W)
        {
            this.color = Color.WHITE;
        }
        else
        {
            this.color = Color.RED;
        }
    }

    public Type getType(){
        return type;
    }

    public Color getColor(){
        return color;
    }
}
