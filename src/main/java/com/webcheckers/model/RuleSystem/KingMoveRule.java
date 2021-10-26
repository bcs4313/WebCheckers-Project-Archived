package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Position;

/**
 * Rule that checks the movement validity of a piece
 * in the King state (diagonally both ways, but not to the side)
 * 
 * @author Michael Ambrose (ma8540@rit.edu)
 */
public class KingMoveRule extends Rule {

    // Piece starting position
    private int before_row;
    private int before_col;

    // Piece ending position
    private int after_row;
    private int after_col;

    public KingMoveRule(RuleMaster master) {
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

        // Return false if move is legal (movement rule should be the same for both red and white king pieces)
        if (this.after_row == (this.before_row - 1) || this.after_row == (this.before_row + 1)){
            if(this.after_col == (this.before_col - 1) || this.after_col == (this.before_col + 1))
                // checker must be a king to make this move anyway
                if(b_after[after_row][after_col] == GameBoard.cells.RK || b_after[after_row][after_col] == GameBoard.cells.WK) {
                    return false;
                }
        }

        return true;
    }

    /**
     * revert move if move was illegal
     * @return the game board as it was prior to move made
     */
    @Override
    public void action() {
        master.invalidKingMove = true;
    }
    
}
