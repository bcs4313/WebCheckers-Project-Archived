package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;
import com.webcheckers.model.GameBoard.cells;

/**
 * Rule that checks if a red or white single piece is on the other side from 
 * their respective sides (first row for red, last row for white)
 * 
 * If true, this rule promotes the proper piece to a King
 * 
 * @author Michael Ambrose (ma8540@rit.edu)
 */
public class KingPromotionRule extends Rule {
    private cells[][] spaces;

    public KingPromotionRule(RuleMaster master) {
        super(master);
    }

    /**
     * checks to see if case is true
     * @param b_before the board before move was made
     * @param b_after the board after move was made
     * @return true if case is valid, false otherwise
     */
    @Override
    public boolean isTriggered(GameBoard.cells[][] b_before, GameBoard.cells[][] b_after) {
        spaces = b_after;

        for(GameBoard.cells space : spaces[0]) {
            if(space == cells.R) 
                return true;
        }

        for(GameBoard.cells space : spaces[7]) {
            if(space == cells.W)
                return true;
        }

        return false;
    }

    /**
     * If case is true, change the type of piece 
     * to a King with their respective colors
     */
    @Override
    public void action() {
        int i = 0;
        for(GameBoard.cells space : spaces[0]) {
            if(space == cells.R) {
                spaces[0][i] = cells.RK;

            }
            i++;
        }
        int j = 0;
        for(GameBoard.cells space : spaces[7]) {
            if(space == cells.W) {
                spaces[7][j] = cells.WK;
            }
            j++;
        }
    }
    
}
