package com.webcheckers.model;

import com.webcheckers.model.RuleSystem.RuleMaster;
import com.webcheckers.model.boardview.BoardView;

/**
 * Model that represents a real life checker board
 * combined with an online user session.
 * @author Cody Smith (bcs4313@rit.edu)
 */
public class GameBoard {
    // used to assign IDs to all gameboard objects
    static int universal_ID = 0;

    // handles all rules regarding the board state and various actions
    private RuleMaster master;

    // current state of the board players see
    BoardView currentView;

    // unique ID number of current game
    int gameID;

    // who is currently signed in?
    Player currentUser;

    // players playing the game at this moment
    Player redPlayer;
    Player whitePlayer;

    // whose turn is it currently?
    activeColors activeColor;

    boolean isGameOver;

    boolean red_win;

    boolean white_win;

    enum viewModes{
        PLAY,
        SPECTATOR,
        REPLAY
    }

    public enum activeColors{
        RED,
        WHITE
    }

    // other stats
    viewModes viewMode = viewModes.PLAY;

    /**
     * an enumeration of all the possible states of
     * a grid square in checkers
     * X = Can't be used, empty (white)
     * E = Empty Square (Black)
     * W = White Checker
     * R = Red Checker
     * WK = White King
     * RK = Red King
      */
    // a grid square in checkers
    public enum cells {
        X,
        E,
        W,
        R,
        WK,
        RK
    }

    // define spaces for simplicity
    private static final cells X = cells.X;
    private static final cells E = cells.E;
    private static final cells W = cells.W;
    private static final cells R = cells.R;
    private static final cells WK = cells.WK;
    private static final cells RK = cells.RK;

    // a matrix representing a checker board
    cells[][] board;

    /**
     * Constructor for a GameBoard
     * @param sender who sent the game request?
     * @param receiver who accepted the request?
     */
    public GameBoard(Player sender, Player receiver)
    {
        currentUser = sender;

        // construct a starting board
        board = new cells[][]{
                {X, W, X, W, X, W, X, W},
                {W, X, W, X, W, X, W, X},
                {X, W, X, W, X, W, X, W},
                {E, X, E, X, E, X, E, X},
                {X, E, X, E, X, E, X, E},
                {R, X, R, X, R, X, R, X},
                {X, R, X, R, X, R, X, R},
                {R, X, R, X, R, X, R, X}};

        // assign game a unique ID
        gameID = universal_ID;
        universal_ID++; // update for next game

        // assign red and white player
        redPlayer = sender;
        whitePlayer = receiver;

        // assign red to go first
        activeColor = activeColors.RED;

        // create rule system
        master = new RuleMaster(this);
    }

    /**
     * Overloaded Constructor for boards that are not in the initial state
     * @param sender who sent the game request?
     * @param receiver who accepted the request?
     * @param baseBoard what is the initial state of this board?
     * @param color whose turn is it currently?
     */
    public GameBoard(Player sender, Player receiver, cells[][] baseBoard, activeColors color)
    {
        currentUser = sender;
        redPlayer = sender;
        whitePlayer = receiver;
        this.board = baseBoard; // now change the board to a new state
        this.activeColor = color;
        master = new RuleMaster(this);
    }

    /**
     * Convert the board into a much stranger looking
     * view object to display on game.ftl.
     * @return a view object to display to the user
     */
    public BoardView toBoardView()
    {
        currentView = new BoardView(this.board);

        return currentView;
    }

    /**
     * Flips the current board for the white player
     * to view their pieces on their side
     *
     * @return a flipped board to display to the opponent
     */

    public GameBoard flipBoard() {
        cells[][] boardFlipped = new cells[8][8];

        // copy a board, raw
        for(int i = 0; i <= 7; i++) {
            // for each piece
            // if the piece is red, change to white
            System.arraycopy(board[i], 0, boardFlipped[i], 0, boardFlipped[i].length);
        }

        // for each row in the bottom half of the board
        for(int i = 0; i <= 7; i++) {
            // for each piece
            for(int j = 0; j < boardFlipped[i].length; j++) {
                // if the piece is red, change to white
                boardFlipped[i][j] = board[7 - i][7 - j];
            }
        }

        return new GameBoard(this.redPlayer, this.whitePlayer, boardFlipped, getActiveColor());
    }

    /**
     * get the current layout of the board
     * @return cells[][], the 2D array representation of the board
     */
    public cells[][] getBoard(){
        return board;
    }

    /**
     * Sets a new board state, along with the master's
     * before and after state
     * @param board cell matrix to incorporate
     */
    public void setBoard(cells[][] board){
        this.board = board;
        master.setBoards(board);
    }
    /**
     * simulates the end of a turn being made.
     * if the current active color is RED, switch it to
     * WHTIE, and vice versa.
     */
    public void switchActiveColor(){
        if (this.activeColor.equals(activeColors.RED)){
            this.activeColor = activeColors.WHITE;
        }
        else{
            this.activeColor = activeColors.RED;
        }
    }

    /**
     * Get the rule system/state of this board in particular
     * @return RuleMaster class
     */
    public RuleMaster getMaster()
    {
        return master;
    }

    /**
     * getter for the GameBoard's red player
     * @return the red player
     */
    public Player getRedPlayer(){
        return this.redPlayer;
    }

    /**
     * getter for the GameBoard's white player
     * @return the white player
     */
    public Player getWhitePlayer(){
        return this.whitePlayer;
    }

    /**
     * getter for the GameBoard's active color
     * @return the current active color (player)
     */
    public activeColors getActiveColor(){
        return this.activeColor;
    }

    public int getGameID(){
        return this.gameID;
    }
}
