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

    // locations of checkers that were jumped over
    // *will be killed at end of turn
    ArrayList<Position> victims;

    /**
     * Respective statuses of each checker captured
     * this turn.
     */
    ArrayList<GameBoard.cells> colors;

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
        victims = new ArrayList<>();
        colors = new ArrayList<>();
    }

    /**
     * log a jump within a specific turn
     * @param posAttacker position of attacker after jump
     * @param posVictim position of checker that was jumped over
     * @param color identity of checker captured
     */
    public void logJump(Position posAttacker, Position posVictim, GameBoard.cells color)
    {
        jumpChains.add(posAttacker);
        victims.add(posVictim);
        colors.add(color);
    }

    /**
     * undo part of a jump chain within the gameboard
     * if possible.
     */
    public void undoJump(GameBoard gb)
    {
        if(jumpChains.size() >= 1) { // error guard
            // restore checker at position
            Position restorePoint = jumpChains.get(jumpChains.size() - 1);
            GameBoard.cells restoreColor = colors.get(colors.size() - 1);
            jumpChains.remove(jumpChains.size() - 1);
            victims.remove(victims.size() - 1);
            colors.remove(victims.size() - 1);

            // restore a checker upon undo if available
            if(restorePoint != null)
            {
                GameBoard.cells[][] restoreFrame = master.getB_Before();

                restoreFrame[restorePoint.getRow()][restorePoint.getCell()] = restoreColor;

                gb.setboard(restoreFrame);
            }
        }
    }

    /**
     * Clear player of jump chain.
     */
    public void clearJumps()
    {
        jumpChains.clear();
        victims.clear();
        colors.clear();
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
