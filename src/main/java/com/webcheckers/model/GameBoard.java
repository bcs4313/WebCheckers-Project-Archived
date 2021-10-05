package com.webcheckers.model;

import com.webcheckers.ui.boardview.BoardView;

/**
 * Model that represents a real life checker board
 * combined with an online user session.
 */
public class GameBoard {
    // used to assign IDs to all gameboard objects
    static int universal_ID = 0;

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

    enum viewModes{
        PLAY,
        SPECTATOR,
        REPLAY
    }

    enum activeColors{
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
    }

    public Player getRedPlayer(){
        return this.redPlayer;
    }

    public Player getWhitePlayer(){
        return this.whitePlayer;
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

    public void flipBoard() {
        cells[][] boardFlipped = this.board;

        for(int i = 0; i <= 2; i++) {
            for(int j = 0; j < boardFlipped[i].length; j++) {
                if(boardFlipped[i][j] == cells.W) {
                    boardFlipped[i][j] = cells.R;
                }
            }
        }

        for(int i = 5; i <= 7; i++) {
            for(int j = 0; j < boardFlipped[i].length; j++) {
                if(boardFlipped[i][j] == cells.R) {
                    boardFlipped[i][j] = cells.W;
                }
            }
        }
    }
}