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

    private GameBoard board; // where the actual gameboard object is stored
    private GameBoard.cells[][] b_before; // board grid before change
    private GameBoard.cells[][] b_after; // board grid after change
    private Position prevPos; // previous position of checker movement
    private Position afterPos; // after position of checker movement
    private Position victim; // potential victim of jump in move
    private GameBoard.cells victimColor; // color of most recently captured victim

    private MoveLog moveLog; // object that stores previous moves a player during their turn
    private Chainer chainer; // object that forces jump chains to occur

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
        chainer = new Chainer(this);
        moveLog = new MoveLog(this);
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
    public void createBoardTransition(Position before, Position after, GameBoard.activeColors color)
    {
        prevPos = before;
        afterPos = after;



        // get board before/after states
        b_before = board.getBoard().clone();
        b_after = board.getBoard().clone();

        int prevCell;
        int prevRow;
        int afterCell;
        int afterRow;
        if(color.equals(GameBoard.activeColors.RED)) {
            prevCell = prevPos.getCell();
            prevRow = prevPos.getRow();
            afterCell = afterPos.getCell();
            afterRow = afterPos.getRow();
        }
        else
        {
            prevCell = 7 - prevPos.getCell();
            prevRow = 7 - prevPos.getRow();
            afterCell = 7 - afterPos.getCell();
            afterRow = 7 - afterPos.getRow();
        }

        // switch position
        b_after[afterRow][afterCell] =  b_before[prevRow][prevCell];
        b_after[prevRow][prevCell] = GameBoard.cells.E;

        // log new movement into memory
        moveLog.addMovement(before);
    }

    /**
     * Retrieve previous board state
     * @return board state before movement
     */
    public GameBoard.cells[][] getB_Before()
    {
        return b_before;
    }

    /**
     * Retrieve new board state
     * @return board state after movement
     */
    public GameBoard.cells[][] getB_After()
    {
        return b_after;
    }

    /**
     * Get stack object of previous positions in turn
     * @return log of checker positions in turn
     */
    public MoveLog getLog()
    {
        return moveLog;
    }

    /**
     * Get object that works with forced implications
     * of chain jumps
     * @return chain jump object
     */
    public Chainer getChainer()
    {
        return chainer;
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

        // log a jump in the chainer if a jump was allowed
        if(!invalidForwardJump || !invalidBackwardJump)
        {
            System.out.println("victim = " + victim.toString());
            chainer.logJump(afterPos, victim, victimColor);
            board.getBoard()[victim.getRow()][victim.getCell()] = GameBoard.cells.E; // MURDER THE CHECKER
        }

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

    /**
     * set the most recent victim of a checker jump
     * @param loc coordinates of checker that was attacked
     * @param color color / king status of checker
     */
    public void setVictim(Position loc, GameBoard.cells color)
    {
        this.victim = loc;
        this.victimColor = color;
    }

    public GameBoard.cells[][] getB_before() {
        return b_before;
    }
}

