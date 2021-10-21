package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Objects;

public class PostSignOutRoute implements Route {
    private final PlayerLobby playerLobby; // needed for the sign out process
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
    public PostSignOutRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
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

        final String username = session.attribute(GetHomeRoute.USERNAME_ATTR);
        HashMap<String, Player> usernameMap = this.playerLobby.getUsernameMap();

        usernameMap.remove(username);
        session.removeAttribute(GetHomeRoute.USERNAME_ATTR);

        response.redirect(WebServer.HOME_URL);

        return null;

    }
}
