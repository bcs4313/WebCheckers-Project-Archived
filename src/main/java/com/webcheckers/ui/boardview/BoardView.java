package com.webcheckers.ui.boardview;

import com.webcheckers.model.GameBoard;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardView implements Iterable<Row> {
    private ArrayList<Row> rows;
    private final int SPACES = 8;

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

    public Iterator<Row> iterator() {
        return rows.iterator();
    }
}
