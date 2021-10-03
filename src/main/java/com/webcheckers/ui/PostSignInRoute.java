package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
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
    //object with the player list and login methods.
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    /**
     * The constructor for the POST /signin route handler.
     *
     * @param playerLobby - contains login info. Aids in signing in a user.
     * @param templateEngine - template engine to use for rendering HTML page
     *
     */
    PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    /**
     * handles the sign in process when the user presses login on the login page.
     * @param request - request object
     * @param response - response object
     * @return - null, returns the user to the home page logged in
     */
    @Override
    public String handle(Request request, Response response) {

        final Session session = request.session();

        final String username = request.queryParams("username");
        boolean attemptLogin = playerLobby.login(new Player(username));
        if (attemptLogin) {
            response.redirect(WebServer.HOME_URL);
        } else{
            response.redirect(WebServer.HOME_URL);
        }

        return null;
        }

}

