package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Player;
import com.webcheckers.ui.boardview.BoardView;
import spark.*;

import java.util.Objects;

public class PostCheckTurn implements Route {
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    /**
     * The constructor for the POST /checkTurn route handler.
     *
     * @param templateEngine - template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *    when the playerLobby or templateEngine parameter is null
     */
    PostCheckTurn(TemplateEngine templateEngine, PlayerLobby playerLobby) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    @Override
    public String handle(Request request, Response response) {

        final Session session = request.session();

        String username = session.attribute(GetHomeRoute.USERNAME_ATTR);
        Player redPlayer = session.attribute("redPlayer");
        Player whitePlayer = session.attribute("whitePlayer");
        GameBoard.activeColors activeColor = session.attribute("activeColor");
        Player currentUser = this.playerLobby.getPlayer(username);

        if (whitePlayer.equals(currentUser)) {
            if (activeColor.equals(GameBoard.activeColors.WHITE)){
                response.redirect(WebServer.GAME_URL);
                return "true";
            }
        }
        else{
            if (activeColor.equals(GameBoard.activeColors.RED)){
                response.redirect(WebServer.GAME_URL);
                return "true";
            }
        }
        response.redirect(WebServer.GAME_URL);
        return "false";
    }
}
