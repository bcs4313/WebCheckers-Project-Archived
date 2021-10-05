package com.webcheckers.model;

/**
 * An entity that manages info regarding
 * a user requesting to start a match.
 * @author Cody Smith (bcs4313@rit.edu)
 */
public class Request {
    // the person who sent the request
    private final Player sender;

    // the person receiving this request
    private final Player receiver;

    // list of states the request could be in
    public enum states {
        PENDING,
        APPROVED,
    }

    // current state of this individual request
    private states state;

    /**
     * Constructor for a request object
     * @param sender the person who sent the request
     * @param receiver the person receiving this request
     */
    public Request(Player sender, Player receiver)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.state = states.PENDING;
    }

    // gets
    public states getState() { return state; }

    public Player getSender() { return sender; }

    public Player getReceiver() { return receiver; }

    // sets
    public void setState(states state) { this.state = state; }
}
