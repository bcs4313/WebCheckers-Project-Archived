package com.webcheckers.model;

/**
 * Representation of a movement from one position to another
 * @author Cody Smith (bcs4313@rit.edu)
 */
public class Move {
    // positions that represent a movement
    private Position previousPos;
    private Position newPos;

    /**
     * Basic constructor for movement representation
     * @param previousPos position of checker target before movement
     * @param newPos position of checker target after movement
     */
    public Move(Position previousPos, Position newPos){
        this.previousPos = previousPos;
        this.newPos = newPos;
    }

    public Position getStart()
    {
        return previousPos;
    }

    public Position getEnd()
    {
        return newPos;
    }
}
