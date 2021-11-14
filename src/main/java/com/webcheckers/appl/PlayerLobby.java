package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.*;

/**
 * Application tier component responsible for
 * handling sign in actions.
 * @author Cody Smith (bcs4313@rit.edu)
 */
public class PlayerLobby {
    // create a logger for this server-based object
    private static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());

    // Map of username -> player object, stores
    // all logged-in users
    private HashMap<String, Player> usernameMap;
    private HashMap<String, Player> loggedOutMap;
    /**
     * Basic constructor. Just makes a username hashmap and logs creation.
     */
    public PlayerLobby()
    {
        LOG.fine("New player services instance created.");

        // create a new username hashmap
        usernameMap = new HashMap<>();
        loggedOutMap = new HashMap<>();
    }

    public HashMap<String, Player> getUsernameMap(){
        return usernameMap;
    }
    /**
     * Checks if the player username is fit for logging in
     * @param player the player to check
     * @return if the name fits syntax rules
     */
    private boolean verifyPlayerName(Player player)
    {
        String username = player.getName();
        if(username.equals("MISSING NAME")) { return false; }
        if(username.equals("")) { return false; }

        for (int i = 0; i < username.length(); i++){
            char j = username.charAt(i);
            String k = Character.toString(j);
            if (k.matches("[a-zA-Z0-9]")){
                return Pattern.matches("[a-zA-Z0-9 ]*", username);
            }
        }

        return false;
    }

    /**
     * Attempts to sign in a user by adding their name
     * to the username list.
     * @param player a player object containing the name
     * @return null if unsuccessful, a player object
     * if successful, player object handles async login
     */
    public Player login(Player player)
    {
        // get list via hashmap keyset
        Set<String> usernameList = usernameMap.keySet();

        // first we will do some syntax checks for username validation
        boolean isNameValid = verifyPlayerName(player);

        Player ply = loggedOutMap.get(player.getName());
        // if the name already exists they cannot login
        if(!isNameValid) { return null; } // in no case should the string formatting be invalid
        if(usernameList.contains(player.getName())) {
            // if the player that exists hasn't
            // logged out than we will deny the login
            if(ply == null)
            {
                return null;
            }
        }
        else if(ply != null)
        { // if the player has logged out, load the relevant player object
            loggedOutMap.remove(ply.getName());
            usernameMap.put(player.getName(), ply);
            ply.setVerified(true);
            return ply;
        }

        // validation checks passed. We will now log in
        usernameMap.put(player.getName(), player);
        player.setVerified(true);
        return player; // return newly created player

    }

    /**
     * Logs out a user by removing their name
     * from the user list.
     * @param username name of player to remove
     */
    public void logout(String username)
    {
        Player ply = getPlayer(username);
        ply.setVerified(false);
        usernameMap.remove(ply.getName());

        // asynchronously store this player's data for later
        loggedOutMap.put(ply.getName(), ply);
    }

    /**
     * Retrieve a player object from the lobby
     * by username.
     * @param username name of player
     */
    public Player getPlayer(String username)
    {
        return usernameMap.get(username);
    }
}
