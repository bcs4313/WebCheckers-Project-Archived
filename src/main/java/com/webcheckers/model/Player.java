package com.webcheckers.model;

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

    /**
     * Basic Constructor for a Player
     * @param username name of the player
     */
    public Player(String username)
    {
        // null safety
        this.name = Objects.requireNonNullElse(username, "MISSING NAME");
        this.isVerified = false;
        this.inGame = false;
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
    public void setInGame(boolean status)
    {
        inGame = status;
    }
}
