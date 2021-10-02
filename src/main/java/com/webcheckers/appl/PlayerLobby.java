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

    public PlayerLobby()
    {
        LOG.fine("New player services instance created.");

        // create a new username hashmap
        usernameMap = new HashMap<>();
    }

    /**
     * Attempts to sign in a user by adding their name
     * to the username list.
     * @param player a player object containing the name
     * @return if the login was successful
     */
    public boolean login(Player player)
    {
        Set<String> usernameList = usernameMap.keySet();
        if(usernameList.contains(player.getUsername()) && !player.getUsername().equals("MISSING NAME"))
        {
            usernameMap.put(player.getUsername(), player);
            player.setVerified(true);
            return true;
        }
        else
        {
            return false;
        }
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
