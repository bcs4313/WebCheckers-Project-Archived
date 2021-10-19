package com.webcheckers.ui.model;
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Player;
import com.webcheckers.model.GameBoard.cells;
import com.webcheckers.ui.boardview.BoardView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the GameBoard model-tier class
 * 
 * @author Michael Ambrose (ma8540@rit.edu)
 */
@Tag ("Model-Tier")
public class GameBoardTest {

    //Object under test
    private GameBoard CuT;

    //Friendly Objects
    private Player player1;
    private Player player2;

    //Mock Board Cells
    private cells[][] board;
    private static final cells X = cells.X;
    private static final cells E = cells.E;
    private static final cells W = cells.W;
    private static final cells R = cells.R;

    /**
     * Creates CuT, players, and board before all tests
     */
    @BeforeEach
    public void setup() {
        //sender
        player1 = new Player("IAmRed");
        //receiver
        player2 = new Player("IAmWhite");
        CuT = new GameBoard(player1, player2);

        board = new cells[][]{
            {X, W, X, W, X, W, X, W},
            {W, X, W, X, W, X, W, X},
            {X, W, X, W, X, W, X, W},
            {E, X, E, X, E, X, E, X},
            {X, E, X, E, X, E, X, E},
            {R, X, R, X, R, X, R, X},
            {X, R, X, R, X, R, X, R},
            {R, X, R, X, R, X, R, X}};
    }

    /**
     * Validates player1 is red
     */
    @Test
    public void test_get_red_player() {
        //player1 (sender) should be red
        assertEquals(player1, CuT.getRedPlayer());
    }

    /**
     * Validates player2 is white
     */
    @Test
    public void test_get_white_player() {
        //player2 (receiver) should be white
        assertEquals(player2, CuT.getWhitePlayer());
    }

    @Test
    public void test_to_board_view() {
        BoardView boardView = new BoardView(board);

        assertEquals(boardView, CuT.toBoardView());
    }

    /**
     * Confirms the board was flipped
     */
    @Test
    public void test_flip_board() {
        cells[][] flipped_board = new cells[][]{
            {X, R, X, R, X, R, X, R},
            {R, X, R, X, R, X, R, X},
            {X, R, X, R, X, R, X, R},
            {E, X, E, X, E, X, E, X},
            {X, E, X, E, X, E, X, E},
            {W, X, W, X, W, X, W, X},
            {X, W, X, W, X, W, X, W},
            {W, X, W, X, W, X, W, X}};
        
        BoardView boardView = new BoardView(flipped_board);
        CuT.flipBoard();

        assertEquals(boardView, CuT.toBoardView());
    }
    
}
