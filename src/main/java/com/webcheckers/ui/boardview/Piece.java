package com.webcheckers.ui.boardview;

import com.webcheckers.model.GameBoard;

/**
 * This class represents a checker piece on the game board
 */

public class Piece {

    private enum Type {
        KING,
        SINGLE
    }

    private enum Color{
        RED,
        WHITE
    }

    private Type type;
    private Color color;

    /**
     * Constructor
     * 
     * @param pieceType
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
