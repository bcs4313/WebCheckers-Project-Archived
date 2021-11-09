package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;

public abstract class Rule {
    RuleMaster master; // master will handle all rule triggers concurrently

    public Rule(RuleMaster master)
    {
        this.master = master;
    }

    /**
     * Given two gameboards, one with the previous state and one
     * with the new state, will your rule trigger?
     * @return if your rule took action
     */
    public abstract boolean isTriggered(GameBoard.cells[][] b_before, GameBoard.cells[][] b_after);

    /**
     * Enter the action that your rule will take upon being
     * triggered.
     */
    public abstract void action();

    /**
     * Flips a matrix. Annoying that this is needed
     * but it makes everything else work.
     * @param b matrix to flip
     */
    public GameBoard.cells[][] flipBoard(GameBoard.cells[][] b)
    {

        GameBoard.cells[][] boardFlipped = new GameBoard.cells[8][8];

        // copy a board, raw
        for(int i = 0; i <= 7; i++) {
            // for each piece
            for(int j = 0; j < boardFlipped[i].length; j++) {
                // if the piece is red, change to white
                boardFlipped[i][j] = b[i][j];
            }
        }

        // for each row in the bottom half of the board
        for(int i = 0; i <= 7; i++) {
            // for each piece
            for(int j = 0; j < boardFlipped[i].length; j++) {
                // if the piece is red, change to white
                boardFlipped[i][j] = b[7 - i][7 - j];
            }
        }

        return boardFlipped;
    }
}
