package com.webcheckers.ui.model;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The unit test suite for the Player Class
 *
 * @author Carlos Hargrove
 */
@Tag("Model-tier")
public class PlayerTest {

        /**
         * Tests all aspects of creating a name.
         */
        @Test
        public void player_name() {
            final Player beta = new Player("beta");
            assertEquals(beta.getName(), "beta");

            final Player alpha = new Player(null);
            assertEquals(alpha.getName(),"MISSING NAME");
        }

        /**
        * Tests the verification status
        */
        @Test
        public void player_verification(){
            final Player beta = new Player("");
            assertEquals(beta.isVerified(),false);

            beta.setVerified(false);
            assertEquals(beta.isVerified(),false);

            beta.setVerified(true);
            assertEquals(beta.isVerified(),true);

            beta.setVerified(true);
            assertEquals(beta.isVerified(),true);

            beta.setVerified(false);
            assertEquals(beta.isVerified(),false);
        }

    /**
     * Tests the in game status.
     */
    @Test
        public void player_in_game(){
            final Player beta = new Player("beta");
            assertEquals(beta.isInGame(),false);

            beta.setInGame(false);
            assertEquals(beta.isInGame(),false);

            beta.setInGame(true);
            assertEquals(beta.isInGame(),true);

            beta.setInGame(true);
            assertEquals(beta.isInGame(),true);

            beta.setInGame(false);
            assertEquals(beta.isInGame(),false);
        }

    /**
     * Tests assigning an opp value
     */
    @Test
        public void player_opp(){
            final Player beta = new Player("beta");
            assertEquals(beta.getOpponent(),null);

            final Player alpha = new Player("alpha");
            beta.setOpponent(alpha);
            assertEquals(beta.getOpponent().getName(),"alpha");
        }

}

