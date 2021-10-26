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

    /**
     * Clear player of jump chain.
     */
    public void clearJumps()
    {
        jumpChains.clear();
    }

    /**
     * Is the player currently inside of a jump chain,
     * and must they jump again?
     * @return if a player must jump again
     */
    public boolean mustJump()
    {
        if(jumpChains.size() >= 1) // if we know they are in a chain
        {
            // now we must check to see if a jump must occur
            BackwardJumpRule brule = new BackwardJumpRule(master);
            ForwardJumpRule frule = new ForwardJumpRule(master);

            GameBoard.cells[][] b_before = master.getB_Before();
            GameBoard.cells[][] b_after = master.getB_After();

            return !(brule.isTriggered(b_before, b_after) && frule.isTriggered(b_before, b_after));
        }
        return false;
    }
}
