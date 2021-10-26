package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Position;

import java.util.ArrayList;

/**
 * RuleMaster is a singular object that handles all individual
 * rule triggers and actions.
 */
public class RuleMaster {
    ArrayList<Rule> ruleSet; // rules to scroll through on each action

    GameBoard board; // where the actual gameboard object is stored
    GameBoard.cells[][] b_before; // board grid before change
    GameBoard.cells[][] b_after; // board grid after change
    Position prevPos; // previous position of checker movement
    Position afterPos; // after position of checker movement

    /**
     * booleans to handled by rule objects
     * each name corresponds to an abstract rule
     */
    // turn level
    boolean invalidForwardJump;
    boolean invalidBackwardJump;
    boolean invalidBasicMove;
    boolean invalidKingMove;

    //game level
    boolean isGameOver;
    boolean white_win;
    boolean red_win;

    // basic constructor
    public RuleMaster(GameBoard currentBoard)

    {
        ruleSet = new ArrayList<>();
        board = currentBoard;

        // initialize game level bools
        isGameOver = false;
        white_win = false;
        red_win = false;

        // add all rules to the ruleset
        ruleSet.add(new BackwardJumpRule(this));
        ruleSet.add(new BasicMoveRule(this));
        ruleSet.add(new ForwardJumpRule(this));
        ruleSet.add(new KingMoveRule(this));
        ruleSet.add(new KingPromotionRule(this));
        ruleSet.add(new WinConditionRule(this));

    }

    public Position getPrevPos()
    {
        return prevPos;
    }

    public Position getAfterPos()
    {
        return afterPos;
    }

    /**
     * store board before and after states
     * to be used by individual rules
     * @param before position before checker was moved
     * @param after position after checker was moved
     */
    public void createBoardTransition(Position before, Position after)
    {
        prevPos = before;
        afterPos = after;

        // get board before/after states
        b_before = board.getBoard().clone();
        b_after = board.getBoard().clone();

        int prevCell = prevPos.getCell();
        int prevRow = prevPos.getRow();
        int afterCell = afterPos.getCell();
        int afterRow = afterPos.getRow();

        // switch position
        b_after[afterRow][afterCell] =  b_before[prevRow][prevCell];
        b_after[prevRow][prevCell] = GameBoard.cells.E;
    }

    /**
     * Add a rule to be triggered by the RuleMaster
     * @param r the rule in question
     */
    public void addRule(Rule r)
    {
        ruleSet.add(r);
    }

    /**
     * Modify the gameboard and do actions by triggering each rule in the ruleset
     * prereq: board has the current acting player's orientation on the bottom of
     * the board.
     * @return if the move is authorized to occur
     */
    public boolean triggerRuleSet()
    {
        // initialize turn level bools
        invalidForwardJump = false;
        invalidBackwardJump = false;
        invalidBasicMove = false;
        invalidKingMove = false;

        for(Rule r : ruleSet)
        {
            if (r.isTriggered(b_before, b_after))
            {
                r.action();
            }
        }

        System.out.println("validForwardJump = " + invalidForwardJump);
        System.out.println("validBackwardJump = " + invalidBackwardJump);
        System.out.println("validBasicMove = " + invalidBasicMove);
        System.out.println("validKingMove = " + invalidKingMove);

        // now to evaluate if this move is allowed
        return (!invalidForwardJump || !invalidBackwardJump || !invalidBasicMove || !invalidKingMove);
    }

    // Rule Helper Methods

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public void setWin(String player){
        if(player.equals("red player")){
            red_win=true;
            return;
        }
        if(player.equals("white player")){
            white_win=true;
            return;
        }
    }

    public GameBoard.cells[][] getB_before() {
        return b_before;
    }
}

