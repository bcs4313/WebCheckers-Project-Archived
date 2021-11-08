package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Position;

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
    public boolean isTriggered(GameBoard.cells[][] b_before, GameBoard.cells[][] b_after) {
        Position prevPos = master.getPrevPos();
        Position afterPos = master.getAfterPos();

        this.before_row = prevPos.getRow();
        this.before_col = prevPos.getCell();
        this.after_row = afterPos.getRow();
        this.after_col = afterPos.getCell();

        GameBoard.cells jumperIdentity;
        GameBoard.cells victimIdentity;

        GameBoard.cells[][] altAfter = b_after;
        if(master.getTurn() == GameBoard.activeColors.WHITE)
        {
            b_before = flipBoard(b_before);
            b_after = flipBoard(b_after);
        }


        // jump dimensions are checked for validity first
        if(before_row - 2 == after_row) {
            if (before_col - 2 == after_col) // jump was made forwards
            {
                jumperIdentity = b_after[after_row][after_col];
                victimIdentity = b_before[before_row - 1][before_col - 1];

                if (jumperIdentity == GameBoard.cells.R || jumperIdentity == GameBoard.cells.RK) {
                    if ((victimIdentity == GameBoard.cells.W || victimIdentity == GameBoard.cells.WK)) {
                        b_after[before_row - 1][before_col - 1] = GameBoard.cells.E;
                        return false;
                    }
                }
                if (jumperIdentity == GameBoard.cells.W || jumperIdentity == GameBoard.cells.WK) {
                    if ((victimIdentity == GameBoard.cells.R || victimIdentity == GameBoard.cells.RK)) {
                        altAfter[7 - (before_row - 1)][7 - (before_col - 1)] = GameBoard.cells.E;
                        return false;
                    }
                }
            }
            if (before_col + 2 == after_col) { // top right jump
                jumperIdentity = b_after[after_row][after_col];
                victimIdentity = b_before[before_row - 1][before_col + 1];
                if (jumperIdentity == GameBoard.cells.R || jumperIdentity == GameBoard.cells.RK) {
                    if ((victimIdentity == GameBoard.cells.W || victimIdentity == GameBoard.cells.WK)) {
                        b_after[before_row - 1][before_col + 1] = GameBoard.cells.E;
                        return false;
                    }
                }
                if (jumperIdentity == GameBoard.cells.W || jumperIdentity == GameBoard.cells.WK) {
                    if ((victimIdentity == GameBoard.cells.R || victimIdentity == GameBoard.cells.RK)) {
                        altAfter[7 - (before_row - 1)][7 - (before_col + 1)] = GameBoard.cells.E;
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void action() {
        master.invalidForwardJump = true;
    }
}
