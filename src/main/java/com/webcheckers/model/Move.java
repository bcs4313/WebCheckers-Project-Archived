package com.webcheckers.model;

/**
 * Representation of a movement from one position to another
 * @author Cody Smith (bcs4313@rit.edu)
 */
public class Move {
    // positions that represent a movement
    private Position start;
    private Position end;

    /**
     * Basic constructor for movement representation
     * @param start position of checker target before movement
     * @param end position of checker target after movement
     */
    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    public Position getStart()
    {
        return start;
    }

    public Position getEnd()
    {
        return end;
    }
}
