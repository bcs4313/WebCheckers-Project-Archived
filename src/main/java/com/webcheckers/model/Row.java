package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

public class Row {
    private int index;
    private ArrayList<Space> spaces;
    private final int EACHSPACE = 8;

    public Row(int index) throws Exception {
        this.index=index;
        if(index>7){
            throw new Exception("Invalid index size: Must be 7 or lower.");
        }

        spaces = new ArrayList<>();
        for(int i=0; i<EACHSPACE; i++){
            spaces.add(i,new Space(i));
        }

    }

    public int getIndex(){
        return index;
    }

    public Iterator<Space> iterator(){
        return spaces.iterator();
    }
}
