package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.GameBoard;
import com.webcheckers.model.Player;
import com.webcheckers.ui.boardview.BoardView;
import com.webcheckers.util.Message;
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
    public Object handle(Request request, Response response) {
        System.out.println("Post Check Turn");
        final Session session = request.session();

        String id = request.queryParams("gameID");
        String test = request.queryParams("actionData");
        System.out.println(id);
        String username = session.attribute(GetHomeRoute.USERNAME_ATTR);

        Player currentUser = this.playerLobby.getPlayer(username);
        GameBoard game = currentUser.getGame();
        System.out.println(game.getGameID());
        Gson gson = new Gson();
        if (game.getWhitePlayer().equals(currentUser)) {
            if (game.getActiveColor().equals(GameBoard.activeColors.WHITE)){
                response.redirect(WebServer.GAME_URL);
                return gson.toJson(Message.info("true"));
            }
        }
        else{
            if (game.getActiveColor().equals(GameBoard.activeColors.RED)){
                response.redirect(WebServer.GAME_URL);
                return gson.toJson(Message.info("true"));
            }
        }
        return gson.toJson(Message.info("false"));
    }
}
