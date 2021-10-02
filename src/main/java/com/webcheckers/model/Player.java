package com.webcheckers.model;

import java.util.Objects;

/**
 * A relatively simple model that stores the player's
 * name.
 */
public class Player {

    // identifies if a player is logged in or not
    private boolean isVerified;
    private final String username;

    /**
     * Basic Constructor for a Player
     * @param username name of the player
     */
    public Player(String username)
    {
        this.username = Objects.requireNonNullElse(username, "MISSING NAME");
        this.isVerified = false;
    }

    public String getUsername()
    {
        return this.username;
    }

    public boolean isVerified()
    {
        return this.isVerified;
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
}
