package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;

public class WinCondition extends Rule {

    private boolean red_has_piece;
    private boolean white_has_piece;

    WinCondition(){
        super(null);
        this.red_has_piece=false;
        this.white_has_piece=false;
    }

    // TODO
    //  is this necessary if the cell Types are present in the GameBoard class? - Triston
    public enum cells {
        X,
        E,
        W,
        R,
        WK,
        RK
    }

    @Override
    public boolean isTriggered(GameBoard.cells[][] b_before, GameBoard.cells[][] b_after) {
        for (GameBoard.cells[] cells : b_after) {
            for (GameBoard.cells cell : cells) {
                if (cell.equals(GameBoard.cells.R) || cell.equals(GameBoard.cells.RK)) {
                    this.red_has_piece = true;
                } else if (cell.equals(GameBoard.cells.W) || cell.equals(GameBoard.cells.WK)) {
                    this.white_has_piece = true;
                }
            }
        }
        return !red_has_piece || !white_has_piece;

    }

    @Override
    public void action() {
        if(!red_has_piece){
            master.setWin("white player");
        }else if(!white_has_piece){
            master.setWin("red player");
        }
        master.setGameOver(true);
    }
}
