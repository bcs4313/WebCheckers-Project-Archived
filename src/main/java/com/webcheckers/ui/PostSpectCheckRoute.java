package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.SessionManager;
import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;

/**
 * the GET /spectator/checkTurn route handler.
 * @author Triston Lincoln
 */
public class PostSpectCheckRoute implements Route {
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final SessionManager sessionManager;

    /**
     * The constructor for the POST /spectator/checkTurn route handler.
     *
     * @param templateEngine - template engine to use for rendering HTML page
     * @param playerLobby
     *  the manager of all players in the application
     * @param sessionManager
     *  manages all games in the application
     * @throws NullPointerException
     *    when the playerLobby or templateEngine parameter is null
     */
    PostSpectCheckRoute(TemplateEngine templateEngine, SessionManager sessionManager, PlayerLobby playerLobby) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(playerLobby, "templateEngine must not be null");
        Objects.requireNonNull(sessionManager, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        this.sessionManager = sessionManager;
    }

    /**
     * Post a command to check if a player is on their turn,
     * assuming its not their turn currently.
     * If this is so, post a gson to switch players.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   gson object to interpret with AJAX
     */
    @Override
    public String handle(Request request, Response response) {
        final Session session = request.session();

        String id = request.queryParams("gameID");
        String username = session.attribute(GetHomeRoute.USERNAME_ATTR);

        Player currentUser = this.playerLobby.getPlayer(username);
        GameBoard game = sessionManager.retrieveSession(Integer.parseInt(id));

        Gson gson = new Gson();
        if (game.getWhitePlayer().getName().equals(currentUser.getName())) {
            if (game.getActiveColor().equals(GameBoard.activeColors.WHITE)){
                return gson.toJson(Message.info("true"));
            }
        }
        else{
            if (game.getActiveColor().equals(GameBoard.activeColors.RED)){
                return gson.toJson(Message.info("true"));
            }
        }
        return gson.toJson(Message.info("false"));
    }
}
