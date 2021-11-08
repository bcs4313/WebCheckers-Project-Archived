package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Position;

import java.util.ArrayList;

/**
 * Class handles checker jump chains by
 * logging each individual jump and
 * forcing them to occur.
 */
public class Chainer {
    // list of positions that are included in this jump
    // chain
    ArrayList<Position> jumpChains;
    RuleMaster master; // used to reference GameBoard state
    GameBoard.cells[][] initState; // initial state of board per turn

    /**
     * Basic chaining class to track
     * forced jumps.
     * @param master RuleMaster that uses this chaining rule
     */
    public Chainer(RuleMaster master)
    {
        this.master = master;
        jumpChains = new ArrayList<>();
    }

    /**
     * log a jump within a specific turn
     */
    public void logJump(Position pos)
    {
        jumpChains.add(pos);
    }

    /**
     * undo part of a jump chain
     */
    public void undoJump()
    {
        if(jumpChains.size() >= 1) { // error guard
            jumpChains.remove(jumpChains.size() - 1);
        }
    }

    public int getLength() { return jumpChains.size(); }

    /**
     * Get latest position of jump location
     * @return Position object
     */
    public Position head()
    {
        if(jumpChains.size() != 0) {
            return jumpChains.get(jumpChains.size() - 1);
        }
        else
        {
            return null;
        }
    }

    /**
     * Clear player of jump chain.
     */
    public void clearJumps()
    {
        jumpChains.clear();
    }
}
