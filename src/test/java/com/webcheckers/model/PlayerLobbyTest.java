package com.webcheckers.model;


import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Set of JUnit tests for the Player Lobby model
 * tier class
 *
 * @author Triston Lincoln (trl6895@rit.edu)
 */
@Tag("Model-Tier")
public class PlayerLobbyTest {

    // Component Under Test
    private PlayerLobby CuT;

    // Friendly Objects
    private Player player;

    @BeforeEach
    public void setup() {
        CuT = new PlayerLobby();
    }

    @Test
    public void loginSystemFails() {
        player = new Player("   "); //INVALID USERNAME: no alphanumeric

        // check if login was a failure
        assertFalse(CuT.login(player));
        assertFalse(CuT.getUsernameMap().containsKey(player.getName()));

        player = new Player(""); //INVALID USERNAME: 0 char long

        // check if login was a failure
        assertFalse(CuT.login(player));
        assertFalse(CuT.getUsernameMap().containsKey(player.getName()));
    }

    @Test
    public void noPlayerLoginTest() {
        //Give CuT null player to login
        assertThrows(NullPointerException.class, () -> CuT.login(null));
        assertFalse(CuT.getUsernameMap().containsKey(null));
    }

    @Test
    public void loginSystemSuccesses() {
        //Valid Username with alphanumerics
        player = new Player("Hi1");
        assertTrue(CuT.login(player));
        assertTrue(CuT.getUsernameMap().containsKey(player.getName()));
        assertEquals(player, CuT.getPlayer(player.getName()));
        CuT.logout(player.getName());
        assertFalse(CuT.getUsernameMap().containsKey(player.getName()));

        //Valid Username with alphanumerics and spaces
        player = new Player("my Name is");
        assertTrue(CuT.login(player));
        assertTrue(CuT.getUsernameMap().containsKey(player.getName()));
        assertEquals(player, CuT.getPlayer(player.getName()));
        CuT.logout(player.getName());
        assertFalse(CuT.getUsernameMap().containsKey(player.getName()));

    }
}
