package com.webcheckers.ui.model.RuleSystemTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.model.GameBoard.cells;
import com.webcheckers.model.RuleSystem.BackwardJumpRule;
import com.webcheckers.model.RuleSystem.RuleMaster;

/**
 * JUnit tests for the BackwardJumpRule model-tier class
 * 
 * @author Michael Ambrose (ma8540@rit.edu)
 */
@Tag ("Model-tier")
public class BackwardJumpRuleTest {
    //Object under test
    private BackwardJumpRule CuT;

    //Friendly Objects
    private RuleMaster master;
    private GameBoard gameBoard;
    private cells[][] b_before;
    private cells[][] b_after;
    private Player user1 = new Player("User1");
    private Player user2 = new Player("User2");
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
        b_before = new cells[][]{
            {X, W, X, W, X, W, X, W},
            {W, X, W, X, W, X, W, X},
            {X, E, X, W, X, E, X, W},
            {E, X, R, X, W, X, E, X},
            {X, W, X, R, X, E, X, E},
            {E, X, E, X, R, X, R, X},
            {X, R, X, R, X, R, X, R},
            {R, X, R, X, R, X, R, X}};
        
        gameBoard = new GameBoard(user1, user2, b_before, GameBoard.activeColors.RED);
        master = new RuleMaster(gameBoard);
        CuT = new BackwardJumpRule(master);
    }

    /**
     * Validates the jump made was illegal
    */
    @Test
    public void test_invalid_jump() {
        //board with moved piece (forward jump)
        b_after = new cells[][] {
            {X, W, X, W, X, W, X, W},
            {W, X, W, X, W, X, W, X},
            {X, E, X, W, X, R, X, W},
            {E, X, R, X, E, X, E, X},
            {X, W, X, E, X, E, X, E},
            {E, X, E, X, R, X, R, X},
            {X, R, X, R, X, R, X, R},
            {R, X, R, X, R, X, R, X}};
        
        //Location of piece
        before_pos = new Position(4, 3);
        after_pos = new Position(2, 5);
        
        //master makes the board transition based on before and after positions
        master.createBoardTransition(before_pos, after_pos, gameBoard.getActiveColor());
        
        //isTriggered() -> returns true if jump is illegal/not valid
        assertTrue(CuT.isTriggered(b_before, b_after));
    }

    /**
     * Validates the jump made was legal
    @Test
    public void test_valid_jump() {
        //board with moved piece (backward jump)
        b_after = new cells[][] {
            {X, W, X, W, X, W, X, W},
            {W, X, W, X, W, X, W, X},
            {X, E, X, W, X, E, X, W},
            {E, X, E, X, W, X, E, X},
            {X, E, X, R, X, E, X, E},
            {R, X, E, X, R, X, R, X},
            {X, R, X, R, X, R, X, R},
            {R, X, R, X, R, X, R, X}};
        
        //Location of piece
        before_pos = new Position(3, 2);
        after_pos = new Position(5, 0);
        
        //master makes the board transition based on before and after positions
        master.createBoardTransition(before_pos, after_pos, gameBoard.getActiveColor());
        
        //isTriggered() -> returns false if jump is legal/valid
        assertFalse(CuT.isTriggered(b_before, b_after));
    }
    */
}
