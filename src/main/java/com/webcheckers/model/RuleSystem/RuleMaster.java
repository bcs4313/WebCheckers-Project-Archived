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

    boolean isGameOver;
    boolean white_win;
    boolean red_win;

    // basic constructor
    public RuleMaster(GameBoard currentBoard)

    {
        ruleSet = new ArrayList<>();
        board = currentBoard;

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
     */
    public void triggerRuleSet()
    {
        for(Rule r : ruleSet)
        {
            if (r.isTriggered(b_before, b_after))
            {
                r.action();
            }
        }
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
}
