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
    enum states {
        PENDING,
        APPROVED,
        DENIED
    }

    // current state of this individual request
    private states state;

    /**
     * Constructor for a request object
     * @param sender the person who sent the request
     * @param receiver the person receiving this request
     */
    Request(Player sender, Player receiver)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.state = states.PENDING;
    }

    // gets
    states getState() { return state; }

    Player getSender() { return sender; }

    Player getReceiver() { return receiver; }

    // sets
    void setState(states state) { this.state = state; }
}
