package com.webcheckers.appl;

import com.webcheckers.model.GameBoard;

import java.util.HashMap;

/**
 * Class that stores all game sessions within it.
 * Sessions may be removed, added, or retrieved via ID.
 */
public class SessionManager {
    // Map of gameID -> GameBoard object, stores
    // all concurrent games
    private HashMap<Integer, GameBoard> gameBoardMap;

    /**
     * Simple constructor that initializes the hashmap
     */
    public SessionManager()
    {
        gameBoardMap = new HashMap<>();
    }

    /**
     * Retrieve a session by its game ID
     * @return GameBoard object that stores all aspects
     * of a checker game.
     */
    public GameBoard retrieveSession(int gameID)
    {
        return gameBoardMap.get(gameID);
    }

    /**
     * Add a game session with a specified id number
     * @param gameID id number of the game
     * @param session GameBoard object players play on
     */
    public void addSession(int gameID, GameBoard session)
    {
        System.out.println("added session");
        gameBoardMap.put(gameID, session);
    }

    /**
     * Remove game session from the server
     * @param gameID id of game to remove
     */
    public void removeSession(int gameID)
    {
        gameBoardMap.remove(gameID);
    }

    public HashMap<Integer, GameBoard> getGameBoardMap(){
        return this.gameBoardMap;
    }
}
