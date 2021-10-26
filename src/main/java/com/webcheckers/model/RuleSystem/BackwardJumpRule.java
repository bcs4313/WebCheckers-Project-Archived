package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Position;

/**
 * This rule analyzes whether or not a checker is making a valid
 * backward jump.
 */
public class BackwardJumpRule extends Rule {


    // Piece starting position
    private int before_row;
    private int before_col;

    // Piece ending position
    private int after_row;
    private int after_col;


    public BackwardJumpRule(RuleMaster master) {
        super(master);
    }

    /**
     * checks to see if this rule is violated
     * @param b_before the board before move was made
     * @param b_after the board after move was made
     * @return true if rule was violated, false otherwise
     */
    @Override
    public boolean isTriggered(GameBoard.cells[][] b_before, GameBoard.cells[][] b_after) {
        Position prevPos = master.getPrevPos();
        Position afterPos = master.getAfterPos();

        this.before_row = prevPos.getRow();
        this.before_col = prevPos.getCell();
        this.after_row = afterPos.getRow();
        this.after_col = afterPos.getCell();

        System.out.println("br " + before_row);
        System.out.println("bc " + before_col);
        System.out.println("ar " + after_row);
        System.out.println("ac " + after_col);

        GameBoard.cells jumperIdentity = b_after[after_row][after_col];

        // jump dimensions are checked for validity first
        if(before_row + 2 == after_row)
        {
            if(before_col - 2 == after_col) // top left jump
            {
                GameBoard.cells victimIdentity = b_before[after_row - 1][after_col + 1];
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
                GameBoard.cells victimIdentity = b_before[after_row - 1][after_col - 1];
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
        return true;
    }

    @Override
    public void action() {
        master.invalidBackwardJump = true;
    }
}
