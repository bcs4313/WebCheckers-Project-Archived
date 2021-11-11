package com.webcheckers.ui.model.RuleSystemTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.model.GameBoard.cells;
import com.webcheckers.model.RuleSystem.RuleMaster;

/**
 * JUnit tests for the RuleMaster model-tier class
 * 
 * @author Michael Ambrose (ma8540@rit.edu)
 */
@Tag ("Model-tier")
public class RuleMasterTest {
    //Object under test
    private RuleMaster CuT;

    //Friendly Objects
    private Player user1 = new Player("User1"); //red player
    private Player user2 = new Player("User2"); //white player
    private GameBoard gameBoard;
    private cells[][] fixedBoard;
    private Position before_pos;
    private Position after_pos;

    //Board Cells
    private static final cells X = cells.X;
    private static final cells E = cells.E;
    private static final cells W = cells.W;
    private static final cells R = cells.R;

     /**
     * Instantiates CuT and other friendly objects before every test
     */
    @BeforeEach
    public void setup() {
        gameBoard = new GameBoard(user1, user2);
        CuT = new RuleMaster(gameBoard);
    }

    @Test
    public void test_trigger_ruleset_move() {
        //Makes a move
        before_pos = new Position(5, 2);
        after_pos = new Position(4, 3);

        CuT.createBoardTransition(before_pos, after_pos, gameBoard.getActiveColor());
        CuT.triggerRuleSet();

        assertEquals(cells.E, gameBoard.getBoard()[5][2]);
        assertEquals(cells.R, gameBoard.getBoard()[4][3]);
    }

    @Test
    public void test_trigger_ruleset_jump() {
        fixedBoard = new cells[][] {
            {X, W, X, W, X, W, X, W},
            {W, X, W, X, W, X, W, X},
            {X, W, X, W, X, E, X, W},
            {E, X, E, X, E, X, W, X},
            {X, E, X, E, X, E, X, R},
            {R, X, R, X, R, X, E, X},
            {X, R, X, R, X, R, X, R},
            {R, X, R, X, R, X, R, X}};
        
            //Makes a jump
            before_pos = new Position(4, 7);
            after_pos = new Position(2, 5);
            
            gameBoard.setBoard(fixedBoard);
            CuT.createBoardTransition(before_pos, after_pos, gameBoard.getActiveColor());
            CuT.triggerRuleSet();

        assertEquals(cells.E, gameBoard.getBoard()[4][7]);
        assertEquals(cells.E, gameBoard.getBoard()[3][6]);
        assertEquals(cells.R, gameBoard.getBoard()[2][5]);
            
    }

    /**
     * Validates the win state is for red (user1)
     */
    @Test
    public void test_set_red_win() {
        //user1 : red player
        CuT.setWin("red player");
        assertEquals(user1, CuT.getWinner());
    }

    /**
     * Validates the win state is for white (user2)
     */
    @Test
    public void test_set_white_win() {
        //user2 : white player
        CuT.setWin("white player");
        assertEquals(user2, CuT.getWinner());
    }

    @Test
    public void test_set_game_over() {
        //Game supposedly started, game is ongoing
        assertFalse(CuT.getGameOver());

        //Sets game condition to be over
        CuT.setGameOver(true);
        assertTrue(CuT.getGameOver());
    }
    
}

