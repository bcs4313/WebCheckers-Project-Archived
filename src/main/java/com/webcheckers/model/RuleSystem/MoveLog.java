package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.Position;

import java.util.Stack;

/**
 * This class stores the previous movements of a player,
 * making it easy to undo a move.
 */
public class MoveLog {
    RuleMaster master;
    Stack<Position> positionStack; // stack of player movements

    /**
     * Constructor for a movement Log
     * @param master master rule handler that uses this stack
     */
    public MoveLog(RuleMaster master)
    {
        this.master = master;
        positionStack = new Stack<>();
    }

    /**
     * Log the previous movement state to potentially undo
     * later.
     * @param oldPos old position to store
     */
    public void addMovement(Position oldPos)
    {
        positionStack.add(oldPos);
    }

    /**
     * Retrieve the position of the checker previous to this move.
     * The position retrieved will automatically be removed.
     * @return Previous position of checker.
     */
    public Position getPrevPosition()
    {
        return positionStack.pop();
    }

    /**
     * Clear the stored positions. Usually done
     * if a turn finally has ended.
     */
    public void clearStack()
    {
        positionStack.clear();
    }
}
