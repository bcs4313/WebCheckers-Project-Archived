package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

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

    // A string of characters that are not allowed in
    // a username. A user cannot log in with these.
    private final String invalidChars = "~`!@#$%^&*()_+" +
            "=-|\\{}[]:;\"'?/>.<,'}";

    /**
     * Basic constructor. Just makes a username hashmap and logs creation.
     */
    public PlayerLobby()
    {
        LOG.fine("New player services instance created.");

        // create a new username hashmap
        usernameMap = new HashMap<>();
    }

    /**
     * Checks if the player username is fit for logging in
     * @param player the player to check
     * @return if the name fits syntax rules
     */
    private boolean verifyPlayerName(Player player)
    {
        String username = player.getUsername();
        if(username.equals("MISSING NAME")) { return false; }
        if(username.equals("")) { return false; }

        // check to see if ANY illegal character exists in
        // the username
        for(int i = 0; i < invalidChars.length(); i++)
        {
            String character = username.substring(i,i+1);
            if(username.contains(character))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Attempts to sign in a user by adding their name
     * to the username list.
     * @param player a player object containing the name
     * @return if the login was successful
     */
    public boolean login(Player player)
    {
        // get list via hashmap keyset
        Set<String> usernameList = usernameMap.keySet();

        // first we will do some syntax checks for username validation
        boolean isNameValid = verifyPlayerName(player);

        // if the name already exists they cannot login
        if(usernameList.contains(player.getUsername())) { return false; }
        if(!isNameValid) { return false; }

        // validation checks passed. We will now log in
        usernameMap.put(player.getUsername(), player);
        player.setVerified(true);
        return true;

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
        usernameMap.remove(ply.getUsername());
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
