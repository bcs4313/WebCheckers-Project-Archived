package com.webcheckers.model;

import java.util.ArrayList;

public class Space {
    private int cellIDx;
    private Piece currentPiece;

    public Space(int cellIDx) throws Exception {
        this.cellIDx=cellIDx;
        if(cellIDx>7){
            throw new Exception("Invalid index size: Must be 7 or lower.");
        }
    }

    public int getCellIDx(){
        return cellIDx;
    }

    //todo: needs to check if piece is a dark square and has no other piece on it.
    public boolean isValid(){
        return false;
    }


    public Piece getPiece(){
        return currentPiece;
    }
}
