package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import spark.*;

import java.util.Objects;

/**
 * The {@code POST /signOut} route handler.
 *
 * @author Triston Lincoln (trl6895@rit.edu)
 */
public class PostSignOutRoute implements Route {
    private final PlayerLobby playerLobby; // needed for the sign out process
    private final TemplateEngine templateEngine;

    /**
     * The constructor for the @code POST /signout route handler.
     *
     * @param playerLobby - contains login info. Aids in signing in a user.
     * @param templateEngine - template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *    when the playerLobby or templateEngine parameter is null
     */
    public PostSignOutRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    /**
     * Post a command to remove a user from the lobby, so
     * they can sign in again.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   null
     */
    @Override
    public String handle(Request request, Response response) {

        final Session session = request.session();

        final String username = session.attribute(GetHomeRoute.USERNAME_ATTR);
        this.playerLobby.logout(username);

        // remove player from UsernameMap
        session.removeAttribute(GetHomeRoute.USERNAME_ATTR);

        response.redirect(WebServer.HOME_URL);

        return null;

    }
}
