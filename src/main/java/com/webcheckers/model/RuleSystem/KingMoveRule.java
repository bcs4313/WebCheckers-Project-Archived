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
        GameBoard.cells[][] before_config = b_before.getBoard();
        GameBoard.cells[][] after_config = b_after.getBoard();
        for(int i = 0; i < before_config.length; i++){
            for(int j = 0; j < before_config[i].length; j++){
                if(before_config[i][j] != after_config[i][j]){
                    if(after_config[i][j].equals(GameBoard.cells.RK) || after_config[i][j].equals(GameBoard.cells.WK)){
                        this.after_row = i;
                        this.after_col = j;
                    }
                    else{
                        this.before_row = i;
                        this.before_col = j;
                    }
                }
            }
        }

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
    public GameBoard action(GameBoard boardToModify) {
        return null;
    }
    
}
