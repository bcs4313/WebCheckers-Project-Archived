package com.webcheckers.model.boardview;

import com.webcheckers.model.GameBoard;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Take a board matrix and adds the rows from the
 * board to an array list so it can be iterated through
 * @author Carlos Hargrove
 */

public class BoardView implements Iterable<Row> {
    private ArrayList<Row> rows;
    private final int SPACES = 8;

    /**
     * Constructor
     * 
     * @param board a 2d matrix of cells that will be converted
     * into a html view.
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
