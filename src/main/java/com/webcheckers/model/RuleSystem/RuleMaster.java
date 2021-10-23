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
