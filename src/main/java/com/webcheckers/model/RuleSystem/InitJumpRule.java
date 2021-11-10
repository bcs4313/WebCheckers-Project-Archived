package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;

/**
 * Rule that checks the entire board to see if a jump
 * is possible for the opponent. Used for jump
 * restrictions. Also engineered to check a singular
 * position in the case of handling chains.
 * @author Cody Smith (bcs4313@rit.edu)
 */
public class InitJumpRule extends Rule {

    // handler for all basic rules
    private final RuleMaster master;

    // if not null, force ONLY a check at this position
    private final int[] forceCheck;

    // Defined pieces to analyze
    private final GameBoard.cells attackerKing;
    private final GameBoard.cells attackerBasic;
    private final GameBoard.cells victimKing;
    private final GameBoard.cells victimBasic;

    /**
     * Enhanced Rule Constructor
     * @param master parent root of this Rule
     * @param teamTarget whose turn is it currently? (used for orientation)
     * @param forceCheck array [y, x] that if not null forces this rule to ONLY check here
     */
    public InitJumpRule(RuleMaster master, GameBoard.activeColors teamTarget, int[] forceCheck) {
        super(master);
        this.master = master;

        this.forceCheck = forceCheck;
        if(teamTarget == GameBoard.activeColors.WHITE)
        {
            attackerKing = GameBoard.cells.WK;
            attackerBasic = GameBoard.cells.W;
            victimKing = GameBoard.cells.RK;
            victimBasic = GameBoard.cells.R;
        }
        else
        {
            attackerKing = GameBoard.cells.RK;
            attackerBasic = GameBoard.cells.R;
            victimKing = GameBoard.cells.WK;
            victimBasic = GameBoard.cells.W;
        }
    }

    /**
     * Checks whether or not a jump CAN be performed,
     * start of a turn.
     * @param b_before board state to analyze
     * @param b_after special case, should be NULL
     * @return true if jump must be performed, false otherwise
     */
    @Override
    public boolean isTriggered(GameBoard.cells[][] b_before, GameBoard.cells[][] b_after) {
        GameBoard.cells[][] b_input;
        // flip board if its white's turn
        if(master.getTurn() == GameBoard.activeColors.WHITE)
        {
            b_input = flipBoard(b_before);
        }
        else
        {
            b_input = b_before;
        }

        if(forceCheck == null) {
            // we must check every possible situation on the board
            // (only for the team in question though)
            for (int y = 0; y < b_input.length; y++) // note that jumps in certain parts of
            {
                for (int x = 0; x < b_input[y].length; x++) {
                    GameBoard.cells cell = b_input[y][x];
                    if (cell == attackerKing || cell == attackerBasic) {
                        if (hasTarget(b_input, y, x)) {
                            return true;
                        }
                    }
                }
            }
        }
        else
        {
            int y = forceCheck[0];
            int x = forceCheck[1];
            GameBoard.cells cell = b_input[y][x];
            if (cell == attackerKing || cell == attackerBasic) {
                return hasTarget(b_input, y, x);
            }
        }
        return false;
    }

    /**
     * Checks to see if a checker has something to jump over
     * at its location.
     * @return Is there an opposing checker to jump over?
     */
    public boolean hasTarget(GameBoard.cells[][] b_before, int y, int x)
    {
        GameBoard.cells cell = b_before[y][x];
        // targets that anything can capture
        GameBoard.cells target1 = b_before[Math.max(y - 1, 0)][Math.min(x + 1, 7)];
        GameBoard.cells target2 = b_before[Math.max(y - 1, 0)][Math.max(x - 1, 0)];

        // targets that only kings can capture
        GameBoard.cells target3 = b_before[Math.min(y + 1, 7)][Math.min(x + 1, 7)];
        GameBoard.cells target4 = b_before[Math.min(y + 1, 7)][Math.max(x - 1, 0)];

        // basic checks
        if(target1 == victimBasic || target1 == victimKing)
        {
            if(b_before[Math.max(y - 2, 0)][Math.min(x + 2, 7)] == GameBoard.cells.E)
            {
                return true;
            }
        }
        if(target2 == victimBasic || target2 == victimKing)
        {
            if(b_before[Math.max(y - 2, 0)][Math.max(x - 2, 0)] == GameBoard.cells.E)
            {
                return true;
            }
        }

        // king checks
        if(target3 == victimBasic || target3 == victimKing)
        {
            if(cell == attackerKing)
            {
                if(b_before[Math.min(y + 2, 7)][Math.min(x + 2, 7)] == GameBoard.cells.E)
                {
                    return true;
                }
            }
        }
        if(target4 == victimBasic || target4 == victimKing)
        {
            if(cell == attackerKing)
            {
                return b_before[Math.min(y + 2, 7)][Math.max(x - 2, 0)] == GameBoard.cells.E;
            }
        }
        return false;
    }


    // the action() for this rule is not used, so nothing should occur
    @Override
    public void action() {
        // not applicable
    }
}
