package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;

public class WinCondition extends Rule {

    private boolean p1_hasPiece;
    private boolean p2_hasPiece;

    WinCondition(){
        super(null);
        this.p1_hasPiece=false;
        this.p2_hasPiece=false;
    }

    public enum cells {
        X,
        E,
        W,
        R,
        WK,
        RK
    }

    @Override
    public boolean isTriggered(GameBoard b_before, GameBoard b_after) {
        GameBoard.cells[][] post_board=b_after.getBoard();
            for(int i=0; i<post_board.length; i++){
                for(int j=0; j<post_board[i].length; j++){
                    if(post_board[i][j].equals(GameBoard.cells.R)||post_board[i][j].equals(GameBoard.cells.RK)){
                        this.p1_hasPiece=true;
                    } else if(post_board[i][j].equals(GameBoard.cells.W)||post_board[i][j].equals(GameBoard.cells.WK)){
                        this.p2_hasPiece=true;
                    }
                }
            }
            if(p1_hasPiece==false||p2_hasPiece==false){
                return true;
            }
            return false;

    }

    @Override
    public GameBoard action(GameBoard boardToModify) {
        return null;
    }
}
