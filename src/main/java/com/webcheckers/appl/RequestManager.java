package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.model.Request;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Application tier Object responsible for storing and managing
 * requests made by players in the server.
 * @author Cody Smith (bcs4313@rit.edu)
 */
public class RequestManager {
    // server level lobby object this request points to
    // used for request validation.
    PlayerLobby lobby;

    // Map of Player Requests Received
    HashMap<String, ArrayList<Request>> requestMap;

    /**
     * Send a request to a particular player. Creates
     * a request object and stores it inside the manager
     * for retrieval.
     * @param sender player that is sending the request
     * @param receiver player that is getting the request
     * @return if the sending process was successful
     */
    public boolean sendRequest(Player sender, Player receiver)
    {
        // get name of receiver
        String receiverName = receiver.getUsername();

        // get request list
        ArrayList<Request> requestList = requestMap.get(receiverName);

        // create a new list if the user has no list to use.
        if(requestList == null)
        {
            requestMap.put(receiverName, new ArrayList<>());
            requestList = requestMap.get(receiverName);
        }

        // check for in game status
        if(!receiver.isInGame()) {
            // now to create a request to add to the list for this user
            Request req = new Request(sender, receiver);
            requestList.add(req);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Remove a request from the list of requests a
     * player receives.
     * @param sender the player who was denied
     * @param receiver the player who denied the sender
     */
    public void declineRequest(Player sender, Player receiver)
    {
        ArrayList<Request> requestList = requestMap.get(receiver.getUsername());
        for(int i = 0; i < requestList.size(); i++)
        {
            Request req = requestList.get(i);
            if(req.getSender().getUsername().equals(sender.getUsername()))
            {
                requestList.remove(i);
                return;
            }
        }
    }

    /**
     * Accept a request from a sender by changing the request state.
     * @implNote Use routes and requestSweep to check request acceptance
     * to determine when to start a game, not through this method.
     * @param sender the player who was accepted
     * @param receiver the player who accepted the sender
     */
    public void acceptRequest(Player sender, Player receiver)
    {
        ArrayList<Request> requestList = requestMap.get(receiver.getUsername());
        for (Request req : requestList) {
            if (req.getSender().getUsername().equals(sender.getUsername())) {
                req.setState(Request.states.APPROVED);
                return;
            }
        }
    }

    /**
     * Check to see if the player has an accepted request in their list
     * @param target the target player to check
     * @return an opponent if found, else null
     */
    public Player requestSweep(Player target)
    {
        ArrayList<Request> requestList = requestMap.get(target.getUsername());
        for (Request req : requestList) {
            if (req.getState() == Request.states.APPROVED) {
                target.setInGame(true);
                req.getSender().setInGame(true);
                return req.getSender();
            }
        }
        return null;
    }

    /**
     * Get all of the requests a player received
     * @param player player to get requests from
     */
    public ArrayList<Request> getRequests(Player player)
    {
        return requestMap.get(player.getUsername());
    }
}
