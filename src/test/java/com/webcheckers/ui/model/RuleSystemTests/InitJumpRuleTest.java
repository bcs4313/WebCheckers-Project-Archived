package com.webcheckers.ui.model.RuleSystemTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Player;
import com.webcheckers.model.GameBoard.cells;
import com.webcheckers.model.RuleSystem.InitJumpRule;
import com.webcheckers.model.RuleSystem.RuleMaster;

/**
 * JUnit tests for the InitJumpRule model-tier class
 * 
 * @author Michael Ambrose (ma8540@rit.edu)
 */
@Tag("Model-Tier")
public class InitJumpRuleTest {
    //Object under test
    private InitJumpRule CuT;

    private RuleMaster master;
    private GameBoard gameBoard;
    private cells[][] fixedCells;
    private Player user1 = new Player("User1"); //red
    private Player user2 = new Player("User2"); //white

    //Board Cells
    private static final cells X = cells.X;
    private static final cells E = cells.E;
    private static final cells W = cells.W;
    private static final cells R = cells.R;


    /**
     * Instatiates CuT and other friendly objects before every test
     */
    @BeforeEach
    public void setup() {
        gameBoard = new GameBoard(user1, user2);
        master = new RuleMaster(gameBoard);
        CuT = new InitJumpRule(master, GameBoard.activeColors.RED, null);
    }

    /**
     * Verifies no jumps should be possible at the
     * start of the game
     */
    @Test
    public void test_starting_jump_moves() {
        boolean actual;

        //Starting board for RED, no jump moves should be possible
        actual = CuT.isTriggered(gameBoard.getBoard(), null);
        assertFalse(actual);

        //Sets active player to White
        gameBoard.switchActiveColor();

        //Starting board for WHITE, no jump moves should be possible
        actual = CuT.isTriggered(gameBoard.getBoard(), null);
        assertFalse(actual);
    }
    
    /**
     * Verifies a jump should be possible
     */
    @Test
    public void test_forward_jump_moves() {
        boolean actual;
        //fixed board for RED
        fixedCells = new cells[][]{
            {X, W, X, W, X, W, X, W},
            {W, X, W, X, W, X, W, X},
            {X, W, X, W, X, E, X, W},
            {E, X, E, X, W, X, E, X},
            {X, E, X, R, X, E, X, E},
            {R, X, E, X, R, X, R, X},
            {X, R, X, R, X, R, X, R},
            {R, X, R, X, R, X, R, X}};
        
        //set fixed board
        gameBoard.setBoard(fixedCells);

        //Fixed board has jump, should return True
        actual = CuT.isTriggered(gameBoard.getBoard(), null);
        assertTrue(actual);

        //Sets active player to White
        gameBoard.switchActiveColor();

        //Starting board for WHITE, no jump moves should be possible
        actual = CuT.isTriggered(gameBoard.getBoard(), null);
        assertFalse(actual);

        //fixed board for WHITE
        fixedCells = new cells[][]{
            {X, R, X, R, X, R, X, R},
            {R, X, R, X, R, X, R, X},
            {X, R, X, R, X, E, X, R},
            {E, X, E, X, R, X, E, X},
            {X, E, X, W, X, E, X, E},
            {W, X, E, X, W, X, W, X},
            {X, W, X, W, X, W, X, W},
            {W, X, W, X, W, X, W, X}};
        
        //set fixed board
        gameBoard.setBoard(fixedCells);

        //Fixed board has jump, should return True
        actual = CuT.isTriggered(gameBoard.getBoard(), null);
        assertTrue(actual);
    }

    /**
     * Verifies a piece had a target
     */
    @Test
    public void test_has_target() {
        boolean actual;
        //fixed board for RED
        fixedCells = new cells[][]{
            {X, W, X, W, X, W, X, W},
            {W, X, W, X, W, X, W, X},
            {X, W, X, W, X, W, X, E},
            {E, X, E, X, E, X, W, X},
            {X, E, X, E, X, R, X, E},
            {R, X, R, X, R, X, E, X},
            {X, R, X, R, X, R, X, R},
            {R, X, R, X, R, X, R, X}};
        
        //set fixed board
        gameBoard.setBoard(fixedCells);

        //Fixed board has target, should return True
        actual = CuT.hasTarget(fixedCells, 4, 5);
        assertTrue(actual);
    }
}
