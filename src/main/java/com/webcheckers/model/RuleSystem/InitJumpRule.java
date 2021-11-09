package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;

public class InitJumpRule extends Rule {

    // handler for all basic rules
    private final RuleMaster master;

    // if not null, force ONLY a check at this position
    private int[] forceCheck;

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
            System.out.println("flipped board");
            b_input = flipBoard(b_before);
        }
        else
        {
            b_input = b_before;
        }


        if(forceCheck == null) {
            System.out.println("MULTICHECK");
            // we must check every possible situation on the board
            // (only for the team in question though)
            for (int y = 0; y < b_input.length; y++) // note that jumps in certain parts of
            {
                for (int x = 0; x < b_input[y].length; x++) {
                    GameBoard.cells cell = b_input[y][x];
                    if (cell == attackerKing || cell == attackerBasic) {
                        System.out.println("Potential Attacker identified: " + cell.toString());
                        System.out.println("At y: " + y + " x: " + x);
                        if (hasTarget(b_input, y, x)) {
                            System.out.println("Returning true...");
                            return true;
                        }
                    }
                }
            }
        }
        else
        {
            System.out.println("SINGLECHECK");
            int y = forceCheck[0];
            int x = forceCheck[1];
            GameBoard.cells cell = b_input[y][x];
            if (cell == attackerKing || cell == attackerBasic) {
                System.out.println("Potential Attacker identified: " + cell.toString());
                System.out.println("At y: " + y + " x: " + x);
                if (hasTarget(b_input, y, x)) {
                    System.out.println("Returning true...");
                    return true;
                }
            }
            else
            {
                System.out.println("Judging...");

                for(int yy = 0; yy < b_input.length; yy++)
                {
                    for(int xx = 0; xx < b_input[yy].length; xx++)
                    {
                        System.out.print(b_input[yy][xx] + ", ");
                    }
                    System.out.println();
                }
                System.out.println("???");
                System.out.println("found " + cell.toString() + " at y: " + y + " x: " + x);
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

        System.out.println("t1: " + target1.toString());
        System.out.println("t2: " + target2.toString());
        System.out.println("t3: " + target3.toString());
        System.out.println("t4: " + target4.toString());

        System.out.println("S0");
        // basic checks
        if(target1 == victimBasic || target1 == victimKing)
        {
            System.out.println("Scope1");
            if(b_before[Math.max(y - 2, 0)][Math.min(x + 2, 7)] == GameBoard.cells.E)
            {
                return true;
            }
        }
        if(target2 == victimBasic || target2 == victimKing)
        {
            System.out.println("Scope2");
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
                System.out.println("Scope3");
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
                System.out.println("Scope4");
                if(b_before[Math.min(y + 2, 7)][Math.max(x - 2, 0)] == GameBoard.cells.E)
                {
                    return true;
                }
            }
        }
        System.out.println("S1");
        return false;
    }


    // the return value of isTriggered is not used, so nothing should occur
    @Override
    public void action() {
        // not applicable
    }
}
