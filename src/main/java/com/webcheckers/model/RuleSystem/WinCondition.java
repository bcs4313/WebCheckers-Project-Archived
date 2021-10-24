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
    public boolean isTriggered(GameBoard b_before, GameBoard b_after) {
        GameBoard.cells[][] post_board=b_after.getBoard();
            for(int i=0; i<post_board.length; i++){
                for(int j=0; j<post_board[i].length; j++){
                    if(post_board[i][j].equals(GameBoard.cells.R)||post_board[i][j].equals(GameBoard.cells.RK)){
                        this.red_has_piece=true;
                    } else if(post_board[i][j].equals(GameBoard.cells.W)||post_board[i][j].equals(GameBoard.cells.WK)){
                        this.white_has_piece=true;
                    }
                }
            }
            if(red_has_piece==false||white_has_piece==false){
                return true;
            }
            return false;

    }

    @Override
    public void action() {
        if(red_has_piece==false){
            master.setWin("white player");
        }else if(white_has_piece==false){
            master.setWin("red player");
        }
        master.setGameOver(true);
    }
}
