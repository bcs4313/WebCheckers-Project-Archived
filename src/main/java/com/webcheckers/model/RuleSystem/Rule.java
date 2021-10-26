package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;

public abstract class Rule {
    RuleMaster master; // master will handle all rule triggers concurrently

    public Rule(RuleMaster master)
    {
        this.master = master;
    }

    /**
     * Given two gameboards, one with the previous state and one
     * with the new state, will your rule trigger?
     * @return if your rule took action
     */
    public abstract boolean isTriggered(GameBoard.cells[][] b_before, GameBoard.cells[][] b_after);

    /**
     * Enter the action that your rule will take upon being
     * triggered.
     */
    public abstract void action();
}
