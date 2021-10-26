package com.webcheckers.ui.ui;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.GameBoard.cells;
import com.webcheckers.model.boardview.Piece;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the Piece ui-tier class
 * 
 * @author Michael Ambrose (ma8540@rit.edu)
 */
@Tag ("UI-Tier")
public class PieceTest {

    //Object under test
    private Piece CuT;

    //Friendly Objects
    private cells pieceType1;
    private cells pieceType2;

    /**
     * Creates two different piece types for 
     * the CuT to analyze
     */
    @BeforeEach
    public void setup() {
        pieceType1 = cells.R;
        pieceType2 = cells.WK;
    }
    
    /**
     * Validates pieceType1 is a Single type piece
     */
    @Test
    public void test_get_type1() {
        CuT = new Piece(pieceType1);

        assertEquals(Piece.Type.SINGLE, CuT.getType());
    }

    /**
     * Validates pieceType1 is a Red colored piece
     */
    @Test
    public void test_get_color1() {
        CuT = new Piece(pieceType1);

        assertEquals(Piece.Color.RED, CuT.getColor());
    }

    /**
     * Validates pieceType2 is a King type piece
     */
    @Test
    public void test_get_type2() {
        CuT = new Piece(pieceType2);

        assertEquals(Piece.Type.KING, CuT.getType());
    }

    /**
     * Validates pieceType2 is a White colored piece
     */
    @Test
    public void test_get_color2() {
        CuT = new Piece(pieceType2);

        assertEquals(Piece.Color.WHITE, CuT.getColor());
    }
}
