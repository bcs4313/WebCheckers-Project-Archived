package com.webcheckers.model;

import java.util.HashMap;
import java.util.Objects;

/**
 * A relatively simple model that stores the player's
 * name and verification status.
 * @author Cody Smith (bcs4313@rit.edu)
 */
public class Player {

    // identifies if a player is logged in or not
    private boolean isVerified;

    // is the player busy in a game right now?
    private boolean inGame;
    private final String name;
    private Player opponent;

    private boolean resigned;
    private GameBoard playersGame;

    /**
     * Basic Constructor for a Player
     * @param username name of the player
     */
    public Player(String username)
    {
        // null safety
        this.name = Objects.requireNonNullElse(username, "MISSING NAME");
        this.playersGame = null;
        this.isVerified = false;
        this.inGame = false;
        this.resigned = false;
    }

    public String getName()
    {
        return this.name;
    }

    // is the user logged in?
    public boolean isVerified()
    {
        return this.isVerified;
    }

    // is the user currently playing a game?
    public boolean isInGame()
    {
        return inGame;
    }

    /**
     * Set verification status of player object
     * (if their session is still active)
     * @param status is the player logged in or not?
     */
    public void setVerified(boolean status)
    {
        isVerified = status;
    }

    // set the in game status for the player
    public void setInGame(boolean status, GameBoard game)
    {
        this.playersGame = game;
        inGame = status;
    }

    public void removeGame(){
        this.playersGame = null;
    }

    public void removeOpponent(){
        this.opponent = null;
    }
    // set the opponent assigned to this player,
    //note: used to initiate a game for another user if
    // an opponent starts a game for them
    public void setOpponent(Player opponent){ this.opponent = opponent; }

    public void setResigned(boolean status){ this.resigned = status; }
    // get the opponent player, returns null if no one has
    // started a match with this player
    public Player getOpponent(){ return this.opponent; }

    public boolean getResigned(){
        return this.resigned;
    }

    public GameBoard getGame(){
        return this.playersGame;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
