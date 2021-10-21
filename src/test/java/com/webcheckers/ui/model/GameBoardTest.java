package com.webcheckers.ui.model;

import com.webcheckers.model.GameBoard;
import com.webcheckers.model.GameBoard.cells;
import com.webcheckers.model.Player;
import com.webcheckers.ui.boardview.BoardView;
import com.webcheckers.ui.boardview.Piece;
import com.webcheckers.ui.boardview.Row;
import com.webcheckers.ui.boardview.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(compareViews(boardView, CuT.toBoardView()));
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
        GameBoard flippedCuT = CuT.flipBoard();

        assertTrue(compareViews(boardView, flippedCuT.toBoardView()));
    }

    /**
     * Private method to compare boardViews with
     * each other. Needed for testing.
     * @return if the boards are equal
     */
    private boolean compareViews(BoardView b1, BoardView b2)
    {
        // equal assertions of boardView require comparisons of board
        // structures.
        // get iterators of generated views
        Iterator<Row> baseIterator = b1.iterator();
        Iterator<Row> CuTIterator = b2.iterator();

        // structures should have the same dimensions, so only
        // one loop for each subsection is required
        while(baseIterator.hasNext()) // iterate through rows
        {
            Row baseRow = baseIterator.next();
            Row CuTRow = CuTIterator.next();
            Iterator<Space> baseRowIterator = baseRow.iterator();
            Iterator<Space> baseCuTIterator = CuTRow.iterator();
            while(baseRowIterator.hasNext()) // iterate through spaces
            {
                // get spaces from iterators
                Space baseSpace = baseRowIterator.next();
                Space CuTSpace = baseCuTIterator.next();

                // get pieces from spaces
                Piece basePiece = baseSpace.getPiece();
                Piece CuTPiece = CuTSpace.getPiece();

                // assertions
                if(basePiece != null) {
                    if(basePiece.getType() != CuTPiece.getType())
                    {
                        return false;
                    }

                    if(basePiece.getColor() != CuTPiece.getColor())
                    {
                        return false;
                    }
                }
                else
                {
                    if(CuTPiece != null)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
}
