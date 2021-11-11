package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.SessionManager;
import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;

public class PostCheckTurn implements Route {
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final SessionManager sessionManager;
    /**
     * The constructor for the POST /checkTurn route handler.
     *
     * @param templateEngine - template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *    when the playerLobby or templateEngine parameter is null
     */
    public PostCheckTurn(TemplateEngine templateEngine, PlayerLobby playerLobby, SessionManager sessionManager) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
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
    public Object handle(Request request, Response response) {
        final Session session = request.session();

        String id = request.queryParams(GetGameRoute.ID_ATTR);
        String username = session.attribute(GetHomeRoute.USERNAME_ATTR);

        Player currentUser = this.playerLobby.getPlayer(username);
        GameBoard game = sessionManager.retrieveSession(Integer.parseInt(id));

        Gson gson = new Gson();
        if (game.getWhitePlayer().getName().equals(currentUser.getName())) {
            if (game.getActiveColor().equals(GameBoard.activeColors.WHITE)){
                //change turn to white player if active color is white
                return gson.toJson(Message.info("true"));
            }
        }
        else{
            if (game.getActiveColor().equals(GameBoard.activeColors.RED)){
                //change turn to red player if active color is red
                return gson.toJson(Message.info("true"));
            }
        }
        return gson.toJson(Message.info("false"));
    }
}
