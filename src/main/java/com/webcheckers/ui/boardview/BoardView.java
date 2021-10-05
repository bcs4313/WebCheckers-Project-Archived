package com.webcheckers.ui.boardview;

import com.webcheckers.model.GameBoard;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Take a board matrix and addes the rows from the 
 * board to an array list so it can be iterated through
 */

public class BoardView implements Iterable<Row> {
    private ArrayList<Row> rows;
    private final int SPACES = 8;

    /**
     * Constructor
     * 
     * @param board
     */
    public BoardView(GameBoard.cells[][] board) {
        try {
            rows = new ArrayList<>();

            for (int i = 0; i < SPACES; i++) {
                rows.add(i, new Row(i, board[i]));
            }
        }
        catch (Exception e)
        {
            System.err.println("Exception in creating board view!: " + e);
        }
    }

    /**
     * Iterator
     * 
     * @return an instance of an Iterator<Row>
     */
    public Iterator<Row> iterator() {
        return rows.iterator();
    }
}
