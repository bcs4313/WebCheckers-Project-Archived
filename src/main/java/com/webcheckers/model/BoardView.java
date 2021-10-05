package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardView {
    private ArrayList<Row> rows;
    private final int SPACES = 8;

    public BoardView() throws Exception {
        rows = new ArrayList<>();

        for(int i=0; i<SPACES; i++){
            rows.add(i,new Row(i));
        }
    }

    public Iterator<Row> iterator(){
        return rows.iterator();
    }
}
