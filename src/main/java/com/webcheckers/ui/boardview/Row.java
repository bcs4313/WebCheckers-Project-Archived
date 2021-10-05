package com.webcheckers.ui.boardview;

import com.webcheckers.model.GameBoard;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class represents a row on
 * the game board
 */
public class Row implements Iterable<Space> {
    private int index;
    private ArrayList<Space> spaces;
    private final int EACHSPACE = 8;

    public Row(int index, GameBoard.cells[] board) throws Exception {
        this.index=index;
        if(index>7){
            throw new Exception("Invalid index size: Must be 7 or lower.");
        }

        spaces = new ArrayList<>();
        for(int i=0; i<EACHSPACE; i++){
            GameBoard.cells pieceType = board[i];
            Space s = new Space(i, pieceType);
            spaces.add(i, s);
        }

    }

    public int getIndex(){
        return index;
    }

    public Iterator<Space> iterator(){
        return spaces.iterator();
    }
}
