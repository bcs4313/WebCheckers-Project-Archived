package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Model tier class. That contains BoardView and all of it's methods
 * @author Carlos Hargrove
 */
public class BoardView {
    //rows on the board
    private ArrayList<Row> rows;
    //max number rows and columns.
    private final int SPACES = 8;

    /**
     * Object that contains objects for every important aspect of a gameboard.
     * @throws Exception - The board is too big. This should not occur.
     */
    public BoardView() throws Exception {
        rows = new ArrayList<>();

        //expands the array to the size the board needs to be.
        for(int i=0; i<SPACES; i++){
            rows.add(i,new Row(i));
        }
    }

    /**
     * Iterable method.
     * @return - rows iterable.
     */
    public Iterator<Row> iterator(){
        return rows.iterator();
    }
}
