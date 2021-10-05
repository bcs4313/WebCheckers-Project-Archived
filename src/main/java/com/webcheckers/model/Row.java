package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Row. Contains each space within a given row and the details within them.
 * @author Carlos Hargrove
 */
public class Row {
    private int index;
    private ArrayList<Space> spaces;
    private final int EACHSPACE = 8;

    /**
     * Object detailing the row of the game board.
     * @param index - the # row on the game board.
     * @throws Exception - thrown if the board is too big. This shouldn't happen.
     */
    public Row(int index) throws Exception {
        this.index=index;
        if(index>7){
            throw new Exception("Invalid index size: Must be 7 or lower.");
        }

        //initializes each space in the row.
        spaces = new ArrayList<>();
        for(int i=0; i<EACHSPACE; i++){
            spaces.add(i,new Space(i,index));
        }

    }

    /**
     * @return - the # row of the board.
     */
    public int getIndex(){
        return index;
    }

    /**
     * Iterator method
     * @return - Space iterator.
     */
    public Iterator<Space> iterator(){
        return spaces.iterator();
    }
}
