package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;

import java.util.Stack;

/**
 * This class stores the previous movements of a player,
 * making it easy to undo a move.
 */
public class MoveLog {
    RuleMaster master;
    Stack<GameBoard.cells[][]> positionStack; // stack of player movements

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
     * @param oldBoard old board state to store
     */
    public void addMovement(GameBoard.cells[][] oldBoard)
    {
        // we can't just throw the reference in, it will change
        // with time. We need a literal copy.
        GameBoard.cells[][] b = new GameBoard.cells[8][8];

        for(int y = 0; y < b.length; y++)
        {
            System.arraycopy(oldBoard[y], 0, b[y], 0, b[y].length);
        }

        positionStack.add(b);
    }

    /**
     * Retrieve the position of the checker previous to this move.
     * The position retrieved will automatically be removed.
     * @return Previous position of checker.
     */
    public GameBoard.cells[][] getPrevPosition()
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
