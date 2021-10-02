package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import static spark.Spark.halt;

/**
 * The POST /signin route handler.
 * @author Carlos Hargrove
 */
public class PostSignInRoute implements Route {
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;


    /**
     * The constructor for the @code POST /signin route handler.
     *
     * @param playerLobby - contains login info.
     * @param templateEngine - template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *    when the playerLobby or templateEngine parameter is null
     */
    PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    //Todo: Finish method.
    @Override
    public String handle(Request request, Response response) {
        return null;
        }
    }


}
