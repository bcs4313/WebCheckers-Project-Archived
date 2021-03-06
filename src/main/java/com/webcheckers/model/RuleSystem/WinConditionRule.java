package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;

/**
 * Defines the requirements for a team to "win" in a game
 * of checkers.
 * @author Triston Lincoln (trl6895@rit.edu)
 */
public class WinConditionRule extends Rule {

    private boolean red_has_piece;
    private boolean white_has_piece;

    WinConditionRule(RuleMaster master){
        super(master);
        this.red_has_piece=false;
        this.white_has_piece=false;
    }

    @Override
    public boolean isTriggered(GameBoard.cells[][] b_before, GameBoard.cells[][] b_after) {
        this.red_has_piece = false;
        this.white_has_piece = false;
        for (GameBoard.cells[] cells : b_after) {
            for (GameBoard.cells cell : cells) {
                if (cell.equals(GameBoard.cells.R) || cell.equals(GameBoard.cells.RK)) {
                    this.red_has_piece = true;
                } else if (cell.equals(GameBoard.cells.W) || cell.equals(GameBoard.cells.WK)) {
                    this.white_has_piece = true;
                }
            }
        }
        return (!red_has_piece) || (!white_has_piece);

    }

    @Override
    public void action() {
        if(!red_has_piece){
            master.setWin(RuleMaster.WHITE_ATTR);
        }else if(!white_has_piece){
            master.setWin(RuleMaster.RED_ATTR);
        }
        master.setGameOver(true);
    }
}
