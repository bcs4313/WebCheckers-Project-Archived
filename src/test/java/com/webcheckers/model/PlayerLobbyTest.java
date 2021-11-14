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

    /**
     * creates the CuT before each test
     */
    @BeforeEach
    public void setup() {
        CuT = new PlayerLobby();
    }

    /**
     * Tests cases where the login method should return a failure
     */
    @Test
    public void loginSystemFails() {
        player = new Player("   "); //INVALID USERNAME: no alphanumeric

        // check if login was a failure
        assertNull(CuT.login(player));
        assertFalse(CuT.getUsernameMap().containsKey(player.getName()));

        player = new Player(""); //INVALID USERNAME: 0 char long

        // check if login was a failure
        assertNull(CuT.login(player));
        assertFalse(CuT.getUsernameMap().containsKey(player.getName()));
    }

    /**
     * Test case where the CuT attempts login of a null player
     */
    @Test
    public void noPlayerLoginTest() {
        //Give CuT null player to login
        assertThrows(NullPointerException.class, () -> CuT.login(null));
        assertFalse(CuT.getUsernameMap().containsKey(null));
    }

    /**
     * Test cases where there should be successful login attempts
     */
    @Test
    public void loginSystemSuccesses() {
        //Valid Username with alphanumerics
        player = new Player("Hi1");
        assertSame(CuT.login(player), player);
        assertTrue(CuT.getUsernameMap().containsKey(player.getName()));
        assertEquals(player, CuT.getPlayer(player.getName()));
        CuT.logout(player.getName());
        assertFalse(CuT.getUsernameMap().containsKey(player.getName()));

        //Valid Username with alphanumerics and spaces
        player = new Player("my Name is");
        assertSame(CuT.login(player), player);
        assertTrue(CuT.getUsernameMap().containsKey(player.getName()));
        assertEquals(player, CuT.getPlayer(player.getName()));
        CuT.logout(player.getName());
        assertFalse(CuT.getUsernameMap().containsKey(player.getName()));

    }
}
