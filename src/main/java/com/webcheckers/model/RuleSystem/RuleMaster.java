package com.webcheckers.model.RuleSystem;

import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;

import java.util.ArrayList;

/**
 * RuleMaster is a singular object that handles all individual
 * rule triggers and actions.
 */
public class RuleMaster {
    ArrayList<Rule> ruleSet; // rules to scroll through on each action

    private GameBoard board; // where the actual gameboard object is stored
    private int moveCounter; // used to force initial jumps in checkers
    private GameBoard.cells[][] b_init = new GameBoard.cells[8][8]; // board grid at turn start
    private GameBoard.cells[][] b_before; // board grid before change
    private GameBoard.cells[][] b_after; // board grid after change
    private Position prevPos; // previous position of checker movement
    private Position afterPos; // after position of checker movement

    private MoveLog moveLog; // object that stores previous moves a player did (turn basis)
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
        moveCounter = 0;
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
        for(int y = 0; y < b_before.length; y++)
        {
            System.arraycopy(b_before[y], 0, b_init[y], 0, b_init[y].length);
        }

        // log old position into memory
        moveLog.addMovement(b_before);

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
     * Get whose turn it is from the gameBoard
     * @return color of user currently in a turn
     */
    GameBoard.activeColors getTurn()
    {
        return board.getActiveColor();
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

        // works with illegal jumpChain movements
        // == basic movements after jump
        if(chainer.jumpChains.size() > 0)
        {
            if(!invalidBasicMove || !invalidKingMove)
            {
                System.out.println("ic1");
                return false;
            }
        }

        // works with illegal jumpChain movements
        // == jump after basic movement
        if(moveLog.positionStack.size() - chainer.jumpChains.size() > 1)
        {
            if(!invalidForwardJump || !invalidBackwardJump)
            {
                System.out.println("ic2");
                return false;
            }
        }

        // handles multiple basic movements in general
        if(moveLog.positionStack.size() - chainer.jumpChains.size() > 1)
        {
            System.out.println("illegal move to jump ratio");
            return false; // in no case should this be true
        }

        // gate for forced jumps at start of turn
        if(moveCounter == 0) // if this is the first move done so far
        {
            System.out.println("first move");
            InitJumpRule rule = new InitJumpRule(this, board.getActiveColor(), null);
            if(rule.isTriggered(b_init, null)) // if a person can jump
            {
                System.out.println("first move is illegal__GATE");
                if(invalidBackwardJump && invalidForwardJump) {
                    System.out.println("first move is illegal");
                    return false; // the move must be illegal
                }
            }
        }

        // log a jump in the chainer if a jump was allowed
        if(!invalidBackwardJump || !invalidForwardJump)
        {
            chainer.logJump(afterPos, prevPos);
        }

        // now to evaluate if this move is allowed
        if((!invalidForwardJump || !invalidBackwardJump || !invalidBasicMove || !invalidKingMove))
        {
            moveCounter += 1; // increment movement counter
            return true;
        } // this section checks if an illegal post-chain move has been made
        else
        {
            // force movement undo
            b_after = b_before;
            board.setBoard(b_before);

            return false;
        }
    }

    // Rule Helper Methods

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    /**
     * Sets board before and after states
     * @param board cell matrix to change
     */
    public void setBoards(GameBoard.cells[][] board)
    {
        this.b_before = board;
        this.b_after = board;
        for(int y = 0; y < board.length; y++)
        {
            System.arraycopy(board[y], 0, b_init[y], 0, board[y].length);
        }
    }

    /**
     * Reset the movement counter. Makes the RuleMaster
     * force any possible jump movements at the start of
     * a turn again.
     */
    public void resetCounter()
    {
        moveCounter = 0;
    }

    /**
     * Reduces the movement counter by 1.
     * Used for undoing movements.
     */
    public void lowerCounter()
    {
        moveCounter -= 1;
    }

    /**
     * Set the player to a win state
     * @param player string identity of player
     */
    public void setWin(String player){
        if(player.equals("red player")){
            red_win=true;
        }
        if(player.equals("white player")){
            white_win=true;
        }
    }

    public GameBoard.cells[][] getB_before() {
        return b_before;
    }

    public boolean getGameOver(){
        return isGameOver;
    }

    public Player getWinner(){
        if (red_win){
            return board.getRedPlayer();
        }
        else{
            return board.getWhitePlayer();
        }
    }
}

