package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;

import java.util.ArrayList;

/**
 * RuleMaster is a singular object that handles all individual
 * rule triggers and actions.
 */
public class RuleMaster {
    ArrayList<Rule> ruleSet; // rules to scroll through on each action

    GameBoard b_before; // stored before state
    GameBoard b_after; // stored after state
    boolean isGameOver;
    boolean white_win;
    boolean red_win;

    // basic constructor
    public RuleMaster()
    {
        ruleSet = new ArrayList<>();
    }

    /**
     * store board before and after states
     * to be used by individual rules
     */
    public void createBoardTransition(GameBoard before, GameBoard after)
    {
        b_before = before;
        b_after = after;
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
     */
    public void triggerRuleSet()
    {
        for(Rule r : ruleSet)
        {
            if (r.isTriggered(b_before, b_after))
            {
                r.action(b_before);
            }
        }
    }

    // Rule Helper Methods

    /**
     * Examines entire board to see if a checker has moved, if so,
     * return it.
     * @return binary list of new position {y, x} or null
     */
    public MovementPair identifyMovement()
    {
        for(int y = 0; y < 8; y++)
        {
            for(int x = 0; x < 8; x++)
            {
                int[] movement = identifyMovementHelper(y, x);
                if(movement != null)
                {
                    MovementPair mp = new MovementPair();
                    mp.before_y = y;
                    mp.before_x = x;
                    mp.after_y = movement[0];
                    mp.after_x = movement[1];
                    return mp;
                }
            }
        }
        return null;
    }

    /**
     * Examines a location to see if a checker has moved from that position.
     * @param start_y initial position
     * @param start_x end position
     * @return binary list of new position {y, x}
     */
    public int[] identifyMovementHelper(int start_y, int start_x)
    {
        // These two arrays run in parallel to identify possible movements
        int[] yShifts = {-1,-1, -2, 2,  1, 1, 2, 2};
        int[] xShifts = {-1, 1, -2, 2, -1, 1,-2, 2};

        GameBoard.cells[][] c_before = b_before.getBoard();
        GameBoard.cells[][] c_after = b_after.getBoard();

        // check to see if original position is empty, if not then the movement
        // clearly has never occurred
        if(c_before[start_y][start_x] != GameBoard.cells.E)
        {
            return null;
        }

        for(int i = 0; i < yShifts.length; i++)
        {
            int y = yShifts[i];
            int x = xShifts[i];
            // first check for a nonempty space
            if (c_before[y][x] != GameBoard.cells.E) {
                // now see if the beforeBoard and afterBoard states are different, if so. return
                if(c_before[y][x] == c_after[y][x])
                {
                    return new int[]{y,x};
                }
            }
        }

        return null;
    }

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
