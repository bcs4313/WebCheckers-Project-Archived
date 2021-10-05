package com.webcheckers.model;

/**
 * Piece Object. The Checker pieces on your checker board.
 * @author Carlos Hargrove
 */
public class Piece {

    /**
     * Enum that describes whether or not a checker piece is a king.
     */
    private enum Type {
        KING,
        SINGLE
    }

    /**
     * Enum that describes what time a checker piece is on.
     */
    private enum Color{
        RED,
        WHITE
    }

    //see Enums above for purpose.
    private Type type;
    private Color color;

    /**
     * Constructor for checker pieces.
     * @param type - Whether a piece is a king or not.
     * @param color - Which player a piece belongs to.
     */
    public Piece(Type type, Color color ){
        this.type=type;
        this.color=color;
    }

    /**
     * @return - Whether a piece is a king or not.
     */
    public Type getType(){
        return type;
    }

    /**
     * @return - Whether a piece is a color or not.
     */
    public Color getColor(){
        return color;
    }
}
