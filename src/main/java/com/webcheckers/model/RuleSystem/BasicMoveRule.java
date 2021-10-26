package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Position;

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

        // If moved illegally, return true
        // else, return false
        if (this.after_row != (this.before_row - 1)){
            return true;
        }
        return (this.after_col != (this.before_col - 1) && this.after_col != (this.before_col + 1));
    }

    /**
     * revert move if move was illegal
     */
    @Override
    public void action(){
        master.invalidBasicMove = true;
    }
}
