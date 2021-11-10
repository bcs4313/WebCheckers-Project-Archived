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

    /**
     * Post a command to sign into the PlayerLobby object,
     * entering their username into the server for processing.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   A string, depicting whether the sign in was successful or not
     */
    @Override
    public String handle(Request request, Response response) {

        final Session session = request.session();

        session.attribute(GetHomeRoute.ERROR_ATTR, false);
        final String username = request.queryParams(GetHomeRoute.USERNAME_ATTR);
        Player ply = new Player(username);
        //check to see if login is valid
        boolean attemptLogin = playerLobby.login(ply);
        if (attemptLogin) {
            //go to home page as signed-in user
            session.attribute(GetHomeRoute.USERNAME_ATTR, ply.getName()); // store username in client session
            response.redirect(WebServer.HOME_URL);
            return "success";
        } else{
            //go back to sign-in page with error msg
            session.attribute(GetHomeRoute.ERROR_ATTR, true);
            response.redirect(WebServer.SIGNIN_URL);
            return "error";
        }
        }

}

