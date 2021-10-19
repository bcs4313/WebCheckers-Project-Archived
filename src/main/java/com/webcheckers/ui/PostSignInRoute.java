package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;

/**
 * The POST /signin route handler.
 * @author Carlos Hargrove
 */
public class PostSignInRoute implements Route {
    private final PlayerLobby playerLobby; // needed for the sign in process
    private final TemplateEngine templateEngine;


    /**
     * The constructor for the @code POST /signin route handler.
     *
     * @param playerLobby - contains login info. Aids in signing in a user.
     * @param templateEngine - template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *    when the playerLobby or templateEngine parameter is null
     */
    public PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    @Override
    public String handle(Request request, Response response) {

        final Session session = request.session();

        session.attribute("error", false);
        final String username = request.queryParams("username");
        Player ply = new Player(username);
        boolean attemptLogin = playerLobby.login(ply);
        if (attemptLogin) {
            session.attribute("username", ply.getName()); // store username in client session
            response.redirect(WebServer.HOME_URL);
            return "success";
        } else{
            session.attribute("error", true);
            response.redirect(WebServer.SIGNIN_URL);
            return "error";
        }
        }

}

