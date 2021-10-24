package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;

/**
 * Rule that checks the movement validity of a piece
 * in the King state (diagonally both ways, but not to the side)
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
    public boolean isTriggered(GameBoard b_before, GameBoard b_after) {
        MovementPair mp = master.identifyMovement();
        this.before_row = mp.before_y;
        this.before_col = mp.before_x;
        this.after_row = mp.after_y;
        this.after_col = mp.after_x;

        // Return false if move is legal (movement rule should be the same for both red and white king pieces)
        if (this.after_row == (this.before_row - 1) || this.after_row == (this.before_row + 1)){
            if(this.after_col == (this.before_col - 1) || this.after_col == (this.before_col + 1))
                return false;
        }

        return true;
    }

    /**
     * revert move if move was illegal
     * @param boardToModify the session's game board
     * @return the game board as it was prior to move made
     */
    @Override
    public void action() {

    }
    
}
