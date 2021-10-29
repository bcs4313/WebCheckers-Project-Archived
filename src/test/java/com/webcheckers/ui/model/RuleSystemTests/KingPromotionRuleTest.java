package com.webcheckers.ui.model.RuleSystemTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.model.GameBoard.cells;
import com.webcheckers.model.RuleSystem.KingPromotionRule;
import com.webcheckers.model.RuleSystem.RuleMaster;

/**
 * JUnit tests for the KingPromotionRule model-tier class
 * 
 * @author Michael Ambrose (ma8540@rit.edu)
 */
@Tag ("Model-tier")
public class KingPromotionRuleTest {
    //Object under test
    private KingPromotionRule CuT;

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
            {X, W, X, E, X, W, X, W},
            {W, X, W, X, W, X, W, X},
            {X, W, X, W, X, R, X, W},
            {W, X, E, X, E, X, E, X},
            {X, R, X, E, X, R, X, E},
            {E, X, E, X, E, X, R, X},
            {X, R, X, R, X, R, X, R},
            {R, X, R, X, R, X, R, X}};
        
        gameBoard = new GameBoard(user1, user2, b_before, GameBoard.activeColors.RED);
        master = new RuleMaster(gameBoard);
        CuT = new KingPromotionRule(master);
    }

    /**
     * Validates there is no case of a piece being promoted
     */
    @Test
    public void test_invalid_promotion() {
        //board with moved piece
        b_after = new cells[][] {
            {X, W, X, E, X, W, X, W},
            {W, X, W, X, W, X, W, X},
            {X, W, X, W, X, R, X, W},
            {W, X, E, X, E, X, R, X},
            {X, R, X, E, X, E, X, E},
            {E, X, E, X, E, X, R, X},
            {X, R, X, R, X, R, X, R},
            {R, X, R, X, R, X, R, X}};

        //Location of piece
        before_pos = new Position(4, 5);
        after_pos = new Position(3, 6);
        
        //master makes the board transition based on before and after positions
        master.createBoardTransition(before_pos, after_pos, gameBoard.getActiveColor());

        //isTriggered() -> returns false if there is no case of a piece being promoted
        assertFalse(CuT.isTriggered(b_before, b_after));
    }

    /**
     * Validates there is a case of a piece being promoted
     */
    @Test
    public void test_valid_promotion() {
        //board with moved piece
        b_after = new cells[][] {
            {X, W, X, R, X, W, X, W},
            {W, X, W, X, E, X, W, X},
            {X, W, X, W, X, E, X, W},
            {W, X, E, X, E, X, E, X},
            {X, R, X, E, X, R, X, E},
            {E, X, E, X, E, X, R, X},
            {X, R, X, R, X, R, X, R},
            {R, X, R, X, R, X, R, X}};

        //Location of piece
        before_pos = new Position(2, 5);
        after_pos = new Position(0, 3);
        
        //master makes the board transition based on before and after positions
        master.createBoardTransition(before_pos, after_pos, gameBoard.getActiveColor());

        //isTriggered() -> returns true if there is a case of a piece being promoted
        assertTrue(CuT.isTriggered(b_before, b_after));
    }

    /**
     * Validates there is a case of a piece being promoted
     */
    @Test
    public void test_action_to_promote() {
        //board with moved piece
        b_after = new cells[][] {
            {X, W, X, R, X, W, X, W},
            {W, X, W, X, E, X, W, X},
            {X, W, X, W, X, E, X, W},
            {W, X, E, X, E, X, E, X},
            {X, R, X, E, X, R, X, E},
            {E, X, E, X, E, X, R, X},
            {X, R, X, R, X, R, X, R},
            {R, X, R, X, R, X, R, X}};

        //Location of piece
        before_pos = new Position(2, 5);
        after_pos = new Position(0, 3);
        
        //master makes the board transition based on before and after positions
        master.createBoardTransition(before_pos, after_pos, gameBoard.getActiveColor());

        //isTriggered() -> returns true if there is a case of a piece being promoted
        if(CuT.isTriggered(b_before, b_after))
            CuT.action();
    }
}
