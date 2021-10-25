package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;

/**
 * This rule analyzes whether or not a checker is making a valid
 * Forward Jump
 */
public class ForwardJumpRule extends Rule {


    // Piece starting position
    private int before_row;
    private int before_col;

    // Piece ending position
    private int after_row;
    private int after_col;


    public ForwardJumpRule(RuleMaster master) {
        super(master);
    }

    /**
     * checks to see if this rule is violated
     * @param b_before the board before move was made
     * @param b_after the board after move was made
     * @return true if rule was violated, false otherwise
     */
    @Override
    public boolean isTriggered(GameBoard b_before, GameBoard b_after) {
        MovementPair mp = master.identifyMovement();

        if(mp == null)
        {
            return false;
        }

        this.before_row = mp.before_y;
        this.before_col = mp.before_x;
        this.after_row = mp.after_y;
        this.after_col = mp.after_x;

        GameBoard.cells jumperIdentity = b_after.getBoard()[after_row][after_col];

        // jump dimensions are checked for validity first
        if(before_row - 2 == after_row)
        {
            if(before_col - 2 == after_col) // top left jump
            {
                GameBoard.cells victimIdentity = b_before.getBoard()[after_row + 1][after_col + 1];
                if(jumperIdentity == GameBoard.cells.R || jumperIdentity == GameBoard.cells.RK)
                {
                    return !(victimIdentity == GameBoard.cells.W || victimIdentity == GameBoard.cells.WK);
                }
                if(jumperIdentity == GameBoard.cells.W || jumperIdentity == GameBoard.cells.WK)
                {
                    return !(victimIdentity == GameBoard.cells.R || victimIdentity == GameBoard.cells.RK);
                }
            }

            if(before_col + 2 == after_col) // top right jump
            {
                GameBoard.cells victimIdentity = b_before.getBoard()[after_row + 1][after_col - 1];
                if(jumperIdentity == GameBoard.cells.R || jumperIdentity == GameBoard.cells.RK)
                {
                    return !(victimIdentity == GameBoard.cells.W || victimIdentity == GameBoard.cells.WK);
                }
                if(jumperIdentity == GameBoard.cells.W || jumperIdentity == GameBoard.cells.WK)
                {
                    return !(victimIdentity == GameBoard.cells.R || victimIdentity == GameBoard.cells.RK);
                }
            }
        }
        return false;
    }

    @Override
    public void action() {
    }
}
