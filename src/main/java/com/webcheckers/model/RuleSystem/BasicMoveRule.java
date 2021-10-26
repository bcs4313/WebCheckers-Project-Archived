package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;

/**
 * Rule that checks to make sure a basic piece wasn't moved
 * illegally (i.e. backwards, to the side, etc.)
 * @author Triston Lincoln
 */
public class BasicMoveRule extends Rule{

    // where did the piece start?
    private int before_row;
    private int before_col;

    // where was the piece placed?
    private int after_row;
    private int after_col;

    public BasicMoveRule(RuleMaster master){
        super(null);
    }

    /**
     * checks to see if this rule is violated
     * @param b_before the board before move was made
     * @param b_after the board after move was made
     * @return true if rule was violated, false otherwise
     */
    @Override
    public boolean isTriggered(GameBoard.cells[][] b_before, GameBoard.cells[][] b_after) {
        // Find the prior position of moved piece and a piece was moved
        for(int i = 0; i < b_before.length; i++){
            for(int j = 0; j < b_before[i].length; j++){
                if(b_before[i][j] != b_after[i][j]){
                    if(b_after[i][j].equals(GameBoard.cells.R) || b_after[i][j].equals(GameBoard.cells.W)){
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
        // If so, check if it was moved illegally
        // If it's a red piece, legality means that the piece was moved to either (row = i-1,col = j-1) or (row = i-1, col = j+1)
        GameBoard.activeColors pieceColor = master.board.getActiveColor();
        if (pieceColor.equals(GameBoard.activeColors.RED)){
            // If moved illegally, return true
            if (this.after_row != (this.before_row - 1)){
                return true;
            }
            else if (this.after_col != (this.before_col - 1) || this.after_col != (this.before_col + 1)){
                return true;
            }
            // else, return false
            else{
                return false;
            }
        }
        // If it's a white piece, legality means that the piece was moved to either (row = i+1,col = j+1) or (row = i+1,col = j-1)
        if (pieceColor.equals(GameBoard.activeColors.WHITE)){
            // If moved illegally, return true
            if (this.after_row != (this.before_row + 1)){
                return true;
            }
            else if (this.after_col != (this.before_col - 1) || this.after_col != (this.before_col + 1)){
                return true;
            }
        }
        // else, return false
        return false;
    }

    /**
     * revert move if move was illegal
     * @return the game board as it was prior to move made
     */
    @Override
    public void action(){
    }
}
